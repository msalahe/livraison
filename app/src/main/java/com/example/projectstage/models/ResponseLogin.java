package com.example.projectstage.models;

public class ResponseLogin {
    private boolean succes;
    private Livreur livreur;

    public ResponseLogin(boolean succes, Livreur livreur) {
        this.succes = succes;
        this.livreur = livreur;
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }
}
