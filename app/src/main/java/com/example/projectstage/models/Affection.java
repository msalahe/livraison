package com.example.projectstage.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Affection {
    @SerializedName("commande")
    @Expose
    private Commande commande;
    @SerializedName("tourne")
    @Expose
    private Tourne tourne;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("decription")
    @Expose
    private String description;

    public Affection(Commande commande, Tourne tourne, int status, String description) {

        this.commande = commande;
        this.tourne = tourne;
        this.status = status;
        this.description = description;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Tourne getTourne() {
        return tourne;
    }

    public void setTourne(Tourne tourne) {
        this.tourne = tourne;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
