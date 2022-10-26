package com.example.projectstage.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Livreur implements Serializable {
    @SerializedName("idLivreur")
    @Expose
    private int  idLivreur;
    @SerializedName("nom")
    @Expose
    private String  nom;
    @SerializedName("prenom")
    @Expose
    private String  prenom ;
    @SerializedName("email")
    @Expose
    private String email ;
    @SerializedName("mdp")
    @Expose
    private String modepasse ;
public Livreur(){

}

    public Livreur(int idLivreur, String nom, String prenom, String email, String modepasse) {
        this.idLivreur = idLivreur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.modepasse = modepasse;

    }

    public int getIdLivreur() {
        return idLivreur;
    }

    public void setIdLivreur(int idLivreur) {
        this.idLivreur = idLivreur;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getModepasse() {
        return modepasse;
    }

    public void setModepasse(String modepasse) {
        this.modepasse = modepasse;
    }


}
