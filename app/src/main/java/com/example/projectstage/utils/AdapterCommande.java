package com.example.projectstage.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectstage.R;
import com.example.projectstage.models.Affection;
import com.example.projectstage.models.Client;
import com.example.projectstage.models.Commande;
import com.example.projectstage.models.Livreur;
import com.example.projectstage.models.Tourne;
import com.example.projectstage.models.Vehicule;
import com.example.projectstage.views.InfoCommande;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterCommande extends RecyclerView.Adapter<AdapterCommande.NewsCommande> {
   private List<Affection> affections;
  private Context context;
    public AdapterCommande(Context context1) {
   this.context = context1;
   affections = new ArrayList<>();

    }

    public void setAffections(List<Affection> affections) {
        this.affections = affections;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsCommande onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View layouut;
  layouut  = LayoutInflater.from(context).inflate(R.layout.itemcommande,parent,false);
        return new NewsCommande(layouut);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCommande holder, final int position) {

        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationrecylew));
        holder.nomC.setText((affections.get(position).getCommande().getClient().getNom()).toUpperCase()+" "+(affections.get(position).getCommande().getClient().getPrenom()).toUpperCase());
        holder.addresClient.setText(affections.get(position).getCommande().getAdress().toLowerCase());
        holder.status.setText(getStatus(affections.get(position).getStatus(),holder));
        holder.view.setText(position+"");
        holder.date.setText(affections.get(position).getCommande().getDateMin()+" - "+affections.get(position).getCommande().getDateMin());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  intent = new Intent(context, InfoCommande.class);
                intent.putExtra("NomClient",(affections.get(position).getCommande().getClient().getNom()).toUpperCase()+" "+(affections.get(position).getCommande().getClient().getPrenom()).toUpperCase());
                intent.putExtra("Adress",affections.get(position).getCommande().getAdress().toLowerCase());
                intent.putExtra("status",affections.get(position).getStatus());
                intent.putExtra("idC",affections.get(position).getCommande().getIdCommande());
                intent.putExtra("idT",affections.get(position).getTourne().getIdTourne());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return affections.size();
    }
    public String getStatus(int i,NewsCommande Hol){
        switch (i){
            case  0:
                Hol.status.setTextColor(Color.parseColor("#B0A8A6"));
                return  "En Cours";
            case 1:
                Hol.status.setTextColor(Color.parseColor("#ABFF38"));
                return "Réaliser";
            case 2 :
                Hol.status.setTextColor(Color.parseColor("#FE1C02"));
                return  "échecs";

                default:
                    return "";
        }
    }

    public class NewsCommande extends  RecyclerView.ViewHolder {
        private TextView nomC,addresClient,date,status;
        private TextView view;
        private LinearLayout linearLayout;
        private RelativeLayout relativeLayout;
        public NewsCommande(@NonNull View itemView) {
            super(itemView);
            nomC = itemView.findViewById(R.id.nomClient);
            addresClient = itemView.findViewById(R.id.addresCommande);
            status = itemView.findViewById(R.id.etatCommande);
            view = itemView.findViewById(R.id.orderC);
            linearLayout = itemView.findViewById(R.id.container1);
            relativeLayout = itemView.findViewById(R.id.relative);
            status =itemView.findViewById(R.id.stateCommande);
            date = itemView.findViewById(R.id.etatCommande);

        }
    }
    public void remove(int position) {
        if (position < 0 || position >= affections.size()) {
            return;
        }
        affections.remove(position);
        notifyItemRemoved(position);
    }

}
