package com.example.soft.controller;

import com.example.soft.Entity.History_Photo;
import com.example.soft.Entity.Photo;
import com.example.soft.list_to_json;
import com.example.soft.mapper.History_Mapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.soft.controller.Upload_Controller.request;
import static com.example.soft.controller.Upload_Controller.resource;

@RestController
@RequestMapping()
public class Use_Controller {

    @Value("${Pri_Block_Delete}")
    private String Pri_Block_Delete; // 分块原图文件存储的目录
    @Value("${Result_Block_Delete}")
    private String Result_Block_Delete; // 分块结果文件存储的目录
    @Value("${upload.result}")
    private String uploadResult; // 原图结果文件存储的目录

    @Autowired
    private History_Mapper history_mapper;

    @Value("${IP}")
    private String IP; // 映射ip路径

    @Value("${IP_Use}")
    private String IP_Use; // 映射ip路径

    @CrossOrigin(origins = "*")
    @GetMapping("/tabledata")
    public String getTableData() {
        // 调用方法删除文件夹中的所有文件
        deleteFolderContents(new File(Pri_Block_Delete));
        deleteFolderContents(new File(Result_Block_Delete));

        // Python后端接口的URL
        String pythonEndpointUrl = IP_Use+"process_image";
        // 创建一个RestTemplate实例
        RestTemplate restTemplate = new RestTemplate();
        // 准备请求参数
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 设置请求体参数
        String requestBody ="{\"image_path\":\""+request+"\"}";
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
        int number_all=1;
        int num=1;
        String mask_result_photo = "";
        for (List<Object> entry : Result_Python.getRes()) {
            mask_result_photo=(String)entry.get(3);
            tableData.add(new Photo(number_all, resource, (String)entry.get(3) , num, (String) entry.get(1), (String) entry.get(2), (double) entry.get(0)));
            num++;
        }
//        tableData.add(new Photo("1", "/src/assets/1.jpg", "/src/assets/1.jpg", "1", "/src/assets/2.jpg","/src/assets/2.jpg",1.5));
//        tableData.add(new Photo("1", "/src/assets/1.jpg", "/src/assets/1.jpg", "2", "/src/assets/2.jpg","/src/assets/2.jpg",2.5));
//        tableData.add(new Photo("1", "/src/assets/1.jpg", "/src/assets/1.jpg", "3", "/src/assets/2.jpg","/src/assets/2.jpg",3.5));
//        tableData.add(new Photo("1", "/src/assets/1.jpg", "/src/assets/1.jpg", "4", "/src/assets/2.jpg","/src/assets/2.jpg",4.5));
//        tableData.add(new Photo("1", "/src/assets/1.jpg", "/src/assets/1.jpg", "5", "/src/assets/2.jpg","/src/assets/2.jpg",5.5));
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        System.out.println(dateFormat.format(calendar.getTime()));

        History_Photo save_photo=new History_Photo(resource,mask_result_photo,Integer.toString(num-1),dateFormat.format(calendar.getTime()));
        history_mapper.save(save_photo);

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

