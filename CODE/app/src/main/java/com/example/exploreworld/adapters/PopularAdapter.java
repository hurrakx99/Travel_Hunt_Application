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

public class PopularAdapter extends RecyclerView.Adapter<PopularViewHolder> {
    private Context context;
    private List<PackageData> myPopularlist;

    public PopularAdapter(Context context, List<PackageData> myPopularlist) {
        this.context = context;
        this.myPopularlist = myPopularlist;
    }

    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.package_load,parent,false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PopularViewHolder holder, final int position) {
        holder.title.setText(myPopularlist.get(position).getTitle());
        holder.location.setText(myPopularlist.get(position).getLocation());
        holder.price.setText(myPopularlist.get(position).getPrice());
        holder.daynight.setText(myPopularlist.get(position).getDays());
        Glide.with(context).load(myPopularlist.get(position).getImage()).into(holder.imageView);
        DatabaseReference ratingdatabase= FirebaseDatabase.getInstance().getReference("Rating").child("packagerating");
        Query query=ratingdatabase.orderByChild("packageId").equalTo(myPopularlist.get(position).getTitle());
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
                Intent i=new Intent(context, Package_details.class);
                Bundle bundle=new Bundle();
                bundle.putString(Package_details.Title,myPopularlist.get(position).getTitle());
                bundle.putString(Package_details.Location,myPopularlist.get(position).getLocation());
                bundle.putString(Package_details.Price,myPopularlist.get(position).getPrice());
                bundle.putString(Package_details.Daynight,myPopularlist.get(position).getDays());
                bundle.putString(Package_details.Description,myPopularlist.get(position).getDescription());
                bundle.putString(Package_details.Tips,myPopularlist.get(position).getTour_tips());
                bundle.putString(Package_details.Image,myPopularlist.get(position).getImage());
                bundle.putString(Package_details.Image1,myPopularlist.get(position).getPhoto1());
                bundle.putString(Package_details.Image2,myPopularlist.get(position).getPhoto2());
                bundle.putString(Package_details.Image3,myPopularlist.get(position).getPhoto3());
                bundle.putString(Package_details.Image4,myPopularlist.get(position).getPhoto4());
                bundle.putString(Package_details.Activity1,myPopularlist.get(position).getActivity1());
                bundle.putString(Package_details.Activity2,myPopularlist.get(position).getActivity2());
                bundle.putString(Package_details.Activity3,myPopularlist.get(position).getActivity3());
                bundle.putString(Package_details.Activity4,myPopularlist.get(position).getActivity4());
                bundle.putString(Package_details.Activitytitle1,myPopularlist.get(position).getActivitytitle1());
                bundle.putString(Package_details.Activitytitle2,myPopularlist.get(position).getActivitytitle2());
                bundle.putString(Package_details.Activitytitle3,myPopularlist.get(position).getActivitytitle3());
                bundle.putString(Package_details.Activitytitle4,myPopularlist.get(position).getActivitytitle4());
                bundle.putString(Package_details.Resortimage,myPopularlist.get(position).getResort());
                bundle.putString(Package_details.Resorttitle,myPopularlist.get(position).getResort_name());
                bundle.putString(Package_details.Resortlocation,myPopularlist.get(position).getResort_location());
                bundle.putString(Package_details.Resortdescription,myPopularlist.get(position).getResort_description());
                bundle.putString(Package_details.Resortprice,myPopularlist.get(position).getResort_price());
                bundle.putString(Package_details.Resortrating,myPopularlist.get(position).getResortrating());
                bundle.putString(Package_details.semiResortimage,myPopularlist.get(position).getSemiresort());
                bundle.putString(Package_details.semiResorttitle,myPopularlist.get(position).getSemiresort_name());
                bundle.putString(Package_details.semiResortlocation,myPopularlist.get(position).getSemiresort_location());
                bundle.putString(Package_details.semiResortdescription,myPopularlist.get(position).getSemiresort_description());
                bundle.putString(Package_details.semiResortprice,myPopularlist.get(position).getSemiresort_price());
                bundle.putString(Package_details.semiResortrating,myPopularlist.get(position).getSemiresortrating());

                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myPopularlist.size();
    }
}
class PopularViewHolder extends RecyclerView.ViewHolder{

    public TextView title,location,price,daynight,ratetxt;
    public ImageView imageView;
    public CardView cardView;
    public PopularViewHolder(@NonNull View itemView) {
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
