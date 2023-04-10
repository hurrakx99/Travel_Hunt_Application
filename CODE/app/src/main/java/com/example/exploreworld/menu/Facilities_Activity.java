package com.example.exploreworld.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.exploreworld.R;
import com.example.exploreworld.facilities.Fragment_Food;
import com.example.exploreworld.facilities.Fragment_activities;
import com.example.exploreworld.facilities.Fragment_camping;
import com.example.exploreworld.facilities.Fragment_guide;
import com.example.exploreworld.facilities.Fragment_medical;
import com.example.exploreworld.main.Customer_drawer;

public class Facilities_Activity extends AppCompatActivity {

    ImageView imageView;
    LinearLayout layout1,layout2,layout3,layout4,layout5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities_);
        imageView=findViewById(R.id.faciback);
        layout1=findViewById(R.id.bookfrag);
        layout2=findViewById(R.id.foodfrag);
        layout3=findViewById(R.id.medicalfrag);
        layout4=findViewById(R.id.campfrag);
        layout5=findViewById(R.id.guidefrag);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Customer_drawer.class));
                finish();
            }
        });

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_activities fragment=new Fragment_activities();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().add(R.id.viewfacilities,fragment).commit();
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_Food fragment_food=new Fragment_Food();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().add(R.id.viewfacilities,fragment_food).commit();
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_medical fragment=new Fragment_medical();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().add(R.id.viewfacilities,fragment).commit();
            }
        });
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_camping fragment=new Fragment_camping();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().add(R.id.viewfacilities,fragment).commit();
            }
        });
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_guide fragment=new Fragment_guide();
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().add(R.id.viewfacilities,fragment).commit();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Customer_drawer.class));
        finish();
    }
}