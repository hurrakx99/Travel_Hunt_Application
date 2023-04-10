package com.example.exploreworld.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.exploreworld.Model.Bookeddetails;
import com.example.exploreworld.R;
import com.example.exploreworld.adapters.BookedPeopleAdapter;
import com.example.exploreworld.main.Admin_drawer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class Booked_Peoples extends AppCompatActivity {
    List BookedList = new ArrayList<>();
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    AlertDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked__peoples);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewbooked);

        final BookedPeopleAdapter adapter = new BookedPeopleAdapter(this,BookedList);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
        progressDialog=new SpotsDialog.Builder().setContext(Booked_Peoples.this).build();
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        databaseReference= FirebaseDatabase.getInstance().getReference("BookingPeoples");
        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BookedList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Bookeddetails bookeddetails=snapshot.getValue(Bookeddetails.class);
                    BookedList.add(bookeddetails);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Admin_drawer.class));
        finish();
    }
}
