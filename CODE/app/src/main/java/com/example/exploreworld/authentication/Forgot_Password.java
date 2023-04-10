package com.example.exploreworld.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.exploreworld.R;
import com.example.exploreworld.main.Admin_drawer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.muddzdev.styleabletoast.StyleableToast;

public class Forgot_Password extends AppCompatActivity {
    ImageView imageView,back;
    EditText editText;
    Button send;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    ScrollView scrollView;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Customer_login.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot__password);
        imageView=findViewById(R.id.forgotimage);
        back=findViewById(R.id.forgotback);
        editText=findViewById(R.id.edforgot);
        send=findViewById(R.id.sendmail);
        scrollView=findViewById(R.id.forgotview);

        String url="https://image.freepik.com/free-vector/forgot-password-login-woman-flat-illustration_111797-214.jpg";
        Glide.with(this).load(url).into(imageView);
        progressBar=findViewById(R.id.edprocess);
        firebaseAuth= FirebaseAuth.getInstance();
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
            send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String link=editText.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(link).matches())
                {
                    Snackbar snackbar=Snackbar.make(scrollView,"Such user does not exist!",Snackbar.LENGTH_INDEFINITE);
                    snackbar.setDuration(5000);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                    snackbar.setBackgroundTint(Color.RED);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(link).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(Forgot_Password.this, "Reset Link send to your email", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(Forgot_Password.this, Customer_login.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Forgot_Password.this, "Error ! mail is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }
        else
    {
        StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
    }
    }
}