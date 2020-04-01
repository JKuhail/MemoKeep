package com.jkuhail.android.memokeep.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jkuhail.android.memokeep.ChooseMemoBook;
import com.jkuhail.android.memokeep.CreateMemo;
import com.jkuhail.android.memokeep.R;
import com.jkuhail.android.memokeep.models.MemoBook;

import java.util.List;

public class ChooseMemoBookAdapter extends RecyclerView.Adapter<ChooseMemoBookAdapter.NotebookHolder> {
    private List<MemoBook> data;
    private Context context;

    public ChooseMemoBookAdapter(List<MemoBook> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public NotebookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_book_item, parent , false);
        return new NotebookHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull NotebookHolder holder, int position) {
        final MemoBook memoBook = data.get(position);
        holder.notebook_name.setText(memoBook.getName());
        holder.notebook_date.setText(memoBook.getDate());
        holder.notebook_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , CreateMemo.class);
                i.putExtra("MemoBookObject", memoBook);
                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class NotebookHolder extends RecyclerView.ViewHolder{

        TextView notebook_name;
        TextView notebook_date;
        CardView notebook_main;

        NotebookHolder(@NonNull View itemView){
            super(itemView);
            notebook_name =itemView.findViewById(R.id.notebook_name);
            notebook_date = itemView.findViewById(R.id.notebook_date);
            notebook_main = itemView.findViewById(R.id.notebook_main);

        }


    }
}
