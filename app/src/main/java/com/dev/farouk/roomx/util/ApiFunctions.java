package com.dev.farouk.roomx.util;

/**
 * Created by AbAbdullah on 23/10/2016.
 */

public enum ApiFunctions {
    delete_room(1), like_room(2),explore_list(3),profile_by_id(4),my_profile(5),reservation_list(6),accept_reserve(7),update_profile(8);

    private final int value;

    ApiFunctions(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
