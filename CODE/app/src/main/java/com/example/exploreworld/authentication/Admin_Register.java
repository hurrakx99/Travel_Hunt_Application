package com.example.exploreworld.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exploreworld.Model.Admindata;
import com.example.exploreworld.R;
import com.example.exploreworld.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.regex.Pattern;

public class Admin_Register extends AppCompatActivity {
    EditText name, email, phone, password;
    TextView txtsignin;
    Button btnupload;
    CheckBox checkbox;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    ScrollView scrollView;
    Dialog dialog;
    Pattern lowercase=Pattern.compile("[a-z]");
    Pattern uppercase=Pattern.compile("[A-Z]");
    Pattern digit=Pattern.compile("[0-9]");
    Pattern specialchar=Pattern.compile("[@#$%^&+=]");


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Admin_login.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnupload=findViewById(R.id.btnadregister);
        name = findViewById(R.id.adrfullname);
        email = findViewById(R.id.adremail);
        phone = findViewById(R.id.adrphone);
        password = findViewById(R.id.adrpassword);
        txtsignin = findViewById(R.id.adrsignin);
        checkbox = findViewById(R.id.adrshow);
        progressBar=findViewById(R.id.adrprogressbar);
        scrollView=findViewById(R.id.adsignupview);
        dialog=new Dialog(Admin_Register.this);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else
                {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin=new Intent(getApplicationContext(),Admin_login.class);
                startActivity(signin);
            }
        });


        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();

            }
        });
        }
        else
        {
            StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
        }
    }

    public void uploadData() {
        uploadCustomerData();
    }

    public void uploadCustomerData()
    {

        firebaseAuth = FirebaseAuth.getInstance();

        String fname=name.getText().toString();
        String user=email.getText().toString();
        String pass=password.getText().toString();
        String ph=phone.getText().toString();
        if(fname.isEmpty())
        {
            name.setError("enter your name");

        }
        else if(user.isEmpty())
        {
            email.setError("Please enter username");
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
            phone.setError("Please enter phone number");
        }
        else if(pass.isEmpty())
        {
            password.setError("Please enter email address");
        }
        else if(ph.length() < 10)
        {
            phone.setError("phone number must be 10 digit");
        }

        else if(pass.length()<8)
        {
            password.setError("password must 8 to 16 character");
        }
        else if(pass.equals(fname))
        {
            password.setError("password should not be equal to name");
        }
        else if(pass.equals(user))
        {
            password.setError("password should not be equal to username");
        }
        else if(!lowercase.matcher(pass).find())
        {
            password.setError("Aleast one lowercase letter");
        }
        else if(!digit.matcher(pass).find())
        {
            password.setError("Aleast one number");
        }
        else if(!specialchar.matcher(pass).find())
        {
            password.setError("Aleast one special character");
        }
        else if(!uppercase.matcher(pass).find())
        {
            password.setError("Aleast one uppercase letter");
        }
        else if(!Patterns.PHONE.matcher(ph).matches())
        {
            phone.setError("Only Digits are allowed");
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            final Admindata admindata = new Admindata
                    (
                            fname,
                            user,
                            ph

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
                                    FirebaseDatabase.getInstance().getReference("User").child("Admin")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(admindata)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Snackbar snackbar=Snackbar.make(scrollView,"New Admin Added successfully",Snackbar.LENGTH_INDEFINITE);
                                                        snackbar.setBackgroundTint(Color.GREEN);
                                                        snackbar.setTextColor(Color.WHITE);
                                                        snackbar.setDuration(3000);
                                                        snackbar.show();
                                                       showpop();
                                                        progressBar.setVisibility(View.GONE);
                                                    } else {
                                                        Snackbar snackbar=Snackbar.make(scrollView,"Failed to register",Snackbar.LENGTH_INDEFINITE);
                                                        snackbar.setBackgroundTint(Color.RED);
                                                        snackbar.setTextColor(Color.WHITE);
                                                        snackbar.setDuration(3000);
                                                        snackbar.show();
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });

                                }
                                else
                                {
                                    Toast.makeText(Admin_Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

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
                            progressBar.setVisibility(View.GONE);
                        }
                        else
                        {
                            Toast.makeText(Admin_Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });
        }
    }

    private void showpop() {
        TextView textView;
        dialog.setContentView(R.layout.login_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        textView=(TextView) dialog.findViewById(R.id.gotologin);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Admin_login.class));
                finish();
            }
        });
    }


}