package com.jkuhail.android.memokeep;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jkuhail.android.memokeep.adapters.MemoBookAdapter;
import com.jkuhail.android.memokeep.models.MemoBook;

import java.util.ArrayList;
import java.util.List;

public class MemoBooksFragment extends Fragment {
    Button new_notebook_fragment;
    RecyclerView recyclerView;
    MemoBookAdapter adapter;
    List<MemoBook> data = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_memo_books, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);

        data = MemoBook.listAll(MemoBook.class);
        adapter = new MemoBookAdapter(data , getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        new_notebook_fragment = root.findViewById(R.id.new_notebook_fragment);
        new_notebook_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this is how we call a class activity in a fragment ;)
                ((MainActivity) getActivity()).ShowSecondPopupWindow();

            }
        });

        return root;
    }



}