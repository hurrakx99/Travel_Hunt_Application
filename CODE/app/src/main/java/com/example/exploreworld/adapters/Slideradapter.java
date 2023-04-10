package com.example.exploreworld.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.bumptech.glide.Glide;
import com.example.exploreworld.Model.Slider;
import com.example.exploreworld.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Slideradapter extends PagerAdapter {
    Context mContext;
    List<Slider> mListScreen;

    public Slideradapter(Context mContext, List<Slider> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }




    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.viewpager_items, null);


        ImageView imgSlide = layoutScreen.findViewById(R.id.image1);
        TextView title = layoutScreen.findViewById(R.id.pagertitle);
        TextView description = layoutScreen.findViewById(R.id.pagerdescription);

        title.setText(mListScreen.get(position).getTitle());
        description.setText(mListScreen.get(position).getSubtitle());
        Glide.with(mContext).load(mListScreen.get(position).getImageview()).into(imgSlide);

        container.addView(layoutScreen);

        return layoutScreen;


    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);

    }
}