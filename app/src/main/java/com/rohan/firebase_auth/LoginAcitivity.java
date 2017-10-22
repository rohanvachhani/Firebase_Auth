package com.rohan.firebase_auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAcitivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private int E = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {        //if already logged in, then redirect user to profile activity
            //profile activity here
            finish();       //before starting any new activity, finish the current activity
            startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
        }

        editTextEmail = (EditText) findViewById(R.id.enterEmail);
        editTextPassword = (EditText) findViewById(R.id.enterPass);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignIn);

        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);

        ProgressDialog progressDialog = new ProgressDialog(this);
    }

    private void userLogin() {
        String Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            E = 1;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    //start profile activity
                    finish();       //before starting any new activity, finish the current activity
                    startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
                } else {
                    progressDialog.dismiss();
                    if (E == 0) {
                        Toast.makeText(LoginAcitivity.this, "Incorrect format of Email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignIn) {
            userLogin();
        }
        if (view == textViewSignUp) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}

