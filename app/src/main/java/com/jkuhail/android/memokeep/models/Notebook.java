package com.jkuhail.android.memokeep.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class Notebook extends SugarRecord {
    private long id;
    String name;

    public Notebook() {
    }

    public Notebook(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
