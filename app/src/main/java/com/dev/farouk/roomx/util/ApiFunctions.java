package com.dev.farouk.roomx.util;

/**
 * Created by AbAbdullah on 23/10/2016.
 */

public enum ApiFunctions {
    delete_room(1), like_room(2),explore_list(3);

    private final int value;

    ApiFunctions(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
