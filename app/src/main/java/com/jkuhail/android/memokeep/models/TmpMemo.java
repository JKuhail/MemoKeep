package com.jkuhail.android.memokeep.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.io.Serializable;


public class TmpMemo extends SugarRecord implements Serializable {

    String title , content ;
    boolean importance;
    int color;

    public TmpMemo() {
    }

    public TmpMemo(String title, String content, boolean importance, int color) {
        this.title = title;
        this.content = content;
        this.importance = importance;
        this.color = color;
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
