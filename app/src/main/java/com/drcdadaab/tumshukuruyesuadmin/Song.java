package com.drcdadaab.tumshukuruyesuadmin;

/**
 * Created by DAGA ICT on 21/09/2017.
 */

public class Song {

    String title, hymnnumber, content, songId, language, language_hymnumber;
    Boolean hymn;

    public Song(){}

    public Song( String title, String hymnnumber, String content, String songId, String language, Boolean hymn, String language_hymnumber){
        this.title = title;
        this.hymnnumber = hymnnumber;
        this.content = content;
        this.songId = songId;
        this.language = language;
        this.hymn = hymn;
        this.language_hymnumber = language_hymnumber;

    }

    public String getTitle(){ return title;}
    public String getSongId(){ return songId;}
    public String getContent() { return content;}
    public String getHymnnumber (){ return hymnnumber;}
    public String getLanguage (){ return language;}
    public Boolean getHymn (){ return hymn;}
    public String getLanguage_hymnumber(){ return language_hymnumber;}


}
