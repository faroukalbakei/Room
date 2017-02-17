
package com.example.farouk.roomx.model;

import java.lang.annotation.Annotation;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Msg {
    @SerializedName("error")
    @Expose
    private String errorLogin;
    @SerializedName("name")
    @Expose
    private List<String> name = null;
    @SerializedName("email")
    @Expose
    private List<String> email = null;
    @SerializedName("password")
    @Expose
    private List<String> password = null;
    @SerializedName("phone")
    @Expose
    private List<String> phone = null;

    public String getErrorLogin() {
        return errorLogin;
    }

    public void setErrorLogin(String errorLogin) {
        this.errorLogin = errorLogin;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "errorLogin='" + errorLogin + '\'' +
                ", name=" + name +
                ", email=" + email +
                ", password=" + password +
                ", phone=" + phone +
                '}';
    }
}
