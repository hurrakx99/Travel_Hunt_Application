package com.example.exploreworld.deletepackages;

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

import com.example.exploreworld.Model.PackageData;
import com.example.exploreworld.R;
import com.example.exploreworld.adapters.DeleteDomesticAdapter;
import com.example.exploreworld.adapters.ViewallAdapter;
import com.example.exploreworld.menu.Delete_Edit_Package;
import com.example.exploreworld.viewall.Wonders_Load;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class Domestic_Delete_Edit extends AppCompatActivity {
    RecyclerView recyclerView;
    List<PackageData> mydomesticviewlist;
    DeleteDomesticAdapter adapter;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic__delete__edit);
        recyclerView=findViewById(R.id.recycler_domesticdelete);
        mydomesticviewlist=new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter=new DeleteDomesticAdapter(this,mydomesticviewlist);
        recyclerView.setAdapter(adapter);
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
        progressDialog=new ProgressDialog(Domestic_Delete_Edit.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        databaseReference= FirebaseDatabase.getInstance().getReference("tourpackages").child("domesticpackage");
        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mydomesticviewlist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    PackageData packageData=snapshot.getValue(PackageData.class);
                    mydomesticviewlist.add(packageData);
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
        else
        {
            StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Delete_Edit_Package.class));
        finish();
    }
}