package com.example.exploreworld.firebasepackageload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.exploreworld.R;
import com.google.firebase.database.DatabaseReference;

import net.cachapa.expandablelayout.ExpandableLayout;

public class Choose_resort extends AppCompatActivity {
    ImageView imageView1,imageView2;
    ExpandableLayout expandableLayout,expandableLayout2;
    int sum1=0,sum2=0;
    CardView cardView1,cardView2;
    RatingBar ratingBar1,ratingBar2;
    TextView tvtitle,tvlocation,txtrating,tvdescription,tvprice,tvexpand1,tvtitle2,tvlocation2,txtrating2,tvdescription2,tvexpand2,tvprice2;
    String packagetitle,daynight,resorttitle,semiresorttitle,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_resort);
        imageView1=findViewById(R.id.resortdeluximg);
        imageView2=findViewById(R.id.semideluximg);
        cardView1=findViewById(R.id.resortdulex);
        cardView2=findViewById(R.id.resortsemidelux);
        expandableLayout2=findViewById(R.id.semimyexpandable);
        expandableLayout=findViewById(R.id.dulexmyexpandable);
        tvtitle=findViewById(R.id.resortdeluxtitle);
        tvlocation=findViewById(R.id.dulexlocation);
        txtrating=findViewById(R.id.dulexratetxt);
        tvprice=findViewById(R.id.dulexprice);
        tvexpand1=findViewById(R.id.readmore);
        tvdescription=findViewById(R.id.dulexdescription);
        tvtitle2=findViewById(R.id.semiresortdeluxtitle);
        tvlocation2=findViewById(R.id.semidulexlocation);
        txtrating2=findViewById(R.id.semidulexratetxt);
        tvprice2=findViewById(R.id.semidulexprice);
        tvdescription2=findViewById(R.id.semidulexdescription);
        tvexpand2=findViewById(R.id.semireadmore);
        ratingBar1=findViewById(R.id.dulexrate);
        ratingBar2=findViewById(R.id.semidulexrate);

        tvexpand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout.toggle();

            }
        });
        tvexpand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout2.toggle();
            }
        });


        Bundle bundle=getIntent().getExtras();
        String packageprice=bundle.getString("Price");
        String resortloc=bundle.getString("Resort location");
        String resortdesc=bundle.getString("Resort description");
        String resortprice=bundle.getString("Resort price");
        String resortrating=bundle.getString("Resort rating");
        String resortimg=bundle.getString("Resort Image");
        String semiresortloc=bundle.getString("Resort semi location");
        String semiresortdesc=bundle.getString("Resort  semi description");
        String semiresortprice=bundle.getString("Resort semi price");
        String semiresortimg=bundle.getString("Resort semi Image");
        String semiresortrating=bundle.getString("Resort semi rating");
        image=bundle.getString("title image");
        packagetitle=bundle.getString("package title");
        resorttitle=bundle.getString("resort Title");
        semiresorttitle=bundle.getString("Resort semi name");
        daynight=bundle.getString("Day night");


        sum1=Integer.parseInt(resortprice)+Integer.parseInt(packageprice);
        sum2=Integer.parseInt(semiresortprice)+Integer.parseInt(packageprice);

        tvtitle.setText(resorttitle);
        tvtitle2.setText(semiresorttitle);
        tvlocation.setText(resortloc);
        tvlocation2.setText(semiresortloc);
        tvprice.setText(resortprice);
        tvprice2.setText(semiresortprice);
        tvdescription.setText(resortdesc);
        tvdescription2.setText(semiresortdesc);
        ratingBar1.setRating(Float.parseFloat(resortrating));
        txtrating.setText(resortrating);
        txtrating2.setText(semiresortrating);
        ratingBar2.setRating(Float.parseFloat(semiresortrating));
        Glide.with(this).load(resortimg).into(imageView1);
        Glide.with(this).load(semiresortimg).into(imageView2);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Fill_details.class);
                i.putExtra("package title",packagetitle);
                i.putExtra("resort Title",resorttitle);
                i.putExtra("Day night",daynight);
                i.putExtra("Sum",String.valueOf(sum1));
                i.putExtra("title img",image);
                startActivity(i);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Fill_details.class);
                i.putExtra("package title",packagetitle);
                i.putExtra("resort Title",semiresorttitle);
                i.putExtra("Day night",daynight);
                i.putExtra("Sum",String.valueOf(sum2));
                i.putExtra("title img",image);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}