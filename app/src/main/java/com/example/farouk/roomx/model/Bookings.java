package com.example.farouk.roomx.model;

import java.util.Date;

/**
 * Created by farouk on 2/3/17.
 */

public class Bookings {

    String PlaceName;
    Date StartBooking;
    Date EndBooking;

    public Bookings() {

    }

    public  Bookings(String PlaceName, Date StartBooking , Date EndBooking){
        this.PlaceName = PlaceName;
        this.StartBooking=StartBooking;
        this.EndBooking = EndBooking;


        setPlaceName(PlaceName);
        setStartBooking(StartBooking);
        setEndBooking(EndBooking);
    }


    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public Date getStartBooking() {
        return StartBooking;
    }

    public void setStartBooking(Date startBooking) {
        StartBooking = startBooking;
    }

    public Date getEndBooking() {
        return EndBooking;
    }

    public void setEndBooking(Date endBooking) {
        EndBooking = endBooking;
    }



}
