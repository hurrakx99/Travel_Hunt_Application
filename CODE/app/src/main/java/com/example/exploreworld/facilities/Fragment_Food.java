package com.example.exploreworld.facilities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.exploreworld.R;
import com.example.exploreworld.menu.Facilities_Activity;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Food extends Fragment {
    ImageView imageView1,imageView2,imageView3,imageView4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment__food, container, false);
        imageView1=view.findViewById(R.id.veganimg);
        imageView2=view.findViewById(R.id.nonvegimg);
        imageView3=view.findViewById(R.id.vegimg);
        imageView4=view.findViewById(R.id.foodback);
        Glide.with(this).load("https://i.dlpng.com/static/png/7267478_preview.png").into(imageView1);
        Glide.with(this).load("https://www.teluguone.com/recipes/img/NonVegetarianBanner.png").into(imageView2);
        Glide.with(this).load("https://us.123rf.com/450wm/paulbrighton/paulbrighton1512/paulbrighton151200244/49876625-vegetarian-curries-selection-of-south-asian-vegetarian-curries-in-white-bowls-paneer-makhani-palak-p.jpg?ver=6").into(imageView3);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Facilities_Activity.class));
            }
        });
        return view;
    }

}