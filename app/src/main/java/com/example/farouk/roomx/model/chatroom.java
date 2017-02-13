package com.example.farouk.roomx.model;

/**
 * Created by farouk on 2/4/17.
 */

public class chatroom extends User{
    String text_msg;
    public chatroom(int photo, String text_msg) {
       // super(photo);
        this.text_msg = text_msg;
    }

}
