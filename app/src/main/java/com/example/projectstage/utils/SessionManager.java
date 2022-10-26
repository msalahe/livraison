package com.example.projectstage.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.projectstage.models.Livreur;

public class SessionManager {
    private static SessionManager sessionManager;
    private int statut = 0;

    public void setPreferences(Context context, Livreur value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE).edit();
        editor.putInt("idLivreur",value.getIdLivreur());
        editor.putString("nom", value.getPrenom());
        editor.putString("prenom",value.getNom());
        editor.putString("email",value.getEmail());
        editor.putString("mdp",value.getModepasse());
        editor.apply();
    }

    public String getPreferences(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }
    public int  getPreferences1(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }
    public void emptySharedPrefrences(Context context, String key){
        SharedPreferences.Editor editor = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    public void setStatut(int stat) {
        this.statut = stat;
    }

    public int getStatut() {
        return this.statut;
    }

    public void logOUt(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    public static SessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new SessionManager();
        }
        return sessionManager;
    }


}
