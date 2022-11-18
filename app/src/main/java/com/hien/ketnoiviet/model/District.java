package com.hien.ketnoiviet.model;

public class District {
    public int Id;
    public String Name;
    public String Prefix;
    public int ProvinceId;

    public District(int id, String name, String prefix, int provinceId) {
        Id = id;
        Name = name;
        Prefix = prefix;
        ProvinceId = provinceId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrefix() {
        return Prefix;
    }

    public void setPrefix(String prefix) {
        Prefix = prefix;
    }

    public int getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(int provinceId) {
        ProvinceId = provinceId;
    }
}
