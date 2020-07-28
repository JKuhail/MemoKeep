package com.jkuhail.android.memokeep.models;

import com.orm.SugarRecord;

import java.io.Serializable;


public class Memo extends SugarRecord implements Serializable {

    String title , content , date , memoBookName;
    boolean importance;
    int color;



    public Memo() {
    }

    public Memo(String title, String content, String date, boolean importance, int color , String memoBookName) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.importance = importance;
        this.color = color;
        this.memoBookName = memoBookName;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isImportance() {
        return importance;
    }

    public void setImportance(boolean importance) {
        this.importance = importance;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getMemoBookName() {
        return memoBookName;
    }

    public void setMemoBookName(String memoBookName) {
        this.memoBookName = memoBookName;
    }
}
