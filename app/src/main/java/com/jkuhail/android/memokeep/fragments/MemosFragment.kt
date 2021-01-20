package com.jkuhail.android.memokeep.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jkuhail.android.memokeep.R
import com.jkuhail.android.memokeep.adapters.MemoAdapter
import com.jkuhail.android.memokeep.adapters.StarredMemoAdapter
import com.jkuhail.android.memokeep.helpers.DbHelper.deleteMemo
import com.jkuhail.android.memokeep.helpers.DbHelper.retrieveMemos
import com.jkuhail.android.memokeep.helpers.DbHelper.retrieveStarredMemos
import com.jkuhail.android.memokeep.interfaces.OnItemDeleted
import com.jkuhail.android.memokeep.models.Memo
import java.util.*

class MemosFragment : Fragment() {
    private lateinit var viewStarredMemos: RecyclerView
    private lateinit var viewMemos: RecyclerView
    private var adapter: StarredMemoAdapter? = null
    var adapter2: MemoAdapter? = null
    private var data = ArrayList<Memo>()
    var data2 = ArrayList<Memo>()
    private var layoutManager: LinearLayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_memos, container, false)

        viewStarredMemos = root.findViewById(R.id.recyclerView_starred_memos)
        data = retrieveStarredMemos(requireContext())
        adapter = StarredMemoAdapter(data, requireContext())
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewStarredMemos.layoutManager = layoutManager
        viewStarredMemos.adapter = adapter

        viewMemos = root.findViewById(R.id.recyclerView_memos)
        data2 = retrieveMemos(requireContext())
        adapter2 = MemoAdapter(data2, requireContext(), object : OnItemDeleted {
            override fun onItemDeleted(v: View?, position: Int, id: Int) {
                deleteMemo(id, context!!)
                data2.removeAt(position)
                adapter2!!.notifyItemRemoved(position)
                updateStarredMemos()
            }
        })
        viewMemos.layoutManager = LinearLayoutManager(context)
        viewMemos.adapter = adapter2
        viewMemos.isNestedScrollingEnabled = false
        return root
    }

    private fun updateStarredMemos() {
        data = retrieveStarredMemos(requireContext())
        adapter = StarredMemoAdapter(data, requireContext())
        viewStarredMemos.adapter = adapter
    }
}