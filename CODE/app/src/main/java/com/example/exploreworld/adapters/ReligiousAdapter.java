package com.example.exploreworld.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exploreworld.Model.PackageData;
import com.example.exploreworld.Model.PackageRating;
import com.example.exploreworld.R;
import com.example.exploreworld.firebasepackageload.Package_details;
import com.example.exploreworld.firebasepackageload.Religious_Package_Details;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ReligiousAdapter extends RecyclerView.Adapter<ReligiousViewHolder> {
    private Context context;
    private List<PackageData> myReligiouslist;

    public ReligiousAdapter(Context context, List<PackageData> myReligiouslist) {
        this.context = context;
        this.myReligiouslist = myReligiouslist;
    }

    @Override
    public ReligiousViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.package_load,parent,false);
        return new ReligiousViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReligiousViewHolder holder, final int position) {
        holder.title.setText(myReligiouslist.get(position).getTitle());
        holder.location.setText(myReligiouslist.get(position).getLocation());
        holder.price.setText(myReligiouslist.get(position).getPrice());
        holder.daynight.setText(myReligiouslist.get(position).getDays());
        Glide.with(context).load(myReligiouslist.get(position).getImage()).into(holder.imageView);
        DatabaseReference ratingdatabase= FirebaseDatabase.getInstance().getReference("Rating").child("packagerating");
        Query query=ratingdatabase.orderByChild("packageId").equalTo(myReligiouslist.get(position).getTitle());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    String valueitem;
                    PackageRating item=snapshot.getValue(PackageRating.class);
                    valueitem=item.getRatevalue();
                    holder.ratetxt.setText(valueitem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, Religious_Package_Details.class);
                Bundle bundle=new Bundle();
                bundle.putString(Package_details.Title,myReligiouslist.get(position).getTitle());
                bundle.putString(Package_details.Location,myReligiouslist.get(position).getLocation());
                bundle.putString(Package_details.Price,myReligiouslist.get(position).getPrice());
                bundle.putString(Package_details.Daynight,myReligiouslist.get(position).getDays());
                bundle.putString(Package_details.Description,myReligiouslist.get(position).getDescription());
                bundle.putString(Package_details.Tips,myReligiouslist.get(position).getTour_tips());
                bundle.putString(Package_details.Image,myReligiouslist.get(position).getImage());
                bundle.putString(Package_details.Image1,myReligiouslist.get(position).getPhoto1());
                bundle.putString(Package_details.Image2,myReligiouslist.get(position).getPhoto2());
                bundle.putString(Package_details.Image3,myReligiouslist.get(position).getPhoto3());
                bundle.putString(Package_details.Image4,myReligiouslist.get(position).getPhoto4());
                bundle.putString(Package_details.Resortimage,myReligiouslist.get(position).getResort());
                bundle.putString(Package_details.Resorttitle,myReligiouslist.get(position).getResort_name());
                bundle.putString(Package_details.Resortlocation,myReligiouslist.get(position).getResort_location());
                bundle.putString(Package_details.Resortdescription,myReligiouslist.get(position).getResort_description());
                bundle.putString(Package_details.Resortprice,myReligiouslist.get(position).getResort_price());
                bundle.putString(Package_details.Resortrating,myReligiouslist.get(position).getResortrating());
                bundle.putString(Package_details.semiResortimage,myReligiouslist.get(position).getSemiresort());
                bundle.putString(Package_details.semiResorttitle,myReligiouslist.get(position).getSemiresort_name());
                bundle.putString(Package_details.semiResortlocation,myReligiouslist.get(position).getSemiresort_location());
                bundle.putString(Package_details.semiResortdescription,myReligiouslist.get(position).getSemiresort_description());
                bundle.putString(Package_details.semiResortprice,myReligiouslist.get(position).getSemiresort_price());
                bundle.putString(Package_details.semiResortrating,myReligiouslist.get(position).getSemiresortrating());

                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myReligiouslist.size();
    }
}
class ReligiousViewHolder extends RecyclerView.ViewHolder{

    public TextView title,location,price,daynight,ratetxt;
    public ImageView imageView;
    public CardView cardView;
    public ReligiousViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.destination);
        title=itemView.findViewById(R.id.textTitle);
        location=itemView.findViewById(R.id.textlocation);
        price=itemView.findViewById(R.id.textprice);
        daynight=itemView.findViewById(R.id.textdaynight);
        ratetxt=itemView.findViewById(R.id.textStarRating);
        cardView=itemView.findViewById(R.id.packageview);
    }
}

