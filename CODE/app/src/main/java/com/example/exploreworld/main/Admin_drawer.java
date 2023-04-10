package com.example.exploreworld.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.exploreworld.R;
import com.example.exploreworld.authentication.Admin_login;
import com.example.exploreworld.authentication.Customer_login;
import com.example.exploreworld.authentication.JavaMailApi;
import com.example.exploreworld.firebaseupload.Domestic_upload;
import com.example.exploreworld.firebaseupload.Forts_upload;
import com.example.exploreworld.firebaseupload.International_upload;
import com.example.exploreworld.firebaseupload.Popular_upload;
import com.example.exploreworld.firebaseupload.Religious_upload;
import com.example.exploreworld.firebaseupload.Wonders_upload;
import com.example.exploreworld.menu.Booked_Peoples;
import com.example.exploreworld.menu.Delete_Edit_Package;
import com.example.exploreworld.menu.Registered_people;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.muddzdev.styleabletoast.StyleableToast;

public class Admin_drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    LinearLayout domestic,international,popular,religious,wonders,fort;
    ImageView imageView1,imageView2,imageView3;
    Dialog dialog;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_drawer);
        Toolbar toolbar = findViewById(R.id.admintoolbar);
        setSupportActionBar(toolbar);
        domestic=findViewById(R.id.domesticup);
       international=findViewById(R.id.internationup);
        popular=findViewById(R.id.popularup);
        religious=findViewById(R.id.religiousup);
        wonders=findViewById(R.id.wondersup);
        fort=findViewById(R.id.fortup);

        dialog=new Dialog(Admin_drawer.this);

        imageView1=findViewById(R.id.updomesticimg);
        imageView2=findViewById(R.id.upinternationaimg);
        imageView3=findViewById(R.id.upfortimg);
        String uri1="https://www.tourismsolutions.in/homepage/images/bucket.png";
        String uri2="https://i.pinimg.com/originals/80/57/1d/80571d1ca85f0129a89cfabdbe8e7c0d.png";
        String uri3="https://i.pinimg.com/originals/10/d1/50/10d150e7ba45e8a5af519cdc3ac78110.png";
        Glide.with(this).load(uri1).into(imageView1);
        Glide.with(this).load(uri2).into(imageView2);
        Glide.with(this).load(uri3).into(imageView3);
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
            domestic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Domestic_upload.class));
                    finish();
                }
            });
            international.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), International_upload.class));
                    finish();
                }
            });
            popular.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Popular_upload.class));
                    finish();
                }
            });
            religious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Religious_upload.class));
                    finish();
                }
            });
            wonders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Wonders_upload.class));
                    finish();
                }
            });
            fort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Forts_upload.class));
                    finish();
                }
            });
        }
        else
        {
            StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
        }
        drawer= findViewById(R.id.admindrawer);
        navigationView = findViewById(R.id.admin_nav);
        navigationView.setNavigationItemSelectedListener(Admin_drawer.this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(Admin_drawer.this,drawer,toolbar
                ,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState==null)
        {
            navigationView.setCheckedItem(R.id.admin_home);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                showpopup();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showpopup() {
        dialog.setContentView(R.layout.activity_send_mail__customer);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btn;
        final EditText editText,editText2;
        btn=dialog.findViewById(R.id.adsendmail);
        editText=dialog.findViewById(R.id.admailto);
        editText2=dialog.findViewById(R.id.admailbody);
        TextView textView=dialog.findViewById(R.id.admailclose);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=editText.getText().toString();
                String sub="Travel Hunt App";
                String msgtext=editText2.getText().toString();
                JavaMailApi javaMailApi=new JavaMailApi(getApplicationContext(),mail,sub,msgtext);
                javaMailApi.execute();
                dialog.dismiss();

            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(backPressedTime+1000>System.currentTimeMillis()) {
                backToast.cancel();
                FirebaseAuth.getInstance().signOut();
                super.onBackPressed();
                return;
            }
            else
            {
                backToast= Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.admin_registered:
                startActivity(new Intent(getApplicationContext(), Registered_people.class));
                finish();
                break;
            case R.id.admin_booked:
                startActivity(new Intent(getApplicationContext(), Booked_Peoples.class));
                finish();
                break;
            case R.id.admin_deledit:
                startActivity(new Intent(getApplicationContext(), Delete_Edit_Package.class));
                finish();
                break;
            case R.id.admin_switchuser:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Customer_login.class));
                finish();
                break;
            case R.id.admin_logout:
                   FirebaseAuth.getInstance().signOut();
                   startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}