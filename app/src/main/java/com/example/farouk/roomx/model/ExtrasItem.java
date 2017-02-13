package com.example.farouk.roomx.model;

/**
 * Created by farouk on 1/30/17.
 */

public class ExtrasItem {


    int iconed;
    String textd;

    public ExtrasItem() {

    }


    public ExtrasItem(int iconed, String textd) {
        this.iconed = iconed;
        this.textd = textd;
    }

    public int getIconed() {
        return iconed;
    }

    public void setIconed(int iconed) {
        this.iconed = iconed;
    }

    public String getTextd() {
        return textd;
    }

    public void setTextd(String textd) {
        this.textd = textd;
    }
}
