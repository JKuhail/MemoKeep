package com.jkuhail.android.memokeep.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jkuhail.android.memokeep.activities.CreateMemoActivity;
import com.jkuhail.android.memokeep.R;
import com.jkuhail.android.memokeep.helpers.Constants;
import com.jkuhail.android.memokeep.models.Memo;


import java.util.List;

public class StarredMemoAdapter extends RecyclerView.Adapter<StarredMemoAdapter.MemoHolder> {
    public List<Memo> data;
    private Context context;

    public StarredMemoAdapter(List<Memo> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == data.size()) ? R.layout.starred_memo_button : R.layout.starred_memo_item;
    }

    @NonNull
    @Override
    public MemoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root;

        if(viewType == R.layout.starred_memo_item) {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.starred_memo_item, parent, false);
        }else{
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.starred_memo_button, parent, false);
        }
        return new MemoHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoHolder holder, int position) {

        if(position == data.size()){
            holder.starred_memos_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , CreateMemoActivity.class);
                    intent.putExtra(Constants.MEMO_IMPORTANCE, true);
                    context.startActivity(intent);
                }
            });
        } else {
            final Memo memo = data.get(position);
            holder.t_memo_title.setText(memo.getTitle());
            holder.t_memo_content.setText(memo.getContent());

            switch (memo.getColor()) {
                case 1:
                    holder.memo_main.setCardBackgroundColor(holder.memo_main.getResources().getColor(R.color.memo_color_1));
                    holder.t_memo_title.setTextColor(Color.WHITE);
                    holder.t_memo_content.setTextColor(Color.WHITE);
                    holder.blank.setBackgroundColor(holder.blank.getResources().getColor(R.color.colorPrimary));
                    break;

                case 3:
                    holder.memo_main.setCardBackgroundColor(holder.memo_main.getResources().getColor(R.color.memo_color_3));
                    holder.t_memo_title.setTextColor(Color.WHITE);
                    holder.t_memo_content.setTextColor(Color.WHITE);
                    holder.blank.setBackgroundColor(holder.blank.getResources().getColor(R.color.colorPrimary));
                    break;
                case 4:
                    holder.memo_main.setCardBackgroundColor(holder.memo_main.getResources().getColor(R.color.memo_color_4));
                    holder.t_memo_title.setTextColor(Color.WHITE);
                    holder.t_memo_content.setTextColor(Color.WHITE);
                    holder.blank.setBackgroundColor(holder.blank.getResources().getColor(R.color.colorPrimary));
                    break;
                case 5:
                    holder.memo_main.setCardBackgroundColor(holder.memo_main.getResources().getColor(R.color.memo_color_5));
                    holder.t_memo_title.setTextColor(Color.WHITE);
                    holder.t_memo_content.setTextColor(Color.WHITE);
                    holder.blank.setBackgroundColor(holder.blank.getResources().getColor(R.color.colorPrimary));
                    break;
                case 6:
                    holder.memo_main.setCardBackgroundColor(holder.memo_main.getResources().getColor(R.color.memo_color_6));
                    holder.t_memo_title.setTextColor(Color.WHITE);
                    holder.t_memo_content.setTextColor(Color.WHITE);
                    holder.blank.setBackgroundColor(holder.blank.getResources().getColor(R.color.colorPrimary));
                    break;
                default:
                    holder.memo_main.setCardBackgroundColor(holder.memo_main.getResources().getColor(R.color.memo_color_0));

            }
            ;

            holder.memo_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context , CreateMemoActivity.class);
                    intent.putExtra(Constants.MEMO_OBJECT, memo);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    static class MemoHolder extends RecyclerView.ViewHolder{

        TextView t_memo_title;
        TextView t_memo_content;
        CardView memo_main , starred_memos_button;
        View blank;
        MemoHolder(@NonNull View itemView){
            super(itemView);
            t_memo_title =itemView.findViewById(R.id.t_memo_title);
            t_memo_content = itemView.findViewById(R.id.t_memo_content);
            memo_main = itemView.findViewById(R.id.memo_main);
            blank = itemView.findViewById(R.id.blank);
            starred_memos_button = itemView.findViewById(R.id.starred_memos_button);
        }


    }
}
