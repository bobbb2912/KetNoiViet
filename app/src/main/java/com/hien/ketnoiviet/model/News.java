package com.hien.ketnoiviet.model;

public class News {
    private String Titlel;
    private String Url;
    private String Date;
    private String Thumbnail;
    private String Description;

    public News(String titlel, String url, String date, String thumbnail, String description) {
        Titlel = titlel;
        Url = url;
        Date = date;
        Thumbnail = thumbnail;
        Description = description;
    }

    public String getTitlel() {
        return Titlel;
    }

    public void setTitlel(String titlel) {
        Titlel = titlel;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
