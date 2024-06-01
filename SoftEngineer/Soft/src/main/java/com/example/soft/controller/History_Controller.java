package com.example.soft.controller;

import com.example.soft.Entity.History_Photo;
import com.example.soft.mapper.History_Mapper;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class History_Controller {

    @Autowired
    private History_Mapper history_mapper;

    @CrossOrigin(origins = "*")
    @GetMapping("/historydata")
    public String getHistoryData() {
        List<History_Photo> tableData = history_mapper.find();
        String blockchain_ProJson = new GsonBuilder().setPrettyPrinting().create().toJson(tableData);
        System.out.println(blockchain_ProJson);
        return blockchain_ProJson;
    }

}
