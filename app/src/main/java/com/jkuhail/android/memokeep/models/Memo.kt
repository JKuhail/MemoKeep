package com.jkuhail.android.memokeep.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Memo extends RealmObject implements Parcelable {

    @PrimaryKey
    private int id;
    private int memoBookId;
    private String title , content , date;
    private boolean importance, archive;
    private int color;

    public Memo(int id, String title, String content, String date, int memoBookId, boolean importance, boolean archive, int color) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.memoBookId = memoBookId;
        this.importance = importance;
        this.archive = archive;
        this.color = color;
    }

    public Memo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getMemoBookId() {
        return memoBookId;
    }

    public void setMemoBookId(int memoBookId) {
        this.memoBookId = memoBookId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.date);
        dest.writeInt(this.memoBookId);
        dest.writeByte(this.importance ? (byte) 1 : (byte) 0);
        dest.writeByte(this.archive ? (byte) 1 : (byte) 0);
        dest.writeInt(this.color);
    }

    protected Memo(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.date = in.readString();
        this.memoBookId = in.readInt();
        this.importance = in.readByte() != 0;
        this.archive = in.readByte() != 0;
        this.color = in.readInt();
    }

    public static final Parcelable.Creator<Memo> CREATOR = new Parcelable.Creator<Memo>() {
        @Override
        public Memo createFromParcel(Parcel source) {
            return new Memo(source);
        }

        @Override
        public Memo[] newArray(int size) {
            return new Memo[size];
        }
    };


}
