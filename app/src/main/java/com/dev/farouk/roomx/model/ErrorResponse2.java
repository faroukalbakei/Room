package com.dev.farouk.roomx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AbAbdullah on 26/02/2017.
 */

public class ErrorResponse2 {

    @SerializedName("error")
    @Expose
    private Error error;
    @SerializedName("result")
    @Expose
    private Integer result;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

}
