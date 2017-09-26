package com.drcdadaab.tumshukuruyesuadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    EditText editTextHymnNumber;
    EditText editTextContent;
    String title, hymnnumber, content, songId, language;
    FirebaseDatabase db;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_song);

        Spinner spinnerCreateSong = (Spinner) findViewById(R.id.spinnerCreateSong);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.song_language, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerCreateSong.setAdapter(adapter);
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("Songs");


        editTextHymnNumber = (EditText) findViewById(R.id.editTextHymnNumber);
        editTextContent = (EditText) findViewById(R.id.editTextContent);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
    }

    public void openViewSongActivity (String title, String hymnnumber, String content){
        Intent intent = new Intent(CreateSongActivity.this,ViewSongActivity.class);
        intent.putExtra("idKey", songId);
        intent.putExtra("titleKey", title);
        intent.putExtra("HymnNumberKey", hymnnumber);
        intent.putExtra("ContentKey", content);
        startActivity(intent);
        finish();
    }

    public void publishSong (View view){
        title = editTextTitle.getText().toString().trim();
        hymnnumber = editTextHymnNumber.getText().toString().trim();
        content = editTextContent.getText().toString().trim();

        if(title.equals("") || hymnnumber.equals("") || content.equals("") ){
            Toast.makeText(CreateSongActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        songId = dbRef.push().getKey();
        Song song = new Song(title, hymnnumber, content, songId, language);
        dbRef.child(songId).setValue(song);

        Toast.makeText(CreateSongActivity.this, "Song Created", Toast.LENGTH_SHORT).show();
        openViewSongActivity(title, hymnnumber, content );
     }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        language = parent.getItemAtPosition(pos).toString();

        if(language.equals("")){
            Toast.makeText(CreateSongActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
