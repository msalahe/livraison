package com.example.projectstage.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.projectstage.R;
import com.example.projectstage.utils.SessionManager;

public class MainActivity extends AppCompatActivity {
    private Animation anButton;
   private ImageView btnC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnC = findViewById(R.id.imageView);
        anButton = AnimationUtils.loadAnimation(this,R.anim.translate);
        btnC.setAnimation(anButton);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.getStatut() == 0){
                Intent intent = new Intent(getApplicationContext(),Authentification.class);
                startActivity(intent);
                finish();
        }else {
       Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
       startActivity(intent);
       finish();
       }
            }
        };
  new Handler().postDelayed(runnable,10000);
    }
}
