package com.example.exploreworld.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exploreworld.Model.Bookeddetails;
import com.example.exploreworld.Model.PackageData;
import com.example.exploreworld.R;
import com.example.exploreworld.authentication.JavaMailApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Booking_Adapter extends RecyclerView.Adapter<BookingHolder> {
    private List<Bookeddetails> mybookingdetail;
    private Context context;
    FirebaseAuth auth;
    FirebaseUser user;

    public Booking_Adapter( Context context,List<Bookeddetails> mybookingdetail) {
        this.context = context;
        this.mybookingdetail = mybookingdetail;
    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_detail,parent,false);
        return new BookingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, final int position) {
        holder.textView1.setText(mybookingdetail.get(position).getPackage_title());
        holder.textView2.setText(mybookingdetail.get(position).getDay());
        holder.textView3.setText(mybookingdetail.get(position).getDate());
        holder.textView4.setText(mybookingdetail.get(position).getTotal_price());
        holder.textView5.setText(mybookingdetail.get(position).getTotal_person());
        holder.textView6.setText(mybookingdetail.get(position).getResort_title());
        Glide.with(context).load(mybookingdetail.get(position).getTitle_image()).into(holder.imageView);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth=FirebaseAuth.getInstance();
                user=auth.getCurrentUser();
                final DatabaseReference reference=FirebaseDatabase.getInstance().getReference("User").child("customer").child(user.getUid()).child("booking").child(mybookingdetail.get(position).getPackage_title());
                final DatabaseReference reference2=FirebaseDatabase.getInstance().getReference("BookingPeoples").child(mybookingdetail.get(position).getName());
                Query query=reference.orderByChild("package_title").equalTo(mybookingdetail.get(position).getPackage_title());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference.setValue(null);
                            String mail=user.getEmail();
                            String sub="Explore App";
                            String policy="CANCELLATION POLICY:\n if you cancel your Booking\nYou or any member of your party may cancel their\narrangements at any time.The cancellation charges application are as per the\npublished cancellation policy.\n \n Cancellation charges per person:\n>>15 days or more before departurre:10% of the total cost\n>>10 days before departure:18% of the total cost\n>>3 days before departure:60% of the total cost.\n";
                            String msgtext="You have cancelled Booking of "+mybookingdetail.get(position).getPackage_title()+"\n Total Amount of 10% will be deducted and money will send to your account\nThank you.\n \n \n"+policy;
                            JavaMailApi javaMailApi=new JavaMailApi(context,mail,sub,msgtext);
                            javaMailApi.execute();
                        Toast.makeText(context, "Booking cancelled successfully ", Toast.LENGTH_SHORT).show();
                        Map<String,Object> map=new HashMap<>();
                        map.put("status","cancelled");
                        reference2.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }



    @Override
    public int getItemCount() {
        return mybookingdetail.size();
    }
}
class BookingHolder extends RecyclerView.ViewHolder {
    public TextView textView1,textView2,textView3,textView4,textView5,textView6;
    public Button btn;
    public ImageView imageView;

    public BookingHolder(@NonNull View itemView) {
        super(itemView);
        textView1=itemView.findViewById(R.id.bdtitle);
        textView2=itemView.findViewById(R.id.bddaynight);
        textView3=itemView.findViewById(R.id.bddate);
        textView4=itemView.findViewById(R.id.bdprice);
        textView5=itemView.findViewById(R.id.bdfor);
        textView6=itemView.findViewById(R.id.bdresort);
        btn=itemView.findViewById(R.id.bdcancel);
        imageView=itemView.findViewById(R.id.bdimg);
    }
}
