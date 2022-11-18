package com.hien.ketnoiviet.model;

public class Province {
    public int Id;
    public String Name;
    public String Code;

    public Province(int id, String name, String code) {
        Id = id;
        Name = name;
        Code = code;
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
    public String getCode() {
        return Code;
    }
    public void setCode(String code) {
        Code = code;
    }
}
