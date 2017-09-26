package com.drcdadaab.tumshukuruyesuadmin;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by DAGA ICT on 22/09/2017.
 */

public class SongViewHolder extends RecyclerView.ViewHolder  {
    TextView textViewSongListTitle;
    View mView;

    public SongViewHolder(View itemView){
        super(itemView);
        this.mView = itemView;
        textViewSongListTitle = (TextView) itemView.findViewById(R.id.textViewSongListTitle);
    }
}