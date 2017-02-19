package com.example.farouk.roomx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by Dev Abir on 2/12/2017.
 */

public class UserResult {
    @SerializedName("user")
    @Expose
    public User user;

    public UserResult() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "user=" + user +
                '}';
    }
}
