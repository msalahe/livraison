package com.example.projectstage.views;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;

import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectstage.R;

import com.example.projectstage.models.Commande;
import com.example.projectstage.models.Response_Success;
import com.example.projectstage.utils.MyContext;
import com.example.projectstage.viewmodels.ViewModelsEpicierVert;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;

import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.camera.NavigationCamera;
import com.mapbox.services.android.navigation.ui.v5.map.NavigationMapboxMap;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.turf.TurfJoins;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.markers.KMappedMarker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class MapsFragement extends Fragment implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {
    // variables for adding location layer
    private MapView mapView;
    public  int  stat= 0;
    private MapboxMap mapboxMap;
    private ViewModelsEpicierVert viewModelsEpicierVert;
    private  LatLng BOUND_CORNER_NW ;
    private  LatLng BOUND_CORNER_SE ;

    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private  View view;
    private MyContext myContext;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    // variables needed to initialize navigation
    private Button button;
    private  LatLngBounds RESTRICTED_BOUNDS_AREA ;
    private final List<List<Point>> points = new ArrayList<>();
    private final List<Point> outerPoints = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(getContext(), "pk.eyJ1Ijoia2FtYWwzNDQiLCJhIjoiY2thOTF3cTBqMDE1MDM1b2l2OHEzNmgyeSJ9.YNIVfRu6sR_wbuMY0C-xow");
         view = inflater.inflate(R.layout.mapsfragement, container, false);
         myContext =(MyContext) getActivity().getApplicationContext();
        viewModelsEpicierVert = ViewModelProviders.of(this).get(ViewModelsEpicierVert.class);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.mapView);
        button = view.findViewById(R.id.startButton);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }



    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        button.setEnabled(true);
        return true;
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);

                addDestinationIconSymbolLayer(style);
                debutMap();
                mapboxMap.setLatLngBoundsForCameraTarget(RESTRICTED_BOUNDS_AREA);

                mapboxMap.setMinZoomPreference(2);
                BOUND_CORNER_NW= new LatLng(myContext.getAreas().getPosXmin(),myContext.getAreas().getPosYmin());
                BOUND_CORNER_SE = new LatLng(myContext.getAreas().getPosXmax(),myContext.getAreas().getPosYmax());
                RESTRICTED_BOUNDS_AREA = new LatLngBounds.Builder()
                        .include(BOUND_CORNER_NW)
                        .include(BOUND_CORNER_SE)
                        .build();
                showBoundsArea(style);

                showCrosshair();
                //calculeTotal(myContext.getCommandes());
               // Toast.makeText(getActivity().getApplicationContext(),   TurfJoins.inside(Point.fromLngLat(-4.9864645,34.0289383),Polygon.fromLngLats(points))+"", Toast.LENGTH_SHORT).show();

                mapboxMap.addOnMapClickListener(MapsFragement.this);
                button = view.findViewById(R.id.startButton);

                    for( int i =0;i<myContext.getCommandes().size();i++){
                        MarkerOptions markerOptions = new MarkerOptions();

                        markerOptions.position(new LatLng(myContext.getCommandes().get(i).getPosX(),myContext.getCommandes().get(i).getPosY()));
                        markerOptions.setTitle(i+"");
                        mapboxMap.addMarker(markerOptions);

                        mapboxMap.setInfoWindowAdapter(new MapboxMap.InfoWindowAdapter() {
                            @Nullable
                            @Override
                            public View getInfoWindow(@NonNull Marker marker) {
                                LinearLayout  linearLayout = new LinearLayout(getContext());
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                                LinearLayout linearLayout1 = new LinearLayout(getContext());
                                LinearLayout linearLayoutid = new LinearLayout(getContext());

                                TextView textViewId = new TextView(getContext());
                                textViewId.setText("Id : ");
                                textViewId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
                                TextView idC = new TextView(getContext());

                                idC.setTextSize(14);


                                idC.setText(myContext.getCommandes().get(Integer.parseInt(marker.getTitle())).getIdCommande()+"");
                                TextView textViewOrder = new TextView(getContext());
                                textViewOrder.setTextColor(getResources().getColor(R.color.colorAccent));
                                textViewOrder.setTextSize(19);
                                textViewOrder.setText(marker.getTitle()+"");

                                linearLayoutid.addView(textViewId);
                                linearLayoutid.addView(idC);
                                TextView INFO = new TextView(getContext());
                                INFO.setText("INFO CLIENT :");

                                INFO.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
                                TextView nom_prenom = new TextView(getContext());
                                nom_prenom.setText("  "+myContext.getCommandes().get(Integer.parseInt(marker.getTitle())).getClient().getNom().toUpperCase()+" "+myContext.getCommandes().get(Integer.parseInt(marker.getTitle())).getClient().getPrenom().toUpperCase());

                                nom_prenom.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
                                linearLayout1.addView(INFO);
                                linearLayout1.addView(nom_prenom);

                                linearLayout1.setPadding(0,0,0,20);
                                LinearLayout linearLayout3 = new LinearLayout(getContext());
                                TextView Adres = new TextView(getContext());
                                Adres.setText("ADRESS:");

                                INFO.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
                                TextView adressCli = new TextView(getContext());
                                adressCli.setText("  "+myContext.getCommandes().get(Integer.parseInt(marker.getTitle())).getAdress());

                                adressCli.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
                                linearLayout3.addView(Adres);
                                linearLayout3.addView(adressCli);

                                linearLayout3.setPadding(0,0,0,20);

                                LinearLayout linearLayout2 = new LinearLayout(getContext());
                                Button buttonAnuu = new Button(getContext());
                                linearLayout.setLayoutParams(layoutParams);

                                linearLayout.setBackgroundColor(Color.WHITE);
                                buttonAnuu.setText("Annuler");
                                buttonAnuu.setWidth(40);

                                buttonAnuu.setBackgroundColor(Color.RED);
                                Button buttonConfirme = new Button(getContext());
                                RadioGroup radioGroup = new RadioGroup(getContext());
                                RadioButton radioLaVioeplien = new RadioButton(getContext());
                                radioLaVioeplien.setText("La Voie est pleine");
                                LinearLayout linearLayoutEche = new LinearLayout(getContext());
                                RadioButton radioClientIndes = new RadioButton(getContext());
                                radioClientIndes.setText("Le Client indesponibile");
                                RadioButton  radioproblemeVehicule = new RadioButton(getContext());
                                radioproblemeVehicule.setText("La Probéleme au Vehicule ");
                                radioGroup.addView(radioLaVioeplien);
                                radioGroup.addView(radioClientIndes);
                                radioGroup.addView(radioproblemeVehicule);
                                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        String message = "";
                                        View radioButton = radioGroup.findViewById(checkedId);
                                        int index = radioGroup.indexOfChild(radioButton);
                                        int idCommande = Integer.parseInt(idC.getText().toString());
                                        switch (index){
                                            case 0:
                                                message ="La Voie est pleine";
                                                break;
                                            case 1:
                                                message ="Le Client indesponibile";
                                                break;
                                            case 2:
                                                message = "La Probéleme au Vehicule";
                                                break;
                                        }
                                        viewModelsEpicierVert.getUpadateDate(idCommande,2,message).observe(getActivity(), new Observer<Response_Success>() {
                                            @Override
                                            public void onChanged(Response_Success response_success) {
                                                Log.d("response",response_success.getSucces());
                                                if(response_success.getSucces() == "succes"){
                                                    buttonAnuu.setEnabled(false);
                                                    radioLaVioeplien.setEnabled(false);
                                                    radioproblemeVehicule.setEnabled(false);
                                                    radioClientIndes.setEnabled(false);

                                                }
                                            }
                                        });

                                    }

                                });
                                linearLayoutEche.addView(radioGroup);
                                 buttonAnuu.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         linearLayout.addView(linearLayoutEche);

                                     }
                                 });
                                buttonConfirme.setText("Confirmer");
                                buttonConfirme.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int idCommande = Integer.parseInt(idC.getText().toString());
                                        viewModelsEpicierVert.getUpadateDate(idCommande,1,"Succes").observe(getActivity(), new Observer<Response_Success>() {
                                            @Override
                                            public void onChanged(Response_Success response_success) {
                                                Log.d("response",response_success.getSucces());
                                                if(response_success.getSucces() == "succes"){
                                                    buttonConfirme.setEnabled(false);
                                                    buttonConfirme.setEnabled(false);
                                                }
                                            }
                                        });

                                    }
                                });
                                linearLayout2.addView(buttonConfirme);
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) buttonConfirme.getLayoutParams();
                                params.setMargins(10, 0, 20, 10); //left, top, right, bottom
                                buttonConfirme.setLayoutParams(params);
                                buttonConfirme.setBackgroundColor(Color.GREEN);
                                linearLayout2.addView(buttonAnuu);
                                linearLayout.addView(linearLayoutid);
                                linearLayout.addView(linearLayout1);
                                linearLayout.addView(linearLayout3);
                                linearLayout.addView(linearLayout2);


                              linearLayout.addView(textViewOrder);
                               // idC.setEnabled(false);
                                buttonConfirme.getLayoutParams().width=300;
                                buttonConfirme.getLayoutParams().height=100;
                                buttonAnuu.setLayoutParams(params);
                                buttonAnuu.getLayoutParams().width=300;
                                buttonAnuu.getLayoutParams().height=100;
                                linearLayout.setOrientation(LinearLayout.VERTICAL);
                                return  linearLayout;
                            }
                        });
                    }
              button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(stat>=myContext.getCommandes().size()-1){
                            stat=0;
                        }
                        Point  originPoint  = Point.fromLngLat(myContext.getCommandes().get(stat).getPosY(),myContext.getCommandes().get(stat).getPosX());
                        int c = stat+1;
                        Point destinationPoint = Point.fromLngLat(myContext.getCommandes().get(c).getPosY(),myContext.getCommandes().get(c).getPosX());
                        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
                        if (source != null) {
                            source.setGeoJson(Feature.fromGeometry(destinationPoint));
                        }

                        getRoute(originPoint, destinationPoint);
                        stat++;

                    }
                });


                /*mapboxMap.setInfoWindowAdapter(new MapboxMap.InfoWindowAdapter() {
                    @Nullable
                    @Override
                    public View getInfoWindow(@NonNull Marker marker) {
                        LinearLayout  linearLayout = new LinearLayout(getContext());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                        LinearLayout linearLayout1 = new LinearLayout(getContext());
                        TextView INFO = new TextView(getContext());
                        INFO.setText("INFO CLIENT :");

                        INFO.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
                        TextView nom_prenom = new TextView(getContext());
                        nom_prenom.setText("SALAH EDDINE EL MAMOUNI");

                        nom_prenom.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
                        linearLayout1.addView(INFO);
                        linearLayout1.addView(nom_prenom);

                        linearLayout1.setPadding(0,0,0,20);
                        LinearLayout linearLayout3 = new LinearLayout(getContext());
                        TextView Adres = new TextView(getContext());
                        Adres.setText("ADRESS:");

                        INFO.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
                        TextView adressCli = new TextView(getContext());
                        adressCli.setText("376,JADE TGHAT FES");

                        adressCli.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
                        linearLayout3.addView(Adres);
                        linearLayout3.addView(adressCli);

                        linearLayout3.setPadding(0,0,0,20);

                        LinearLayout linearLayout2 = new LinearLayout(getContext());
                        Button buttonAnuu = new Button(getContext());
                        linearLayout.setLayoutParams(layoutParams);
                        linearLayout.setBackgroundColor(Color.WHITE);
                        buttonAnuu.setText("Annuler");
                        buttonAnuu.setWidth(40);

                        buttonAnuu.setBackgroundColor(Color.RED);
                        Button buttonConfirme = new Button(getContext());
                        buttonConfirme.setText("Confirmer");
                        linearLayout2.addView(buttonConfirme);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) buttonConfirme.getLayoutParams();
                        params.setMargins(10, 0, 20, 10); //left, top, right, bottom
                        buttonConfirme.setLayoutParams(params);
                        buttonConfirme.setBackgroundColor(Color.GREEN);

                        linearLayout2.addView(buttonAnuu);
                        linearLayout.addView(linearLayout1);
                        linearLayout.addView(linearLayout3);
                        linearLayout.addView(linearLayout2);
                        buttonConfirme.getLayoutParams().width=300;
                        buttonConfirme.getLayoutParams().height=100;
                        buttonAnuu.setLayoutParams(params);
                        buttonAnuu.getLayoutParams().width=300;
                        buttonAnuu.getLayoutParams().height=100;
                        linearLayout.setOrientation(LinearLayout.VERTICAL);



                        return  linearLayout;
                    }
                });*/

            }
        });
    }
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {
            // Activate the MapboxMap LocationComponent to show user location
            // Adding in LocationComponentOptions is also an optional parameter
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(getContext(), loadedMapStyle).build());
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        Canvas canvas =new Canvas();
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)

        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }
    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(getContext())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        DirectionsResponse jsonObject = response.body();

                        Log.d(TAG, "Response code: " + jsonObject.routes().get(0).distance());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getContext(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        }
         else {
            Toast.makeText(getContext(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
  public void debutMap(){
      if(stat>=myContext.getCommandes().size()-1){
          stat=0;
      }
      Point  originPoint  = Point.fromLngLat(myContext.getCommandes().get(stat).getPosY(),myContext.getCommandes().get(stat).getPosX());
      int c = stat+1;
      Point destinationPoint = Point.fromLngLat(myContext.getCommandes().get(c).getPosY(),myContext.getCommandes().get(c).getPosX());
      GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
      if (source != null) {
          source.setGeoJson(Feature.fromGeometry(destinationPoint));
      }

      getRoute(originPoint, destinationPoint);
      stat++;
  }
    private void showBoundsArea(@NonNull Style loadedMapStyle) {
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getNorthWest().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getNorthWest().getLatitude()));
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getNorthEast().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getNorthEast().getLatitude()));
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getSouthEast().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getSouthEast().getLatitude()));
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getSouthWest().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getSouthWest().getLatitude()));
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getNorthWest().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getNorthWest().getLatitude()));
        points.add(outerPoints);

        loadedMapStyle.addSource(new GeoJsonSource("source-id",
                Polygon.fromLngLats(points)));

        loadedMapStyle.addLayer(new FillLayer("layer-id", "source-id").withProperties(
                fillColor(Color.RED),
                fillOpacity(.25f)
        ));
    }
    private void showCrosshair()
    {
        View crosshair = new View(getActivity().getApplicationContext());
        crosshair.setLayoutParams(new FrameLayout.LayoutParams(15, 15, Gravity.CENTER));
        crosshair.setBackgroundColor(Color.GREEN);
        mapView.addView(crosshair);
    }


}