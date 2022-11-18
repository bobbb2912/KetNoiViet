package com.hien.ketnoiviet.model;

public class Comment {
    public String NameUser;
    public String PhoneUser;
    public String ImageUser;
    public String Content;

    public Comment(String nameUser, String phoneUser, String imageUser, String content) {
        NameUser = nameUser;
        PhoneUser = phoneUser;
        ImageUser = imageUser;
        Content = content;
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public String getPhoneUser() {
        return PhoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        PhoneUser = phoneUser;
    }

    public String getImageUser() {
        return ImageUser;
    }

    public void setImageUser(String imageUser) {
        ImageUser = imageUser;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
