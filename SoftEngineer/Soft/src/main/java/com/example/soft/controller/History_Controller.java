package com.example.soft.controller;

import com.example.soft.Entity.History_Photo;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
public class History_Controller {

    @CrossOrigin(origins = "*")
    @GetMapping("/historydata")
    public String getHistoryData() {
        ArrayList<History_Photo> tableData = new ArrayList<>();
        tableData.add(new History_Photo("1.jpg","/src/Pri_block/1.jpg","12","200平方厘米","2024-4-26"));
        tableData.add(new History_Photo("2.jpg","/src/Pri_block/2.jpg","123","328平方厘米","2024-4-27"));
        String blockchain_ProJson = new GsonBuilder().setPrettyPrinting().create().toJson(tableData);
        System.out.println(blockchain_ProJson);
        return blockchain_ProJson;
    }

}
