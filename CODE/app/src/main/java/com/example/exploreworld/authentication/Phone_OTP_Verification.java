package com.example.exploreworld.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exploreworld.R;
import com.example.exploreworld.main.Admin_drawer;
import com.example.exploreworld.main.Customer_drawer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Phone_OTP_Verification extends AppCompatActivity {
    EditText edCode;
    String verificationCodeBySystem;
    FirebaseAuth mAuth;
    Button btnverify;
    ProgressBar progressBar;
    ScrollView scrollView;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone__otp__verification);
        btnverify = findViewById(R.id.verify);
        edCode = findViewById(R.id.code1);
        scrollView=findViewById(R.id.otpview);
        progressBar = findViewById(R.id.phprogress_bar);
        mAuth=FirebaseAuth.getInstance();
        dialog=new Dialog(Phone_OTP_Verification.this);

        String phoneno=getIntent().getStringExtra("phoneNo");
        sendVerificationCodeToUser(phoneno);


        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin=edCode.getText().toString();
                if(pin.isEmpty()|| pin.length()<6)
                {
                    edCode.setError("Wrong OTP..");
                    edCode.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(pin);

            }
        });


    }

    private void sendVerificationCodeToUser(String phoneno) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
               "+91"+phoneno,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);
    }
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(Phone_OTP_Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    private void verifyCode(String codeByUser)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUser);
        signInTheUserByCredentials(credential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Phone_OTP_Verification.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Snackbar snackbar=Snackbar.make(scrollView,"Code is Verified",Snackbar.LENGTH_INDEFINITE);
                            snackbar.setTextColor(Color.WHITE);
                            snackbar.setBackgroundTint(Color.GREEN);
                            snackbar.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    showpopup();
                                }
                            },3000);
                        }
                        else
                        {
                            Toast.makeText(Phone_OTP_Verification.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showpopup() {
        TextView textView;
        dialog.setContentView(R.layout.login_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        textView=(TextView) dialog.findViewById(R.id.gotologin);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Customer_login.class));
                finish();
            }
        });
    }
}
