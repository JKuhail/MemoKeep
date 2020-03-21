package com.jkuhail.android.memokeep.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class Category extends SugarRecord {
    private long id;
    String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
