package com.drcdadaab.tumshukuruyesuadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    EditText editTextEmail;
    EditText editTextPassword;
    private FirebaseAuth mAuth;
    String email, password;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.editTextRegisterEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextRegisterPassword);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            openDasboardActivity();
            finish();
            return;
        }
    }


    public void openDasboardActivity(){
        Intent intent = new Intent(LoginActivity.this,DasboardActivity.class);
        startActivity (intent);
        finish();

    }

    public void openRegistrationActivity(View view) {
        Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    public void loginUser(View v){
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if(email.equals("") || password.equals("")){
            Toast.makeText(LoginActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    openDasboardActivity();
                } else{
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
