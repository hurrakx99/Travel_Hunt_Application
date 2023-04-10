package com.example.exploreworld.firebasepackageload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.exploreworld.Model.PackageRating;
import com.example.exploreworld.R;
import com.example.exploreworld.main.Customer_drawer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;

import net.cachapa.expandablelayout.ExpandableLayout;

public class Religious_Package_Details extends AppCompatActivity {
    TextView tvtitle,tvsubtitle,tvdescription,tvtips,tvday,tvprice,tvratetxt;
    private Button btnbook;
    ImageView img1,img2,img3,img4,img5;
    RatingBar ratingBar;
    public static String Title="title";
    public static String Location="location";
    public static String Price="price";
    public static String Daynight="dayngt";
    public static String Description="description";
    public static String Tips="Tips";
    public static String Image="image";
    public static String Image1="image1";
    public static String Image2="image2";
    public static String Image3="image3";
    public static String Image4="image4";
    public static String Resortimage="resortimage";
    public static String Resorttitle="resorttitle";
    public static String Resortlocation="resortlocation";
    public static String Resortdescription="resortdescription";
    public static String Resortprice="resortprice";
    public static String Resortrating="resortrating";
    public static String semiResortimage="semiresortimage";
    public static String semiResorttitle="semiresorttitle";
    public static String semiResortlocation="semiresortlocation";
    public static String semiResortdescription="semiresortdescription";
    public static String semiResortprice="semiresortprice";
    public static String semiResortrating="semiresortrating";

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference ratingdatabase;
    FirebaseDatabase database;

    String valueitem;
    String title,subtitle,price,dayngt,image,image1,image2,image3,image4,description,tips,strdulexprice,strsemiprice,strresortimage,strresorttitle,strresortlocation,strresortdescription,strresortrating,strsemiresortimage,strsemiresorttitle,strsemiresortlocation,strsemiresortdescription,strsemiresortrating;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Customer_drawer.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_religious__package__details);
        ratingBar=findViewById(R.id.relidetailrate);
        tvtitle=findViewById(R.id.relidetailtitle);
        tvsubtitle=findViewById(R.id.relidetailsubtitle);
        tvdescription=findViewById(R.id.relidetaildescription);
        tvprice=findViewById(R.id.relidetailprice);
        tvday=findViewById(R.id.relidetaildn);
        tvtips=findViewById(R.id.relidetailtips);
        tvratetxt=findViewById(R.id.relidetailratetxt);
        img1=findViewById(R.id.relidetailimage1);
        img2=findViewById(R.id.relidetailimage2);
        img3=findViewById(R.id.relidetailimage3);
        img4=findViewById(R.id.relidetailimage4);
        img5=findViewById(R.id.relidetailimage5);
        btnbook=findViewById(R.id.btnrelibook);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        database=FirebaseDatabase.getInstance();
        ratingdatabase=database.getReference("Rating").child("packagerating");

        title=getIntent().getStringExtra(Title);
        subtitle=getIntent().getStringExtra(Location);
        price=getIntent().getStringExtra(Price);
        dayngt=getIntent().getStringExtra(Daynight);
        description=getIntent().getStringExtra(Description);
        tips=getIntent().getStringExtra(Tips);
        image=getIntent().getStringExtra(Image);
        image1=getIntent().getStringExtra(Image1);
        image2=getIntent().getStringExtra(Image2);
        image3=getIntent().getStringExtra(Image3);
        image4=getIntent().getStringExtra(Image4);
       

        strresortimage=getIntent().getStringExtra(Resortimage);
        strresorttitle=getIntent().getStringExtra(Resorttitle);
        strresortlocation=getIntent().getStringExtra(Resortlocation);
        strresortdescription=getIntent().getStringExtra(Resortdescription);
        strdulexprice=getIntent().getStringExtra(Resortprice);
        strresortrating=getIntent().getStringExtra(Resortrating);
        strsemiresorttitle=getIntent().getStringExtra(semiResorttitle);
        strsemiresortimage=getIntent().getStringExtra(semiResortimage);
        strsemiresortlocation=getIntent().getStringExtra(semiResortlocation);
        strsemiresortdescription=getIntent().getStringExtra(semiResortdescription);
        strsemiprice=getIntent().getStringExtra(semiResortprice);
        strsemiresortrating=getIntent().getStringExtra(semiResortrating);

        tvtitle.setText(title);
        tvsubtitle.setText(subtitle);
        tvprice.setText(price);
        tvday.setText(dayngt);
        tvdescription.setText(description);
        tvtips.setText(tips);
    
        Query query=ratingdatabase.orderByChild("packageId").equalTo(title);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    PackageRating item=snapshot.getValue(PackageRating.class);
                    valueitem=item.getRatevalue();
                    tvratetxt.setText(valueitem);
                    ratingBar.setRating(Float.parseFloat(valueitem));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Glide.with(this).load(image).into(img1);
        Glide.with(this).load(image1).into(img2);
        Glide.with(this).load(image2).into(img3);
        Glide.with(this).load(image3).into(img4);
        Glide.with(this).load(image4).into(img5);
    
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
            btnbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getApplicationContext(), Choose_resort.class);
                    i.putExtra("package title",title);
                    i.putExtra("title image",image);
                    i.putExtra("Price",price);
                    i.putExtra("resort Title",strresorttitle);
                    i.putExtra("Resort location",strresortlocation);
                    i.putExtra("Resort description",strresortdescription);
                    i.putExtra("Resort price",strdulexprice);
                    i.putExtra("Resort rating",strresortrating);
                    i.putExtra("Resort Image",strresortimage);
                    i.putExtra("Resort semi name",strsemiresorttitle);
                    i.putExtra("Resort semi location",strsemiresortlocation);
                    i.putExtra("Resort  semi description",strsemiresortdescription);
                    i.putExtra("Resort semi price",strsemiprice);
                    i.putExtra("Resort semi Image",strsemiresortimage);
                    i.putExtra("Resort semi rating",strsemiresortrating);
                    i.putExtra("Day night",dayngt);
                    startActivity(i);
                }
            });
        }
        else
        {
            StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
        }
    }

}