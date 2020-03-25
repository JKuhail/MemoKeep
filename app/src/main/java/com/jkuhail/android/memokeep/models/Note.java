package com.jkuhail.android.memokeep.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class Note extends SugarRecord {
    private long id;
    String title , description , date;
    boolean importance;
    int color;
    //defining a relationship
    Notebook notebook;

    public Note() {
    }

    public Note(String title, String description, String date, boolean importance, int color) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.importance = importance;
        this.color = color;
    }

    public Long getId() {
        return id;
    }
}
