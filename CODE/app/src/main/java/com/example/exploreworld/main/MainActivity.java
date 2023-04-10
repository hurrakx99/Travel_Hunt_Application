package com.example.exploreworld.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.exploreworld.R;
import com.example.exploreworld.authentication.Customer_Registration;
import com.example.exploreworld.authentication.Customer_login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoast.StyleableToast;
import com.tomer.fadingtextview.FadingTextView;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    Button btn,btn2;
    ImageView imageView;
    Animation fromleft,fromright;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btn=findViewById(R.id.signin);
        btn2=findViewById(R.id.signup);
        imageView=findViewById(R.id.homeimg);
        Glide.with(this).load("https://image.freepik.com/free-vector/air-travel-ticket-buying-app-people-buying-tickets-online-phone-booking-service-tourism-vacation-travel-concept-illustration-flight-search-tool-tourists-making-reservation_229548-132.jpg").into(imageView);

        fromleft= AnimationUtils.loadAnimation(this,R.anim.fromleftside);
        fromright= AnimationUtils.loadAnimation(this,R.anim.fromrightside);
        btn.startAnimation(fromright);
        btn2.startAnimation(fromleft);

        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Customer_login.class));
                    finish();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Customer_Registration.class));
                    finish();
                }
            });
        }
        else
        {
            StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
        }
    }



}