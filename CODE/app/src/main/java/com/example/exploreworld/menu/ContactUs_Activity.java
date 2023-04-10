package com.example.exploreworld.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.exploreworld.R;
import com.example.exploreworld.main.Customer_drawer;

public class ContactUs_Activity extends AppCompatActivity {
    ImageView Ivfacebook, IvbackImage,Ivinstagram,Ivtwitter,Ivyoutube,imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_);
            imageView=findViewById(R.id.logo);
                IvbackImage = findViewById(R.id.contactback);
                Ivfacebook = findViewById(R.id.aboutflink);
                Ivtwitter = findViewById(R.id.abouttwlink);
                Ivinstagram = findViewById(R.id.aboutinstalink);
                Ivyoutube = findViewById(R.id.aboutyoutubelink);
                String uri="https://silverliningstechnologies.co.za/wp-content/uploads/2020/04/Contact-Us-SLT.jpg";
        Glide.with(this).load(uri).into(imageView);

                Ivfacebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = "https://www.facebook.com/KLSGITBelagavi/";
                        Intent launch = new Intent(Intent.ACTION_VIEW);
                        launch.setData(Uri.parse(uri));
                        startActivity(launch);
                    }
                });
                Ivtwitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = "https://twitter.com/gogtebca?lang=en";
                        Intent launch = new Intent(Intent.ACTION_VIEW);
                        launch.setData(Uri.parse(uri));
                        startActivity(launch);
                    }
                });
                Ivinstagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = "https://www.instagram.com/gcc_bca/?igshid=seqddsoqxv3e&hl=en";
                        Intent launch = new Intent(Intent.ACTION_VIEW);
                        launch.setData(Uri.parse(uri));
                        startActivity(launch);
                    }
                });
                Ivyoutube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = "https://youtu.be/ZLNO2c7nqjw";
                        Intent launch = new Intent(Intent.ACTION_VIEW);
                        launch.setData(Uri.parse(uri));
                        startActivity(launch);
                    }
                });
                IvbackImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), Customer_drawer.class));
                        finish();
                    }
                });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Customer_drawer.class));
        finish();
    }
}