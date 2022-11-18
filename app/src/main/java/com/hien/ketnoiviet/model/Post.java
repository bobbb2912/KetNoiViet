package com.hien.ketnoiviet.model;

public class Post {
    private int idpost;
    private String nameplace;
    private String province;
    private String district;
    private String ward;
    private String address;
    private String description;
    private String content;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String phoneuser;
    private String datepost;
    private int status;
    private String nameuser;
    private String imageuser;



    public Post(int idpost, String nameplace, String province, String district, String ward, String address, String description, String content, String image1, String image2, String image3, String image4, String phoneuser, String datepost, int status, String nameuser, String imageuser) {
        this.idpost = idpost;
        this.nameplace = nameplace;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.address = address;
        this.description = description;
        this.content = content;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.phoneuser = phoneuser;
        this.datepost = datepost;
        this.status = status;
        this.nameuser = nameuser;
        this.imageuser = imageuser;
    }

    public int getIdpost() {
        return idpost;
    }

    public void setIdpost(int idpost) {
        this.idpost = idpost;
    }

    public String getNameplace() {
        return nameplace;
    }

    public void setNameplace(String nameplace) {
        this.nameplace = nameplace;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getPhoneuser() {
        return phoneuser;
    }

    public void setPhoneuser(String phoneuser) {
        this.phoneuser = phoneuser;
    }

    public String getDatepost() {
        return datepost;
    }

    public void setDatepost(String datepost) {
        this.datepost = datepost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getImageuser() {
        return imageuser;
    }

    public void setImageuser(String imageuser) {
        this.imageuser = imageuser;
    }
}
