package com.example.projectstage.views;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectstage.R;
import com.example.projectstage.utils.ActionButton;
import com.example.projectstage.utils.MyContext;
import com.example.projectstage.utils.SessionManager;
import com.example.projectstage.viewmodels.ViewModelsEpicierVert;

public class Authentification extends AppCompatActivity {
    private EditText email,mdp;
    private Button btnConnecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentification);

        btnConnecter = findViewById(R.id.btnConnecter);
        MyContext myContext = (MyContext) getApplicationContext();
        ActionButton actionButton = new ActionButton(myContext);
        actionButton.setAuthentification(this);
        btnConnecter.setOnClickListener(actionButton);


    }
}
