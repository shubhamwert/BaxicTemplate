package com.frictionhacks.tenderhaltinfo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.frictionhacks.tenderhaltinfo.R;
import com.frictionhacks.tenderhaltinfo.Util.Preferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private EditText editText;
    private String verificationId;
    private FirebaseAuth mAuth;
    private EditText editText2;
    private String person1;
    Button btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        person1 = "contractor";
//        spinner = findViewById(R.id.person);
//        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.person));
        FirebaseApp.initializeApp(this);

        editText = findViewById(R.id.editTextMobile);
        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.contractor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person1 = "Contractor";
                findViewById(R.id.contractor).setBackgroundColor(Color.BLUE);
                findViewById(R.id.official).setBackgroundColor(Color.WHITE);
            }


        });


        findViewById(R.id.official).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person1 = "Government";
                findViewById(R.id.contractor).setBackgroundColor(Color.WHITE);
                findViewById(R.id.official).setBackgroundColor(Color.BLUE);
            }
        });

        btnContinue = findViewById(R.id.buttonContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnContinue.setText("Re-send OTP");
                findViewById(R.id.rl_login_otp).setVisibility(View.VISIBLE);
                String number = editText.getText().toString().trim();
                editText.setEnabled(false);
                editText.setBackgroundColor(Color.GRAY);


                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phonenumber = "+91"  + number;
                sendVerificationCode(phonenumber);
                Toast.makeText(LoginActivity.this, "OTP will be sent to you within 5 minutes.", Toast.LENGTH_LONG).show();


//
//                Intent intent = new Intent(MainActivity.this , VerifyPhoneActivity.class);
//                intent.putExtra("phonenumber", phoneNumber);
//                intent.putExtra("person", person);
//                startActivity(intent);


                editText2 = findViewById(R.id.editTextCode);

                findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Preferences.setUser(getApplicationContext(),person1);
                        String code = editText2.getText().toString().trim();

                        if (code.isEmpty() || code.length() < 6) {

                            editText2.setError("Enter code...");
                            editText2.requestFocus();
                            return;
                        }
                        verifyCode(code);
                    }
                });
            }

        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        String user=Preferences.getUser(getApplicationContext());
        if (FirebaseAuth.getInstance().getCurrentUser() != null && user.equals("Contractor")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(FirebaseAuth.getInstance().getCurrentUser() != null && user.equals("Government")){
            Intent intent = new Intent(this, GovtMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            intent.putExtra("person1", person1);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);


                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            Log.d("OnComplete",task.getException().getMessage());
                        }
                    }
                });
    }


    private void sendVerificationCode(String number){
//        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                editText2.setText(code);
                verifyCode(code);

            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("VERIFY_FAIL",e.getMessage());
        }
    };


}


