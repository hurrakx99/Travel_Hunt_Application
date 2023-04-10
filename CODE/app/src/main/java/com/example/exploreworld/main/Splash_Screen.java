package com.example.exploreworld.main;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader;
import com.example.exploreworld.R;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.muddzdev.styleabletoast.StyleableToast;
import com.tomer.fadingtextview.FadingTextView;

public class Splash_Screen extends AppCompatActivity {
    SharedPreferences pref;
    FirebaseUser user;
    FirebaseAuth auth;
    private FadingTextView fadingTextView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        CircularDotsLoader loader = findViewById(R.id.progress_circular);
        progressBar=findViewById(R.id.spin_kit);
        fadingTextView=findViewById(R.id.text1);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
            FadingCircle fadingCircle=new FadingCircle();
            progressBar.setIndeterminateDrawable(fadingCircle);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    pref = getSharedPreferences("myPrefs", MODE_PRIVATE);
                    Boolean isInstroOpened = pref.getBoolean("isInstroOpened", true);
                    if (user != null && user.isEmailVerified()) {
                        startActivity(new Intent(Splash_Screen.this, Customer_drawer.class));
                        finish();
                    } else if (isInstroOpened) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("isInstroOpened", false);
                        editor.commit();
                        startActivity(new Intent(getApplicationContext(), On_Boarding.class));
                        finish();
                    } else {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }
            }, 4700);
        }
        else
        {
            loader.setVisibility(View.INVISIBLE);
            StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
        }
    }

}