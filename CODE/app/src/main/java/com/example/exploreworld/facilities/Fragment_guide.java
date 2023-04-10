package com.example.exploreworld.facilities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.exploreworld.R;
import com.example.exploreworld.menu.Facilities_Activity;

public class Fragment_guide extends Fragment {

    ImageView imageView,imageView2;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_guide, container, false);
        imageView=view.findViewById(R.id.tguideimg);
        Glide.with(this).load("https://www.encounterstravel.com/sysimages/rszimages/encounters-group-karnak-luxor-egypt_tg_2.jpg").into(imageView);
        imageView2=view.findViewById(R.id.guideback);
        textView=view.findViewById(R.id.txtguide);
        textView.setText("      Tour Guides usually perform many of the following tasks: Describing places to people. Greeting tourists groups. Providing safety devises.Usually guide planning travel itineraries, familiarizing customers with the locality by vehicle or foot, and ensuring that the group remains safe at all times. You should also stay up-to-date with new attractions that may be of interest to customers.\n      Basically provides assistance, information on cultural, historical and contemporary heritage to people on organized tours and individual clients at educational establishments, religious and historical sites, museums, and at venues of other significant interest, attractions sites.");
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Facilities_Activity.class));
            }
        });
        return view;
    }
}