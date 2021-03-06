package com.dev.farouk.roomx.model;

/**
 * Created by ababdullah on 31/01/2017.
 */

public class Response {
    Object object;
    boolean isValid;
    String Message;
    String onError;
    private int result=-1;
    private int position=-1;
    int functionName;

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

    public Response(int result) {
        this.result = result;
    }

    public Response(String message, int result) {
        Message = message;
        this.result = result;
    }

    public Response(boolean isValid, String message) {
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
                ", onError='" + onError + '\'' +
                ", result=" + result +
                ", position=" + position +
                ", functionName='" + functionName + '\'' +
                '}';
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int getFunctionName() {
        return functionName;
    }

    public void setFunctionName(int functionName) {
        this.functionName = functionName;
    }
}
