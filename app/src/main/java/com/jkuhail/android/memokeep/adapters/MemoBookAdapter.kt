package com.jkuhail.android.memokeep.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jkuhail.android.memokeep.R
import com.jkuhail.android.memokeep.activities.MemoBookActivity
import com.jkuhail.android.memokeep.helpers.Constants
import com.jkuhail.android.memokeep.helpers.DbHelper.deleteMemoBook
import com.jkuhail.android.memokeep.helpers.DbHelper.deleteMemos
import com.jkuhail.android.memokeep.models.MemoBook

class MemoBookAdapter(private val data: MutableList<MemoBook>,
                              private val context: Context)
    : RecyclerView.Adapter<MemoBookAdapter.NotebookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.memo_book_item, parent, false)
        return NotebookHolder(root)
    }

    override fun onBindViewHolder(holder: NotebookHolder, position: Int) {
        val memoBook = data[position]
        holder.notebookName.text = memoBook.name
        holder.notebookDate.text = memoBook.date

        holder.notebookMain.setOnClickListener {
            val intent = Intent(context, MemoBookActivity::class.java)
            intent.putExtra(Constants.MEMO_BOOK_ID, memoBook.id)
            context.startActivity(intent)
        }

        holder.notebookMain.setOnLongClickListener {
            val popup = PopupMenu(context, holder.notebookMain)
            popup.inflate(R.menu.memo_book_menu)
            popup.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.delete -> {
                        val memoBookId = memoBook.id
                        deleteMemoBook(memoBookId, context)
                        deleteMemos(memoBookId, context)
                        data.removeAt(position)
                        notifyItemRemoved(position)
                        return@setOnMenuItemClickListener true
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }
            popup.show()
            true
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