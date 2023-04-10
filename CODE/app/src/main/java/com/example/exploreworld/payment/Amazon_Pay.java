package com.example.exploreworld.payment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;

public class Amazon_Pay extends AppCompatActivity implements RatingDialogListener {
    String packagetitle,daynight,resorttitle,status,img,image,sum2,sum1,cname,noadhar,choosedate,address,noperson,nofemale,phno;
    TextView price;
    EditText name,id,msg;
    ImageView imageView;
    Button btn;
    ScrollView scrollView;
    Uri uri ;
    public static  final String AMAZON_PAY_PACKAGE_NAME= "in.amazon.mShop.android.shopping";
    int AMAZON_PAY_REQUEST_CODE = 123;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference, reference2;
    float avg,sum=0;
    String valueitem;
    DatabaseReference ratingdatabase,feedbackdatabase;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), PaymentMethod.class);
        i.putExtra("image", img);
        i.putExtra("Title", packagetitle);
        i.putExtra("resort", resorttitle);
        i.putExtra("Day", daynight);
        i.putExtra("Name", cname);
        i.putExtra("Address", address);
        i.putExtra("No of males", noperson);
        i.putExtra("No of females", nofemale);
        i.putExtra("Phone number", phno);
        i.putExtra("Date", choosedate);
        i.putExtra("Document", image);
        i.putExtra("Total person", String.valueOf(sum1));
        i.putExtra("Total price", String.valueOf(sum2));
        i.putExtra("Adhar NO", noadhar);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon__pay);
        scrollView = findViewById(R.id.peview);
        price = findViewById(R.id.amtaznpay);
        name = findViewById(R.id.aznpaypersonname);
        id = findViewById(R.id.aznpayupid);
        msg = findViewById(R.id.aznpaynote);
        btn = findViewById(R.id.aznpaynow);
        imageView = findViewById(R.id.aznpayback);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
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
        name.setText("Saiprasad Patil");
        id.setText("8105529983@apl");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String upid=id.getText().toString();
                String note=msg.getText().toString();
                String pername=name.getText().toString();

                if(upid.isEmpty())
                {
                    Snackbar snackbar=Snackbar.make(scrollView,"UPI box is empty",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.setDuration(1000);
                }
                else if(note.isEmpty())
                {
                    Snackbar snackbar=Snackbar.make(scrollView,"message box is empty",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.setDuration(1000);
                }
                else
                {
                    uri=  payUsingUpi("Saiprasad Patil","saiprasadpatil1999@oksbi",note,sum2);
                    paywithGpay();
                }
            }
        });
        }
        else
        {
            StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
        }
    }

    private void paywithGpay() {
        if(isAppInstalled(this,AMAZON_PAY_PACKAGE_NAME     )) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(AMAZON_PAY_PACKAGE_NAME     );
            startActivityForResult(intent, AMAZON_PAY_REQUEST_CODE);
        }
        else {
            Snackbar snackbar=Snackbar.make(scrollView,"Please Install Google Pay",Snackbar.LENGTH_SHORT);
            snackbar.show();
            snackbar.setBackgroundTint(Color.RED);
            snackbar.setTextColor(Color.WHITE);
            snackbar.setDuration(1000);
        }
    }

    private Uri payUsingUpi(String pername, String upid, String note, String sum2) {

        return  new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", upid)
                .appendQueryParameter("pn", pername)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", sum2)
                .appendQueryParameter("cu", "INR")
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            status=data.getStringExtra("Status").toLowerCase();
        }
        if((RESULT_OK==resultCode)&& status.equals("success"))
        {
            Toast.makeText(getApplicationContext(), "Transaction is sucessful", Toast.LENGTH_SHORT).show();
            uploadbooking();
            sendmail();

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Transation is failed", Toast.LENGTH_SHORT).show();

        }
        ratingdatabase=FirebaseDatabase.getInstance().getReference("Rating").child("packagerating");
        feedbackdatabase=FirebaseDatabase.getInstance().getReference("Feedbacks");
        Query query=ratingdatabase.orderByChild("packageId").equalTo(packagetitle);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    PackageRating item=snapshot.getValue(PackageRating.class);
                    valueitem=item.getRatevalue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        showRatingDialog();
    }
    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setDefaultRating(1)
                .setTitle("Rate this Package")
                .setDescription("Please select some stars and give your feedback")
                .setCommentInputEnabled(true)
                .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                .setTitleTextColor(R.color.titleTextColor)
                .setDescriptionTextColor(R.color.contentTextColor)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.hintTextColor)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.commentbg)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(Amazon_Pay.this)// only if listener is implemented by fragment
                .show();
    }
    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {

        sum= i + Float.parseFloat(valueitem);
        avg=sum/2;
        final PackageRating packageRating = new PackageRating(user.getEmail(),
                packagetitle, String.valueOf(new DecimalFormat("#.#").format(avg)));
        PackageFeedback packageFeedback=new PackageFeedback(user.getEmail(),packagetitle,s);

        ratingdatabase.child("packagerating").child(packagetitle).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(packagetitle).exists()) {
                    //Remove old value
                    ratingdatabase.child(packagetitle).removeValue();
                    //update new value
                    ratingdatabase.child(packagetitle).setValue(packageRating);
                } else {
                    //update new Value
                    ratingdatabase.child(packagetitle).setValue(packageRating);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        feedbackdatabase.child(packagetitle).child(user.getUid()).setValue(packageFeedback)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Thank you for rating & feedback", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "sorry for rating & feedback is not submitted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    public static boolean isAppInstalled(Context context, String AMAZON_PAY_PACKAGE_NAME)
    {
        try
        {
            context.getPackageManager().getApplicationInfo(AMAZON_PAY_PACKAGE_NAME,0);
            return true;

        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

}