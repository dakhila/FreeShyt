package com.firstapp.anas.freeshyt;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignUp;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private int dumbChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initalize firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            // profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), ListActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);

        progressDialog = new ProgressDialog(this);

        buttonSignUp.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            // email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            // stopping the function execution further
            return;
        }
        if (TextUtils.isEmpty(password)) {
            // password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            // stopping the function execution further
            return;
        }
        // if validations are ok
        // we will first show a progressbar

        progressDialog.setMessage("Please wait a moment...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // check if successfully
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                        } else {
                            Toast.makeText(MainActivity.this, "Failed to Register", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignUp) {
            registerUser();
        }
        if (view == textViewSignin) {
            // will open login activity here
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
