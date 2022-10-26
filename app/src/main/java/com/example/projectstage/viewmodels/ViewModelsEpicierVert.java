package com.example.projectstage.viewmodels;

import android.util.Log;
import android.webkit.WebResourceResponse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectstage.dao.ImpUser;
import com.example.projectstage.models.Affection;
import com.example.projectstage.models.Areas;
import com.example.projectstage.models.Commande;
import com.example.projectstage.models.Livreur;
import com.example.projectstage.models.ResponseLogin;
import com.example.projectstage.models.Response_Success;
import com.example.projectstage.models.Tourne;
import com.example.projectstage.models.Vehicule;
import com.example.projectstage.views.PriseEnCharge;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModelsEpicierVert extends ViewModel {
    private  MutableLiveData<List<Vehicule>> dataVeh = new MutableLiveData<>();
    private MutableLiveData<List<Tourne>> dataT = new MutableLiveData<>();
    private  MutableLiveData<List<Affection>> dataTLive = new MutableLiveData<>();
    private MutableLiveData<List<Commande>>  dataCommande = new MutableLiveData<>();
    private MutableLiveData<Response_Success>  data_reponse = new MutableLiveData<>();
    private MutableLiveData<Response_Success> data_updateCommande = new MutableLiveData<>();
    private MutableLiveData<Response_Success> dataforInsertAffec = new MutableLiveData<>();
    private  MutableLiveData<String> optimiser = new MutableLiveData<>();
    private MutableLiveData<Tourne> dataTournepopup = new MutableLiveData<>();
    private  MutableLiveData<Areas> dataAreas = new MutableLiveData<>();
     private  ImpUser impUser ;
    private MutableLiveData<ResponseLogin> responseLoginMutableLiveData = new MutableLiveData<>();
   public  ViewModelsEpicierVert(){
       impUser = ImpUser.getInstance();
   }

    public MutableLiveData<List<Vehicule>> getVehicule(){
        impUser.getPost().enqueue(new Callback<List<Vehicule>>() {
            @Override
            public void onResponse(Call<List<Vehicule>> call, Response<List<Vehicule>> response) {
                dataVeh.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Vehicule>> call, Throwable t) {
                Log.d("infora",t.getMessage());
            }
        });
        return  dataVeh;
    }
    public  MutableLiveData<List<Tourne>> getTourne(){
        impUser.getTourne().enqueue(new Callback<List<Tourne>>() {
            @Override
            public void onResponse(Call<List<Tourne>> call, Response<List<Tourne>> response) {
               dataT.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Tourne>> call, Throwable t) {

            }
        });
        return  dataT;
    }
    public  MutableLiveData<List<Affection>> getTourneByLivre(int id){
        ImpUser.getInstance().getAllTournen(id).enqueue(new Callback<List<Affection>>() {
            @Override
            public void onResponse(Call<List<Affection>> call, Response<List<Affection>> response) {
                dataTLive.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Affection>> call, Throwable t) {

            }
        });
        return  dataTLive;
    }
    public  MutableLiveData<ResponseLogin> login(String name,String mdp){
       impUser.Login(name,mdp).enqueue(new Callback<ResponseLogin>() {
           @Override
           public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
               ResponseLogin responseLogin = response.body();
               responseLoginMutableLiveData.setValue(responseLogin);
           }

           @Override
           public void onFailure(Call<ResponseLogin> call, Throwable t) {

           }
       });
       return  responseLoginMutableLiveData;
    }
    public MutableLiveData<List<Commande>> getDataCommande(){
       impUser.getCommande().enqueue(new Callback<List<Commande>>() {
           @Override
           public void onResponse(Call<List<Commande>> call, Response<List<Commande>> response) {
               List<Commande> commandes = response.body();
               dataCommande.setValue(commandes);
               Log.d("final",""+commandes.size());
           }

           @Override
           public void onFailure(Call<List<Commande>> call, Throwable t) {
                Log.d("final","ssa"+t.getMessage());
           }
       });
       return  dataCommande;
    }
    public  MutableLiveData<Response_Success> getData_reponse(int  idC,int status){
       impUser.changeData(idC,status).enqueue(new Callback<Response_Success>() {
           @Override
           public void onResponse(Call<Response_Success> call, Response<Response_Success> response) {
               Response_Success response_success = response.body();
               data_reponse.setValue(response_success);
           }

           @Override
           public void onFailure(Call<Response_Success> call, Throwable t) {

           }
       });
       return  data_reponse;
    }
    public MutableLiveData<Response_Success> getUpadateDate(int idC,int status,String message){
       impUser.changeStateAffesc(idC,status,message).enqueue(new Callback<Response_Success>() {
           @Override
           public void onResponse(Call<Response_Success> call, Response<Response_Success> response) {
               Response_Success response_success = response.body();
               data_updateCommande.setValue(response_success);

           }

           @Override
           public void onFailure(Call<Response_Success> call, Throwable t) {

           }
       });
       return  data_updateCommande;
    }
    public MutableLiveData<Response_Success> inserDataAffec(int idC,int idT){
       impUser.ajouterAffectation(idC,idT).enqueue(new Callback<Response_Success>() {
           @Override
           public void onResponse(Call<Response_Success> call, Response<Response_Success> response) {
               Response_Success response_success = response.body();
               dataforInsertAffec.setValue(response_success);
           }

           @Override
           public void onFailure(Call<Response_Success> call, Throwable t) {

           }
       });
       return  dataforInsertAffec;
    }
    public  MutableLiveData<Tourne> getDataTourne(int idL){
       impUser.getTournepo(idL).enqueue(new Callback<Tourne>() {
           @Override
           public void onResponse(Call<Tourne> call, Response<Tourne> response) {
               Tourne tourne = response.body();
               Log.d("sa",response.body().getLivreur().getNom()+"");
               dataTournepopup.setValue(tourne);
           }

           @Override
           public void onFailure(Call<Tourne> call, Throwable t) {
               Log.d("sa",t.getMessage());
           }
       });
       return  dataTournepopup;
    }
    public  MutableLiveData<Areas> getDataAreas(int idL){
       impUser.getArreas1(idL).enqueue(new Callback<Areas>() {
           @Override
           public void onResponse(Call<Areas> call, Response<Areas> response) {
               Areas areas = response.body();
               dataAreas.setValue(areas);
           }

           @Override
           public void onFailure(Call<Areas> call, Throwable t) {

           }
       });
       return  dataAreas;
    }

}
