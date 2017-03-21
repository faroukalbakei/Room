package com.dev.farouk.roomx.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dev Abir on 2/5/2017.
 */
public class ResponsePlace {
    private List<PlaceObject> value = new ArrayList<PlaceObject>();

    public ResponsePlace() {
    }

    public ResponsePlace(List<PlaceObject> value) {
        this.value = value;
    }

    public List<PlaceObject> getValue() {
        return value;
    }

    public void setValue(List<PlaceObject> value) {
        this.value = value;
    }
}
