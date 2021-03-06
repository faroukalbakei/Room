
package com.dev.farouk.roomx.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class PlaceObject extends SugarRecord{
    private Long id;

    @SerializedName("id")
    @Expose
    private int pid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("number_of_guests")
    @Expose
    private String numberOfGuests;
    @SerializedName("number_of_beds")
    @Expose
    private String numberOfBeds;
    @SerializedName("number_of_baths")
    @Expose
    private String numberOfBaths;
    @SerializedName("number_of_rooms")
    @Expose
    private String numberOfRooms;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("tv")
    @Expose
    private String tv;
    @SerializedName("wifi")
    @Expose
    private String wifi;
    @SerializedName("pool")
    @Expose
    private String pool;
    @SerializedName("air_condition")
    @Expose
    private String airCondition;
    @SerializedName("kitchen")
    @Expose
    private String kitchen;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("isFavourate")
    @Expose
    private Integer isFavourate;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("room_photo")
    @Expose
    private List<RoomPhoto> roomPhoto = null;
    @SerializedName("room_reservation")
    @Expose
    private List<RoomReservation> roomReservation = null;

    public PlaceObject() {
    }


    public PlaceObject(Long id) {
        this.id = id;
    }

    public PlaceObject(int pid) {
        this.pid = pid;
    }

    public PlaceObject(Long id, Integer pid, String name,
                       String userId, String numberOfGuests,
                       String numberOfBeds, String numberOfBaths,
                       String numberOfRooms, String price, String rating,
                       String location, String description, String tv, String wifi,
                       String pool, String airCondition, String kitchen, String createdAt,
                       String updatedAt, Integer isFavourate, User user, List<RoomPhoto> roomPhoto,
                       List<RoomReservation> roomReservation) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.userId = userId;
        this.numberOfGuests = numberOfGuests;
        this.numberOfBeds = numberOfBeds;
        this.numberOfBaths = numberOfBaths;
        this.numberOfRooms = numberOfRooms;
        this.price = price;
        this.rating = rating;
        this.location = location;
        this.description = description;
        this.tv = tv;
        this.wifi = wifi;
        this.pool = pool;
        this.airCondition = airCondition;
        this.kitchen = kitchen;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isFavourate = isFavourate;
        this.user = user;
        this.roomPhoto = roomPhoto;
        this.roomReservation = roomReservation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(String numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(String numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public String getNumberOfBaths() {
        return numberOfBaths;
    }

    public void setNumberOfBaths(String numberOfBaths) {
        this.numberOfBaths = numberOfBaths;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(String numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public String getAirCondition() {
        return airCondition;
    }

    public void setAirCondition(String airCondition) {
        this.airCondition = airCondition;
    }

    public String getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getIsFavourate() {
        return isFavourate;
    }

    public void setIsFavourate(Integer isFavourate) {
        this.isFavourate = isFavourate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<RoomPhoto> getRoomPhoto() {
        return roomPhoto;
    }

    public void setRoomPhoto(List<RoomPhoto> roomPhoto) {
        this.roomPhoto = roomPhoto;
    }

    public List<RoomReservation> getRoomReservation() {
        return roomReservation;
    }

    public void setRoomReservation(List<RoomReservation> roomReservation) {
        this.roomReservation = roomReservation;
    }

    @Override
    public String toString() {
        return "PlaceObject{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", numberOfGuests='" + numberOfGuests + '\'' +
                ", numberOfBeds='" + numberOfBeds + '\'' +
                ", numberOfBaths='" + numberOfBaths + '\'' +
                ", numberOfRooms='" + numberOfRooms + '\'' +
                ", price='" + price + '\'' +
                ", rating='" + rating + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", tv='" + tv + '\'' +
                ", wifi='" + wifi + '\'' +
                ", pool='" + pool + '\'' +
                ", airCondition='" + airCondition + '\'' +
                ", kitchen='" + kitchen + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", isFavourate=" + isFavourate +
                ", user=" + user +
                ", roomPhoto=" + roomPhoto +
                ", roomReservation=" + roomReservation +
                '}';
    }
}
