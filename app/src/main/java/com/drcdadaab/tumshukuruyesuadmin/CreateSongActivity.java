package com.drcdadaab.tumshukuruyesuadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateSongActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText editTextTitle;
    Spinner spinnerCreateSong;
    CheckBox checkBoxHymn;
    EditText editTextHymnNumber;
    EditText editTextContent;
    String title, hymnnumber, content, songId, language, language_hymnumber;
    Boolean hymn = false;
    FirebaseDatabase db;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_song);


        spinnerCreateSong = (Spinner) findViewById(R.id.spinnerCreateSong);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.song_language, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCreateSong.setAdapter(adapter);
        spinnerCreateSong.setOnItemSelectedListener(this);

        checkBoxHymn = (CheckBox) findViewById(R.id.checkBoxHymn);
        editTextHymnNumber = (EditText) findViewById(R.id.editTextHymnNumber);
        editTextContent = (EditText) findViewById(R.id.editTextContent);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);

        spinnerCreateSong.setVisibility(View.INVISIBLE);
        editTextHymnNumber.setVisibility(View.INVISIBLE);

        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("Songs");
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBoxHymn:
                if (checked){
                    spinnerCreateSong.setVisibility(View.VISIBLE);
                    editTextHymnNumber.setVisibility(View.VISIBLE);
                }
            else{
                    spinnerCreateSong.setVisibility(View.INVISIBLE);
                    editTextHymnNumber.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        language = parent.getItemAtPosition(pos).toString();
        if(language.equals("")){
            Toast.makeText(CreateSongActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void openViewSongActivity (String title, String hymnnumber, String content, String language, Boolean hymn){
        Intent intent = new Intent(CreateSongActivity.this,ViewSongActivity.class);
        intent.putExtra("idKey", songId);
        intent.putExtra("titleKey", title);
        intent.putExtra("HymnNumberKey", hymnnumber);
        intent.putExtra("ContentKey", content);
        intent.putExtra("languageKey", language);
        intent.putExtra("hymnKey", hymn);
        startActivity(intent);
        finish();
    }

    public void publishSong (View view){
        title = editTextTitle.getText().toString().trim();
        hymnnumber = editTextHymnNumber.getText().toString().trim();
        content = editTextContent.getText().toString().trim();


        if (checkBoxHymn.isChecked()){
            hymn = true;

            if(hymnnumber.equals("")){
                Toast.makeText(CreateSongActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
                return;
            }
            int mod_hymnumber = 1000000 + Integer.valueOf(hymnnumber);
            language_hymnumber = String.valueOf(language+"_"+mod_hymnumber);
        }else{
            language = "";
        }

        if(title.equals("") || content.equals("") ){
            Toast.makeText(CreateSongActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        songId = dbRef.push().getKey();
        Song song = new Song(title, hymnnumber, content, songId, language, hymn, language_hymnumber);
        dbRef.child(songId).setValue(song);

        Toast.makeText(CreateSongActivity.this, "Song Created", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(CreateSongActivity.this, ListSongActivity.class));
//        openViewSongActivity(title, hymnnumber, content, language, hymn);
     }



}
