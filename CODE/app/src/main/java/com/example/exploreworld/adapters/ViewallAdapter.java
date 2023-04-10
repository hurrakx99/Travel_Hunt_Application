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

public class ViewallAdapter extends RecyclerView.Adapter<ViewallHolder> {
    private Context context;
    private List<PackageData> mydomesticalllist;

    public ViewallAdapter(Context context, List<PackageData> mydomesticalllist) {
        this.context = context;
        this.mydomesticalllist = mydomesticalllist;
    }

    @Override
    public ViewallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all,parent,false);
        return new ViewallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewallHolder holder, final int position) {
        holder.title.setText(mydomesticalllist.get(position).getTitle());
        holder.location.setText(mydomesticalllist.get(position).getLocation());
        holder.price.setText(mydomesticalllist.get(position).getPrice());
        holder.daynight.setText(mydomesticalllist.get(position).getDays());
        Glide.with(context).load(mydomesticalllist.get(position).getImage()).into(holder.imageView);
        DatabaseReference ratingdatabase= FirebaseDatabase.getInstance().getReference("Rating").child("packagerating");
        Query query=ratingdatabase.orderByChild("packageId").equalTo(mydomesticalllist.get(position).getTitle());
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
                bundle.putString(Package_details.Title,mydomesticalllist.get(position).getTitle());
                bundle.putString(Package_details.Location,mydomesticalllist.get(position).getLocation());
                bundle.putString(Package_details.Price,mydomesticalllist.get(position).getPrice());
                bundle.putString(Package_details.Daynight,mydomesticalllist.get(position).getDays());
                bundle.putString(Package_details.Description,mydomesticalllist.get(position).getDescription());
                bundle.putString(Package_details.Tips,mydomesticalllist.get(position).getTour_tips());
                bundle.putString(Package_details.Image,mydomesticalllist.get(position).getImage());
                bundle.putString(Package_details.Image1,mydomesticalllist.get(position).getPhoto1());
                bundle.putString(Package_details.Image2,mydomesticalllist.get(position).getPhoto2());
                bundle.putString(Package_details.Image3,mydomesticalllist.get(position).getPhoto3());
                bundle.putString(Package_details.Image4,mydomesticalllist.get(position).getPhoto4());
                bundle.putString(Package_details.Activity1,mydomesticalllist.get(position).getActivity1());
                bundle.putString(Package_details.Activity2,mydomesticalllist.get(position).getActivity2());
                bundle.putString(Package_details.Activity3,mydomesticalllist.get(position).getActivity3());
                bundle.putString(Package_details.Activity4,mydomesticalllist.get(position).getActivity4());
                bundle.putString(Package_details.Activitytitle1,mydomesticalllist.get(position).getActivitytitle1());
                bundle.putString(Package_details.Activitytitle2,mydomesticalllist.get(position).getActivitytitle2());
                bundle.putString(Package_details.Activitytitle3,mydomesticalllist.get(position).getActivitytitle3());
                bundle.putString(Package_details.Activitytitle4,mydomesticalllist.get(position).getActivitytitle4());
                bundle.putString(Package_details.Resortimage,mydomesticalllist.get(position).getResort());
                bundle.putString(Package_details.Resorttitle,mydomesticalllist.get(position).getResort_name());
                bundle.putString(Package_details.Resortlocation,mydomesticalllist.get(position).getResort_location());
                bundle.putString(Package_details.Resortdescription,mydomesticalllist.get(position).getResort_description());
                bundle.putString(Package_details.Resortprice,mydomesticalllist.get(position).getResort_price());
                bundle.putString(Package_details.Resortrating,mydomesticalllist.get(position).getResortrating());
                bundle.putString(Package_details.semiResortimage,mydomesticalllist.get(position).getSemiresort());
                bundle.putString(Package_details.semiResorttitle,mydomesticalllist.get(position).getSemiresort_name());
                bundle.putString(Package_details.semiResortlocation,mydomesticalllist.get(position).getSemiresort_location());
                bundle.putString(Package_details.semiResortdescription,mydomesticalllist.get(position).getSemiresort_description());
                bundle.putString(Package_details.semiResortprice,mydomesticalllist.get(position).getSemiresort_price());
                bundle.putString(Package_details.semiResortrating,mydomesticalllist.get(position).getSemiresortrating());

                i.putExtras(bundle);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mydomesticalllist.size();
    }
}
class ViewallHolder extends RecyclerView.ViewHolder{

    public TextView title,location,price,daynight,ratetxt;
    public ImageView imageView;
    public CardView cardView;
    public ViewallHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.viewdestination);
        title=itemView.findViewById(R.id.viewtextTitle);
        location=itemView.findViewById(R.id.viewtextlocation);
        price=itemView.findViewById(R.id.viewtextprice);
        daynight=itemView.findViewById(R.id.viewtextdaynight);
        cardView=itemView.findViewById(R.id.viewpackageview);
        ratetxt=itemView.findViewById(R.id.viewtextrate);
    }
}
