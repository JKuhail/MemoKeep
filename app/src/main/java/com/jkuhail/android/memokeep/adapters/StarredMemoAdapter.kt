package com.jkuhail.android.memokeep.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jkuhail.android.memokeep.R
import com.jkuhail.android.memokeep.activities.CreateMemoActivity
import com.jkuhail.android.memokeep.helpers.Constants
import com.jkuhail.android.memokeep.models.Memo

class StarredMemoAdapter(var data: MutableList<Memo>,
                                 private val context: Context)
    : RecyclerView.Adapter<StarredMemoAdapter.MemoHolder>() {
    override fun getItemViewType(position: Int): Int {
        return if (position == data.size) R.layout.starred_memo_button else R.layout.starred_memo_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoHolder {
        val root: View = if (viewType == R.layout.starred_memo_item) {
            LayoutInflater.from(parent.context).inflate(R.layout.starred_memo_item, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.starred_memo_button, parent, false)
        }
        return MemoHolder(root)
    }

    override fun onBindViewHolder(holder: MemoHolder, position: Int) {
        if (position == data.size) {
            holder.starredMemosButton?.setOnClickListener { v: View? ->
                val intent = Intent(context, CreateMemoActivity::class.java)
                intent.putExtra(Constants.MEMO_IMPORTANCE, true)
                context.startActivity(intent)
            }
        } else {
            val memo = data[position]
            holder.memoTitle?.text = memo.title
            holder.memoContent?.text = memo.content
            when (memo.color) {
                1 -> {
                    holder.memoMain?.setCardBackgroundColor(holder.memoMain!!.resources.getColor(R.color.memo_color_1))
                    holder.memoTitle?.setTextColor(Color.WHITE)
                    holder.memoContent?.setTextColor(Color.WHITE)
                    holder.blank?.setBackgroundColor(holder.blank!!.resources.getColor(R.color.colorPrimary))
                }
                3 -> {
                    holder.memoMain?.setCardBackgroundColor(holder.memoMain!!.resources.getColor(R.color.memo_color_3))
                    holder.memoTitle?.setTextColor(Color.WHITE)
                    holder.memoContent?.setTextColor(Color.WHITE)
                    holder.blank?.setBackgroundColor(holder.blank!!.resources.getColor(R.color.colorPrimary))
                }
                4 -> {
                    holder.memoMain?.setCardBackgroundColor(holder.memoMain!!.resources.getColor(R.color.memo_color_4))
                    holder.memoTitle?.setTextColor(Color.WHITE)
                    holder.memoContent?.setTextColor(Color.WHITE)
                    holder.blank?.setBackgroundColor(holder.blank!!.resources.getColor(R.color.colorPrimary))
                }
                5 -> {
                    holder.memoMain?.setCardBackgroundColor(holder.memoMain!!.resources.getColor(R.color.memo_color_5))
                    holder.memoTitle?.setTextColor(Color.WHITE)
                    holder.memoContent?.setTextColor(Color.WHITE)
                    holder.blank?.setBackgroundColor(holder.blank!!.resources.getColor(R.color.colorPrimary))
                }
                6 -> {
                    holder.memoMain?.setCardBackgroundColor(holder.memoMain!!.resources.getColor(R.color.memo_color_6))
                    holder.memoTitle?.setTextColor(Color.WHITE)
                    holder.memoContent?.setTextColor(Color.WHITE)
                    holder.blank?.setBackgroundColor(holder.blank!!.resources.getColor(R.color.colorPrimary))
                }
                else -> holder.memoMain?.setCardBackgroundColor(holder.memoMain!!.resources.getColor(R.color.memo_color_0))
            }
            holder.memoMain?.setOnClickListener {
                val intent = Intent(context, CreateMemoActivity::class.java)
                intent.putExtra(Constants.MEMO_OBJECT, memo)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    class MemoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var memoTitle: TextView? = itemView.findViewById(R.id.t_memo_title)
        var memoContent: TextView? = itemView.findViewById(R.id.t_memo_content)
        var memoMain: CardView? = itemView.findViewById(R.id.memo_main)
        var starredMemosButton: CardView? = itemView.findViewById(R.id.starred_memos_button)
        var blank: View? = itemView.findViewById(R.id.blank)

    }
}