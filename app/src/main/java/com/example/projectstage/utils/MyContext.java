package com.example.projectstage.utils;

import android.app.Application;
import android.util.Log;
import android.widget.Button;

import com.example.projectstage.models.Affection;
import com.example.projectstage.models.Areas;
import com.example.projectstage.models.Commande;

import java.util.ArrayList;
import java.util.List;

public class MyContext extends Application {
    private Button buttonommanceMaTourne;
    private List<Commande> commandes ;
    private  List<Affection> affections ;
    public int drapo = 0;
    private Areas areas;
    private  int idTourne ;
    public  int diff =  1000;
    private int stateChangeCommande = 0;
    private Affection  affectionCurrent;
    public int indexEtatComa = 0;
   public void addC(Commande commande){
       commandes.add(commande);
       affections = new ArrayList<>();
   }

    public Areas getAreas() {
        return areas;
    }

    public void setAreas(Areas areas) {
        this.areas = areas;
    }

    public int getIdTourne() {
        return idTourne;
    }

    public void setIdTourne(int idTourne) {
        this.idTourne = idTourne;
    }

    public int getStateChangeCommande() {
        return stateChangeCommande;
    }

    public void setStateChangeCommande(int stateChangeCommande) {
        this.stateChangeCommande = stateChangeCommande;
    }

    public int getIndexEtatComa() {
        return indexEtatComa;
    }

    public void setIndexEtatComa(int indexEtatComa) {
        this.indexEtatComa = indexEtatComa;
    }

    public List<Affection> getAffections() {
        return affections;
    }

    public Affection getAffectionCurrent() {
        return affectionCurrent;
    }

    public void setAffectionCurrent(Affection affectionCurrent) {
        this.affectionCurrent = affectionCurrent;
    }

    public void setAffections(List<Affection> affections) {
        this.affections = affections;
        for(int i=0;i<this.affections.size();i++){
            if(this.affections.get(i).getStatus()==0){
                affectionCurrent = this.affections.get(i);
                indexEtatComa = i;
                return;
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
       commandes = new ArrayList<>();
    }

    public Button getButtonommanceMaTourne() {
        return buttonommanceMaTourne;
    }

    public void setButtonommanceMaTourne(Button buttonommanceMaTourne) {
        this.buttonommanceMaTourne = buttonommanceMaTourne;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    public  void setE(){
       if(commandes.size()>0)
                 drapo = 1;
    }
    public void setD(){
        drapo = 0;
    }
}
