
package com.example.farouk.roomx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("msg")
    @Expose
    private Msg msg;
    @SerializedName("result")
    @Expose
    private Integer result;

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

}
