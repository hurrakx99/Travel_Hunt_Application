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
import android.widget.Toast;

import com.bumptech.glide.Glide;;
import com.example.exploreworld.Model.PackageRating;
import com.example.exploreworld.Model.PackageFeedback;
import com.example.exploreworld.R;
import com.example.exploreworld.authentication.Customer_login;
import com.example.exploreworld.main.Customer_drawer;
import com.example.exploreworld.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import net.cachapa.expandablelayout.ExpandableLayout;


import java.text.DecimalFormat;
import java.util.Arrays;

public class Package_details extends AppCompatActivity {
    TextView tvtitle,tvsubtitle,tvdescription,tvtips,tvday,tvprice,tvratetxt,tvact1,tvact2,tvact3,tvact4;
    private Button btnbook;
    ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9;
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
    public static String Activity1="activity1";
    public static String Activity2="activity2";
    public static String Activity3="activity3";
    public static String Activity4="activity4";
    public static String Activitytitle1="activitytitle1";
    public static String Activitytitle2="activitytitle2";
    public static String Activitytitle3="activitytitle3";
    public static String Activitytitle4="activitytitle4";
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

    TextView expand;
    ExpandableLayout expandableLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference ratingdatabase;
    FirebaseDatabase database;

    String valueitem;
    String title,subtitle,price,dayngt,act1,act2,act3,act4,image,image1,image2,image3,image4,actimg1,actimg2,actimg3,actimg4,description,tips,strdulexprice,strsemiprice,strresortimage,strresorttitle,strresortlocation,strresortdescription,strresortrating,strsemiresortimage,strsemiresorttitle,strsemiresortlocation,strsemiresortdescription,strsemiresortrating;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Customer_drawer.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);
        expand=findViewById(R.id.expand_tourtips);
        expandableLayout=findViewById(R.id.myexpandable);
        ratingBar=findViewById(R.id.detailrate);
        tvtitle=findViewById(R.id.detailtitle);
        tvsubtitle=findViewById(R.id.detailsubtitle);
        tvdescription=findViewById(R.id.detaildescription);
        tvprice=findViewById(R.id.detailprice);
        tvday=findViewById(R.id.detaildn);
        tvtips=findViewById(R.id.detailtips);
        tvratetxt=findViewById(R.id.detailratetxt);
        tvact1=findViewById(R.id.activity1);
        tvact2=findViewById(R.id.activity2);
        tvact3=findViewById(R.id.activity3);
        tvact4=findViewById(R.id.activity4);
        img1=findViewById(R.id.detailimage1);
        img2=findViewById(R.id.detailimage2);
        img3=findViewById(R.id.detailimage3);
        img4=findViewById(R.id.detailimage4);
        img5=findViewById(R.id.detailimage5);
        img6=findViewById(R.id.detailimage6);
        img7=findViewById(R.id.detailimage7);
        img8=findViewById(R.id.detailimage8);
        img9=findViewById(R.id.detailimage9);
        btnbook=findViewById(R.id.btnBook);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        database=FirebaseDatabase.getInstance();
        ratingdatabase=database.getReference("Rating").child("packagerating");

        title=getIntent().getStringExtra(Title);
        subtitle=getIntent().getStringExtra(Location);
        price=getIntent().getStringExtra(Price);
        dayngt=getIntent().getStringExtra(Daynight);
        description=getIntent().getStringExtra(Description);
        act1=getIntent().getStringExtra(Activitytitle1);
        act2=getIntent().getStringExtra(Activitytitle2);
        act3=getIntent().getStringExtra(Activitytitle3);
        act4=getIntent().getStringExtra(Activitytitle4);
        tips=getIntent().getStringExtra(Tips);
        image=getIntent().getStringExtra(Image);
        image1=getIntent().getStringExtra(Image1);
        image2=getIntent().getStringExtra(Image2);
        image3=getIntent().getStringExtra(Image3);
        image4=getIntent().getStringExtra(Image4);
        actimg1=getIntent().getStringExtra(Activity1);
        actimg2=getIntent().getStringExtra(Activity2);
        actimg3=getIntent().getStringExtra(Activity3);
        actimg4=getIntent().getStringExtra(Activity4);

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
        tvact1.setText(act1);
        tvact2.setText(act2);
        tvact3.setText(act3);
        tvact4.setText(act4);
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
        Glide.with(this).load(actimg1).into(img6);
        Glide.with(this).load(actimg2).into(img7);
        Glide.with(this).load(actimg3).into(img8);
        Glide.with(this).load(actimg4).into(img9);
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout.toggle();
            }
        });

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