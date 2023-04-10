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
import com.example.exploreworld.Model.ScreenItem;
import com.example.exploreworld.R;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context context;
    List<ScreenItem> screenItemList;

    public IntroViewPagerAdapter(Context context, List<ScreenItem> screenItemList) {
        this.context = context;
        this.screenItemList = screenItemList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen=inflater.inflate(R.layout.layoutscreen,null);

        ImageView imgslide=layoutScreen.findViewById(R.id.intro_img);
        TextView title=layoutScreen.findViewById(R.id.intro_title);
        TextView description=layoutScreen.findViewById(R.id.intro_description);

        title.setText(screenItemList.get(position).getIntroTitle());
        description.setText(screenItemList.get(position).getIntroDescription());
        Glide.with(context).load(screenItemList.get(position).getScreenImg()).into(imgslide);
        container.addView(layoutScreen);

        return layoutScreen;

    }

    @Override
    public int getCount() {
        return screenItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
