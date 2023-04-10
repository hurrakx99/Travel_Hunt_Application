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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class InternationalAdapter extends RecyclerView.Adapter<InternationalViewHolder> {
    private Context context;
    private List<PackageData> myInternationallist;

    public InternationalAdapter(Context context, List<PackageData> myInternationallist) {
        this.context = context;
        this.myInternationallist = myInternationallist;
    }

    @Override
    public InternationalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.package_load,parent,false);
        return new InternationalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InternationalViewHolder holder, final int position) {
        holder.title.setText(myInternationallist.get(position).getTitle());
        holder.location.setText(myInternationallist.get(position).getLocation());
        holder.price.setText(myInternationallist.get(position).getPrice());
        holder.daynight.setText(myInternationallist.get(position).getDays());
        Glide.with(context).load(myInternationallist.get(position).getImage()).into(holder.imageView);



        DatabaseReference ratingdatabase= FirebaseDatabase.getInstance().getReference("Rating").child("packagerating");
        Query query2=ratingdatabase.orderByChild("packageId").equalTo(myInternationallist.get(position).getTitle());
        query2.addValueEventListener(new ValueEventListener() {
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
                Intent i=new Intent(context, Package_details.class);
                Bundle bundle=new Bundle();
                bundle.putString(Package_details.Title,myInternationallist.get(position).getTitle());
                bundle.putString(Package_details.Location,myInternationallist.get(position).getLocation());
                bundle.putString(Package_details.Price,myInternationallist.get(position).getPrice());
                bundle.putString(Package_details.Daynight,myInternationallist.get(position).getDays());
                bundle.putString(Package_details.Description,myInternationallist.get(position).getDescription());
                bundle.putString(Package_details.Tips,myInternationallist.get(position).getTour_tips());
                bundle.putString(Package_details.Image,myInternationallist.get(position).getImage());
                bundle.putString(Package_details.Image1,myInternationallist.get(position).getPhoto1());
                bundle.putString(Package_details.Image2,myInternationallist.get(position).getPhoto2());
                bundle.putString(Package_details.Image3,myInternationallist.get(position).getPhoto3());
                bundle.putString(Package_details.Image4,myInternationallist.get(position).getPhoto4());
                bundle.putString(Package_details.Activity1,myInternationallist.get(position).getActivity1());
                bundle.putString(Package_details.Activity2,myInternationallist.get(position).getActivity2());
                bundle.putString(Package_details.Activity3,myInternationallist.get(position).getActivity3());
                bundle.putString(Package_details.Activity4,myInternationallist.get(position).getActivity4());
                bundle.putString(Package_details.Activitytitle1,myInternationallist.get(position).getActivitytitle1());
                bundle.putString(Package_details.Activitytitle2,myInternationallist.get(position).getActivitytitle2());
                bundle.putString(Package_details.Activitytitle3,myInternationallist.get(position).getActivitytitle3());
                bundle.putString(Package_details.Activitytitle4,myInternationallist.get(position).getActivitytitle4());
                bundle.putString(Package_details.Resortimage,myInternationallist.get(position).getResort());
                bundle.putString(Package_details.Resorttitle,myInternationallist.get(position).getResort_name());
                bundle.putString(Package_details.Resortlocation,myInternationallist.get(position).getResort_location());
                bundle.putString(Package_details.Resortdescription,myInternationallist.get(position).getResort_description());
                bundle.putString(Package_details.Resortprice,myInternationallist.get(position).getResort_price());
                bundle.putString(Package_details.Resortrating,myInternationallist.get(position).getResortrating());
                bundle.putString(Package_details.semiResortimage,myInternationallist.get(position).getSemiresort());
                bundle.putString(Package_details.semiResorttitle,myInternationallist.get(position).getSemiresort_name());
                bundle.putString(Package_details.semiResortlocation,myInternationallist.get(position).getSemiresort_location());
                bundle.putString(Package_details.semiResortdescription,myInternationallist.get(position).getSemiresort_description());
                bundle.putString(Package_details.semiResortprice,myInternationallist.get(position).getSemiresort_price());
                bundle.putString(Package_details.semiResortrating,myInternationallist.get(position).getSemiresortrating());

                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myInternationallist.size();
    }
}
class InternationalViewHolder extends RecyclerView.ViewHolder{
    public TextView title,location,price,daynight,ratetxt;
    public ImageView imageView;
    public CardView cardView;
    public InternationalViewHolder(@NonNull View itemView) {
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
