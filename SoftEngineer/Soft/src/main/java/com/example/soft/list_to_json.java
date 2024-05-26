package com.example.soft;

import com.example.soft.Entity.Photo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class list_to_json {

    public String convertListToJson(List<Photo> photoList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将列表转换为 JSON 格式的字符串
            return objectMapper.writeValueAsString(photoList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // 处理异常，例如返回一个错误信息
            return "Error while converting list to JSON";
        }
    }
}
