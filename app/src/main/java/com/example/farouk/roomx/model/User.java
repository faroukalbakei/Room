package com.example.farouk.roomx.model;

/**
 * Created by farouk on 1/26/17.
 */

public class User {
   private String name ;
    private String Email;
    private String password;
    private String mobile;
    private String token;
    private boolean Validation = true;


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


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setdata(String name , String email , String password, String mobile){
        setName(name);
        setEmail(email);
        setPassword(password);
        setMobile(mobile);
    }

    public void setlogin(String email , String password){
        setEmail(email);
        setPassword(password);
    }


    public boolean Verification (){
        if (isValidation()){
            getToken();
            return true;
        }else {
            return false;
        }
    }

}
