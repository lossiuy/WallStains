package com.example.soft.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class Photo {
    private int id;
    private String name;
    private String mark_photo;
    private int num_photo;
    private String pre_photo;
    private String result_photo;
    private double area;

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Photo(int id, String name, String mark_photo, int num_photo, String pre_photo, String result_photo, double area) {
        this.id = id;
        this.name = name;
        this.mark_photo = mark_photo;
        this.num_photo = num_photo;
        this.pre_photo = pre_photo;
        this.result_photo = result_photo;
        this.area = area;
    }
}
