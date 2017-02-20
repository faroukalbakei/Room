package com.example.farouk.roomx.model;

/**
 * Created by ababdullah on 31/01/2017.
 */

public class Response {
    Object object;
    boolean isValid;
    String Message;
    String onError;
    private int result=-1;

    public Response() {
    }

    public Response(Object object, boolean isValid, String message, String onError, int result) {
        this.object = object;
        this.isValid = isValid;
        Message = message;
        this.onError = onError;
        this.result = result;
    }

    public Response(boolean isValid) {
        this.isValid = isValid;
    }

    public Response( boolean isValid, String message) {
        this.isValid = isValid;
        this.Message = message;
    }
    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getOnError() {
        return onError;
    }

    public void setOnError(String onError) {
        this.onError = onError;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "object=" + object +
                ", isValid=" + isValid +
                ", Message='" + Message + '\'' +
                '}';
    }
}
