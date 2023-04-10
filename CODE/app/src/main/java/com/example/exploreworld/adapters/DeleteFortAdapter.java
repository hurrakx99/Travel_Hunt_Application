package com.example.exploreworld.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exploreworld.Model.PackageData;
import com.example.exploreworld.R;
import com.example.exploreworld.deletepackages.Delete_Package_Details;
import com.example.exploreworld.firebasepackageload.Package_details;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteFortAdapter extends RecyclerView.Adapter<DeleteFortHolder> {
    private Context context;
    private List<PackageData> mydeleteFortlist;

    public DeleteFortAdapter(Context context, List<PackageData> mydeleteFortlist) {
        this.context = context;
        this.mydeleteFortlist = mydeleteFortlist;
    }

    @Override
    public DeleteFortHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.deleteui_layout,parent,false);
        return new DeleteFortHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteFortHolder holder, final int position) {
        holder.title.setText(mydeleteFortlist.get(position).getTitle());
        holder.location.setText(mydeleteFortlist.get(position).getLocation());
        holder.price.setText(mydeleteFortlist.get(position).getPrice());
        holder.daynight.setText(mydeleteFortlist.get(position).getDays());
        Glide.with(context).load(mydeleteFortlist.get(position).getImage()).into(holder.imageView);
        holder.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Are sure to delete the package..!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("tourpackages").child("fortpackage").child(mydeleteFortlist.get(position).getTitle());
                        Query query=reference.orderByChild("title").equalTo(mydeleteFortlist.get(position).getTitle());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                reference.setValue(null);
                                Toast.makeText(context, "Package Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(context, "Package failed to delete", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.editui_layout);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                TextView textView=dialog.findViewById(R.id.editpacktitle);
                Button btn=dialog.findViewById(R.id.btnupdate);
                TextView textView2=dialog.findViewById(R.id.editclose);
                final EditText etpackprice=dialog.findViewById(R.id.editpackprice);
                final EditText etresortprice=dialog.findViewById(R.id.editresortprice);
                final EditText etsemiresortprice=dialog.findViewById(R.id.editsemiresortprice);
                final EditText etpacktips=dialog.findViewById(R.id.editpacktips);
                final EditText etpackdesc=dialog.findViewById(R.id.editpackdesc);
                final EditText etroominfo=dialog.findViewById(R.id.editroominfo);
                final EditText etroominfo2=dialog.findViewById(R.id.editroominfo2);

                textView.setText(mydeleteFortlist.get(position).getTitle());
                etpackprice.setText(mydeleteFortlist.get(position).getPrice());
                etresortprice.setText(mydeleteFortlist.get(position).getResort_price());
                etsemiresortprice.setText(mydeleteFortlist.get(position).getSemiresort_price());
                etpacktips.setText(mydeleteFortlist.get(position).getTour_tips());
                etpackdesc.setText(mydeleteFortlist.get(position).getDescription());
                etroominfo.setText(mydeleteFortlist.get(position).getResort_description());
                etroominfo2.setText(mydeleteFortlist.get(position).getSemiresort_description());

                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("price",etpackprice.getText().toString());
                        map.put("resort_price",etresortprice.getText().toString());
                        map.put("semiresort_price",etsemiresortprice.getText().toString());
                        map.put("tour_tips",etpacktips.getText().toString());
                        map.put("description",etpackdesc.getText().toString());
                        map.put("resort_description",etroominfo.getText().toString());
                        map.put("semiresort_description",etroominfo2.getText().toString());
                        final DatabaseReference refeencre= FirebaseDatabase.getInstance().getReference("tourpackages").child("fortpackage").child(mydeleteFortlist.get(position).getTitle());
                        refeencre.updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialog.dismiss();
                                        Toast.makeText(context, "Package updated Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(context, "failed to update", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, Delete_Package_Details.class);
                Bundle bundle=new Bundle();
                bundle.putString(Package_details.Title,mydeleteFortlist.get(position).getTitle());
                bundle.putString(Package_details.Location,mydeleteFortlist.get(position).getLocation());
                bundle.putString(Package_details.Price,mydeleteFortlist.get(position).getPrice());
                bundle.putString(Package_details.Daynight,mydeleteFortlist.get(position).getDays());
                bundle.putString(Package_details.Description,mydeleteFortlist.get(position).getDescription());
                bundle.putString(Package_details.Tips,mydeleteFortlist.get(position).getTour_tips());
                bundle.putString(Package_details.Image,mydeleteFortlist.get(position).getImage());
                bundle.putString(Package_details.Image1,mydeleteFortlist.get(position).getPhoto1());
                bundle.putString(Package_details.Image2,mydeleteFortlist.get(position).getPhoto2());
                bundle.putString(Package_details.Image3,mydeleteFortlist.get(position).getPhoto3());
                bundle.putString(Package_details.Image4,mydeleteFortlist.get(position).getPhoto4());
                bundle.putString(Package_details.Activity1,mydeleteFortlist.get(position).getActivity1());
                bundle.putString(Package_details.Activity2,mydeleteFortlist.get(position).getActivity2());
                bundle.putString(Package_details.Activity3,mydeleteFortlist.get(position).getActivity3());
                bundle.putString(Package_details.Activity4,mydeleteFortlist.get(position).getActivity4());
                bundle.putString(Package_details.Activitytitle1,mydeleteFortlist.get(position).getActivitytitle1());
                bundle.putString(Package_details.Activitytitle2,mydeleteFortlist.get(position).getActivitytitle2());
                bundle.putString(Package_details.Activitytitle3,mydeleteFortlist.get(position).getActivitytitle3());
                bundle.putString(Package_details.Activitytitle4,mydeleteFortlist.get(position).getActivitytitle4());
                bundle.putString(Package_details.Resortimage,mydeleteFortlist.get(position).getResort());
                bundle.putString(Package_details.Resorttitle,mydeleteFortlist.get(position).getResort_name());
                bundle.putString(Package_details.Resortlocation,mydeleteFortlist.get(position).getResort_location());
                bundle.putString(Package_details.Resortdescription,mydeleteFortlist.get(position).getResort_description());
                bundle.putString(Package_details.Resortprice,mydeleteFortlist.get(position).getResort_price());
                bundle.putString(Package_details.semiResortimage,mydeleteFortlist.get(position).getSemiresort());
                bundle.putString(Package_details.semiResorttitle,mydeleteFortlist.get(position).getSemiresort_name());
                bundle.putString(Package_details.semiResortlocation,mydeleteFortlist.get(position).getSemiresort_location());
                bundle.putString(Package_details.semiResortdescription,mydeleteFortlist.get(position).getSemiresort_description());
                bundle.putString(Package_details.semiResortprice,mydeleteFortlist.get(position).getSemiresort_price());

                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mydeleteFortlist.size();
    }
}
class DeleteFortHolder extends RecyclerView.ViewHolder{

    public TextView title,location,price,daynight;
    public ImageView imageView,imageView2,imageView3;
    public DeleteFortHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.deleteditimage);
        imageView2=itemView.findViewById(R.id.editpackage);
        imageView3=itemView.findViewById(R.id.deletepackage);
        title=itemView.findViewById(R.id.deleteditTitle);
        location=itemView.findViewById(R.id.deleteditlocation);
        price=itemView.findViewById(R.id.deleteditprice);
        daynight=itemView.findViewById(R.id.deleteditdaynight);
    }
}
