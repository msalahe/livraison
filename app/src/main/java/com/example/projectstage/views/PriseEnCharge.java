package com.example.projectstage.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectstage.R;
import com.example.projectstage.models.Affection;
import com.example.projectstage.models.Areas;
import com.example.projectstage.models.Commande;
import com.example.projectstage.utils.ActionButton;
import com.example.projectstage.utils.AdapterCommande;
import com.example.projectstage.utils.AdapterCommandeInial;
import com.example.projectstage.utils.MyContext;
import com.example.projectstage.utils.SessionManager;
import com.example.projectstage.viewmodels.ViewModelsEpicierVert;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.turf.TurfJoins;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;

public class PriseEnCharge  extends AppCompatActivity {
   private RecyclerView  recyclerView;
  // private AdapterCommande adapterCommande;
   private Button button1;
   private Drawable drawableIcon ;
   private MyContext  myContext;
   private PriseEnCharge priseEnCharge;
   private AdapterCommandeInial adapterCommandeInial;
    private  LatLng BOUND_CORNER_NW ;
    private  LatLng BOUND_CORNER_SE ;
    private  LatLngBounds RESTRICTED_BOUNDS_AREA ;
    private final List<List<Point>> points = new ArrayList<>();
    private final List<Point> outerPoints = new ArrayList<>();
    private List<Commande> commandesBounds = new ArrayList<>();
    List<Affection> affectionsAll = new ArrayList<>();
   ViewModelsEpicierVert viewModelsEpicierVert;
   private ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF0000"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prisecharge);
        recyclerView  = findViewById(R.id.recyclerViewPriseCharger);
        myContext = (MyContext) getApplicationContext();
        priseEnCharge = this;
        //adapterCommande = new AdapterCommande(getApplicationContext());
        adapterCommandeInial = new AdapterCommandeInial(getApplicationContext(),myContext);
        drawableIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.delete);
         button1 = findViewById(R.id.commanceauprise);
        ActionButton actionButton = new ActionButton(myContext);
        actionButton.setPriseEnCharge(this);
        button1.setOnClickListener(actionButton);
        SessionManager sessionManager = SessionManager.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        viewModelsEpicierVert = ViewModelProviders.of(this).get(ViewModelsEpicierVert.class);


      viewModelsEpicierVert.getDataCommande().observe(this, new Observer<List<Commande>>() {
          @Override
          public void onChanged(List<Commande> commandes) {
              Log.d("sa",".size()+");
              viewModelsEpicierVert.getDataAreas(sessionManager.getPreferences1(getApplicationContext(),"idLivreur")).observe(priseEnCharge, new Observer<Areas>() {
                  @Override
                  public void onChanged(Areas areas) {
                      Log.d("areas1",areas.getIdareas()+"");
                      Log.d("areas2",commandes.get(0).getClient().getNom()+"");
                      myContext.setAreas(areas);
                      BOUND_CORNER_NW= new LatLng(areas.getPosXmin(),areas.getPosYmin());
                      BOUND_CORNER_SE = new LatLng(areas.getPosXmax(),areas.getPosYmax());
                      RESTRICTED_BOUNDS_AREA = new LatLngBounds.Builder()
                              .include(BOUND_CORNER_NW)
                              .include(BOUND_CORNER_SE)
                              .build();
                      showBoundsArea();
                    for(int i=0;i<commandes.size();i++){
                        if(inBounds(commandes.get(i).getPosX(),commandes.get(i).getPosY()) == true){
                           commandesBounds.add(commandes.get(i));
                        }
                    }

                      adapterCommandeInial.setCommandes(commandesBounds);

                  }
              });

          }
      });

        recyclerView.setAdapter(adapterCommandeInial);

        setUpItemTouchHelper();


    }
    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();

                AdapterCommandeInial adapter = (AdapterCommandeInial)recyclerView.getAdapter();
                adapter.remove(swipedPosition);
            }
            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof AdapterCommandeInial.CommandeIniliser
                        && ((AdapterCommandeInial.CommandeIniliser) viewHolder).isSwipeable) {
                    return super.getSwipeDirs(recyclerView, viewHolder);
                } else {
                    return 0;
                }
            }
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                View itemView = viewHolder.itemView;

                int height = (itemView.getHeight() - drawableIcon.getIntrinsicHeight())/2;
                if(dX>0 ){
               colorDrawable.setBounds(itemView.getLeft(),itemView.getTop(),(int)dX,itemView.getBottom());
               drawableIcon.setBounds(itemView.getLeft()+height,itemView.getTop()+height,itemView.getLeft()+height+drawableIcon.getIntrinsicHeight(),itemView.getBottom()-height);
                dX=-1;
                }else {
                    colorDrawable.setBounds(itemView.getRight(),itemView.getTop(),(int)dX,itemView.getBottom());
                    drawableIcon.setBounds(itemView.getRight()-height-drawableIcon.getIntrinsicHeight(),itemView.getTop()+height,itemView.getRight()-height,itemView.getBottom()-height);
                }
                colorDrawable.draw(c);
                drawableIcon.draw(c);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }
  public boolean inBounds(double posX,double posY){

     if(TurfJoins.inside(Point.fromLngLat(posY,posX),Polygon.fromLngLats(points)) == true){
         Log.d("areas1","Ok");

         return  true;
     }
  return  false;
  }
    private void showBoundsArea() {
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

    }
}
