import cv2
import numpy as np


def partition_image(image, num_rows, num_cols):
    height, width, channels = image.shape
    block_height = height // num_rows
    block_width = width // num_cols

    blocks = []
    for i in range(num_rows):
        for j in range(num_cols):
            block = image[i * block_height: (i + 1) * block_height, j * block_width: (j + 1) * block_width]
            blocks.append(block)

    return blocks


def calculate_mean_values(blocks):
    mean_values = []
    for block in blocks:
        mean_value = np.mean(block[:, :, 0])
        mean_values.append(mean_value)

    return mean_values


def process_image(image_path, save_path):
    # Read the image
    image = cv2.imread(image_path, cv2.IMREAD_COLOR)

    # Define parameters
    num_rows = 4
    num_cols = 4

    blocks = partition_image(image, num_rows, num_cols)
    mean_values = calculate_mean_values(blocks)
    variance_value = np.var(mean_values)
    res = "-1"
    if variance_value <= 40:
        print("-1")
    else:
        # Image Enhancement
        enhanced_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        denoised_image = cv2.fastNlMeansDenoising(enhanced_image, None, h=10, templateWindowSize=7, searchWindowSize=21)
        threshold_value = cv2.mean(denoised_image)[0]
        ret, binary_image = cv2.threshold(denoised_image, threshold_value, 255, cv2.THRESH_BINARY)

        # Add Transparency
        alpha = 0.5
        binary_bgr = cv2.cvtColor(binary_image, cv2.COLOR_GRAY2BGR)
        binary_with_alpha = cv2.addWeighted(binary_bgr, alpha, image, 1 - alpha, 0)

        # Calculate Black Pixel Ratio
        white_pixels = cv2.countNonZero(binary_image)
        total_pixels = binary_image.size
        black_ratio = 1 - white_pixels / total_pixels

        print(str(black_ratio * 100) + '%')
        res = black_ratio
        cv2.imwrite(save_path, binary_with_alpha)
    return res


if __name__ == "__main__":
    image_path = "image.jpg"
    process_image(image_path)
