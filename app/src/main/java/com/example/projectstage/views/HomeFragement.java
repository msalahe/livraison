package com.example.projectstage.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectstage.R;
import com.example.projectstage.models.Affection;
import com.example.projectstage.models.Commande;
import com.example.projectstage.utils.AdapterCommande;
import com.example.projectstage.utils.MyContext;
import com.example.projectstage.utils.SessionManager;
import com.example.projectstage.viewmodels.ViewModelsEpicierVert;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragement extends Fragment {
    private RecyclerView recyclerView;
    private ViewModelsEpicierVert viewModelsEpicierVert;
     private double distance =0.0;
    private AdapterCommande adapterCommande;
    private TextView textViewKm , textViewtemps,nbrarretes;

    private double duration =0;
    private MyContext myContext ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.homefragement,container,false);
        recyclerView = view.findViewById(R.id.commandeList);
       myContext = (MyContext) getActivity().getApplicationContext();
        adapterCommande = new AdapterCommande(getContext());
      textViewKm = view.findViewById(R.id.kmroute);
      textViewtemps = view.findViewById(R.id.timerout);
        nbrarretes = view.findViewById(R.id.arrests);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModelsEpicierVert = ViewModelProviders.of(this).get(ViewModelsEpicierVert.class);
        SessionManager sessionManager = SessionManager.getInstance();

        viewModelsEpicierVert.getTourneByLivre(sessionManager.getPreferences1(getContext(),"idLivreur")).observe(getActivity(), new Observer<List<Affection>>() {
            @Override
            public void onChanged(List<Affection> affections) {
                adapterCommande.setAffections(affections);
                Log.d("s",affections.size()+"");
                myContext.setAffections(affections);
            }
        });
        recyclerView.setAdapter(adapterCommande);
        calculeTotal(myContext.getCommandes());

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    public void calculeTotal(List<Commande>  commandes){
        for(int i=0;i<commandes.size()-1;i++){
            Point originPoint1  = Point.fromLngLat(commandes.get(i).getPosY(),commandes.get(i).getPosX());

            Point destinationPoint1 = Point.fromLngLat(commandes.get(i+1).getPosY(),commandes.get(i+1).getPosX());
            NavigationRoute.builder(getContext())
                    .accessToken("pk.eyJ1Ijoia2FtYWwzNDQiLCJhIjoiY2thOTF3cTBqMDE1MDM1b2l2OHEzNmgyeSJ9.YNIVfRu6sR_wbuMY0C-xow")
                    .origin(originPoint1)
                    .destination(destinationPoint1)
                    .build()
                    .getRoute(new Callback<DirectionsResponse>() {
                        @Override
                        public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                            // You can get the generic HTTP info about the response
                            DirectionsResponse jsonObject = response.body();
                            distance += (jsonObject.routes().get(0).distance())*0.001;
                            duration +=(jsonObject.routes().get(0).duration())*0.0166667;
                            duration = Math.floor(duration * 100) / 100;
                            distance = Math.floor(distance * 100) / 100;
                            textViewKm.setText((distance)+" km");
                            textViewtemps.setText(duration+" min");
                            nbrarretes.setText(myContext.getCommandes().size()+" arréts");
                            Log.d("", "distance1 "+ jsonObject.routes().get(0).distance());
                            if (response.body() == null) {
                                Log.e("", "No routes found, make sure you set the right user and access token.");
                                return;
                            } else if (response.body().routes().size() < 1) {
                                Log.e("", "No routes found");
                                return;
                            }
                        }

                        @Override
                        public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                            Log.e("", "Error: " + throwable.getMessage());
                        }
                    });
        }

    }
}
