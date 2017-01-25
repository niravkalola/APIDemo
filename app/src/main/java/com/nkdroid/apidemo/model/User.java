package com.nkdroid.apidemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nirav on 25/01/17.
 */

public class User {

    @SerializedName("id")
    private String userId;
    @SerializedName("response")
    private String responseId;
    @SerializedName("message")
    private String message;

    public User(String userId, String responseId, String message) {
        this.userId = userId;
        this.responseId = responseId;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
