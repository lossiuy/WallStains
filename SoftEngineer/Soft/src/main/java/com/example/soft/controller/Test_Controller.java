package com.example.soft.controller;

import com.example.soft.Entity.History_Photo;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class Test_Controller {

    @CrossOrigin(origins = "*")
    @PostMapping("/process_image")
    public String test_py(@RequestBody String Json_String) {
        String jsonString = "{\"res\":[[0.40028388238325097,\"/src/Pri_block/2.jpg\",\"/src/Pri_block/2.jpg\"],[0.47,\"/src/Pri_block/2.jpg\",\"/src/Pri_block/2.jpg\"],[-1.0,\"/src/Pri_block/2.jpg\",\"/src/Pri_block/2.jpg\"]]}";
        System.out.println(Json_String);
        return jsonString;
    }

}
