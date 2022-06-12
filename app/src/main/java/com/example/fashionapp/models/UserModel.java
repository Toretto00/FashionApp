package com.example.fashionapp.models;

public class UserModel {

    String name;
    String phone;
    String email;
    String password;
    String gender;
    String birthday;
    String address;

    public UserModel() {
    }

    public UserModel(String name, String phone, String email, String password, String gender, String birthday, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
