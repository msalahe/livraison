package com.example.projectstage.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectstage.R;
import com.example.projectstage.models.Tourne;
import com.example.projectstage.utils.ActionButton;
import com.example.projectstage.utils.MyContext;
import com.example.projectstage.utils.SessionManager;
import com.example.projectstage.viewmodels.ViewModelsEpicierVert;

public class Choix extends AppCompatActivity {
     private Button btnPriseCharger;
     private MyContext myContext;
     private Tourne  tourne1 ;
     public int drapoo=0;

     private Button buttonCommanceMaTourne;
    private ViewModelsEpicierVert viewModelsEpicierVert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitychoix);
        btnPriseCharger = findViewById(R.id.priseCharge);
        ActionButton actionButton = new ActionButton(myContext);
        SessionManager sessionManager = SessionManager.getInstance();

        viewModelsEpicierVert = ViewModelProviders.of(this).get(ViewModelsEpicierVert.class);
        int  idL =  sessionManager.getPreferences1(this,"idLivreur");
        viewModelsEpicierVert.getDataTourne(idL).observe(this, new Observer<Tourne>() {
            @Override
            public void onChanged(Tourne tourne) {
                drapoo=1;
                Log.d("tourne",tourne.getIdTourne()+"");
                myContext.setIdTourne(tourne.getIdTourne());
            }

        });
        actionButton.SetMainActivity(this);
        btnPriseCharger.setOnClickListener(actionButton);
        myContext  = (MyContext) getApplication();
        buttonCommanceMaTourne = findViewById(R.id.commencamatourne);
        buttonCommanceMaTourne.setOnClickListener(actionButton);
        Log.d("",myContext.getCommandes().size()+"");

        if(myContext.drapo==0 ){
        buttonCommanceMaTourne.setEnabled(false);
    }else if(myContext.drapo == 1){
            Log.d("enable",myContext.getCommandes().size()+"");
        buttonCommanceMaTourne.setEnabled(true);
       for(int i=0;i<myContext.getCommandes().size();i++){
          viewModelsEpicierVert.getData_reponse(myContext.getCommandes().get(i).getIdCommande(),4);
           int idComm= myContext.getCommandes().get(i).getIdCommande();
           viewModelsEpicierVert.inserDataAffec(idComm,myContext.getIdTourne());
       }
    }

    }
    public void showCustomDialog() {
        SessionManager sessionManager = SessionManager.getInstance();
        String nom  =   sessionManager.getPreferences(this,"nom");
        String prenom  =   sessionManager.getPreferences(this,"prenom");
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.custompopup, viewGroup, false);
        TextView textView;
        textView = (TextView) dialogView.findViewById(R.id.poupText);
        Button buttonPo = dialogView.findViewById(R.id.buttonOk);
   buttonPo.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           finish();
       }
   });
        textView.setText(nom.toUpperCase()+" "+prenom.toUpperCase()+" Tu Ne Aucun Tourne Maintenat Veulliez Entre Plus tard");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        if(drapoo==0)
           alertDialog.show();
    }
}
