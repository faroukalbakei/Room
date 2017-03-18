package com.dev.farouk.roomx.model;

/**
 * Created by farouk on 1/30/17.
 */

public class ExtrasItem {


    int iconed;
    String textd;
    String iconUrl;

    public ExtrasItem(int iconed, String textd) {
        this.iconed = iconed;
        this.textd = textd;
    }

    public ExtrasItem(String photolink, String username) {
        this.iconUrl = photolink;
        this.textd = username;

    }

    public int getIconed() {
        return iconed;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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
