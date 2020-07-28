package com.jkuhail.android.memokeep;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jkuhail.android.memokeep.adapters.MemoAdapter;
import com.jkuhail.android.memokeep.adapters.StarredMemoAdapter;
import com.jkuhail.android.memokeep.models.Memo;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

public class MemosFragment extends Fragment {
    RecyclerView recyclerView_starred_memos , recyclerView_memos;
    StarredMemoAdapter adapter;
    MemoAdapter adapter2;
    List<Memo> data = new ArrayList<>();
    List<Memo> data2 = new ArrayList<>();
    LinearLayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_memos, container, false);

        recyclerView_starred_memos = root.findViewById(R.id.recyclerView_starred_memos);
        data = Select.from(Memo.class).where(Condition.prop("importance").eq("1")).orderBy("Id DESC").list();
        adapter = new StarredMemoAdapter(data , getContext());
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_starred_memos.setLayoutManager(layoutManager);
        recyclerView_starred_memos.setAdapter(adapter);


        recyclerView_memos = root.findViewById(R.id.recyclerView_memos);
        data2 = Select.from(Memo.class).orderBy("Id DESC").list();
        adapter2 = new MemoAdapter(data2 , getContext());
        recyclerView_memos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_memos.setAdapter(adapter2);
        recyclerView_memos.setNestedScrollingEnabled(false);

        return root;
    }


}
