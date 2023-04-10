package com.example.exploreworld.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.exploreworld.R;
import com.example.exploreworld.main.Admin_drawer;
import com.example.exploreworld.main.Customer_drawer;
import com.example.exploreworld.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.Map;

public class Customer_login extends AppCompatActivity {
    ImageView imageView;
    EditText edtusername, edtpassword;
    TextView txtsignup, txtForgot;
    Button btnlog;
    FirebaseAuth mAuth;
    CheckBox checkBox;
    ProgressBar progressBar;
    ScrollView scrollView;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView = findViewById(R.id.loginimage);
        String uri = "https://img.freepik.com/free-vector/account-log-page_41910-263.jpg?size=626&amp;ext=jpg";
        Glide.with(this).load(uri).into(imageView);
        edtusername = findViewById(R.id.ctusername);
        edtpassword = findViewById(R.id.ctpassword);
        btnlog = findViewById(R.id.btnlogin);
        txtsignup = findViewById(R.id.txtsignup);
        txtForgot = findViewById(R.id.txtforgot);
        checkBox=findViewById(R.id.show);
        progressBar=findViewById(R.id.ctprogressbar);

        scrollView=findViewById(R.id.loginview);

        mAuth = FirebaseAuth.getInstance();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edtpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else
                {
                    edtpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos=connectivityManager.getActiveNetworkInfo();
        if(null!=infos) {
        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Customer_login.this,Forgot_Password.class));
            }
        });
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Customerusername = edtusername.getText().toString();
                final String Customerpassword = edtpassword.getText().toString();
                if (Customerusername.isEmpty()) {
                    edtusername.setError("Please enter username");
                }
                else if (Customerpassword.isEmpty()) {
                    edtpassword.setError("Please enter email address");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(Customerusername).matches())
                {
                    Snackbar snackbar=Snackbar.make(scrollView,"Such user does not exist!",Snackbar.LENGTH_INDEFINITE);
                    snackbar.setDuration(5000);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                    snackbar.setBackgroundTint(Color.RED);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child("customer");
                    Query query=reference.orderByChild("username")
                            .equalTo(Customerusername);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                {
                                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                                    String usern = map.get("username").toString();
                                    if (usern.equals(Customerusername))
                                    {

                                        mAuth.signInWithEmailAndPassword(Customerusername, Customerpassword)
                                                .addOnCompleteListener(Customer_login.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            if(mAuth.getCurrentUser().isEmailVerified())
                                                            {
                                                                FirebaseUser user = mAuth.getCurrentUser();
                                                                updateUI(user);

                                                            }
                                                            else
                                                            {
                                                                Snackbar snackbar=Snackbar.make(scrollView,"Please verify email",Snackbar.LENGTH_INDEFINITE);
                                                                snackbar.setDuration(5000);
                                                                snackbar.setTextColor(Color.WHITE);
                                                                snackbar.show();
                                                                snackbar.setBackgroundTint(Color.RED);
                                                                progressBar.setVisibility(View.INVISIBLE);                                            progressBar.setVisibility(View.INVISIBLE);
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if(task.getException() instanceof FirebaseAuthInvalidUserException)
                                                            {
                                                                Snackbar snackbar=Snackbar.make(scrollView,"Invalid Username!",Snackbar.LENGTH_INDEFINITE);
                                                                snackbar.setDuration(5000);
                                                                snackbar.setTextColor(Color.WHITE);
                                                                snackbar.show();
                                                                snackbar.setBackgroundTint(Color.RED);
                                                                progressBar.setVisibility(View.INVISIBLE);

                                                            }
                                                            else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                                                            {
                                                                Snackbar snackbar2=Snackbar.make(scrollView,"Invalid Password!",Snackbar.LENGTH_INDEFINITE);
                                                                snackbar2.setDuration(5000);
                                                                snackbar2.setTextColor(Color.WHITE);
                                                                snackbar2.show();
                                                                snackbar2.setBackgroundTint(Color.RED);
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                            }
                                                        }
                                                    }
                                                });                                    }

                                }
                            }
                            else
                            {
                                Snackbar snackbar = Snackbar.make(scrollView, "Such user does not exist!", Snackbar.LENGTH_INDEFINITE);
                                snackbar.setDuration(5000);
                                snackbar.setTextColor(Color.WHITE);
                                snackbar.show();
                                snackbar.setBackgroundTint(Color.RED);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }
            }
        });
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtusername.setText("");
                edtpassword.setText("");
                startActivity(new Intent(getApplicationContext(), Customer_Registration.class));
                finish();
            }
        });
    }
        else
    {
        StyleableToast.makeText(getApplicationContext(),"NO Internet Connection",R.style.errorToast).show();
    }
    }
    private void updateUI(FirebaseUser user) {
        Snackbar snackbar=Snackbar.make(scrollView,"Login successful",Snackbar.LENGTH_INDEFINITE);
        snackbar.setDuration(5000);
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
        snackbar.setBackgroundTint(Color.GREEN);
        progressBar.setVisibility(View.INVISIBLE);
        edtusername.setText("");
        edtpassword.setText("");
        startActivity(new Intent(Customer_login.this, Customer_drawer.class));
        finish();
    }
}


