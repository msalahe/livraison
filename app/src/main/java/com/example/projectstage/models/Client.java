package com.example.projectstage.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {
    @SerializedName("idclient")
    @Expose
    private int idClient;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("prenom")
    @Expose
    private String prenom;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mdp")
    @Expose
    private String mdp;

    public Client(int idClient, String nom, String prenom, String email, String mdp) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
