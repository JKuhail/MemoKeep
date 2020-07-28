package com.jkuhail.android.memokeep.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jkuhail.android.memokeep.activities.CreateMemoActivity;
import com.jkuhail.android.memokeep.R;
import com.jkuhail.android.memokeep.helpers.Constants;
import com.jkuhail.android.memokeep.models.Memo;

import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoHolder> {
    private List<Memo> data2;
    private Context context;

    public MemoAdapter(List<Memo> data2, Context context) {
        this.data2 = data2;
        this.context = context;
    }

    @NonNull
    @Override
    public MemoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_item, parent , false);
        return new MemoHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoHolder holder, int position) {
        final Memo memo = data2.get(position);
        holder.t2_memo_title.setText(memo.getTitle());
        holder.t2_memo_content.setText(memo.getContent());
        holder.memo_date.setText(memo.getDate());

        holder.memo_main_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , CreateMemoActivity.class);
                intent.putExtra(Constants.MEMO_OBJECT, memo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data2.size();
    }

    static class MemoHolder extends RecyclerView.ViewHolder{

        TextView t2_memo_title;
        TextView t2_memo_content;
        TextView memo_date;
        CardView memo_main_2;
        View blank2;
        MemoHolder(@NonNull View itemView){
            super(itemView);
            t2_memo_title =itemView.findViewById(R.id.t2_memo_title);
            t2_memo_content = itemView.findViewById(R.id.t2_memo_content);
            memo_main_2 = itemView.findViewById(R.id.memo_main_2);
            blank2 = itemView.findViewById(R.id.blank2);
            memo_date = itemView.findViewById(R.id.memo_date);

        }


    }
}
