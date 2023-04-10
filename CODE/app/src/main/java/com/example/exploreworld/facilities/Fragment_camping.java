package com.example.exploreworld.facilities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.exploreworld.R;
import com.example.exploreworld.menu.Facilities_Activity;

public class Fragment_camping extends Fragment {
    ImageView imageView,imageView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_camping, container, false);
        imageView=view.findViewById(R.id.campimge);
        Glide.with(this).load("https://www.outsideonline.com/sites/default/files/styles/full-page/public/2020/06/19/glamping-ventana-california-camping_h.jpg?itok=O-Qa9XxF").into(imageView);
        imageView2=view.findViewById(R.id.campback);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Facilities_Activity.class));
            }
        });
        return view;
    }
}