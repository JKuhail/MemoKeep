package com.jkuhail.android.memokeep.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class Memo extends SugarRecord {
    private long id;
    String title , description , date;
    boolean importance;
    int color;
    //defining a relationship
    MemoBook memoBook;

    public Memo() {
    }

    public Memo(String title, String description, String date, boolean importance, int color) {
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
