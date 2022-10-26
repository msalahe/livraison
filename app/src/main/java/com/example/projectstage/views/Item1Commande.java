package com.example.projectstage.views;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectstage.R;
import com.example.projectstage.utils.ActionButton;
import com.example.projectstage.utils.MyContext;

public class Item1Commande extends AppCompatActivity {
    private TextView nomClient,adress,status,dateCommande;
    private  MyContext myContext;
    private Button buttonRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infocom1);
        nomClient = findViewById(R.id.nomClient1);
        adress = findViewById(R.id.addresCommande1);
        status = findViewById(R.id.etatCommande1);
        nomClient.setText(getIntent().getStringExtra("NomClient"));
        adress.setText(getIntent().getStringExtra("Adress"));
        status.setText(getStatus(getIntent().getIntExtra("status",1)));
        dateCommande = findViewById(R.id.dateCommande1);
        dateCommande.setText(getIntent().getStringExtra("dateMin")+"  "+getIntent().getStringExtra("dateMax"));
        myContext  =(MyContext) getApplicationContext();
        ActionButton actionButton = new ActionButton(myContext);
        actionButton.setItem1Commande(this);
            buttonRouter = findViewById(R.id.buttonRetour);
            buttonRouter.setOnClickListener(actionButton);
    }
    public String getStatus(int i){
        switch (i){

            case 1:
                status.setTextColor(Color.parseColor("#04F704"));
                return "Prêt à Charger";
            case 2 :
                status.setTextColor(Color.parseColor("#FFA200"));
                return  "Reprogrammer";

            default:
                return "";
        }
    }
}

