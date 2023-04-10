package com.example.exploreworld.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.exploreworld.Model.RegisterModel;
import com.example.exploreworld.R;
import com.example.exploreworld.adapters.RegisteredPeopleAdapter;
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

public class Registered_people extends AppCompatActivity {
    List RegisterList = new ArrayList<>();
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_people);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDeliveryProductList);

        final RegisteredPeopleAdapter adapter = new RegisteredPeopleAdapter(this,RegisterList);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
        progressDialog=new ProgressDialog(Registered_people.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        databaseReference=FirebaseDatabase.getInstance().getReference("User").child("customer");
        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RegisterList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    RegisterModel registerModel=snapshot.getValue(RegisterModel.class);
                    RegisterList.add(registerModel);
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
