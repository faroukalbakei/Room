package com.example.farouk.roomx.model;

/**
 * Created by Dev Abir on 2/12/2017.
 */

public class Result {

    public User user;

    public Result() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Result{" +
                "user=" + user +
                '}';
    }
}
