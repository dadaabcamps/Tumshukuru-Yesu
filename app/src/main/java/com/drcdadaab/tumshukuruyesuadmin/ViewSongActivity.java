package com.drcdadaab.tumshukuruyesuadmin;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewSongActivity extends AppCompatActivity {


    TextView textViewShowTitle, textViewHymnNumber, textViewShowContent;
    String title, HymnNumber, Content, id, language, language_hymnumber;
    Boolean hymn;

    FirebaseDatabase db;
    DatabaseReference dbSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_song2);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

// Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

// Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        db = FirebaseDatabase.getInstance();
        dbSongs = db.getReference("Songs");

        textViewShowTitle = (TextView) findViewById(R.id.textViewShowTitle);
        textViewHymnNumber = (TextView) findViewById(R.id.textViewHymnNumber);
        textViewShowContent = (TextView) findViewById(R.id.textViewShowContent);

        Intent i = getIntent();
        id = i.getStringExtra("idKey");
        title = i.getStringExtra("titleKey");
        HymnNumber =i.getStringExtra("HymnNumberKey");
        language_hymnumber =i.getStringExtra("language_hymnumberKey");
        Content =i.getStringExtra("ContentKey");
        language =i.getStringExtra("languageKey");
        hymn =i.getBooleanExtra("hymnKey", false);

        getSupportActionBar().setTitle(title);


        textViewShowTitle.setText(title);
        textViewHymnNumber.setText(HymnNumber);
        textViewShowContent.setText(Content);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crud_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent songIntent = new Intent(ViewSongActivity.this, EditSongActivity.class);

                songIntent.putExtra("idKey", id);
                songIntent.putExtra("titleKey", title);
                songIntent.putExtra("hymnNumberKey", HymnNumber);
                songIntent.putExtra("language_hymnumberKey", language_hymnumber);
                songIntent.putExtra("contentKey", Content);
                songIntent.putExtra("languageKey", language);
                songIntent.putExtra("hymnKey", hymn);

                startActivity(songIntent);
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_delete:

                try{
                    dbSongs.child(id).removeValue();
//                    progress.dismiss();
                    Toast.makeText(ViewSongActivity.this, "Song deleted", Toast.LENGTH_SHORT).show();
                }
                catch (DatabaseException e){
                    Toast.makeText(ViewSongActivity.this, e+"", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(new Intent(ViewSongActivity.this, ListSongActivity.class));
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



}