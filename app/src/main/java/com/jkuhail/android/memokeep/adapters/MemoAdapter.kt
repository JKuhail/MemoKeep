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
import com.jkuhail.android.memokeep.activities.CreateMemoActivity
import com.jkuhail.android.memokeep.helpers.Constants
import com.jkuhail.android.memokeep.interfaces.OnItemDeleted
import com.jkuhail.android.memokeep.models.Memo

class MemoAdapter(private val data: MutableList<Memo>,
                          private val context: Context,
                          private val onItemDeleted: OnItemDeleted)
    : RecyclerView.Adapter<MemoAdapter.MemoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.memo_item, parent, false)
        return MemoHolder(root)
    }

    override fun onBindViewHolder(holder: MemoHolder, position: Int) {
        val memo = data[position]
        holder.memoTitle.text = memo.title
        holder.memoContent.text = memo.content
        holder.memoDate.text = memo.date
        holder.memoMain.setOnClickListener {
            val intent = Intent(context, CreateMemoActivity::class.java)
            intent.putExtra(Constants.MEMO_OBJECT, memo)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        holder.memoMain.setOnLongClickListener {
            val popup = PopupMenu(context, holder.memoMain)
            popup.inflate(R.menu.memo_menu)
            popup.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.delete -> {
                        onItemDeleted.onItemDeleted(it, position, memo.id)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.archive -> return@setOnMenuItemClickListener false
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

    class MemoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var memoTitle: TextView = itemView.findViewById(R.id.t2_memo_title)
        var memoContent: TextView = itemView.findViewById(R.id.t2_memo_content)
        var memoDate: TextView = itemView.findViewById(R.id.memo_date)
        var memoMain: CardView = itemView.findViewById(R.id.memo_main_2)

    }
}