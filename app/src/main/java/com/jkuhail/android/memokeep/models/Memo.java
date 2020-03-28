package com.jkuhail.android.memokeep.models;

import com.orm.SugarRecord;



public class Memo extends SugarRecord {


    String title , content , date;
    boolean importance;
    int color;

    //defining a relationship
    MemoBook memoBook;

    public MemoBook getMemoBook() {
        return memoBook;
    }

    public void setMemoBook(MemoBook memoBook) {
        this.memoBook = memoBook;
    }

    public Memo() {
    }

    public Memo(String title, String content, String date, boolean importance, int color , MemoBook memoBook) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.importance = importance;
        this.color = color;
        this.memoBook = memoBook;
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


}
