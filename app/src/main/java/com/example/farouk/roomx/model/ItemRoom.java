package com.example.farouk.roomx.model;

/**
 * Created by Dev Abir on 2/16/2017.
 */
public class ItemRoom {
    String roomItem;
    String isAvailable;

    public ItemRoom(String roomItem, String isAvailable) {
        this.roomItem = roomItem;
        this.isAvailable = isAvailable;
    }

    public String getRoomItem() {
        return roomItem;
    }

    public void setRoomItem(String roomItem) {
        this.roomItem = roomItem;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "ItemRoom{" +
                "roomItem='" + roomItem + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
