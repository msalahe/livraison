package com.example.projectstage.views;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectstage.R;
import com.example.projectstage.utils.ActionButton;
import com.example.projectstage.utils.AdapterCommande;
import com.example.projectstage.utils.MyContext;

public class InfoCommande extends AppCompatActivity {
   private TextView nomClient,adress,status;
   private RadioButton radioButton;
   private RadioGroup radioGroup;
   public TextView getStatus(){
       return  status;
   }
  public int idC,idTourne;
  private MyContext myContext;
  private Button buttonConfirmer,buttonRetour,buttonAnnulation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infocammande);
         nomClient = findViewById(R.id.nomClient);
         adress = findViewById(R.id.addresCommande);
         status = findViewById(R.id.etatCommande);
         nomClient.setText(getIntent().getStringExtra("NomClient"));
         adress.setText(getIntent().getStringExtra("Adress"));
         status.setText(getStatus(getIntent().getIntExtra("status",1)));
        idC = getIntent().getIntExtra("idC",1);
        idTourne = getIntent().getIntExtra("idT",1);
        myContext = (MyContext) getApplicationContext();
        ActionButton actionButton = new ActionButton(myContext);
        actionButton.setInfoCommande(this);
        buttonRetour = findViewById(R.id.buttonRetour2);
        buttonConfirmer = findViewById(R.id.buttonValider);
        buttonConfirmer.setOnClickListener(actionButton);
        buttonRetour.setOnClickListener(actionButton);
        buttonAnnulation = findViewById(R.id.buttonAnnuler);
        buttonAnnulation.setOnClickListener(actionButton);

    }
    public String getStatus(int i){
        switch (i){
            case  0:
                status.setTextColor(Color.parseColor("#B0A8A6"));
                return  "En Cours";
            case 1:
                status.setTextColor(Color.parseColor("#ABFF38"));
                return "Réaliser";
            case 2 :
                status.setTextColor(Color.parseColor("#FE1C02"));
                return  "échecs";

            default:
                return "";
        }
    }

}
