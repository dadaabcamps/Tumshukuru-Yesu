package com.drcdadaab.tumshukuruyesuadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListSongActivity extends AppCompatActivity {
    private static final String TAG = ListSongActivity.class.getSimpleName();
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference db;
    FirebaseRecyclerAdapter<Song, SongViewHolder> firebasenewsRecycleAdapter;
    ProgressBar progressBarSongList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);

        //Initialize Firebase DB
        db = FirebaseDatabase.getInstance().getReference();

        //SETUP RECYCLER
        rv = (RecyclerView) findViewById(R.id.recyclerViewSongList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(false);


        progressBarSongList = (ProgressBar) findViewById(R.id.progressBarSongList);
        progressBarSongList.setVisibility(View.VISIBLE);

        firebasenewsRecycleAdapter = new FirebaseRecyclerAdapter<Song, SongViewHolder>(Song.class, R.layout.song_list_item, SongViewHolder.class, db.child("Songs")) {
            @Override
            protected void populateViewHolder(SongViewHolder viewHolder, final Song model, final int position) {
                viewHolder.textViewSongListTitle.setText(model.getTitle());
                progressBarSongList.setVisibility(View.GONE);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //firebasenewsRecycleAdapter.getRef(position).removeValue();
                        openNoticeDetailActivity(model.getSongId(), model.getTitle(), model.getHymnnumber(), model.getContent(), model.getLanguage());
                    }
                });
            }

            private void openNoticeDetailActivity(String id, String title, String hymnnumber, String content, String language) {
                Intent intent = new Intent(ListSongActivity.this, ViewSongActivity.class);
                intent.putExtra("idKey", id);
                intent.putExtra("titleKey", title);
                intent.putExtra("HymnNumberKey", hymnnumber);
                intent.putExtra("ContentKey", content);
                intent.putExtra("languageKey", language);

                startActivity(intent);
            }
        };
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(firebasenewsRecycleAdapter);

    }
    public void openCreateSongActivity (View view) {
        Intent intent = new Intent(ListSongActivity.this, CreateSongActivity.class);
        startActivity(intent);
        finish();
    }
}
