package com.example.soft.mapper;

import com.example.soft.Entity.History_Photo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface History_Mapper {
    @Select("SELECT * FROM history_photo")
    List<History_Photo> find();

    @Insert("INSERT INTO history_photo (name,photo,num,time) VALUES (#{name},#{photo},#{num},#{time})")
    void save(History_Photo history_photo);
}
