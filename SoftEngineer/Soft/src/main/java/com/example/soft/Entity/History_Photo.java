package com.example.soft.Entity;

public class History_Photo {
    private String name;
    private String photo;
    private String num;
    private String result;
    private String time;

    public History_Photo(String name, String photo, String num, String result, String time) {
        this.name = name;
        this.photo = photo;
        this.num = num;
        this.result = result;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
