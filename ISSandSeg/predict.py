import shutil

from ultralytics import YOLO
import matplotlib.pyplot as plt
import cv2
import numpy as np
import os
import ISS


def sort(pts):
    pts = pts.reshape(-1, 2)
    # 基于x坐标进行排序
    sorted_x = pts[np.argsort(pts[:, 0]), :]
    # 最左边的两个点
    leftmost = sorted_x[:2, :]
    # 最右边的两个点
    rightmost = sorted_x[2:, :]
    if leftmost[0, 1] != leftmost[1, 1]:
        # 最左边两个点的y坐标不同时，按y坐标从小到大排序
        leftmost = leftmost[np.argsort(leftmost[:, 1]), :]
    else:
        # 最左边两个点的y坐标相同时，按x坐标从大到小排序
        leftmost = leftmost[np.argsort(leftmost[:, 0])[::-1], :]
    (tl, bl) = leftmost
    if rightmost[0, 1] != rightmost[1, 1]:
        # 最右边两个点的y坐标不同时，按y坐标从小到大排序
        rightmost = rightmost[np.argsort(rightmost[:, 1]), :]
    else:
        # 最右边两个点的y坐标相同时，按x坐标从大到小排序
        rightmost = rightmost[np.argsort(rightmost[:, 0])[::-1], :]
    (tr, br) = rightmost
    return np.array([tl, tr, br, bl], dtype="float32")

def find_mask_centroid(mask):
    """Find the centroid of a binary mask."""
    M = cv2.moments(mask)
    if M["m00"] != 0:
        cX = int(M["m10"] / M["m00"])
        cY = int(M["m01"] / M["m00"])
    else:
        cX, cY = 0, 0
    return (cX, cY)

def visualize_masks_on_image(save_path, image, masks, colors, alpha=0.2):
    overlay = image.copy()

    for i, (mask_data, color) in enumerate(zip(masks, colors)):
        mask_image = mask_data[:, :, 0] if mask_data.ndim == 3 else mask_data
        mask_image = mask_image.astype(bool)  # 将掩码转换为布尔类型

        # 创建一个彩色掩码
        mask_colored = np.zeros_like(image, dtype=np.uint8)
        mask_colored[mask_image] = color

        # 仅在掩码覆盖区域应用透明度叠加
        overlay[mask_image] = cv2.addWeighted(image[mask_image], 1 - alpha, mask_colored[mask_image], alpha, 0)

        # Find the centroid of the mask to place the number
        centroid = find_mask_centroid(mask_image.astype(np.uint8))
        # Put the mask number at the centroid
        cv2.putText(overlay, str(i + 1), centroid, cv2.FONT_HERSHEY_SIMPLEX, 10, (255, 255, 255), 2, cv2.LINE_AA)

    cv2.imwrite(save_path, overlay)
    # plt.imshow(cv2.cvtColor(overlay, cv2.COLOR_BGR2RGB))
    # plt.title('Colored Masks on Image')
    # plt.axis('off')
    # plt.show()


def predict(image_path):
    # image_id = '005'
    # train
    model = YOLO('best.pt')
    # image = plt.imread(image_path)

    abs_image_path = os.path.abspath(image_path)

    # Train the model
    results = model.predict(
        source=abs_image_path,
        save=True,  # 保存预测结果
        imgsz=640,  # 输入图像的大小，可以是整数或w，h
        conf=0.60,  # 用于检测的目标置信度阈值（默认为0.25，用于预测，0.001用于验证）
        iou=0.45,  # 非极大值抑制 (NMS) 的交并比 (IoU) 阈值
        show=False,  # 如果可能的话，显示结果
        project='runs/predict',  # 项目名称（可选）
        name='exp',  # 实验名称，结果保存在'project/name'目录下（可选）
        save_txt=False,  # 保存结果为 .txt 文件
        save_conf=False,  # 保存结果和置信度分数
        save_crop=False,  # 保存裁剪后的图像和结果
        show_labels=False,  # 在图中显示目标标签
        show_conf=True,  # 在图中显示目标置信度分数
        vid_stride=1,  # 视频帧率步长
        line_width=3,  # 边界框线条粗细（像素）
        visualize=False,  # 可视化模型特征
        augment=False,  # 对预测源应用图像增强
        agnostic_nms=False,  # 类别无关的NMS
        retina_masks=True,  # 使用高分辨率的分割掩码
        boxes=False,  # 在分割预测中显示边界框
    )
    return results


def get_res(results, image, image_path):
    ress = []
    if results is not None:
        num = 1
        colors = []
        masks = []
        for mask in results[0].masks:
            # Convert mask to single channel image
            mask_data = mask.cpu().data.numpy().transpose(1, 2, 0)
            # print(mask_data)

            # Execute contour detection
            contours, _ = cv2.findContours(mask_data.astype(np.uint8), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

            # 多边形逼近
            polygons = []
            corner_points = []  # 用于存储每个轮廓的四个角

            for contour in contours:
                # 计算轮廓的凸包
                hull = cv2.convexHull(contour)

                # 获取凸包的四个角
                if len(hull) >= 4:
                    points = cv2.approxPolyDP(hull, 0.02 * cv2.arcLength(hull, True), True)
                    if len(points) == 4:
                        corner_points.append(points)

            # 创建一个空白图像
            # output_image = np.zeros_like(mask_data)
            # print(corner_points)

            # 绘制四个角点并连接形成四边形
            # for points in corner_points:
            #     cv2.polylines(output_image, [points], isClosed=True, color=(255), thickness=2)
            #     cv2.fillPoly(output_image, [points], (255))
            # 可视化结果
            # plt.imshow(output_image, cmap='gray')
            # plt.colorbar()
            # plt.show()

            if corner_points:
                masks.append(mask_data)
                # 定义源点和目标点
                src_pts = np.float32(corner_points[0])
                src_pts = sort(src_pts)
                # print(src_pts)
                # 定义目标图像尺寸，可以根据分割结果的大小进行调整
                output_width = int(cv2.norm(src_pts[0], src_pts[1]))
                output_height = int(cv2.norm(src_pts[0], src_pts[3]))
                dst_pts = np.float32([[0, 0], [output_width, 0], [output_width, output_height], [0, output_height]])

                # 计算透视变换矩阵
                perspective_matrix = cv2.getPerspectiveTransform(src_pts, dst_pts)

                original_image = cv2.imread(image_path)
                # 应用透视变换
                warped_image = cv2.warpPerspective(original_image, perspective_matrix, (output_width, output_height))
                save_dir = f'E:/TJU/Soft_ThirdDown/VUE3/vue3/src'
                os.makedirs(save_dir, exist_ok=True)
                cv2.imwrite(f'{save_dir}/Pri_block/warped_image{num}.jpg', warped_image)
                res = float(ISS.process_image(f'{save_dir}/Pri_block/warped_image{num}.jpg', f'{save_dir}/Result_block/result{num}.jpg'))
                path = [res, f'/src/Pri_block/warped_image{num}.jpg', 'null' if res == -1 else f'/src/Result_block/result{num}.jpg']
                ress.append(path)
                if res == -1:
                    colors.append((0, 255, 0))
                elif res > 0.35:
                    colors.append((0, 0, 255))
                else:
                    colors.append((0, 255, 255))
                # 可视化结果
                # plt.imshow(warped_image)
                # plt.axis('off')

                # plt.show()
                num = num + 1
        visualize_masks_on_image('E:/TJU/Soft_ThirdDown/VUE3/vue3/src/Master_Result_Photo/mask_image.jpg', image, masks, colors)
    return ress
