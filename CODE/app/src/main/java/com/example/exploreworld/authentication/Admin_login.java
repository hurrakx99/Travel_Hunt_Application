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

public class Admin_login extends AppCompatActivity {
    ImageView imageView;
    EditText username, password;
    Button loginbtn;
    ScrollView scrollView;
    ProgressBar progressBar;
    TextView register;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    CheckBox checkBox;
    String userid;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView=findViewById(R.id.adloginimage);
        String uri="https://imgproxy.epicpxls.com/2GhAjccE9MUsDNSJw4B3Z2s-wwBp-C87q-4B1hHv57Q/rs:fill:800:600:0/g:no/aHR0cHM6Ly9pdGVt/cy5lcGljcHhscy5j/b20vdXBsb2Fkcy9w/aG90by84MDdiMDEz/OGY4NjQ2NzNlNTNm/ZTYxZGMzYjgxM2Rm/MQ.jpg";
        Glide.with(this).load(uri).into(imageView);
        username=findViewById(R.id.adusername);
        password=findViewById(R.id.adpassword);
        loginbtn=findViewById(R.id.btnadlogin);
        scrollView=findViewById(R.id.adloginview);
        register=findViewById(R.id.adsignup);
        progressBar=findViewById(R.id.adprogressbar);
        checkBox=findViewById(R.id.adshow);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
            loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Customerusername = username.getText().toString();
                final String Customerpassword = password.getText().toString();
                if (Customerusername.isEmpty()) {
                    username.setError("Please enter username");
                }
                else if (Customerpassword.isEmpty()) {
                    password.setError("Please enter email address");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(Customerusername).matches())
                {
                    Snackbar snackbar= Snackbar.make(scrollView,"Such user does not exist!",Snackbar.LENGTH_INDEFINITE);
                    snackbar.setDuration(5000);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                    snackbar.setBackgroundTint(Color.RED);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    userid=firebaseAuth.getUid();
                    DatabaseReference reference = firebaseDatabase.getReference("User").child("Admin");
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
                                        progressBar.setVisibility(View.VISIBLE);
                                        firebaseAuth.signInWithEmailAndPassword(Customerusername, Customerpassword)
                                                .addOnCompleteListener(Admin_login.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                                                updateUI(user);

                                                            } else {
                                                                Toast.makeText(Admin_login.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                            }
                                                        } else {
                                                            if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                                                Snackbar snackbar = Snackbar.make(scrollView, "Invalid Username!", Snackbar.LENGTH_INDEFINITE);
                                                                snackbar.setDuration(5000);
                                                                snackbar.setTextColor(Color.WHITE);
                                                                snackbar.show();
                                                                snackbar.setBackgroundTint(Color.RED);
                                                                progressBar.setVisibility(View.INVISIBLE);

                                                            } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                                                Snackbar snackbar2 = Snackbar.make(scrollView, "Invalid Password!", Snackbar.LENGTH_INDEFINITE);
                                                                snackbar2.setDuration(5000);
                                                                snackbar2.setTextColor(Color.WHITE);
                                                                snackbar2.show();
                                                                snackbar2.setBackgroundTint(Color.RED);
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                            }


                                                        }

                                                        // ...
                                                    }
                                                });
                                    }

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
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                password.setText("");
                startActivity(new Intent(getApplicationContext(), Admin_Register.class));
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
        username.setText("");
        password.setText("");
        Intent i=new Intent(Admin_login.this, Admin_drawer.class);
        startActivity(i);
        finish();
    }

}