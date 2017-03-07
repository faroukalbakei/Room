
package com.example.farouk.roomx.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {

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
    @SerializedName("price")
    @Expose
    private List<String> price = null;

    @SerializedName("air_condition")
    @Expose
    private List<String> airCondition = null;

    @SerializedName("number_of_guests")
    @Expose
    private List<String> numberOfGuests = null;

    @SerializedName("number_of_beds")
    @Expose
    private List<String> numberOfBeds = null;

    @SerializedName("number_of_baths")
    @Expose
    private List<String> numberOfBaths = null;

    @SerializedName("number_of_rooms")
    @Expose
    private List<String> numberOfRooms = null;

    @SerializedName("location")
    @Expose
    private List<String> location = null;

    @SerializedName("tv")
    @Expose
    private List<String> tv = null;

    @SerializedName("description")
    @Expose
    private List<String> description = null;

    @SerializedName("pool")
    @Expose
    private List<String> pool = null;

    @SerializedName("kitchen")
    @Expose
    private List<String> kitchen = null;

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

    public List<String> getPrice() {
        return price;
    }

    public void setPrice(List<String> price) {
        this.price = price;
    }

    public List<String> getAirCondition() {
        return airCondition;
    }

    public void setAirCondition(List<String> airCondition) {
        this.airCondition = airCondition;
    }

    public List<String> getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(List<String> numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public List<String> getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(List<String> numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public List<String> getNumberOfBaths() {
        return numberOfBaths;
    }

    public void setNumberOfBaths(List<String> numberOfBaths) {
        this.numberOfBaths = numberOfBaths;
    }

    public List<String> getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(List<String> numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public List<String> getTv() {
        return tv;
    }

    public void setTv(List<String> tv) {
        this.tv = tv;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getPool() {
        return pool;
    }

    public void setPool(List<String> pool) {
        this.pool = pool;
    }

    public List<String> getKitchen() {
        return kitchen;
    }

    public void setKitchen(List<String> kitchen) {
        this.kitchen = kitchen;
    }

    @Override
    public String toString() {
        return "Error{" +
                "name=" + name +
                ", email=" + email +
                ", password=" + password +
                ", phone=" + phone +
                ", price=" + price +
                ", airCondition=" + airCondition +
                ", numberOfGuests=" + numberOfGuests +
                ", numberOfBeds=" + numberOfBeds +
                ", numberOfBaths=" + numberOfBaths +
                ", numberOfRooms=" + numberOfRooms +
                ", location=" + location +
                ", tv=" + tv +
                ", description=" + description +
                ", pool=" + pool +
                ", kitchen=" + kitchen +
                '}';
    }
}
