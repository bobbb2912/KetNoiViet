package com.hien.ketnoiviet.model;

public class Ward {
    public int Id;
    public String Name;
    public String Prefix;
    public int ProvinceId;
    public int DistrictId;

    public Ward(int id, String name, String prefix, int provinceId, int districtId) {
        Id = id;
        Name = name;
        Prefix = prefix;
        ProvinceId = provinceId;
        DistrictId = districtId;
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

    public int getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(int districtId) {
        DistrictId = districtId;
    }
}
