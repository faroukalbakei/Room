package com.example.farouk.roomx;

import java.util.Date;

/**
 * Created by farouk on 1/29/17.
 */

public class Room {


    int UserPic;
    String UserName;
    String Tag;
    String dateup;
    String City;
    int RoomPic;
    int like ;
    String Detail;



    public Room(){};

    public Room(int userPic, String userName, String tag, String dateup, String city, int roomPic, int like, String detail) {
        UserPic = userPic;
        UserName = userName;
        Tag = tag;
        this.dateup = dateup;
        City = city;
        RoomPic = roomPic;
        this.like = like;
        Detail = detail;
    }

    public int getUserPic() {
        return UserPic;
    }

    public void setUserPic(int userPic) {
        UserPic = userPic;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDateup() {
        return dateup;
    }

    public void setDateup(String dateup) {
        this.dateup = dateup;
    }

    public int getRoomPic() {
        return RoomPic;
    }

    public void setRoomPic(int roomPic) {
        RoomPic = roomPic;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }
}
