package com.example.exploreworld.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
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

public class DomesticAdapter extends RecyclerView.Adapter<DomesticViewHolder> {
    private Context context;
    private List<PackageData> mydomesticlist;

    public DomesticAdapter(Context context, List<PackageData> mydomesticlist) {
        this.context = context;
        this.mydomesticlist = mydomesticlist;
    }

    @Override
    public DomesticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.package_load,parent,false);
        return new DomesticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DomesticViewHolder holder, final int position) {
        holder.title.setText(mydomesticlist.get(position).getTitle());
        holder.location.setText(mydomesticlist.get(position).getLocation());
        holder.price.setText(mydomesticlist.get(position).getPrice());
        holder.daynight.setText(mydomesticlist.get(position).getDays());
        Glide.with(context).load(mydomesticlist.get(position).getImage()).into(holder.imageView);
        DatabaseReference ratingdatabase= FirebaseDatabase.getInstance().getReference("Rating").child("packagerating");
        Query query=ratingdatabase.orderByChild("packageId").equalTo(mydomesticlist.get(position).getTitle());
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
                bundle.putString(Package_details.Title,mydomesticlist.get(position).getTitle());
                bundle.putString(Package_details.Location,mydomesticlist.get(position).getLocation());
                bundle.putString(Package_details.Price,mydomesticlist.get(position).getPrice());
                bundle.putString(Package_details.Daynight,mydomesticlist.get(position).getDays());
                bundle.putString(Package_details.Description,mydomesticlist.get(position).getDescription());
                bundle.putString(Package_details.Tips,mydomesticlist.get(position).getTour_tips());
                bundle.putString(Package_details.Image,mydomesticlist.get(position).getImage());
                bundle.putString(Package_details.Image1,mydomesticlist.get(position).getPhoto1());
                bundle.putString(Package_details.Image2,mydomesticlist.get(position).getPhoto2());
                bundle.putString(Package_details.Image3,mydomesticlist.get(position).getPhoto3());
                bundle.putString(Package_details.Image4,mydomesticlist.get(position).getPhoto4());
                bundle.putString(Package_details.Activity1,mydomesticlist.get(position).getActivity1());
                bundle.putString(Package_details.Activity2,mydomesticlist.get(position).getActivity2());
                bundle.putString(Package_details.Activity3,mydomesticlist.get(position).getActivity3());
                bundle.putString(Package_details.Activity4,mydomesticlist.get(position).getActivity4());
                bundle.putString(Package_details.Activitytitle1,mydomesticlist.get(position).getActivitytitle1());
                bundle.putString(Package_details.Activitytitle2,mydomesticlist.get(position).getActivitytitle2());
                bundle.putString(Package_details.Activitytitle3,mydomesticlist.get(position).getActivitytitle3());
                bundle.putString(Package_details.Activitytitle4,mydomesticlist.get(position).getActivitytitle4());
                bundle.putString(Package_details.Resortimage,mydomesticlist.get(position).getResort());
                bundle.putString(Package_details.Resorttitle,mydomesticlist.get(position).getResort_name());
                bundle.putString(Package_details.Resortlocation,mydomesticlist.get(position).getResort_location());
                bundle.putString(Package_details.Resortdescription,mydomesticlist.get(position).getResort_description());
                bundle.putString(Package_details.Resortprice,mydomesticlist.get(position).getResort_price());
                bundle.putString(Package_details.Resortrating,mydomesticlist.get(position).getResortrating());
                bundle.putString(Package_details.semiResortimage,mydomesticlist.get(position).getSemiresort());
                bundle.putString(Package_details.semiResorttitle,mydomesticlist.get(position).getSemiresort_name());
                bundle.putString(Package_details.semiResortlocation,mydomesticlist.get(position).getSemiresort_location());
                bundle.putString(Package_details.semiResortdescription,mydomesticlist.get(position).getSemiresort_description());
                bundle.putString(Package_details.semiResortprice,mydomesticlist.get(position).getSemiresort_price());
                bundle.putString(Package_details.semiResortrating,mydomesticlist.get(position).getSemiresortrating());

                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mydomesticlist.size();
    }
}
class DomesticViewHolder extends RecyclerView.ViewHolder{

    public TextView title,location,price,daynight,ratetxt;
    public ImageView imageView;
    public CardView cardView;
    public DomesticViewHolder(@NonNull View itemView) {
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
