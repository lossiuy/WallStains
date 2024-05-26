使用Flask框架，运行在8081端口
接口名process_image
参数以json格式，如：

````
{
    "image_path": "006.jpg"
}
````

返回值以json格式，如下：

````
{
   "res": [
      [
         0.1224522370453246,
         "./segment/res/warped_image1.jpg",
         "./segment/res/result1.jpg"
      ],
      [
         0.26087444976662955,
         "./segment/res/warped_image2.jpg",
         "./segment/res/result2.jpg"
      ]
   ]
}
````
第一个为污渍面积、第二个为分割块路径、第三个为识别结果路径

部署可以考虑将yolo识别结果不保存
污渍识别结果保存在E:/TJU/Soft_ThirdDown/VUE3/vue3/src下，Master_Result_Photo/mask_image.jpg为总览结果，Result_block/result*.jpg为单块污渍识别结果，Pri_block/warped_image*为分割结果（*代表序号）
如要更改路径，更改predict.py中的路径，将E:/TJU/Soft_ThirdDown/VUE3/vue3/src改到vue3前端文件夹所在的地址
![示例图片](image.png)