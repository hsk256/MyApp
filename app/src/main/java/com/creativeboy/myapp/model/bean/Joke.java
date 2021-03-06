package com.creativeboy.myapp.model.bean;

/**
 * Created by heshaokang on 2015/9/11.
 * 娱乐接口的bean
 */
public class Joke {
    private String title;
    private String description;
    private String picUrl;
    private String url;
    private String time;

    public Joke() {}

    public Joke(String title,String description,String picUrl,String url,String time) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
