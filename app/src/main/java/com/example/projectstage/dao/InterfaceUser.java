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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterfaceUser {
    @GET("vehicules")
    public Call<List<Vehicule>> getAllVehicule();

    @GET("tourne")
    public Call<List<Tourne>> getAllTourne();

    @GET("commandes/{idL}")
    public Call<List<Affection>> getTourneByLiVreur(@Path("idL") int idl);

    @FormUrlEncoded
    @POST("loginliv")
    public Call<ResponseLogin> Login(@Field("username") String userID, @Field("password") String token);

    @FormUrlEncoded
    @POST("ajouteraffec")
    public Call<Response_Success> inserAffectationCommande(@Field("idC") int idC, @Field("idT") int idT);


    @GET("commandesALL")
    public  Call<List<Commande>>  getCommande();
    @GET("tournes/{idL}")
    public  Call<Tourne>  getTournepoup(@Path("idL") int idl);
    @FormUrlEncoded
    @POST("updatecommande")
    public Call<Response_Success> updateCommandeIniale(@Field("idC") int idCommande, @Field("status") int status);
    @FormUrlEncoded
    @POST("updateaffectaion")
    public Call<Response_Success> updateCommandeAffectation(@Field("idC") int idCommande, @Field("status") int status,@Field("message") String message);

    @GET("areasliv/{idl}")
    public  Call<Areas>  getArreas(@Path("idl") int idl);

}
