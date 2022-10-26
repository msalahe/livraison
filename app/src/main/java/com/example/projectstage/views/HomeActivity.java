package com.example.projectstage.views;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectstage.R;
import com.example.projectstage.models.Affection;
import com.example.projectstage.models.Tourne;
import com.example.projectstage.models.Vehicule;
import com.example.projectstage.utils.MyContext;
import com.example.projectstage.viewmodels.ViewModelsEpicierVert;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
    ChipNavigationBar bottomNavigationView ;
    FragmentManager fragmentManager;
    private ViewModelsEpicierVert viewModelsEpicierVert;
    private MyContext myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);
        myContext  = (MyContext) getApplication();

        viewModelsEpicierVert = ViewModelProviders.of(this).get(ViewModelsEpicierVert.class);
    viewModelsEpicierVert.getTourneByLivre(myContext.getIdTourne());

        bottomNavigationView = findViewById(R.id.bottomView);
        if(savedInstanceState == null){
  getSupportFragmentManager().beginTransaction().replace(R.id.framLayout,new HomeFragement()).commit();
        }
        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.navigation_home :
                        fragment = new HomeFragement();
                        break;
                    case R.id.navigation_palces:
                        fragment = new MapsFragement();
                }
                if(fragment != null){
    fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.framLayout,fragment).commit();
                }
            }
        });

    }
}
