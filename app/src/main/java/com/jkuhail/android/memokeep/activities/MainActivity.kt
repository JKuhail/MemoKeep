package com.jkuhail.android.memokeep.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.jkuhail.android.memokeep.R
import com.jkuhail.android.memokeep.fragments.MemoBooksFragment
import com.jkuhail.android.memokeep.fragments.MemosFragment
import com.jkuhail.android.memokeep.helpers.DbHelper.incrementMemoBookId
import com.jkuhail.android.memokeep.helpers.DbHelper.retrieveMemoBooks
import com.jkuhail.android.memokeep.helpers.DbHelper.saveMemoBook
import com.jkuhail.android.memokeep.helpers.Helper.getCurrentDate
import com.jkuhail.android.memokeep.models.MemoBook
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceNavigationView
import com.luseen.spacenavigation.SpaceOnClickListener

class MainActivity : AppCompatActivity() {
    var appBar: Toolbar? = null
    private lateinit var navigationView: SpaceNavigationView
    private var window: PopupWindow? = null
    var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appBar = findViewById(R.id.app_bar)
        setSupportActionBar(appBar)
        context = applicationContext
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, MemosFragment()).commit()
        navigationView = findViewById(R.id.space)
        navigationView.initWithSaveInstanceState(savedInstanceState)
        navigationView.addSpaceItem(SpaceItem("Memos", R.drawable.ic_note))
        navigationView.addSpaceItem(SpaceItem("Memo books", R.drawable.ic_notebook))
        navigationView.setSpaceOnClickListener(object : SpaceOnClickListener {
            override fun onCentreButtonClick() {
                val intent = Intent(context, CreateMemoActivity::class.java)
                startActivity(intent)
            }

            override fun onItemClick(itemIndex: Int, itemName: String) {
                when (itemIndex) {
                    0 -> supportFragmentManager.beginTransaction().replace(R.id.mainContainer, MemosFragment()).commit()
                    1 -> supportFragmentManager.beginTransaction().replace(R.id.mainContainer, MemoBooksFragment()).commit()
                }
            }

            override fun onItemReselected(itemIndex: Int, itemName: String) {
                when (itemIndex) {
                    0 -> supportFragmentManager.beginTransaction().replace(R.id.mainContainer, MemosFragment()).commit()
                    1 -> supportFragmentManager.beginTransaction().replace(R.id.mainContainer, MemoBooksFragment()).commit()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.side_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.archive) {
            val intent = Intent(context, ArchiveActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    fun showSecondPopupWindow() {
        try {
            val ok: Button
            val edNewNotebook: EditText
            val errorMessage: TextView
            val inflater = this@MainActivity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.new_memo_book_popup, null)
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val height = LinearLayout.LayoutParams.MATCH_PARENT
            window = PopupWindow(layout, width, height, true)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.isOutsideTouchable = true
            window!!.showAtLocation(layout, Gravity.CENTER, 0, 0)
            edNewNotebook = layout.findViewById(R.id.ed_new_notebook)
            ok = layout.findViewById(R.id.ok)
            errorMessage = layout.findViewById(R.id.error_message)
            ok.setOnClickListener { v: View? ->
                val memo_book_name = edNewNotebook.text.toString().trim { it <= ' ' }
                if (edNewNotebook.length() == 0) {
                    errorMessage.visibility = View.VISIBLE
                    errorMessage.text = "Please enter a name!"
                    edNewNotebook.setBackgroundResource(R.drawable.error_edit_text_shape)
                } else if (isDuplicated(memo_book_name)) {
                    errorMessage.visibility = View.VISIBLE
                    errorMessage.text = "This Memo book is already exists!"
                    edNewNotebook.setBackgroundResource(R.drawable.error_edit_text_shape)
                } else {
                    val date = getCurrentDate(DATE_FORMAT)
                    val id = incrementMemoBookId(context!!)
                    val memoBook = MemoBook(id, memo_book_name, date)
                    saveMemoBook(memoBook, context!!)
                    window!!.dismiss()
                }
            }
            layout.setOnTouchListener { view, motionEvent -> //Close the window when clicked
                window!!.dismiss()
                true
            }
        } catch (e: Exception) {
        }
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }

    private fun isDuplicated(word: String): Boolean {
        val memoBooks = retrieveMemoBooks(context!!)
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