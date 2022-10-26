
package com.example.projectstage.utils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectstage.R;
import com.example.projectstage.models.Commande;
import com.example.projectstage.models.ResponseLogin;
import com.example.projectstage.models.Response_Success;
import com.example.projectstage.viewmodels.ViewModelsEpicierVert;
import com.example.projectstage.views.Authentification;
import com.example.projectstage.views.Choix;
import com.example.projectstage.views.HomeActivity;
import com.example.projectstage.views.InfoCommande;
import com.example.projectstage.views.Item1Commande;
import com.example.projectstage.views.PriseEnCharge;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class ActionButton implements View.OnClickListener  {
    private Choix mainActivity;
    private PriseEnCharge priseEnCharge;
    private Authentification authentification;
    private Item1Commande item1Commande;
    private InfoCommande infoCommande;
    private  RadioGroup radioGroup;
    public void setInfoCommande(InfoCommande infoCommande) {
        this.infoCommande = infoCommande;
    }

    public void setItem1Commande(Item1Commande item1Commande) {
        this.item1Commande = item1Commande;
    }

    private EditText email,mdp;
    private ViewModelsEpicierVert viewModelsEpicierVert;

    public  void setAuthentification(Authentification authentification){
        this.authentification = authentification;
    }
   private MyContext myContext;
    public PriseEnCharge getPriseEnCharge() {
        return priseEnCharge;
    }

    public void setPriseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharge = priseEnCharge;
    }

    public ActionButton(MyContext myContext){
   this.myContext = myContext;
    }
    public  void SetMainActivity(Choix activity){
        this.mainActivity = activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.priseCharge:

         if(mainActivity.drapoo == 0){
              mainActivity.showCustomDialog();
         }
         else {
             Intent  intent = new Intent(mainActivity, PriseEnCharge.class);
             mainActivity.startActivity(intent);
         }
                      break;

            case R.id.commanceauprise:
                Intent intent1 = new Intent(priseEnCharge,Choix.class);

                priseEnCharge.startActivity(intent1);

                if(myContext.getCommandes().size()<4){
                    StyleableToast.makeText(priseEnCharge,"number n'est pas suffussiant",R.style.warni).show();

                }else{
                    myContext.setE();

                }
                priseEnCharge.finish();
                break;
            case R.id.commencamatourne:

                optimiser();
                //insertAffectation();
                break;
            case R.id.btnConnecter :
                   Log();
                Test();
                 break;
            case R.id.buttonRetour:
                  item1Commande.finish();
                  break;
            case  R.id.buttonValider:
                        if(infoCommande.idTourne == myContext.getAffectionCurrent().getTourne().getIdTourne() && infoCommande.idC == myContext.getAffectionCurrent().getCommande().getIdCommande()){
                           update();
                           try {
                               myContext.setIndexEtatComa(myContext.getIndexEtatComa()+1);
                               myContext.setAffectionCurrent(myContext.getAffections().get(myContext.getIndexEtatComa()));
                           }catch (Exception e){

                           }

                        }else {
                            Toast.makeText(infoCommande, "Not Ok", Toast.LENGTH_SHORT).show();
                        }
                infoCommande.finish();
                Intent  intent4 = new Intent(infoCommande, HomeActivity.class);
                infoCommande.startActivity(intent4);
                        break;
            case R.id.buttonAnnuler:
                if(infoCommande.idTourne == myContext.getAffectionCurrent().getTourne().getIdTourne() && infoCommande.idC == myContext.getAffectionCurrent().getCommande().getIdCommande()){

                    showCustomDialog1();
                  myContext.setIndexEtatComa(myContext.getIndexEtatComa()+1);
                  myContext.setAffectionCurrent(myContext.getAffections().get(myContext.getIndexEtatComa()));
                }else {
                    Toast.makeText(infoCommande, "Not Ok", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.buttonRetour2 :
                infoCommande.finish();
            default:
                break;

        }
    }
    public  void Log(){
        email = authentification.findViewById(R.id.email);
        mdp = authentification.findViewById(R.id.mdp);
        String emai = email.getText().toString();
        String md = mdp.getText().toString();
        viewModelsEpicierVert = ViewModelProviders.of(authentification).get(ViewModelsEpicierVert.class);
        viewModelsEpicierVert.login(emai,md).observe(authentification, new Observer<ResponseLogin>() {
            @Override
            public void onChanged(ResponseLogin responseLogin) {
                if(responseLogin.isSucces()){
                    StyleableToast.makeText(authentification,"Succes",R.style.conneter).show();
                    SessionManager sessionManager = SessionManager.getInstance();
                    sessionManager.setPreferences(authentification.getApplicationContext(),responseLogin.getLivreur());
                    sessionManager.setStatut(1);
                    Intent  intent4 = new Intent(authentification, Choix.class);
                    authentification.startActivity(intent4);
                    authentification.finish();
                    authentification.findViewById(R.id.btnConnecter).setEnabled(false);

                }else{
                    StyleableToast.makeText(authentification,"Not Succes",R.style.notConnect).show();
                    authentification.findViewById(R.id.btnConnecter).setEnabled(true);

                }
            }
        });

    }
    public void update(){
        viewModelsEpicierVert = ViewModelProviders.of(infoCommande).get(ViewModelsEpicierVert.class);

        viewModelsEpicierVert.getUpadateDate(infoCommande.idC,1,"Succes").observe(infoCommande, new Observer<Response_Success>() {
            @Override
            public void onChanged(Response_Success response_success) {
                Log.d("response",response_success.getSucces());
                if(response_success.getSucces() == "succes"){
                    Toast.makeText(infoCommande, "Ok", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public  void annuler(String message){
        viewModelsEpicierVert = ViewModelProviders.of(infoCommande).get(ViewModelsEpicierVert.class);

        viewModelsEpicierVert.getUpadateDate(infoCommande.idC,2,message).observe(infoCommande, new Observer<Response_Success>() {
            @Override
            public void onChanged(Response_Success response_success) {
                Log.d("response",response_success.getSucces());
                if(response_success.getSucces() == "succes"){
                    Toast.makeText(infoCommande, "Ok annulation", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void insertAffectation(){
        viewModelsEpicierVert = ViewModelProviders.of(mainActivity).get(ViewModelsEpicierVert.class);
        for(int i=0;i<myContext.getCommandes().size();i++){
            int idComm= myContext.getCommandes().get(i).getIdCommande();
            viewModelsEpicierVert.inserDataAffec(idComm,myContext.getIdTourne()).observe(mainActivity, new Observer<Response_Success>() {
                @Override
                public void onChanged(Response_Success response_success) {
                    Log.d("succes",response_success.getSucces());
                }
            });
        }

    }
    public void Test(){
        if(email.getText().toString().equals("")){
            email.setError("s'il vous Plait entre votre email");
        }
        if(mdp.getText().toString().equals("")){
            mdp.setError("s'il vous Plait entre votre mode de passe");
        }
    }
    public void showCustomDialog1() {
   RadioGroup radioGroup;
        ViewGroup viewGroup = infoCommande.findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(infoCommande).inflate(R.layout.popupanullercommande, viewGroup, false);


        AlertDialog.Builder builder = new AlertDialog.Builder(infoCommande);

        builder.setView(dialogView);
        Button buttonA = dialogView.findViewById(R.id.annpop);
        radioGroup = dialogView.findViewById(R.id.radiogroupe1);

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = radioGroup.getCheckedRadioButtonId();
                RadioButton  radioButton = dialogView.findViewById(i);

                annuler(radioButton.getText().toString());
                infoCommande.finish();
                Intent  intent3 = new Intent(infoCommande, HomeActivity.class);
                infoCommande.startActivity(intent3);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
       public void optimiser(){
           ProgressDialog progressDialog  = new ProgressDialog(mainActivity);
           progressDialog.show();
           progressDialog.setContentView(R.layout.customoptimisationpopup);
           progressDialog.getWindow().setBackgroundDrawableResource(
                   android.R.color.transparent
           );
           Runnable runnable =new Runnable() {
               @Override
               public void run() {
                   Intent  intent3 = new Intent(mainActivity, HomeActivity.class);
                   mainActivity.startActivity(intent3);
                   mainActivity.finish();
               }
           };
           new Handler().postDelayed(runnable,10000);

       }
}