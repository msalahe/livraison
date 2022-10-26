package com.example.projectstage.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectstage.R;
import com.example.projectstage.models.Commande;
import com.example.projectstage.models.DateCommande;
import com.example.projectstage.views.InfoCommande;
import com.example.projectstage.views.Item1Commande;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AdapterCommandeInial extends RecyclerView.Adapter<AdapterCommandeInial.CommandeIniliser>{
    private List<Commande> commandes ;
    private MyContext myContext;
    private List<Integer> positios = new ArrayList<>();
    public   void setCommandes(List<Commande> commandes1){
        this.commandes = commandes1;
        notifyDataSetChanged();

    }
    private Context context;
    public  AdapterCommandeInial(Context context,MyContext myContext){
        this.context = context;
        commandes = new ArrayList<>();
        this.myContext = myContext;
    }
    @NonNull
    @Override
    public CommandeIniliser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layouut;
        layouut  = LayoutInflater.from(context).inflate(R.layout.itemcommande,parent,false);
        return new AdapterCommandeInial.CommandeIniliser(layouut);
    }

    @Override
    public void onBindViewHolder(@NonNull CommandeIniliser holder, int position) {
        String dateMax = commandes.get(position).getDateMax();
        String dateMin = commandes.get(position).getDateMin();
        DateCommande dateCommande = null;
        try {
             dateCommande = new DateCommande(dateMax,dateMin);
            boolean drapo = dateCommande.exicts();

            if(drapo == true){
                holder.relativeLayout.setEnabled(true);
                Log.d("sala",commandes.get(position).getIdCommande()+"");
                holder.isSwipeable = true;
            }else{
                holder.relativeLayout.setEnabled(false);
                holder.linearLayout.setBackgroundColor(0x88000000);
                positios.add(position);
                holder.isSwipeable = false;
            }
        }catch (Exception e){
            Log.d("sa",e.getMessage());
        }

        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationrecylew));
        holder.nomC.setText((commandes.get(position).getClient().getNom()).toUpperCase()+" "+(commandes.get(position).getClient().getPrenom()).toUpperCase());
        holder.addresClient.setText(commandes.get(position).getAdress().toLowerCase());
        holder.date.setText(dateMin+"-"+dateMax);
        holder.view.setText(commandes.get(position).getIdCommande()+"");
        holder.state = 0;
        DateCommande finalDateCommande = dateCommande;
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posit = Integer.parseInt(holder.view.getText().toString());
               int  position1 = 0;
                for(int i=0;i<commandes.size();i++){
                    if(commandes.get(i).getIdCommande() == posit){
                         position1 = i;
                    }
                }

                Intent intent = new Intent(context, Item1Commande.class);
                intent.putExtra("NomClient",(commandes.get(position1).getClient().getNom()).toUpperCase()+" "+(commandes.get(position1).getClient().getPrenom()).toUpperCase());
                intent.putExtra("Adress",commandes.get(position1).getAdress().toLowerCase());
                intent.putExtra("status",commandes.get(position1).getStatus());
                intent.putExtra("dateMin",commandes.get(position1).getDateMin());
                intent.putExtra("dateMax",commandes.get(position1).getDateMax());
                context.startActivity(intent);
            }
        });
        holder.status.setText(getStatus(commandes.get(position).getStatus(),holder));




    }
    public String getStatus(int i, AdapterCommandeInial.CommandeIniliser Hol){
        switch (i){

            case 1:
                Hol.status.setTextColor(Color.parseColor("#04F704"));
                return "Prêt à Charger";
            case 2 :
                Hol.status.setTextColor(Color.parseColor("#FFA200"));
                return  "Reprogrammer";

            default:
                return "";
        }
    }
    @Override
    public int getItemCount() {
        return commandes.size();
    }

    public class CommandeIniliser extends  RecyclerView.ViewHolder {
        private TextView nomC,addresClient,date,status;
        private TextView view;
        private LinearLayout linearLayout;
        private RelativeLayout relativeLayout;
        public boolean isSwipeable;

        public  int state;
        public CommandeIniliser(@NonNull View itemView) {
            super(itemView);
            nomC = itemView.findViewById(R.id.nomClient);
            addresClient = itemView.findViewById(R.id.addresCommande);
            date = itemView.findViewById(R.id.etatCommande);
            view = itemView.findViewById(R.id.orderC);
            linearLayout = itemView.findViewById(R.id.container1);
            relativeLayout = itemView.findViewById(R.id.relative);
   status =itemView.findViewById(R.id.stateCommande);
        }
    }
    public void remove(int position) {
        if (position < 0 || position >= commandes.size()) {
            return;
        }
       myContext.addC(commandes.get(position));
        commandes.remove(position);
        notifyItemRemoved(position);
    }



}
