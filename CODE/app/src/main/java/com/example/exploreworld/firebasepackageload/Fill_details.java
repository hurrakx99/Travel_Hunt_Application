package com.example.exploreworld.firebasepackageload;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.exploreworld.Model.Customerdata;
import com.example.exploreworld.R;
import com.example.exploreworld.payment.PaymentMethod;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoast.StyleableToast;
import java.util.Calendar;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;

public class Fill_details extends AppCompatActivity {
    EditText editText1,editText2,editText3,editText4,editText5,eddate,editText6;
    ImageView imageView,imageView2,imageView3;
    Button btn;
    int year,month,day;
    Calendar calendar;
    Uri FilePathUri;
    AlertDialog progressBar;
    int Image_request_Code=1;
    String image,packagetitle,daynight,resorttitle,img,sum;
    int sum1=0,sum2=0;
    ScrollView scrollView;
    DatabaseReference database;
    FirebaseUser user;
    String cname,phno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_details);
        editText1=findViewById(R.id.fillpersonname);
        editText2=findViewById(R.id.fillphoneno);
        editText3=findViewById(R.id.fillAddress);
        eddate=findViewById(R.id.filldate);
        scrollView=findViewById(R.id.fillview);
        editText4=findViewById(R.id.fillnoofperson);
        editText5=findViewById(R.id.fillnooffemale);
        editText6=findViewById(R.id.adharnumber);
        btn=findViewById(R.id.gpaynow);
        imageView3=findViewById(R.id.fillback);
        imageView2=findViewById(R.id.adharimage);
        progressBar=new SpotsDialog.Builder().setContext(Fill_details.this).build();
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setMessage("Please wait");
        imageView=findViewById(R.id.calenderimg);
        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
         month=calendar.get(Calendar.MONTH);
         day=calendar.get(Calendar.DAY_OF_MONTH);


        user=FirebaseAuth.getInstance().getCurrentUser();
        database=FirebaseDatabase.getInstance().getReference("User").child("customer").child(user.getUid());
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customerdata customerdata = dataSnapshot.getValue(Customerdata.class);
                cname=customerdata.getFullname();
                phno=customerdata.getPhone();
                editText1.setText(customerdata.getFullname());
                editText2.setText(customerdata.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Bundle bundle=getIntent().getExtras();
        packagetitle=bundle.getString("package title");
        resorttitle=bundle.getString("resort Title");
        daynight=bundle.getString("Day night");
        sum=bundle.getString("Sum");
        img=bundle.getString("title img");
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Fill_details.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthofyear, int dayofmonth) {

                        monthofyear=month+1;
                        String date=dayofmonth+"/"+monthofyear+"/"+year;
                        eddate.setText(date);

                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select document"),Image_request_Code);
            }
        });
    imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), Choose_resort.class);
                i.putExtra("title image",img);
                i.putExtra("package title",packagetitle);
                i.putExtra("resort Title",resorttitle);
                i.putExtra("Day night",daynight);
                i.putExtra("Price",sum);
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

    private void uploadData() {
        uploadDocument();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Image_request_Code && resultCode == RESULT_OK && data !=null) {
            FilePathUri = data.getData();
            imageView2.setImageURI(FilePathUri);
        }
        else {
            Toast.makeText(Fill_details.this, "Please select images", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadDocument()
    {
        progressBar.show();
        if(FilePathUri==null)
        {
            Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
            progressBar.dismiss();
        }
        else {
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference().child("bookingDetail").child(user.getUid())
                    .child(String.valueOf(FilePathUri.getLastPathSegment()));
            storageReference.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isComplete()) ;
                            Uri urlImage = uriTask.getResult();
                            image = urlImage.toString();

                           uploadDetails();
                            progressBar.dismiss();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.dismiss();
                }
            });
        }
    }

    private void uploadDetails() {

        final String address=editText3.getText().toString();
        final String choosedate=eddate.getText().toString();
        final String noperson=editText4.getText().toString();
        final String nofemale=editText5.getText().toString();
        final String noadhar=editText6.getText().toString();
        sum1=Integer.parseInt(noperson)+Integer.parseInt(nofemale);
        sum2=sum1*Integer.parseInt(sum);

        if(cname.isEmpty())
        {
            editText1.setError("Enter a name");
        }
        else if(address.isEmpty())
        {
            editText2.setError("Enter a address");
        }
        else if(nofemale.isEmpty())
        {
            editText2.setError("Enter a no of female");
        }
        else if(noperson.isEmpty())
        {
            editText2.setError("Enter a no of males");
        }
        else if(phno.isEmpty())
        {
            editText2.setError("Enter a phone number");
        }
        else if(choosedate.isEmpty())
        {
            eddate.setError("choose date");
        }
        else if(noadhar.isEmpty())
        {
            editText6.setError("Enter a adhar number");
        }
        else if(noadhar.length()<12)
        {
            editText6.setError("Enter a correct adhar number");
        }
        else
        {

            progressBar.dismiss();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
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
                    i.putExtra("Document",image);
                    i.putExtra("Date",choosedate);
                    i.putExtra("Total person",String.valueOf(sum1));
                    i.putExtra("Total price",String.valueOf(sum2));
                    i.putExtra("Adhar NO",noadhar);
                    startActivity(i);
                    finish();
                }
            },3000);

        }
    }
}