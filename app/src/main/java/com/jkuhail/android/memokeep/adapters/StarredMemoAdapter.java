package com.jkuhail.android.memokeep.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jkuhail.android.memokeep.R;
import com.jkuhail.android.memokeep.models.Memo;


import java.util.List;

public class StarredMemoAdapter extends RecyclerView.Adapter<StarredMemoAdapter.MemoHolder> {
    private List<Memo> data;
    private Context context;

    public StarredMemoAdapter(List<Memo> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MemoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.starred_memo_item, parent , false);
        return new MemoHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoHolder holder, int position) {
        final Memo memo = data.get(position);
        holder.t_memo_title.setText(memo.getTitle());
        holder.t_memo_content.setText(memo.getContent());

        switch(memo.getColor()){
            case 1:
                holder.memo_main.setCardBackgroundColor( holder.memo_main.getResources().getColor(R.color.memo_color_1));
                holder.t_memo_title.setTextColor(Color.WHITE);
                holder.t_memo_content.setTextColor(Color.WHITE);
                holder.blank.setBackgroundColor(holder.blank.getResources().getColor(R.color.colorPrimary));
                break;

            case 3:
                holder.memo_main.setCardBackgroundColor( holder.memo_main.getResources().getColor(R.color.memo_color_3));
                holder.t_memo_title.setTextColor(Color.WHITE);
                holder.t_memo_content.setTextColor(Color.WHITE);
                holder.blank.setBackgroundColor(holder.blank.getResources().getColor(R.color.colorPrimary));
                break;
            case 4:
                holder.memo_main.setCardBackgroundColor( holder.memo_main.getResources().getColor(R.color.memo_color_4));
                holder.t_memo_title.setTextColor(Color.WHITE);
                holder.t_memo_content.setTextColor(Color.WHITE);
                holder.blank.setBackgroundColor(holder.blank.getResources().getColor(R.color.colorPrimary));
                break;
            case 5:
                holder.memo_main.setCardBackgroundColor( holder.memo_main.getResources().getColor(R.color.memo_color_5));
                holder.t_memo_title.setTextColor(Color.WHITE);
                holder.t_memo_content.setTextColor(Color.WHITE);
                holder.blank.setBackgroundColor(holder.blank.getResources().getColor(R.color.colorPrimary));
                break;
            case 6:
                holder.memo_main.setCardBackgroundColor( holder.memo_main.getResources().getColor(R.color.memo_color_6));
                holder.t_memo_title.setTextColor(Color.WHITE);
                holder.t_memo_content.setTextColor(Color.WHITE);
                holder.blank.setBackgroundColor(holder.blank.getResources().getColor(R.color.colorPrimary));
                break;
            default:
                holder.memo_main.setCardBackgroundColor( holder.memo_main.getResources().getColor(R.color.memo_color_0));

        };

        holder.memo_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: change this!
                Toast.makeText(context, "item clicked! the Id is: " + memo.getId().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MemoHolder extends RecyclerView.ViewHolder{

        TextView t_memo_title;
        TextView t_memo_content;
        CardView memo_main;
        View blank;
        MemoHolder(@NonNull View itemView){
            super(itemView);
            t_memo_title =itemView.findViewById(R.id.t_memo_title);
            t_memo_content = itemView.findViewById(R.id.t_memo_content);
            memo_main = itemView.findViewById(R.id.memo_main);
            blank = itemView.findViewById(R.id.blank);

        }


    }
}
