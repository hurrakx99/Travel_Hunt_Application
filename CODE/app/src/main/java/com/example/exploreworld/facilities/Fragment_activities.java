package com.example.exploreworld.facilities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.exploreworld.R;
import com.example.exploreworld.menu.Facilities_Activity;

public class Fragment_activities extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_activities, container, false);
        ImageView imageView=view.findViewById(R.id.actiimg);
        ImageView imageView2=view.findViewById(R.id.acitback);
        Glide.with(this).load("https://www.japabalitours.com/wp-content/uploads/2016/01/bali-water-sports-tour.jpg").into(imageView);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Facilities_Activity.class));
            }
        });

        return view;
    }
}