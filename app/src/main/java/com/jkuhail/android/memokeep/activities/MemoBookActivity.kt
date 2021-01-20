package com.jkuhail.android.memokeep.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jkuhail.android.memokeep.R
import com.jkuhail.android.memokeep.adapters.MemoAdapter
import com.jkuhail.android.memokeep.helpers.Constants
import com.jkuhail.android.memokeep.helpers.DbHelper.deleteMemo
import com.jkuhail.android.memokeep.helpers.DbHelper.retrieveMemoBookMemos
import com.jkuhail.android.memokeep.interfaces.OnItemDeleted
import com.jkuhail.android.memokeep.models.Memo
import java.util.*

class MemoBookActivity : AppCompatActivity() {
    lateinit var viewMemos: RecyclerView
    lateinit var adapter: MemoAdapter
    var data = ArrayList<Memo>()
    var layoutManager: LinearLayoutManager? = null
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_book)
        viewMemos = findViewById(R.id.recyclerView_memos)
        id = intent.getIntExtra(Constants.MEMO_BOOK_ID, -1)
        data = retrieveMemoBookMemos(applicationContext, id)
        adapter = MemoAdapter(data, applicationContext, object : OnItemDeleted {
            override fun onItemDeleted(v: View?, position: Int, id: Int) {
                deleteMemo(id, applicationContext)
                data.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        })
        layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        viewMemos.layoutManager = layoutManager
        viewMemos.adapter = adapter
    }
}