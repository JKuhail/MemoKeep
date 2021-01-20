package com.jkuhail.android.memokeep.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jkuhail.android.memokeep.R
import com.jkuhail.android.memokeep.helpers.Constants
import com.jkuhail.android.memokeep.models.MemoBook

class ChooseMemoBookAdapter(private val data: MutableList<MemoBook>,
                                    private val context: Context)
    : RecyclerView.Adapter<ChooseMemoBookAdapter.NotebookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.memo_book_item, parent, false)
        return NotebookHolder(root)
    }

    override fun onBindViewHolder(holder: NotebookHolder, position: Int) {
        val memoBook = data[position]
        holder.notebookName.text = memoBook.name
        holder.notebookDate.text = memoBook.date
        holder.notebookMain.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Constants.MEMO_BOOK_ID, memoBook.id)
            (context as Activity).setResult(Activity.RESULT_OK, intent)
            context.finish()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class NotebookHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var notebookName: TextView = itemView.findViewById(R.id.notebook_name)
        var notebookDate: TextView = itemView.findViewById(R.id.notebook_date)
        var notebookMain: CardView = itemView.findViewById(R.id.notebook_main)

    }
}