package com.example.exploreworld.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exploreworld.Model.PackageFeedback;
import com.example.exploreworld.Model.PackageRating;
import com.example.exploreworld.R;
import com.example.exploreworld.authentication.JavaMailApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;

public class Card_payment extends AppCompatActivity implements PaymentResultListener {
   String packagetitle,daynight,resorttitle,status,img,image,sum2,sum1,cname,noadhar,choosedate,address,noperson,nofemale,phno;
    TextView price;
    ImageView imageView;
    Button btn;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference, reference2;


    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), PaymentMethod.class);
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
        setContentView(R.layout.activity_card_payment);
        price=findViewById(R.id.amtrp);
        imageView=findViewById(R.id.rpback);
        btn=findViewById(R.id.rpnow);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), PaymentMethod.class);
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
        Checkout.preload(getApplicationContext());
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
        price.setText(sum2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });

    }
    public void startPayment() {

        Checkout checkout = new Checkout();

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", sum2);//pass amount in currency subunits
            options.put("prefill.email", "exploreworld3470@gmail.com");
            options.put("prefill.contact",phno);
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }
    private void uploadbooking() {
        reference= FirebaseDatabase.getInstance().getReference("User").child("customer").child(user.getUid()).child("booking").child(packagetitle);
        reference2= FirebaseDatabase.getInstance().getReference("BookingPeoples");
        HashMap<String,String> map=new HashMap<>();
        map.put("name",cname);
        map.put("address",address);
        map.put("no_of_males",noperson);
        map.put("no_of_females",nofemale);
        map.put("phone_number",phno);
        map.put("date",choosedate);
        map.put("total_person",String.valueOf(sum1));
        map.put("total_price",String.valueOf(sum2));
        map.put("package_title",packagetitle);
        map.put("day",daynight);
        map.put("adhar_no",noadhar);
        map.put("resort_title",resorttitle);
        map.put("document",image);
        map.put("title_image",img);
        map.put("status","booked");
        reference.setValue(map);
        reference2.child(cname).setValue(map);
        Toast.makeText(getApplicationContext(), "Booked Successfully", Toast.LENGTH_SHORT).show();
    }
    private void sendmail() {
        String mail=user.getEmail();
        String sub="Travel Hunt App";
        String policy="The Tourist must carry his/her one identity proof.\n if he/she booked for outside of india he/she must carry Passport.";
        String msgtext="You have Successfully Booked for "+packagetitle+ "\nResort Name:"+resorttitle+"\nYour planned Date: "+choosedate+"\nYou have Fight from "+address+" to "+packagetitle+"\t on "+choosedate+"\nDay and Night "+daynight+"\nNumber of Peoples: "+sum1+"\nTotal amount you paid: "+sum2+"\n\nPOLICY:\n"+policy;
        JavaMailApi javaMailApi=new JavaMailApi(this,mail,sub,msgtext);
        javaMailApi.execute();
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getApplicationContext(), "Payment successful:", Toast.LENGTH_SHORT).show();
        uploadbooking();
        sendmail();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), "Payment failed:", Toast.LENGTH_SHORT).show();
    }
}