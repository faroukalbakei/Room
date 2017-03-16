package com.example.farouk.roomx.util;

/**
 * Created by AbAbdullah on 23/10/2016.
 */

public enum FragmentType {
    MY_ROOMS(1), RESERVATION_REQUESTS(2);

    private final int value;

    FragmentType(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
