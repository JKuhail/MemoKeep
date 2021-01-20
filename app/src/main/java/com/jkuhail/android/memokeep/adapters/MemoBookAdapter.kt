package com.jkuhail.android.memokeep.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jkuhail.android.memokeep.R;
import com.jkuhail.android.memokeep.activities.CreateMemoActivity;
import com.jkuhail.android.memokeep.activities.MemoBookActivity;
import com.jkuhail.android.memokeep.helpers.Constants;
import com.jkuhail.android.memokeep.helpers.DbHelper;
import com.jkuhail.android.memokeep.models.MemoBook;

import java.util.List;

public class MemoBookAdapter extends RecyclerView.Adapter<MemoBookAdapter.NotebookHolder> {
    private List<MemoBook> data;
    private Context context;

    public MemoBookAdapter(List<MemoBook> data, Context context) {
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
        holder.notebook_main.setOnClickListener(view -> {
            Intent intent = new Intent(context , MemoBookActivity.class);
            intent.putExtra(Constants.MEMO_BOOK_ID, memoBook.getId());
            context.startActivity(intent);
        });
        holder.notebook_main.setOnLongClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, holder.notebook_main);
            popup.inflate(R.menu.memo_book_menu);
            popup.setOnMenuItemClickListener(item -> {

                switch (item.getItemId()){
                    case R.id.delete :
                        int memoBookId = memoBook.getId();
                        DbHelper.deleteMemoBook(memoBookId, context);
                        DbHelper.deleteMemos(memoBookId, context);
                        data.remove(position);
                        notifyItemRemoved(position);
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
            return true;
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
