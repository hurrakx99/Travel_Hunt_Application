package com.example.exploreworld.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.exploreworld.R;
import com.example.exploreworld.deletepackages.Domestic_Delete_Edit;
import com.example.exploreworld.deletepackages.Fort_Delete_Edit;
import com.example.exploreworld.deletepackages.International_Delete_Edit;
import com.example.exploreworld.deletepackages.Popular_Delete_Edit;
import com.example.exploreworld.deletepackages.Religious_Delete_Edit;
import com.example.exploreworld.deletepackages.Wonder_Delete_Edit;
import com.example.exploreworld.main.Admin_drawer;
import com.example.exploreworld.main.Customer_drawer;
import com.example.exploreworld.viewall.Domestic_Viewall;
import com.example.exploreworld.viewall.International_viewall;
import com.example.exploreworld.viewall.Popular_viewall;
import com.example.exploreworld.viewall.Religious_viewall;

public class Delete_Edit_Package extends AppCompatActivity {
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete__edit__package);
        imageView1=findViewById(R.id.catodomesticimg);
        imageView2=findViewById(R.id.cateinternationalimg);
        imageView3=findViewById(R.id.catereligiousimg);
        imageView4=findViewById(R.id.catepopularimg);
        imageView5=findViewById(R.id.catewondersimg);
        imageView6=findViewById(R.id.catefortimg);
        imageView7=findViewById(R.id.catelehimg);
        imageView8=findViewById(R.id.cateback);
        Glide.with(this).load("https://images.thrillophilia.com/image/upload/s--oAt1mryM--/c_fill,f_auto,fl_strip_profile,h_775,q_auto,w_1600/v1/images/photos/000/052/793/original/1556370242_shutterstock_780181144.jpg.jpg?1556370242").into(imageView1);
        Glide.with(this).load("https://cdn.londonandpartners.com/-/media/images/london/study/summer-schools-campaign/summer-schools-landing/summer-schools-homepage-640x360.jpg?h=360&amp;la=en&amp;w=640&amp;hash=9F9C3C7A79DD6CA2B53948E9CBF42833FE588111").into(imageView2);
        Glide.with(this).load("https://images.squarespace-cdn.com/content/v1/5a9fd56f266c0787b01e18d4/1528730986518-G2QTPYG7DVTYJ5N4RO9Q/ke17ZwdGBToddI8pDm48kOdzDdw5b51qd8LWcq9GF9ZZw-zPPgdn4jUwVcJE1ZvWQUxwkmyExglNqGp0IvTJZUJFbgE-7XRK3dMEBRBhUpwb7kkYSBZ-vesKghH-TzLiRZDvSNo4q94t0OghEso8pDw5dpFNrqxK_jvP4ywHtms/17038811266_b95cdd7e18_z.jpg").into(imageView3);
        Glide.with(this).load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQrVlmwLa4ZcS3NqX1Uj6f8xvqKrDVIrP85IA&amp;usqp=CAU").into(imageView4);
        Glide.with(this).load("https://static2.tripoto.com/media/filter/tst/img/308363/TripDocument/1501662617_wotw1.jpg").into(imageView5);
        Glide.with(this).load("https://static.toiimg.com/photo/73698308.cms").into(imageView6);
        Glide.with(this).load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ2F9RLB5MMdwLMiYz8xmmTJLhlSTujCHoImg&amp;usqp=CAU").into(imageView7);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Domestic_Delete_Edit.class));
                finish();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), International_Delete_Edit.class));
                finish();
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Religious_Delete_Edit.class));
                finish();
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Popular_Delete_Edit.class));
                finish();
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Wonder_Delete_Edit.class));
                finish();
            }
        });
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Fort_Delete_Edit.class));
                finish();
            }
        });
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_drawer.class));
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Admin_drawer.class));
        finish();
    }
}