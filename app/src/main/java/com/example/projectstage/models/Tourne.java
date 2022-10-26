package com.example.projectstage.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tourne implements Serializable  {

    @SerializedName("idTourne")
    @Expose
   private  int IdTourne;
    @SerializedName("Livreur")
    @Expose
   private  Livreur livreur;
    @SerializedName("Vehicule")
    @Expose
   private  Vehicule vehicule;
    @SerializedName("dateTourne")
    @Expose
   private String dateTourne;
public  Tourne(){

}
    public Tourne(int idTourne, Livreur livreur, Vehicule vehicule, String dateTourne) {
        IdTourne = idTourne;
        this.livreur = livreur;
        this.vehicule = vehicule;
        this.dateTourne = dateTourne;
    }

    public int getIdTourne() {
        return IdTourne;
    }

    public void setIdTourne(int idTourne) {
        IdTourne = idTourne;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public String getDateTourne() {
        return dateTourne;
    }

    public void setDateTourne(String dateTourne) {
        this.dateTourne = dateTourne;
    }
}
