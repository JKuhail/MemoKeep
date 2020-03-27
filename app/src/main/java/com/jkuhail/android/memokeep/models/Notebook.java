package com.jkuhail.android.memokeep.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class Notebook extends SugarRecord {
    private long id;
    String name , date;

    public Notebook() {
    }

    public Notebook(String name , String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }
}
