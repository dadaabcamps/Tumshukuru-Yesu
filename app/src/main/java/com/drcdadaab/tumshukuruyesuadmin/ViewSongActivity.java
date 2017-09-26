package com.drcdadaab.tumshukuruyesuadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewSongActivity extends AppCompatActivity {


    TextView textViewShowTitle, textViewHymnNumber, textViewShowContent;
    String title, HymnNumber, Content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_song2);

        textViewShowTitle = (TextView) findViewById(R.id.textViewShowTitle);
        textViewHymnNumber = (TextView) findViewById(R.id.textViewHymnNumber);
        textViewShowContent = (TextView) findViewById(R.id.textViewShowContent);

        Intent i = getIntent();
        title = i.getStringExtra("titleKey");
        HymnNumber =i.getStringExtra("HymnNumberKey");
        Content =i.getStringExtra("ContentKey");


        textViewShowTitle.setText(title);
        textViewHymnNumber.setText(HymnNumber);
        textViewShowContent.setText(Content);

    }


}