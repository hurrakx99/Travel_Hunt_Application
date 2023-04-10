package com.example.exploreworld.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.exploreworld.R;
import com.example.exploreworld.authentication.JavaMailApi;
import com.example.exploreworld.firebasepackageload.Fill_details;
import com.muddzdev.styleabletoast.StyleableToast;

public class PaymentMethod extends AppCompatActivity {
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
    String packagetitle,daynight,resorttitle,img,image,sum2,sum1,cname,noadhar,choosedate,address,noperson,nofemale,phno;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Fill_details.class);
        i.putExtra("image",img);
        i.putExtra("Title",packagetitle);
        i.putExtra("resort",resorttitle);
        i.putExtra("Day",daynight);
        i.putExtra("Name",cname);
        i.putExtra("Address",address);
        i.putExtra("No of males",noperson);
        i.putExtra("No of females",nofemale);
        i.putExtra("Phone number",phno);
        i.putExtra("Date",choosedate);
        i.putExtra("Document",image);
        i.putExtra("Total person",String.valueOf(sum1));
        i.putExtra("Total price",String.valueOf(sum2));
        i.putExtra("Adhar NO",noadhar);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        imageView2 = findViewById(R.id.googlepay);
        imageView3 = findViewById(R.id.phonepay);
        imageView4 = findViewById(R.id.paytmpayment);
        imageView5 = findViewById(R.id.debitcard);
        imageView6 = findViewById(R.id.amzonpayment);
        imageView1 = findViewById(R.id.paymentback);

        Bundle bundle = getIntent().getExtras();
        packagetitle = bundle.getString("Title");
        resorttitle = bundle.getString("resort");
        daynight = bundle.getString("Day");
        img = bundle.getString("image");
        sum1=bundle.getString("Total person");
        sum2=bundle.getString("Total price");
        choosedate=bundle.getString("Date");
        cname=bundle.getString("Name");
        address=bundle.getString("Address");
        phno=bundle.getString("Phone number");
        noadhar=bundle.getString("Adhar NO");
        nofemale=bundle.getString("No of males");
        noperson=bundle.getString("No of females");
        image=bundle.getString("Document");
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), Fill_details.class);
                    i.putExtra("image",img);
                    i.putExtra("Title",packagetitle);
                    i.putExtra("resort",resorttitle);
                    i.putExtra("Day",daynight);
                    i.putExtra("Name",cname);
                    i.putExtra("Address",address);
                    i.putExtra("No of males",noperson);
                    i.putExtra("No of females",nofemale);
                    i.putExtra("Phone number",phno);
                    i.putExtra("Date",choosedate);
                    i.putExtra("Document",image);
                    i.putExtra("Total person",String.valueOf(sum1));
                    i.putExtra("Total price",String.valueOf(sum2));
                    i.putExtra("Adhar NO",noadhar);
                    startActivity(i);
                    finish();
                }
            });

            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), Google_pay.class);
                    i.putExtra("image",img);
                    i.putExtra("Title",packagetitle);
                    i.putExtra("resort",resorttitle);
                    i.putExtra("Day",daynight);
                    i.putExtra("Name",cname);
                    i.putExtra("Document",image);
                    i.putExtra("Address",address);
                    i.putExtra("No of males",noperson);
                    i.putExtra("No of females",nofemale);
                    i.putExtra("Phone number",phno);
                    i.putExtra("Date",choosedate);
                    i.putExtra("Total person",String.valueOf(sum1));
                    i.putExtra("Total price",String.valueOf(sum2));
                    i.putExtra("Adhar NO",noadhar);
                    startActivity(i);
                    finish();
                }
            });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Phone_pe.class);
                i.putExtra("image",img);
                i.putExtra("Title",packagetitle);
                i.putExtra("resort",resorttitle);
                i.putExtra("Day",daynight);
                i.putExtra("Name",cname);
                i.putExtra("Document",image);
                i.putExtra("Address",address);
                i.putExtra("No of males",noperson);
                i.putExtra("No of females",nofemale);
                i.putExtra("Phone number",phno);
                i.putExtra("Date",choosedate);
                i.putExtra("Total person",String.valueOf(sum1));
                i.putExtra("Total price",String.valueOf(sum2));
                i.putExtra("Adhar NO",noadhar);
                startActivity(i);
                finish();
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Paytm.class);
                i.putExtra("image",img);
                i.putExtra("Title",packagetitle);
                i.putExtra("resort",resorttitle);
                i.putExtra("Day",daynight);
                i.putExtra("Name",cname);
                i.putExtra("Document",image);
                i.putExtra("Address",address);
                i.putExtra("No of males",noperson);
                i.putExtra("No of females",nofemale);
                i.putExtra("Phone number",phno);
                i.putExtra("Date",choosedate);
                i.putExtra("Total person",String.valueOf(sum1));
                i.putExtra("Total price",String.valueOf(sum2));
                i.putExtra("Adhar NO",noadhar);
                startActivity(i);
                finish();
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Card_payment.class);
                i.putExtra("image",img);
                i.putExtra("Title",packagetitle);
                i.putExtra("resort",resorttitle);
                i.putExtra("Day",daynight);
                i.putExtra("Name",cname);
                i.putExtra("Document",image);
                i.putExtra("Address",address);
                i.putExtra("No of males",noperson);
                i.putExtra("No of females",nofemale);
                i.putExtra("Phone number",phno);
                i.putExtra("Date",choosedate);
                i.putExtra("Total person",String.valueOf(sum1));
                i.putExtra("Total price",String.valueOf(sum2));
                i.putExtra("Adhar NO",noadhar);
                startActivity(i);
                finish();
            }
        });
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Amazon_Pay.class);
                i.putExtra("image",img);
                i.putExtra("Title",packagetitle);
                i.putExtra("resort",resorttitle);
                i.putExtra("Day",daynight);
                i.putExtra("Name",cname);
                i.putExtra("Address",address);
                i.putExtra("No of males",noperson);
                i.putExtra("No of females",nofemale);
                i.putExtra("Phone number",phno);
                i.putExtra("Date",choosedate);
                i.putExtra("Document",image);
                i.putExtra("Total person",String.valueOf(sum1));
                i.putExtra("Total price",String.valueOf(sum2));
                i.putExtra("Adhar NO",noadhar);
                startActivity(i);
                finish();
            }
        });
    }
        else
    {
        StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
    }
}

}
