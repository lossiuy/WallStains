package com.example.soft.Entity;

public class Python_Dto {

    private double area;

    private String pre_url;

    private String url;

    public Python_Dto(double area, String pre_url, String url) {
        this.area = area;
        this.pre_url = pre_url;
        this.url = url;
    }
    public String getPre_url() {
        return pre_url;
    }

    public void setPre_url(String pre_url) {
        this.pre_url = pre_url;
    }


    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
