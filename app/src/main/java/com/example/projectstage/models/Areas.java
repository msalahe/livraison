package com.example.projectstage.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Areas implements Serializable {
    @SerializedName("posXmin")
    @Expose
    private double posXmin;
    @SerializedName("posYmin")
    @Expose
    private double posYmin;
    @SerializedName("posXmax")
    @Expose
    private double posXmax;
    @SerializedName("posYmax")
    @Expose
    private double posYmax;

    public Areas(double posXmin, double posYmin, double posXmax, double posYmax, String idareas) {
        this.posXmin = posXmin;
        this.posYmin = posYmin;
        this.posXmax = posXmax;
        this.posYmax = posYmax;
        this.idareas = idareas;
    }

    @SerializedName("idareas")
    @Expose

    private String idareas;

    public double getPosXmin() {
        return posXmin;
    }

    public void setPosXmin(double posXmin) {
        this.posXmin = posXmin;
    }

    public double getPosXmax() {
        return posXmax;
    }

    public void setPosXmax(double posXmax) {
        this.posXmax = posXmax;
    }

    public String getIdareas() {
        return idareas;
    }

    public void setIdareas(String idareas) {
        this.idareas = idareas;
    }

    public double getPosYmin() {
        return posYmin;
    }

    public void setPosYmin(double posYmin) {
        this.posYmin = posYmin;
    }

    public double getPosYmax() {
        return posYmax;
    }

    public void setPosYmax(double posYmax) {
        this.posYmax = posYmax;
    }
}
