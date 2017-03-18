package com.dev.farouk.roomx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by farouk on 1/26/17.
 */

public class UserinfoLogin {
   private String name ;
    private String Email;
    private String password;
    private String mobile;
    private String city;
    private String birthday;
    private int photo;
    private String Tag;
    private boolean Validation = true;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("result")
    @Expose
    private Integer result;
    public UserinfoLogin() {

    }

    public UserinfoLogin(String name, String city, int photo) {
        this.name = name;
        this.city = city;
        this.photo = photo;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public boolean isValidation() {
        return Validation;
    }

    public void setValidation(boolean validation) {
        Validation = validation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserinfoLogin{" +
                "name='" + name + '\'' +
                ", Email='" + Email + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", city='" + city + '\'' +
                ", birthday='" + birthday + '\'' +
                ", photo=" + photo +
                ", Tag='" + Tag + '\'' +
                ", Validation=" + Validation +
                ", token='" + token + '\'' +
                ", result=" + result +
                '}';
    }
}
