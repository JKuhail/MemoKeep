package com.jkuhail.android.memokeep.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.jkuhail.android.memokeep.R
import com.jkuhail.android.memokeep.helpers.Constants
import com.jkuhail.android.memokeep.helpers.DbHelper.findMemoBook
import com.jkuhail.android.memokeep.helpers.DbHelper.incrementMemoId
import com.jkuhail.android.memokeep.helpers.DbHelper.saveMemo
import com.jkuhail.android.memokeep.helpers.Helper.getCurrentDate
import com.jkuhail.android.memokeep.helpers.Helper.hideSoftKeyboard
import com.jkuhail.android.memokeep.models.Memo
import com.jkuhail.android.memokeep.models.MemoBook

class CreateMemoActivity : AppCompatActivity() {
    private lateinit var backBtn: Button
    private lateinit var saveBtn: Button
    private lateinit var etMemoTitle: EditText
    private lateinit var etMemoContent: EditText
    private lateinit var memoBookBtn: TextView
    private lateinit var colorPicker: ImageView
    private lateinit var star: ImageView
    private lateinit var window: PopupWindow
    private lateinit var memoTitle: String
    private lateinit var memoContent: String
    private lateinit var memoDate: String
    private var memoBookId = 0
    private var memoBook: MemoBook? = null
    private var editedMemo: Memo? = null
    private var memoImportance = false
    private var memoColor = 0
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_memo)
        backBtn = findViewById(R.id.back_btn)
        saveBtn = findViewById(R.id.save_btn)
        etMemoTitle = findViewById(R.id.memo_title)
        etMemoContent = findViewById(R.id.memo_content)
        colorPicker = findViewById(R.id.color_picker)
        memoBookBtn = findViewById(R.id.memo_book_btn)
        star = findViewById(R.id.star)

        etMemoContent.autoLinkMask = Linkify.ALL
        etMemoContent.movementMethod = LinkMovementMethod.getInstance()
        //If the edit text contains previous text with potential links
        Linkify.addLinks(etMemoContent, Linkify.ALL)
        etMemoContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                saveBtn.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable) {
                Linkify.addLinks(s, Linkify.ALL)
            }
        })
        context = applicationContext
        memoImportance = intent.getBooleanExtra(Constants.MEMO_IMPORTANCE, memoImportance)
        editedMemo = try {
            intent.extras!!.getParcelable(Constants.MEMO_OBJECT)
        } catch (e: Exception) {
            null
        }


        //Editing mode
        if (editedMemo != null) {
            etMemoTitle.setText(editedMemo!!.title)
            etMemoContent.setText(editedMemo!!.content)
            memoColor = editedMemo!!.color
            memoImportance = editedMemo!!.isImportance
            memoBook = findMemoBook(editedMemo!!.memoBookId, context)
            memoBookBtn.text = memoBook!!.name
            saveBtn.visibility = View.GONE
            saveBtn.text = "Update"
        } else {
            etMemoContent.requestFocus()
            //            Helper.showSoftKeyboard(CreateMemoActivity.this);
        }
        backBtn.setOnClickListener(View.OnClickListener {
            hideSoftKeyboard(this@CreateMemoActivity)
            finish()
        })
        memoBookBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, ChooseMemoBookActivity::class.java)
            startActivityForResult(intent, 2)
        })
        colorPicker.setOnClickListener(View.OnClickListener { showThirdPopupWindow() })
        if (memoImportance) star.setImageResource(R.drawable.ic_star_light)
        star.setOnClickListener(View.OnClickListener {
            memoImportance = if (!memoImportance) {
                star.setImageResource(R.drawable.ic_star_light)
                true
            } else {
                star.setImageResource(R.drawable.ic_star_dark)
                false
            }
        })
        when (memoColor) {
            1 -> colorPicker.background.setColorFilter(Color.parseColor("#D32727"), PorterDuff.Mode.SRC_ATOP)
            3 -> colorPicker.background.setColorFilter(Color.parseColor("#EF8E0B"), PorterDuff.Mode.SRC_ATOP)
            4 -> colorPicker.background.setColorFilter(Color.parseColor("#03A8C4"), PorterDuff.Mode.SRC_ATOP)
            5 -> colorPicker.background.setColorFilter(Color.parseColor("#516F55"), PorterDuff.Mode.SRC_ATOP)
            6 -> colorPicker.background.setColorFilter(Color.parseColor("#842c72"), PorterDuff.Mode.SRC_ATOP)
            else -> colorPicker.background.setColorFilter(Color.parseColor("#737373"), PorterDuff.Mode.SRC_ATOP)
        }
        saveBtn.setOnClickListener {
            memoTitle = etMemoTitle.text.toString().trim { it <= ' ' }
            memoContent = etMemoContent.text.toString().trim { it <= ' ' }
            memoDate = getCurrentDate(DATE_FORMAT)
            if (etMemoTitle.length() == 0 && etMemoContent.length() == 0) {
                Toast.makeText(this@CreateMemoActivity, "No content to save. Memo discarded.", Toast.LENGTH_LONG).show()
            } else {
                if (editedMemo == null) {
                    val memoId = incrementMemoId(context)
                    val memo = Memo(memoId, memoTitle, memoContent, memoDate, memoBookId, memoImportance, false, memoColor)
                    saveMemo(memo, context)
                    Toast.makeText(this@CreateMemoActivity, "Memo saved.", Toast.LENGTH_LONG).show()
                } else {
                    editedMemo!!.title = memoTitle
                    editedMemo!!.content = memoContent
                    editedMemo!!.memoBookId = memoBookId
                    editedMemo!!.isImportance = memoImportance
                    editedMemo!!.color = memoColor
                    editedMemo!!.date = memoDate
                    saveMemo(editedMemo!!, context)
                    Toast.makeText(this@CreateMemoActivity, "Memo updated.", Toast.LENGTH_LONG).show()
                }
            }
            hideSoftKeyboard(this@CreateMemoActivity)
            val intent = Intent(this@CreateMemoActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showThirdPopupWindow() {
        try {
            val color1: CardView
            val color3: CardView
            val color4: CardView
            val color5: CardView
            val color6: CardView
            val btnDefault: Button
            hideSoftKeyboard(this@CreateMemoActivity)
            val inflater = this@CreateMemoActivity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.color_picker_popup_window, null)
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val height = LinearLayout.LayoutParams.MATCH_PARENT
            window = PopupWindow(layout, width, height, true)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.isOutsideTouchable = true
            window.showAtLocation(layout, Gravity.CENTER, 0, 0)
            color1 = layout.findViewById(R.id.memo_color_1)
            color3 = layout.findViewById(R.id.memo_color_3)
            color4 = layout.findViewById(R.id.memo_color_4)
            color5 = layout.findViewById(R.id.memo_color_5)
            color6 = layout.findViewById(R.id.memo_color_6)
            btnDefault = layout.findViewById(R.id.btn_default)
            color1.setOnClickListener {
                memoColor = 1
                colorPicker.background.setColorFilter(Color.parseColor("#D32727"), PorterDuff.Mode.SRC_ATOP)
                window.dismiss()
            }
            color3.setOnClickListener {
                memoColor = 3
                colorPicker.background.setColorFilter(Color.parseColor("#EF8E0B"), PorterDuff.Mode.SRC_ATOP)
                window.dismiss()
            }
            color4.setOnClickListener {
                memoColor = 4
                colorPicker.background.setColorFilter(Color.parseColor("#03A8C4"), PorterDuff.Mode.SRC_ATOP)
                window.dismiss()
            }
            color5.setOnClickListener {
                memoColor = 5
                colorPicker.background.setColorFilter(Color.parseColor("#516F55"), PorterDuff.Mode.SRC_ATOP)
                window.dismiss()
            }
            color6.setOnClickListener {
                memoColor = 6
                colorPicker.background.setColorFilter(Color.parseColor("#842c72"), PorterDuff.Mode.SRC_ATOP)
                window.dismiss()
            }
            btnDefault.setOnClickListener {
                memoColor = 0
                colorPicker.background.setColorFilter(Color.parseColor("#737373"), PorterDuff.Mode.SRC_ATOP)
                window.dismiss()
            }
            layout.setOnTouchListener { view, motionEvent -> //Close the window when clicked
                window!!.dismiss()
                true
            }
        } catch (e: Exception) {
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            memoBookId = data.getIntExtra(Constants.MEMO_BOOK_ID, -1)
            if (memoBookId != -1) {
                memoBook = findMemoBook(memoBookId, context)
                memoBookBtn.text = memoBook!!.name
            }
        }
    }

    companion object {
        const val DATE_FORMAT = "MMM dd, yyyy"
    }
}