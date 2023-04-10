package com.example.exploreworld.viewall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.exploreworld.Model.PackageData;
import com.example.exploreworld.R;
import com.example.exploreworld.adapters.LehAdapter;
import com.example.exploreworld.main.Customer_drawer;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class Leh_Ladakh extends AppCompatActivity {
    RecyclerView recyclerView;
    List<PackageData> myLehlist;
    LehAdapter adapter;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leh__ladakh);
        recyclerView=findViewById(R.id.recycler_leh);
        myLehlist=new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter=new LehAdapter(this,myLehlist);
        recyclerView.setAdapter(adapter);
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
            progressBar=findViewById(R.id.spin_kitl);
            Wave wave=new Wave();
            progressBar.setIndeterminateDrawable(wave);
            progressBar.setVisibility(View.VISIBLE);
            databaseReference= FirebaseDatabase.getInstance().getReference("tourpackages").child("Lehpackage");
            valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    myLehlist.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        PackageData packageData=snapshot.getValue(PackageData.class);
                        myLehlist.add(packageData);
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
        else
        {
            StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Customer_drawer.class));
        finish();
    }

}