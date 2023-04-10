package com.example.exploreworld.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exploreworld.Model.Bookeddetails;
import com.example.exploreworld.Model.RegisterModel;
import com.example.exploreworld.R;

import java.util.List;

public class BookedPeopleAdapter extends RecyclerView.Adapter {

    private Context context;
    private List BookedPeopleList;

    public BookedPeopleAdapter(Context context, List bookedPeopleList) {
        this.context = context;
        BookedPeopleList = bookedPeopleList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.booked_table_list, parent, false);

        return new BookedPeopleAdapter.BookedPeopleViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookedPeopleAdapter.BookedPeopleViewHolder BookedPeopleViewHolder = (BookedPeopleAdapter.BookedPeopleViewHolder) holder;

        int rowPos = BookedPeopleViewHolder.getAdapterPosition();

        if (rowPos == 0) {
            BookedPeopleViewHolder.txtname.setBackgroundResource(R.drawable.table_header_bg);
            BookedPeopleViewHolder.txtptitle.setBackgroundResource(R.drawable.table_header_bg);
            BookedPeopleViewHolder.txtrtitle.setBackgroundResource(R.drawable.table_header_bg);
            BookedPeopleViewHolder.txtphone.setBackgroundResource(R.drawable.table_header_bg);
            BookedPeopleViewHolder.txtdate.setBackgroundResource(R.drawable.table_header_bg);
            BookedPeopleViewHolder.txtadhar.setBackgroundResource(R.drawable.table_header_bg);
            BookedPeopleViewHolder.txtmale.setBackgroundResource(R.drawable.table_header_bg);
            BookedPeopleViewHolder.txtfemale.setBackgroundResource(R.drawable.table_header_bg);
            BookedPeopleViewHolder.txtstatus.setBackgroundResource(R.drawable.table_header_bg);
            BookedPeopleViewHolder.txtprice.setBackgroundResource(R.drawable.table_header_bg);
            BookedPeopleViewHolder.txtaddress.setBackgroundResource(R.drawable.table_header_bg);

            BookedPeopleViewHolder.txtname.setText("Name");
            BookedPeopleViewHolder.txtptitle.setText("Package title");
            BookedPeopleViewHolder.txtrtitle.setText("Resort title");
            BookedPeopleViewHolder.txtphone.setText("Phone Number");
            BookedPeopleViewHolder.txtdate.setText("Date");
            BookedPeopleViewHolder.txtaddress.setText("Address");
            BookedPeopleViewHolder.txtadhar.setText("Adhar number");
            BookedPeopleViewHolder.txtmale.setText("Males");
            BookedPeopleViewHolder.txtfemale.setText("Females");
            BookedPeopleViewHolder.txtstatus.setText("Status");
            BookedPeopleViewHolder.txtprice.setText("Paid Amount");
            
            BookedPeopleViewHolder.txtname.setTextColor(Color.WHITE);
            BookedPeopleViewHolder.txtptitle.setTextColor(Color.WHITE);
            BookedPeopleViewHolder.txtrtitle.setTextColor(Color.WHITE);
            BookedPeopleViewHolder.txtphone.setTextColor(Color.WHITE);
            BookedPeopleViewHolder.txtdate.setTextColor(Color.WHITE);
            BookedPeopleViewHolder.txtadhar.setTextColor(Color.WHITE);
            BookedPeopleViewHolder.txtmale.setTextColor(Color.WHITE);
            BookedPeopleViewHolder.txtfemale.setTextColor(Color.WHITE);
            BookedPeopleViewHolder.txtstatus.setTextColor(Color.WHITE);
            BookedPeopleViewHolder.txtprice.setTextColor(Color.WHITE);
            BookedPeopleViewHolder.txtaddress.setTextColor(Color.WHITE);

            BookedPeopleViewHolder.txtname.setPadding(10, 2, 5, 2);
            BookedPeopleViewHolder.txtptitle.setPadding(10, 2, 5, 2);
            BookedPeopleViewHolder.txtrtitle.setPadding(10, 2, 5, 2);
            BookedPeopleViewHolder.txtphone.setPadding(10, 2, 5, 2);
            BookedPeopleViewHolder.txtdate.setPadding(10, 2, 5, 2);
            BookedPeopleViewHolder.txtadhar.setPadding(10, 2, 5, 2);
            BookedPeopleViewHolder.txtmale.setPadding(10, 2, 5, 2);
            BookedPeopleViewHolder.txtfemale.setPadding(10, 2, 5, 2);
            BookedPeopleViewHolder.txtstatus.setPadding(10, 2, 5, 2);
            BookedPeopleViewHolder.txtprice.setPadding(10, 2, 5, 2);
            BookedPeopleViewHolder.txtaddress.setPadding(10, 2, 5, 2);
        } else {
            Bookeddetails modal = (Bookeddetails) BookedPeopleList.get(rowPos - 1);

            BookedPeopleViewHolder.txtname.setBackgroundResource(R.drawable.table_content_cell_bg);
            BookedPeopleViewHolder.txtptitle.setBackgroundResource(R.drawable.table_content_cell_bg);
            BookedPeopleViewHolder.txtrtitle.setBackgroundResource(R.drawable.table_content_cell_bg);
            BookedPeopleViewHolder.txtphone.setBackgroundResource(R.drawable.table_content_cell_bg);
            BookedPeopleViewHolder.txtdate.setBackgroundResource(R.drawable.table_content_cell_bg);
            BookedPeopleViewHolder.txtadhar.setBackgroundResource(R.drawable.table_content_cell_bg);
            BookedPeopleViewHolder.txtmale.setBackgroundResource(R.drawable.table_content_cell_bg);
            BookedPeopleViewHolder.txtfemale.setBackgroundResource(R.drawable.table_content_cell_bg);
            BookedPeopleViewHolder.txtstatus.setBackgroundResource(R.drawable.table_content_cell_bg);
            BookedPeopleViewHolder.txtprice.setBackgroundResource(R.drawable.table_content_cell_bg);
            BookedPeopleViewHolder.txtaddress.setBackgroundResource(R.drawable.table_content_cell_bg);

            BookedPeopleViewHolder.txtname.setText(modal.getName());
            BookedPeopleViewHolder.txtptitle.setText(modal.getPackage_title());
            BookedPeopleViewHolder.txtrtitle.setText(modal.getResort_title());
            BookedPeopleViewHolder.txtphone.setText(modal.getPhone_number());
            BookedPeopleViewHolder.txtdate.setText(modal.getDate());
            BookedPeopleViewHolder.txtaddress.setText(modal.getAddress());
            BookedPeopleViewHolder.txtadhar.setText(modal.getAdhar_no());
            BookedPeopleViewHolder.txtmale.setText(modal.getNo_of_males());
            BookedPeopleViewHolder.txtfemale.setText(modal.getNo_of_females());
            BookedPeopleViewHolder.txtstatus.setText(modal.getStatus());
            BookedPeopleViewHolder.txtprice.setText(modal.getTotal_price());
        }
    }

    @Override
    public int getItemCount() {
        return BookedPeopleList.size() + 1;
    }

    public class BookedPeopleViewHolder extends RecyclerView.ViewHolder {
        TextView txtname, txtptitle,txtrtitle,txtaddress,txtphone,txtdate,txtadhar,txtmale,txtfemale,txtstatus,txtprice;

        BookedPeopleViewHolder(View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txtnamebp);
            txtptitle=itemView.findViewById(R.id.txtptitlebp);
            txtrtitle=itemView.findViewById(R.id.txtrtitlebp);
            txtphone=itemView.findViewById(R.id.txtphonenobp);
            txtdate=itemView.findViewById(R.id.txtdatebp);
            txtadhar=itemView.findViewById(R.id.txtadharbp);
            txtmale=itemView.findViewById(R.id.txtmalebp);
            txtfemale=itemView.findViewById(R.id.txtfemalebp);
            txtstatus=itemView.findViewById(R.id.txtstatusbp);
            txtprice=itemView.findViewById(R.id.txtpricebp);
            txtaddress=itemView.findViewById(R.id.txtaddressbp);


        }
    }
}
