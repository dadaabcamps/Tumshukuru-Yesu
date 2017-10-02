package com.drcdadaab.tumshukuruyesuadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditSongActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText editTextEditTitle;
    EditText editTextEditHymnNumber;
    EditText editTextEditContent;
    CheckBox checkBoxEditHymn;
    Spinner spinnerEditSong;
    String title, hymnnumber, content, id, language;
    Boolean hymn;
    FirebaseDatabase db;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_song);

        spinnerEditSong = (Spinner) findViewById(R.id.spinnerEditSong);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.song_language, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEditSong.setAdapter(adapter);
        spinnerEditSong.setOnItemSelectedListener(this);

        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("Songs");


        editTextEditHymnNumber = (EditText) findViewById(R.id.editTextEditHymnNumber);
        editTextEditContent = (EditText) findViewById(R.id.editTextEditContent);
        editTextEditTitle = (EditText) findViewById(R.id.editTextEditTitle);
        checkBoxEditHymn = (CheckBox) findViewById(R.id.checkBoxEditHymn);


        Intent i = getIntent();

        id = i.getStringExtra("idKey");
        title = i.getStringExtra("titleKey");
        hymnnumber =i.getStringExtra("hymnNumberKey");
        content =i.getStringExtra("contentKey");
        language =i.getStringExtra("languageKey");
        hymn =i.getBooleanExtra("hymnKey", false);

        int selectionPosition= adapter.getPosition(language);
        spinnerEditSong.setSelection(selectionPosition);

        editTextEditTitle.setText(title);
        editTextEditHymnNumber.setText(hymnnumber);
        editTextEditContent.setText(content);
        checkBoxEditHymn.setChecked(hymn);

        if(!hymn){
            spinnerEditSong.setVisibility(View.INVISIBLE);
            editTextEditHymnNumber.setVisibility(View.INVISIBLE);
        }
    }

    public void  editSong(View v){
        title = editTextEditTitle.getText().toString().trim();
        hymnnumber = editTextEditHymnNumber.getText().toString().trim();
        content = editTextEditContent.getText().toString().trim();

        if (checkBoxEditHymn.isChecked()){
            hymn = true;
            if(hymnnumber.equals("")){
                Toast.makeText(EditSongActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            language ="";
        }

        if(title.equals("")  || content.equals("") ){
            Toast.makeText(EditSongActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Song song = new Song(title, hymnnumber, content, id, language, hymn);
        dbRef.child(id).setValue(song);

        Toast.makeText(EditSongActivity.this, "Song Updated", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditSongActivity.this, ListSongActivity.class));
//        openViewSongActivity(title, hymnnumber, content );
    }

    public void openViewSongActivity (String title, String hymnnumber, String content){
        Intent intent = new Intent(EditSongActivity.this,ViewSongActivity.class);
        intent.putExtra("titleKey", title);
        intent.putExtra("HymnNumberKey", hymnnumber);
        intent.putExtra("ContentKey", content);
        startActivity(intent);
        finish();
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        language = parent.getItemAtPosition(pos).toString();

        if(language.equals("")){
            Toast.makeText(EditSongActivity.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBoxEditHymn:
                if (checked){
                    spinnerEditSong.setVisibility(View.VISIBLE);
                    editTextEditHymnNumber.setVisibility(View.VISIBLE);
                }
                else{
                    spinnerEditSong.setVisibility(View.INVISIBLE);
                    editTextEditHymnNumber.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}
