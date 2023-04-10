package com.example.exploreworld.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exploreworld.Model.PackageData;
import com.example.exploreworld.Model.PackageRating;
import com.example.exploreworld.R;
import com.example.exploreworld.firebasepackageload.Choose_resort;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class LehAdapter extends RecyclerView.Adapter<LehViewHolder> {
    private Context context;
    private List<PackageData> myLehlist;

    public LehAdapter(Context context, List<PackageData> myLehlist) {
        this.context = context;
        this.myLehlist = myLehlist;
    }

    @Override
    public LehViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.leh_view,parent,false);
        return new LehViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LehViewHolder holder, final int position) {
        holder.title.setText(myLehlist.get(position).getTitle());
        holder.location.setText(myLehlist.get(position).getLocation());
        holder.price.setText(myLehlist.get(position).getPrice());
        holder.daynight.setText(myLehlist.get(position).getDays());
        holder.tips.setText(myLehlist.get(position).getTour_tips());
        holder.description.setText(myLehlist.get(position).getDescription());
        holder.act1.setText(myLehlist.get(position).getActivitytitle1());
        holder.act2.setText(myLehlist.get(position).getActivitytitle2());
        holder.act3.setText(myLehlist.get(position).getActivitytitle3());
        holder.act4.setText(myLehlist.get(position).getActivitytitle4());

        Glide.with(context).load(myLehlist.get(position).getImage()).into(holder.img1);
        Glide.with(context).load(myLehlist.get(position).getPhoto1()).into(holder.img2);
        Glide.with(context).load(myLehlist.get(position).getPhoto2()).into(holder.img3);
        Glide.with(context).load(myLehlist.get(position).getPhoto3()).into(holder.img4);
        Glide.with(context).load(myLehlist.get(position).getPhoto4()).into(holder.img5);
        Glide.with(context).load(myLehlist.get(position).getActivity1()).into(holder.img6);
        Glide.with(context).load(myLehlist.get(position).getActivity2()).into(holder.img7);
        Glide.with(context).load(myLehlist.get(position).getActivity3()).into(holder.img8);
        Glide.with(context).load(myLehlist.get(position).getActivity4()).into(holder.img9);

        DatabaseReference ratingdatabase= FirebaseDatabase.getInstance().getReference("Rating").child("packagerating");
        Query query=ratingdatabase.orderByChild("packageId").equalTo(myLehlist.get(position).getTitle());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    String valueitem;
                    PackageRating item=snapshot.getValue(PackageRating.class);
                    valueitem=item.getRatevalue();
                    holder.ratetxt.setText(valueitem);
                    holder.ratingBar.setRating(Float.parseFloat(valueitem));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder. expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.expandableLayout.toggle();
            }
        });
        holder.btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, Choose_resort.class);
                i.putExtra("package title",myLehlist.get(position).getTitle());
                i.putExtra("title image",myLehlist.get(position).getImage());
                i.putExtra("Price",myLehlist.get(position).getPrice());
                i.putExtra("resort Title",myLehlist.get(position).getResort_name());
                i.putExtra("Resort location",myLehlist.get(position).getResort_location());
                i.putExtra("Resort description",myLehlist.get(position).getResort_description());
                i.putExtra("Resort price",myLehlist.get(position).getResort_price());
                i.putExtra("Resort rating",myLehlist.get(position).getResortrating());
                i.putExtra("Resort Image",myLehlist.get(position).getResort());
                i.putExtra("Resort semi name",myLehlist.get(position).getSemiresort_name());
                i.putExtra("Resort semi location",myLehlist.get(position).getSemiresort_location());
                i.putExtra("Resort  semi description",myLehlist.get(position).getSemiresort_description());
                i.putExtra("Resort semi price",myLehlist.get(position).getSemiresort_price());
                i.putExtra("Resort semi Image",myLehlist.get(position).getSemiresort());
                i.putExtra("Resort semi rating",myLehlist.get(position).getSemiresortrating());
                i.putExtra("Day night",myLehlist.get(position).getDays());
                context.startActivity(i);
            }
        });

    

    }

    @Override
    public int getItemCount() {
        return myLehlist.size();
    }
}
class LehViewHolder extends RecyclerView.ViewHolder{

    public TextView title,location,description,tips,daynight,price,ratetxt,act1,act2,act3,act4;
    public Button btnbook;
    public ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9;
    public RatingBar ratingBar;
    TextView expand;
    ExpandableLayout expandableLayout;
    public LehViewHolder(@NonNull View itemView) {
        super(itemView);
        expand=itemView.findViewById(R.id.Lehexpand_tourtips);
        expandableLayout=itemView.findViewById(R.id.Lehmyexpandable);
        ratingBar=itemView.findViewById(R.id.Lehrate);
        title=itemView.findViewById(R.id.Lehtitle);
        location=itemView.findViewById(R.id.Lehsubtitle);
        description=itemView.findViewById(R.id.Lehdescription);
        price=itemView.findViewById(R.id.Lehprice);
        daynight=itemView.findViewById(R.id.Lehdn);
        tips=itemView.findViewById(R.id.Lehtips);
        ratetxt=itemView.findViewById(R.id.Lehratetxt);
        act1=itemView.findViewById(R.id.Lehactivity1);
        act2=itemView.findViewById(R.id.Lehactivity2);
        act3=itemView.findViewById(R.id.Lehactivity3);
        act4=itemView.findViewById(R.id.Lehactivity4);
        img1=itemView.findViewById(R.id.Lehimage1);
        img2=itemView.findViewById(R.id.Lehimage2);
        img3=itemView.findViewById(R.id.Lehimage3);
        img4=itemView.findViewById(R.id.Lehimage4);
        img5=itemView.findViewById(R.id.Lehimage5);
        img6=itemView.findViewById(R.id.Lehimage6);
        img7=itemView.findViewById(R.id.Lehimage7);
        img8=itemView.findViewById(R.id.Lehimage8);
        img9=itemView.findViewById(R.id.Lehimage9);
        btnbook=itemView.findViewById(R.id.LehbtnBook);
    }
}
