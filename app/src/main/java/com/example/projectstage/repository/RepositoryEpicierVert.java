package com.example.projectstage.repository;

import com.example.projectstage.dao.ImpUser;
import com.example.projectstage.dao.InterfaceUser;
import com.example.projectstage.models.Vehicule;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryEpicierVert {
    private static final String base_url = "http://10.0.2.2/api/web/index.php/";
    private InterfaceUser interfaceUser ;
    private  static ImpUser instance  = null;
    public RepositoryEpicierVert(){
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


}
