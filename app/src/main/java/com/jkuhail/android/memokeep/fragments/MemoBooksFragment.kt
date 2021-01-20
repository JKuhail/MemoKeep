package com.jkuhail.android.memokeep.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jkuhail.android.memokeep.R
import com.jkuhail.android.memokeep.activities.MainActivity
import com.jkuhail.android.memokeep.adapters.MemoBookAdapter
import com.jkuhail.android.memokeep.helpers.DbHelper.retrieveMemoBooks
import com.jkuhail.android.memokeep.models.MemoBook
import kotlin.collections.ArrayList

class MemoBooksFragment : Fragment() {
    private lateinit var newNotebook: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MemoBookAdapter
    private var data: ArrayList<MemoBook> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_memo_books, container, false)

        recyclerView = root.findViewById(R.id.recyclerView)
        data = retrieveMemoBooks(requireContext())
        adapter = MemoBookAdapter(data, requireContext())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false

        newNotebook = root.findViewById(R.id.new_notebook_fragment)
        newNotebook.setOnClickListener { //this is how we call a class activity in a fragment ;)
            (activity as MainActivity?)!!.showSecondPopupWindow()
        }
        return root
    }
}