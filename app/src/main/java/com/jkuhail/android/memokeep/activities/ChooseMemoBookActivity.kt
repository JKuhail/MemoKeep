package com.jkuhail.android.memokeep.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jkuhail.android.memokeep.R
import com.jkuhail.android.memokeep.adapters.ChooseMemoBookAdapter
import com.jkuhail.android.memokeep.helpers.Constants
import com.jkuhail.android.memokeep.helpers.DbHelper.incrementMemoBookId
import com.jkuhail.android.memokeep.helpers.DbHelper.retrieveMemoBooks
import com.jkuhail.android.memokeep.helpers.DbHelper.saveMemoBook
import com.jkuhail.android.memokeep.helpers.Helper.getCurrentDate
import com.jkuhail.android.memokeep.models.MemoBook
import java.util.*
import kotlin.collections.ArrayList

class ChooseMemoBookActivity : AppCompatActivity() {
    var appBar: Toolbar? = null
    private lateinit var newNoteBook: Button
    private lateinit var backButton: Button
    private lateinit var recyclerView: RecyclerView
    private var adapter: ChooseMemoBookAdapter? = null
    private var window: PopupWindow? = null
    private var data: ArrayList<MemoBook> = ArrayList()
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_memo_book)
        appBar = findViewById(R.id.app_bar)
        setSupportActionBar(appBar)
        context = applicationContext
        newNoteBook = findViewById(R.id.new_notebook_fragment2)
        backButton = findViewById(R.id.back_to_memo_btn)
        recyclerView = findViewById(R.id.recyclerView2)
        data = retrieveMemoBooks(context)
        adapter = ChooseMemoBookAdapter(data, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        newNoteBook.setOnClickListener {
            //this is how we call a class activity in a fragment ;)
            showSecondPopupWindow()
        }
        backButton.setOnClickListener { finish() }
    }

    private fun showSecondPopupWindow() {
        try {
            val ok: Button
            val edNewNotebook: EditText
            val errorMessage: TextView
            val inflater = this@ChooseMemoBookActivity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.new_memo_book_popup, null)
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val height = LinearLayout.LayoutParams.MATCH_PARENT
            window = PopupWindow(layout, width, height, true)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.isOutsideTouchable = true
            window!!.showAtLocation(layout, Gravity.CENTER, 0, 0)
            //  window.showAtLocation(layout, 17, 100, 100);
            edNewNotebook = layout.findViewById(R.id.ed_new_notebook)
            ok = layout.findViewById(R.id.ok)
            errorMessage = layout.findViewById(R.id.error_message)
            ok.setOnClickListener {
                val memoBookName = edNewNotebook.text.toString().trim { it <= ' ' }
                if (edNewNotebook.length() == 0) {
                    errorMessage.visibility = View.VISIBLE
                    errorMessage.text = "Please enter a name!"
                    edNewNotebook.setBackgroundResource(R.drawable.error_edit_text_shape)
                } else if (isDuplicated(memoBookName)) {
                    errorMessage.visibility = View.VISIBLE
                    errorMessage.text = "This Memo book is already exists!"
                    edNewNotebook.setBackgroundResource(R.drawable.error_edit_text_shape)
                } else {
                    //TODO: handle the date
                    val date = getCurrentDate(DATE_FORMAT)
                    val id = incrementMemoBookId(context)
                    val memoBook = MemoBook(id, memoBookName, date)
                    saveMemoBook(memoBook, context)
                    val intent = Intent()
                    intent.putExtra(Constants.MEMO_BOOK_ID, id)
                    setResult(RESULT_OK, intent)
                    window!!.dismiss()
                    finish()
                }
            }
            layout.setOnTouchListener { view, motionEvent -> //Close the window when clicked
                window!!.dismiss()
                true
            }
        } catch (e: Exception) {
        }
    }

    private fun isDuplicated(word: String): Boolean {
        val memoBooks = retrieveMemoBooks(context)
        var memoBookName: String
        if (memoBooks.isNotEmpty()) {
            for (memoBook in memoBooks) {
                memoBookName = memoBook.name.toString()
                if (word == memoBookName) {
                    return true
                }
            }
        }
        return false
    }

    companion object {
        const val DATE_FORMAT = "MMM dd, yyyy"
    }
}