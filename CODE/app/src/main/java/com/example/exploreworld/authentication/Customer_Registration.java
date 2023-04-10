package com.example.exploreworld.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exploreworld.Model.Customerdata;
import com.example.exploreworld.R;
import com.example.exploreworld.main.Admin_drawer;
import com.example.exploreworld.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class Customer_Registration extends AppCompatActivity {

    Pattern lowercase=Pattern.compile("[a-z]");
    Pattern uppercase=Pattern.compile("[A-Z]");
    Pattern digit=Pattern.compile("[0-9]");
    Pattern specialchar=Pattern.compile("[@#$%^&+=]");

    CircleImageView imageView;
    EditText etname, etemail, etphone, etpassword;
    Uri FilePathUri;
    TextView txtsignin;
    Button btnupload;
    CheckBox checkbox;
    int Image_request_Code=1;
    String image;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    ScrollView scrollView;
   String ph;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__registration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView = findViewById(R.id.ctrprofile);
        btnupload=findViewById(R.id.btnregister);
        etname = findViewById(R.id.ctrfullname);
        etemail = findViewById(R.id.ctremail);
        etphone = findViewById(R.id.ctrphone);
        etpassword = findViewById(R.id.ctrpassword);
        txtsignin = findViewById(R.id.ctrsignin);
        checkbox = findViewById(R.id.ctrshow);
        progressBar=findViewById(R.id.ctrprogressbar);
        scrollView=findViewById(R.id.signupview);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    etpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else
                {
                    etpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin=new Intent(getApplicationContext(),Customer_login.class);
                startActivity(signin);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select Image"),Image_request_Code);
            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfile();

            }
        });
        }
        else
        {
            StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
        }
    }

    public void uploadData(View view) {
        uploadProfile();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Image_request_Code && resultCode == RESULT_OK && data !=null) {
            FilePathUri = data.getData();
            imageView.setImageURI(FilePathUri);
        }
        else {
            Toast.makeText(Customer_Registration.this, "Please select images", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadProfile()
    {

        progressBar.setVisibility(View.VISIBLE);
        if(FilePathUri==null)
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
        }
        else {
            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference().child("Customerphoto")
                    .child(String.valueOf(FilePathUri.getLastPathSegment()));
            storageReference.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isComplete()) ;
                            Uri urlImage = uriTask.getResult();
                            image = urlImage.toString();

                            uploadCustomerData();
                            progressBar.setVisibility(View.GONE);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }



    public void uploadCustomerData()
    {
        firebaseAuth = FirebaseAuth.getInstance();

        final String fname=etname.getText().toString();
        final String user=etemail.getText().toString();
        String pass=etpassword.getText().toString();
        ph=etphone.getText().toString();
        if(fname.isEmpty())
        {
            etname.setError("enter your name");
        }
        else if(user.isEmpty())
        {
            etemail.setError("Please enter username");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(user).matches())
        {
            Snackbar snackbar = Snackbar.make(scrollView, "Email must be in proper format", Snackbar.LENGTH_INDEFINITE);
            snackbar.setDuration(5000);
            snackbar.setTextColor(Color.WHITE);
            snackbar.setBackgroundTint(Color.RED);
            snackbar.show();
        }
        else if(ph.isEmpty())
        {
            etphone.setError("Please enter phone number");
        }
        else if(pass.isEmpty())
        {
            etpassword.setError("Please enter email address");
        }
        else if(ph.length() < 10)
        {
            etphone.setError("phone number must be 10 digit");
        }
        else if(pass.length()<8)
        {
            etpassword.setError("password must 8 to 16 character");
        }
        else if(pass.equals(fname))
        {
            etpassword.setError("password must not be equal to name");
        }
        else if(pass.equals(user))
        {
            etpassword.setError("password must not be equal to username");
        }
        else if(!lowercase.matcher(pass).find())
        {
            etpassword.setError("Aleast one lowercase letter");
        }
        else if(!digit.matcher(pass).find())
        {
            etpassword.setError("Aleast one number");
        }
        else if(!specialchar.matcher(pass).find())
        {
            etpassword.setError("Aleast one special character");
        }
        else if(!uppercase.matcher(pass).find())
        {
            etpassword.setError("Aleast one uppercase letter");
        }
        else if(!Patterns.PHONE.matcher(ph).matches())
        {
            etphone.setError("Only Digits are allowed");
        }
        else
        {
            final Customerdata customerdata = new Customerdata
                    (
                            fname,
                            user,
                            ph,
                            image

                    );

            firebaseAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    FirebaseDatabase.getInstance().getReference("User").child("customer")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(customerdata)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Intent i = new Intent(Customer_Registration.this, Phone_OTP_Verification.class);
                                                        i.putExtra("phoneNo", ph);
                                                        startActivity(i);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(Customer_Registration.this, "user registration failed!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                }
                                else
                                {
                                    Toast.makeText(Customer_Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                    }
                    else
                    {
                        if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Snackbar snackbar = Snackbar.make(scrollView, "User already exist!", Snackbar.LENGTH_INDEFINITE);
                            snackbar.setDuration(5000);
                            snackbar.setTextColor(Color.WHITE);
                            snackbar.show();
                            snackbar.setBackgroundTint(Color.RED);
                        }
                        else
                        {
                            Toast.makeText(Customer_Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}