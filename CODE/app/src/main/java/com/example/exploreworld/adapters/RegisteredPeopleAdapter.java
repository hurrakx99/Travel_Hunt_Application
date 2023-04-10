package com.example.exploreworld.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exploreworld.Model.RegisterModel;
import com.example.exploreworld.R;
import com.example.exploreworld.menu.Registered_people;

import java.util.List;

public class RegisteredPeopleAdapter extends RecyclerView.Adapter {

    private List RegisterList;

    public RegisteredPeopleAdapter(Registered_people registered_people, List RegisterList) {
        this.RegisterList = RegisterList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_item, parent, false);

        return new RowViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {rowViewHolder.txtname.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtemail.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtphone.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtname.setText("Name");
            rowViewHolder.txtemail.setText("Customer Email Address");
            rowViewHolder.txtphone.setText("Phone Number");
            rowViewHolder.txtname.setTextColor(Color.WHITE);
            rowViewHolder.txtemail.setTextColor(Color.WHITE);
            rowViewHolder.txtphone.setTextColor(Color.WHITE);
            rowViewHolder.txtname.setPadding(10,2,5,2);
            rowViewHolder.txtemail.setPadding(10,2,5,2);
            rowViewHolder.txtphone.setPadding(10,2,5,2);
        } else {
            RegisterModel modal = (RegisterModel) RegisterList.get(rowPos - 1);

            rowViewHolder.txtname.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtemail.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtphone.setBackgroundResource(R.drawable.table_content_cell_bg);

            rowViewHolder.txtname.setText(modal.getFullname());
            rowViewHolder.txtemail.setText(modal.getUsername());
            rowViewHolder.txtphone.setText(modal.getPhone());
        }
    }
    @Override
    public int getItemCount() {
        return RegisterList.size() + 1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txtname,txtemail,txtphone;

        RowViewHolder(View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txtnamerp);
            txtemail = itemView.findViewById(R.id.txtemailrp);
            txtphone = itemView.findViewById(R.id.txtphonenorp);
            
        }
    }
}