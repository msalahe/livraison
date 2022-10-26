package com.example.projectstage.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Commande implements Serializable {
    @SerializedName("idCommande")
    @Expose
    private int idCommande;
    @SerializedName("posX")
    @Expose
    private double posX;
    @SerializedName("posY")
    @Expose
    private double posY;
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("poidsTotal")
    @Expose
    private double poidsTotal;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("dateMax")
    @Expose
    private String dateMax;
    @SerializedName("dateMin")
    @Expose
    private String dateMin;
    @SerializedName("client")
    @Expose
    private Client client;

    public Commande(int idCommande, double posX, double posY, String adress, int status, double poidsTotal, int order, String dateMax, String dateMin, Client client) {
        this.idCommande = idCommande;
        this.posX = posX;
        this.posY = posY;
        this.adress = adress;
        this.status = status;
        this.poidsTotal = poidsTotal;
        this.order = order;
        this.dateMax = dateMax;
        this.dateMin = dateMin;
        this.client = client;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPoidsTotal() {
        return poidsTotal;
    }

    public void setPoidsTotal(double poidsTotal) {
        this.poidsTotal = poidsTotal;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDateMax() {
        return dateMax;
    }

    public void setDateMax(String dateMax) {
        this.dateMax = dateMax;
    }

    public String getDateMin() {
        return dateMin;
    }

    public void setDateMin(String dateMin) {
        this.dateMin = dateMin;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
