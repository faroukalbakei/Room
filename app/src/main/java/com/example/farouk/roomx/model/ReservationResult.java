
package com.example.farouk.roomx.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationResult {

    @SerializedName("reservation")
    @Expose
    private List<Reservation> reservation = null;

    public List<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "ReservationResult{" +
                "reservation=" + reservation +
                '}';
    }
}
