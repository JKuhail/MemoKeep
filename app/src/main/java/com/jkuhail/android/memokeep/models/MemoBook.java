package com.jkuhail.android.memokeep.models;

import com.orm.SugarRecord;


public class MemoBook extends SugarRecord {
    String name , date;

    public MemoBook() {
    }

    public MemoBook(String name , String date) {
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



}
