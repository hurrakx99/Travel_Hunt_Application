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

public class Fragment_medical extends Fragment {
    ImageView imageView,imageView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_medical, container, false);
        imageView=view.findViewById(R.id.medicimg);
        Glide.with(this).load("https://e1.nmcdn.io/oswaldcompanies/wp-content/uploads/2020/03/Blog_Emergency_Prep.jpg/v:1-width:1900-height:1047-fit:cover/Blog_Emergency_Prep.jpg?signature=59c125ded53b835fd566de03862eaefe3bf91f673cf7ea69010b9228a9463ed7").into(imageView);
        imageView2=view.findViewById(R.id.medicalback);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Facilities_Activity.class));
            }
        });
        return view;
    }
}