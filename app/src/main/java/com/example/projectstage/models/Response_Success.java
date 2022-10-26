package com.example.projectstage.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response_Success {
    @SerializedName("success")
    @Expose
    private String succes;

    public Response_Success(String succes) {
        this.succes = succes;
    }

    public String getSucces() {
        return succes;
    }

    public void setSucces(String succes) {
        this.succes = succes;
    }
}
