package com.example.projectstage.dao;

import android.webkit.WebResourceResponse;

import com.example.projectstage.models.Affection;
import com.example.projectstage.models.Areas;
import com.example.projectstage.models.Commande;
import com.example.projectstage.models.Livreur;
import com.example.projectstage.models.ResponseLogin;
import com.example.projectstage.models.Response_Success;
import com.example.projectstage.models.Tourne;
import com.example.projectstage.models.Vehicule;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImpUser {

    private static final String base_url = "http://10.0.2.2/api/web/index.php/";
    private InterfaceUser interfaceUser ;
    private  static  ImpUser instance  = null;
    public ImpUser(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build();
        interfaceUser = retrofit.create(InterfaceUser.class);
    }
    public static  ImpUser getInstance(){
        if(instance == null)
             instance = new ImpUser();
        return  instance;
    }
    public Call<List<Vehicule>> getPost(){
        return  interfaceUser.getAllVehicule();
    }
    public Call<List<Tourne>> getTourne(){
        return  interfaceUser.getAllTourne();
    }
    public Call<List<Affection>> getAllTournen(int idL){
        return  interfaceUser.getTourneByLiVreur(idL);
    }
    public Call<ResponseLogin> Login(String userName, String mdp){
        return interfaceUser.Login(userName,mdp);
    }
    public  Call<List<Commande>> getCommande(){
        return  interfaceUser.getCommande();
    }
    public Call<Response_Success>  changeData(int idC,int status){
        return  interfaceUser.updateCommandeIniale(idC,status);
    }
    public Call<Response_Success> changeStateAffesc(int idC,int status,String message){
        return  interfaceUser.updateCommandeAffectation(idC,status,message);
    }
    public Call<Response_Success> ajouterAffectation(int idC,int idT){
        return  interfaceUser.inserAffectationCommande(idC,idT);
    }
    public Call<Tourne> getTournepo(int id){
        return  interfaceUser.getTournepoup(id);
    }
    public  Call<Areas> getArreas1(int idl){
        return  interfaceUser.getArreas(idl);
    }

}
