package com.example.exploreworld.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exploreworld.Model.Bookeddetails;
import com.example.exploreworld.Model.PackageData;
import com.example.exploreworld.R;
import com.example.exploreworld.adapters.Booking_Adapter;
import com.example.exploreworld.main.Customer_drawer;
import com.example.exploreworld.viewall.Domestic_Viewall;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Booking_Detail extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Bookeddetails> mybookingdetail;
   Booking_Adapter adapter;
   ImageView imageView;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking__detail);
        recyclerView=findViewById(R.id.recycler_bookingdetail);
        mybookingdetail=new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter=new Booking_Adapter(this,mybookingdetail);
        recyclerView.setAdapter(adapter);
        imageView=findViewById(R.id.bkdetailsback);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        progressDialog=new ProgressDialog(Booking_Detail.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        databaseReference= FirebaseDatabase.getInstance().getReference("User").child("customer").child(user.getUid()).child("booking");
        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mybookingdetail.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Bookeddetails bds=snapshot.getValue(Bookeddetails.class);
                    mybookingdetail.add(bds);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Customer_drawer.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Customer_drawer.class));
        finish();
    }
}