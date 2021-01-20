package com.jkuhail.android.memokeep.models;


import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MemoBook extends RealmObject implements Parcelable {

    @PrimaryKey
    private int id;
    private String name , date;

    public MemoBook(int id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public MemoBook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.date);
    }

    protected MemoBook(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<MemoBook> CREATOR = new Parcelable.Creator<MemoBook>() {
        @Override
        public MemoBook createFromParcel(Parcel source) {
            return new MemoBook(source);
        }

        @Override
        public MemoBook[] newArray(int size) {
            return new MemoBook[size];
        }
    };
}
