package com.example.soft.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Test_Photo_Controller implements WebMvcConfigurer {

    @Value("${upload.dir}")
    private String uploadDir; // 上传文件存储的目录
    @Value("${upload.result}")
    private String uploadResult; // 原图结果文件存储的目录
    @Value("${Pri_Block}")
    private String Pri_Block; // 原图结果文件存储的目录
    @Value("${Result_Block}")
    private String Result_Block; // 原图结果文件存储的目录

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 访问地址：如http://localhost:8080/photo/1.jpg
        registry.addResourceHandler("/Master_Photo/**")
                // 访问http://localhost:8080/photo/1.jpg 会自动找C:\Users\Lenovo\Desktop\resource\ 路径下名为1.jpg图片
                .addResourceLocations("file:" + uploadDir);

        registry.addResourceHandler("/Master_Result_Photo/**")
                .addResourceLocations("file:" + uploadResult);

        registry.addResourceHandler("/Pri_Block/**")
                .addResourceLocations("file:" + Pri_Block);

        registry.addResourceHandler("/Result_Block/**")
                .addResourceLocations("file:" + Result_Block);
    }
}
