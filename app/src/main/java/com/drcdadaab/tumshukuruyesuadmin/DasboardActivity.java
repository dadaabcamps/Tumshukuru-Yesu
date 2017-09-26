package com.drcdadaab.tumshukuruyesuadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class DasboardActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private static Boolean isVisited = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            openLoginActivity();
            finish();
            return;
        }

        if(!isVisited){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            isVisited = true;
        }
    }


    public void openLoginActivity () {
        Intent intent = new Intent(DasboardActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void openListSongActivity (View view) {
        Intent intent = new Intent(DasboardActivity.this, ListSongActivity.class);
        startActivity(intent);
        finish();
    }

    public void logOutUser(View v){
        mAuth.signOut();
        openLoginActivity();
    }
}
