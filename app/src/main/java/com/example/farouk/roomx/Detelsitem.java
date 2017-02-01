package com.example.farouk.roomx;

/**
 * Created by farouk on 1/30/17.
 */

public class Detelsitem {


    int iconed;
    String textd;

    public Detelsitem() {

    }


    public Detelsitem(int iconed, String textd) {
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
