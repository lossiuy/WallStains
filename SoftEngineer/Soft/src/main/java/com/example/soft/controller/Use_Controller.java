package com.example.soft.controller;

import com.example.soft.Entity.Photo;
import com.example.soft.list_to_json;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.soft.controller.Upload_Controller.resource;
import static com.example.soft.controller.Upload_Controller.resource_send;

@RestController
@RequestMapping()
public class Use_Controller {

    @CrossOrigin(origins = "*")
    @GetMapping("/tabledata")
    public String getTableData() {
        String folderPath = "E:\\TJU\\Soft_ThirdDown\\VUE3\\vue3\\src\\Pri_block";
        String folderPath2 = "E:\\TJU\\Soft_ThirdDown\\VUE3\\vue3\\src\\Result_block";
        // 调用方法删除文件夹中的所有文件
        deleteFolderContents(new File(folderPath));
        deleteFolderContents(new File(folderPath2));
        // Python后端接口的URL
        String pythonEndpointUrl = "http://localhost:8081/process_image";
        // 创建一个RestTemplate实例
        RestTemplate restTemplate = new RestTemplate();
        // 准备请求参数
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 设置请求体参数
        String requestBody ="{\"image_path\":\"E:\\\\TJU\\\\Soft_ThirdDown\\\\VUE3\\\\vue3\\\\src\\\\Master_Photo\\\\"+resource_send+"\"}";
        // 创建HttpEntity对象
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        // 发送POST请求到后端接口，并获取响应
        String response = restTemplate.postForObject(pythonEndpointUrl, requestEntity, String.class);
        System.out.println(response);


        List<Photo> tableData = new ArrayList<>();
//        String jsonString = "{\"res\":[[-1.0,\"null\"],[0.40028388238325097,\"/src/Pri_block/2.jpg\"],[-1.0,\"null\"],[-1.0,\"null\"]]}";
        // 创建Gson对象
        Gson gson = new Gson();
        // 将JSON字符串转换为对象
        Result Result_Python = gson.fromJson(response, Result.class);
        System.out.println(resource);
        int number_all=1;
        int num=1;
        for (List<Object> entry : Result_Python.getRes()) {
            tableData.add(new Photo(number_all, resource, "/src/Master_Result_Photo/mask_image.jpg", num, (String) entry.get(1), (String) entry.get(2), (double) entry.get(0)));
            num++;
        }
//        tableData.add(new Photo("1", "/src/assets/1.jpg", "/src/assets/1.jpg", "1", "/src/assets/2.jpg","/src/assets/2.jpg",1.5));
//        tableData.add(new Photo("1", "/src/assets/1.jpg", "/src/assets/1.jpg", "2", "/src/assets/2.jpg","/src/assets/2.jpg",2.5));
//        tableData.add(new Photo("1", "/src/assets/1.jpg", "/src/assets/1.jpg", "3", "/src/assets/2.jpg","/src/assets/2.jpg",3.5));
//        tableData.add(new Photo("1", "/src/assets/1.jpg", "/src/assets/1.jpg", "4", "/src/assets/2.jpg","/src/assets/2.jpg",4.5));
//        tableData.add(new Photo("1", "/src/assets/1.jpg", "/src/assets/1.jpg", "5", "/src/assets/2.jpg","/src/assets/2.jpg",5.5));
        list_to_json listToJson=new list_to_json();
        String result;
        result=listToJson.convertListToJson(tableData);

        return result;
    }

    public static void deleteFolderContents(File folder) {
// 判断文件夹是否存在
        if (folder.exists() && folder.isDirectory()) {
// 获取文件夹中的所有文件和子文件夹
            File[] files = folder.listFiles();
            if (files != null) {
// 循环遍历文件夹中的所有文件和子文件夹
                for (File file : files) {
                    if (file.isDirectory()) {
// 如果是子文件夹，则递归调用删除子文件夹中的内容
                        deleteFolderContents(file);
                    } else {
// 如果是文件，则直接删除
                        file.delete();
                    }
                }
            }
        }
    }
}

class Result {
    private List<List<Object>> res;

    public List<List<Object>> getRes() {
        return res;
    }

    public void setRes(List<List<Object>> res) {
        this.res = res;
    }
}



