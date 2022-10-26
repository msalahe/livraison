package com.example.projectstage.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vehicule implements Serializable {

    @SerializedName("idVehicule")
    @Expose
    private int idVehicule;
    @SerializedName("nameV")
    @Expose
    private String  namV;
    @SerializedName("capaciteVolumetrique")
    @Expose
    private double  capaciteVolumetrique;
    @SerializedName("capacitepoids")
    @Expose
    private double    capacitepoids;
    @SerializedName("Kilometrage")
    @Expose
    private int  kilometrage;
public  Vehicule(){

}
    public Vehicule(int idVehicule, String namV, double capaciteVolumetrique, double capacitepoids, int kilometrage) {
        this.idVehicule = idVehicule;
        this.namV = namV;
        this.capaciteVolumetrique = capaciteVolumetrique;
        this.capacitepoids = capacitepoids;
        this.kilometrage = kilometrage;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public String getNamV() {
        return namV;
    }

    public void setNamV(String namV) {
        this.namV = namV;
    }

    public double getCapaciteVolumetrique() {
        return capaciteVolumetrique;
    }

    public void setCapaciteVolumetrique(double capaciteVolumetrique) {
        this.capaciteVolumetrique = capaciteVolumetrique;
    }

    public double getCapacitepoids() {
        return capacitepoids;
    }

    public void setCapacitepoids(double capacitepoids) {
        this.capacitepoids = capacitepoids;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        kilometrage = kilometrage;
    }
}
