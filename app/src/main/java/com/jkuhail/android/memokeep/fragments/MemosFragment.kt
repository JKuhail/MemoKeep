package com.jkuhail.android.memokeep.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jkuhail.android.memokeep.R;
import com.jkuhail.android.memokeep.adapters.MemoAdapter;
import com.jkuhail.android.memokeep.adapters.StarredMemoAdapter;
import com.jkuhail.android.memokeep.helpers.DbHelper;
import com.jkuhail.android.memokeep.models.Memo;

import java.util.ArrayList;

public class MemosFragment extends Fragment {
    RecyclerView recyclerView_starred_memos, recyclerView_memos;
    StarredMemoAdapter adapter;
    MemoAdapter adapter2;
    ArrayList<Memo> data = new ArrayList<>();
    ArrayList<Memo> data2 = new ArrayList<>();
    LinearLayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_memos, container, false);

        recyclerView_starred_memos = root.findViewById(R.id.recyclerView_starred_memos);
        data = DbHelper.retrieveStarredMemos(getContext());
        adapter = new StarredMemoAdapter(data, getContext());
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_starred_memos.setLayoutManager(layoutManager);
        recyclerView_starred_memos.setAdapter(adapter);


        recyclerView_memos = root.findViewById(R.id.recyclerView_memos);
        data2 = DbHelper.retrieveMemos(getContext());
        adapter2 = new MemoAdapter(data2, getContext(), (v, position, id)-> {
            DbHelper.deleteMemo(id, getContext());
            data2.remove(position);
            adapter2.notifyItemRemoved(position);

            updateStarredMemos();
        });
        recyclerView_memos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_memos.setAdapter(adapter2);
        recyclerView_memos.setNestedScrollingEnabled(false);

        return root;
    }

    private void updateStarredMemos(){
        data = DbHelper.retrieveStarredMemos(getContext());
        adapter = new StarredMemoAdapter(data, getContext());
        recyclerView_starred_memos.setAdapter(adapter);
    }
}
