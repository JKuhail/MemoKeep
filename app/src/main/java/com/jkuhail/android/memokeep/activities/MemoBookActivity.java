package com.jkuhail.android.memokeep.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.jkuhail.android.memokeep.R;
import com.jkuhail.android.memokeep.adapters.MemoAdapter;
import com.jkuhail.android.memokeep.adapters.StarredMemoAdapter;
import com.jkuhail.android.memokeep.helpers.Constants;
import com.jkuhail.android.memokeep.helpers.DbHelper;
import com.jkuhail.android.memokeep.models.Memo;

import java.util.ArrayList;

public class MemoBookActivity extends AppCompatActivity {

    RecyclerView recyclerView_memos;
    MemoAdapter adapter;
    ArrayList<Memo> data = new ArrayList<>();
    LinearLayoutManager layoutManager;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_book);

        recyclerView_memos = findViewById(R.id.recyclerView_memos);

        id = getIntent().getIntExtra(Constants.MEMO_BOOK_ID, -1);
        data = DbHelper.retrieveMemoBookMemos(getApplicationContext(), id);
        adapter = new MemoAdapter(data, getApplicationContext(), (v, position, id)-> {
            DbHelper.deleteMemo(id, getApplicationContext());
            data.remove(position);
            adapter.notifyItemRemoved(position);
        });
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_memos.setLayoutManager(layoutManager);
        recyclerView_memos.setAdapter(adapter);
    }
}