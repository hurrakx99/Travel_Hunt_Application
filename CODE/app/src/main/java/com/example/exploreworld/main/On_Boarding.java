package com.example.exploreworld.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.exploreworld.Model.ScreenItem;
import com.example.exploreworld.R;
import com.example.exploreworld.adapters.IntroViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class On_Boarding extends AppCompatActivity {
    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    List<ScreenItem> list;
    TabLayout tabLayout;
    Button getstarted;
    TextView btnnext,back,skip;
    int position=0;
    Animation btnanimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on__boarding);
        tabLayout=findViewById(R.id.tab_indicator);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnnext=findViewById(R.id.btn_onnext);
        getstarted=findViewById(R.id.btn_getstarted);
        back=findViewById(R.id.intro_back);
        skip=findViewById(R.id.intro_skip);
        btnanimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_animation);
        list=new ArrayList<>();
        list.add(new ScreenItem("WELCOME TO EXPLORE","Best platform to complete your dream with us with various great facilities","https://static.vecteezy.com/system/resources/previews/000/450/920/non_2x/man-with-laptop-sitting-in-nature-and-leaves-concept-illustration-for-working-freelancing-studying-education-work-from-home-vector-illustration-in-flat-cartoon-style.jpg"));
        list.add(new ScreenItem("BOOK YOUR TRIP","Book your trip with us. Booking all destination and hotel tickets at the best prices.","https://image.freepik.com/free-vector/air-travel-ticket-buying-app-people-buying-tickets-online-phone-booking-service-tourism-vacation-travel-concept-illustration-flight-search-tool-tourists-making-reservation_229548-132.jpg"));
        list.add(new ScreenItem("GET READY TO TRAVEL","Choose your destination, plan your trip Pick the best place for your holiday.","https://cdni.iconscout.com/illustration/premium/thumb/tourist-finding-location-with-help-of-mobile-gps-2357764-2012388.png"));
        list.add(new ScreenItem("ENJOY YOUR TRIP","Choose your destination, plan your trip Pick the best place for your holiday.","https://cdni.iconscout.com/illustration/premium/thumb/online-flight-traveling-ticket-booking-1917437-1632710.png"));
        screenPager=findViewById(R.id.intro_viewpager);
        introViewPagerAdapter=new IntroViewPagerAdapter(this,list);
        screenPager.setAdapter(introViewPagerAdapter);

        tabLayout.setupWithViewPager(screenPager);

        //next button
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position<list.size())
                position=screenPager.getCurrentItem();
                {
                    position++;
                    screenPager.setCurrentItem(position);
                    back.setVisibility(View.VISIBLE);
                }
                if(position==list.size()-1)
                {
                    loadLastScreen();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position=screenPager.getCurrentItem();
                screenPager.setCurrentItem(position-1);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==list.size()-1)
                {
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
        else
    {
        StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
    }
}

    private void loadLastScreen() {
        btnnext.setVisibility(View.INVISIBLE);
        getstarted.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
        skip.setVisibility(View.INVISIBLE);

        getstarted.setAnimation(btnanimation);


    }
}