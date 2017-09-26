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

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText editTextRegisterEmail;
    EditText editTextRegisterPassword;
    String email, password;
    private FirebaseUser currentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextRegisterEmail = (EditText) findViewById(R.id.editTextRegisterEmail);
        editTextRegisterPassword = (EditText) findViewById(R.id.editTextRegisterPassword);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            openDasboardActivity();
            finish();
            return;
        }

    }

    public void openDasboardActivity (){
        Intent intent = new Intent(RegistrationActivity.this,DasboardActivity.class);
        startActivity(intent);
        finish();
    }

    public void openLoginActivity (View view) {
        Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void registerUser(View v){
        email = editTextRegisterEmail.getText().toString().trim();
        password = editTextRegisterPassword.getText().toString().trim();

        if(email.equals("") || password.equals("")){
            Toast.makeText(RegistrationActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    openDasboardActivity();
                } else{
                    Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
