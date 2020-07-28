package com.jkuhail.android.memokeep.activities;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jkuhail.android.memokeep.R;
import com.jkuhail.android.memokeep.helpers.Constants;
import com.jkuhail.android.memokeep.helpers.DbHelper;
import com.jkuhail.android.memokeep.models.Memo;
import com.jkuhail.android.memokeep.models.MemoBook;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.TimeZone;


public class CreateMemoActivity extends AppCompatActivity {
    private Button back_btn, save_btn;
    private EditText memo_title, memo_content;
    private TextView memo_book_btn;
    private ImageView color_picker, star;
    private PopupWindow window;
    private String memoTitle, memoContent, memoDate;
    private int memoBookId;
    private MemoBook memoBook;
    private Memo editedMemo;
    private boolean memoImportance;
    private int memoColor;
    public static final String DATE_FORMAT = "MMM dd, yyyy";
    String tmpId;
    View main_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_memo);

        back_btn = findViewById(R.id.back_btn);
        save_btn = findViewById(R.id.save_btn);
        memo_title = findViewById(R.id.memo_title);
        memo_content = findViewById(R.id.memo_content);
        color_picker = findViewById(R.id.color_picker);
        memo_book_btn = findViewById(R.id.memo_book_btn);
        star = findViewById(R.id.star);
        main_view = findViewById(R.id.main_view);


        memoImportance = getIntent().getBooleanExtra(Constants.MEMO_IMPORTANCE, memoImportance);

        try{
            editedMemo = getIntent().getExtras().getParcelable(Constants.MEMO_OBJECT);
        }catch (Exception e){
            editedMemo = null;
        }


        /** Editing mode...*/
        if (editedMemo != null) {
            memo_title.setText(editedMemo.getTitle());

            memo_content.setText(editedMemo.getContent());

            memoColor = editedMemo.getColor();

            memoImportance = editedMemo.isImportance();

            memoBook = DbHelper.findMemoBook(editedMemo.getMemoBookId(), getApplicationContext());
            memo_book_btn.setText(memoBook.getName());
        }

        back_btn.setOnClickListener(view -> finish());

        memo_book_btn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ChooseMemoBookActivity.class);
            startActivityForResult(intent, 2);
        });

        color_picker.setOnClickListener(view -> ShowThirdPopupWindow());

        if (memoImportance)
            star.setImageResource(R.drawable.ic_star_light);


        star.setOnClickListener(view -> {
            if (!memoImportance) {
                star.setImageResource(R.drawable.ic_star_light);
                memoImportance = true;
            } else {
                star.setImageResource(R.drawable.ic_star_dark);
                memoImportance = false;
            }
        });

        switch (memoColor) {
            case 1:
                color_picker.getBackground().setColorFilter(Color.parseColor("#D32727"), PorterDuff.Mode.SRC_ATOP);
                break;
            case 3:
                color_picker.getBackground().setColorFilter(Color.parseColor("#EF8E0B"), PorterDuff.Mode.SRC_ATOP);
                break;
            case 4:
                color_picker.getBackground().setColorFilter(Color.parseColor("#03A8C4"), PorterDuff.Mode.SRC_ATOP);
                break;
            case 5:
                color_picker.getBackground().setColorFilter(Color.parseColor("#516F55"), PorterDuff.Mode.SRC_ATOP);
                break;
            case 6:
                color_picker.getBackground().setColorFilter(Color.parseColor("#842c72"), PorterDuff.Mode.SRC_ATOP);
                break;
            default:
                color_picker.getBackground().setColorFilter(Color.parseColor("#737373"), PorterDuff.Mode.SRC_ATOP);
                break;
        }

        save_btn.setOnClickListener(view -> {
            memoTitle = memo_title.getText().toString().trim();
            memoContent = memo_content.getText().toString().trim();

            memoDate = getCurrentDate();

            if (memo_title.length() == 0 && memo_content.length() == 0) {
                Toast.makeText(CreateMemoActivity.this, "No content to save. Memo discarded.", Toast.LENGTH_LONG).show();
            } else {
                if (editedMemo == null) {
                    int memoId = DbHelper.incrementMemoId(getApplicationContext());
                    Memo memo = new Memo(memoId, memoTitle, memoContent, memoDate, memoBookId, memoImportance, false, memoColor);
                    DbHelper.saveMemo(memo, getApplicationContext());
                    Toast.makeText(CreateMemoActivity.this, "Memo saved.", Toast.LENGTH_LONG).show();
                } else {
                    editedMemo.setTitle(memoTitle);
                    editedMemo.setContent(memoContent);
                    editedMemo.setMemoBookId(memoBookId);
                    editedMemo.setImportance(memoImportance);
                    editedMemo.setColor(memoColor);
                    editedMemo.setDate(memoDate);
                    DbHelper.saveMemo(editedMemo, getApplicationContext());
                    Toast.makeText(CreateMemoActivity.this, "Memo updated.", Toast.LENGTH_LONG).show();
                }
            }
            Intent intent = new Intent(CreateMemoActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }

    public void ShowThirdPopupWindow() {
        try {
            final CardView color1, color3, color4, color5, color6;
            final Button btn_default;

            hideSoftKeyboard(CreateMemoActivity.this);

            LayoutInflater inflater = (LayoutInflater) CreateMemoActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.color_picker_popup_window, null);

            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;

            window = new PopupWindow(layout, width, height, true);

            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setOutsideTouchable(true);
            window.showAtLocation(layout, Gravity.CENTER, 0, 0);


            color1 = layout.findViewById(R.id.memo_color_1);
            color3 = layout.findViewById(R.id.memo_color_3);
            color4 = layout.findViewById(R.id.memo_color_4);
            color5 = layout.findViewById(R.id.memo_color_5);
            color6 = layout.findViewById(R.id.memo_color_6);
            btn_default = layout.findViewById(R.id.btn_default);


            color1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memoColor = 1;
                    color_picker.getBackground().setColorFilter(Color.parseColor("#D32727"), PorterDuff.Mode.SRC_ATOP);
                    window.dismiss();
                }
            });

            color3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memoColor = 3;
                    color_picker.getBackground().setColorFilter(Color.parseColor("#EF8E0B"), PorterDuff.Mode.SRC_ATOP);
                    window.dismiss();
                }
            });
            color4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memoColor = 4;
                    color_picker.getBackground().setColorFilter(Color.parseColor("#03A8C4"), PorterDuff.Mode.SRC_ATOP);
                    window.dismiss();
                }
            });
            color5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memoColor = 5;
                    color_picker.getBackground().setColorFilter(Color.parseColor("#516F55"), PorterDuff.Mode.SRC_ATOP);
                    window.dismiss();
                }
            });
            color6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memoColor = 6;
                    color_picker.getBackground().setColorFilter(Color.parseColor("#842c72"), PorterDuff.Mode.SRC_ATOP);
                    window.dismiss();
                }
            });


            btn_default.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memoColor = 0;
                    color_picker.getBackground().setColorFilter(Color.parseColor("#737373"), PorterDuff.Mode.SRC_ATOP);
                    window.dismiss();
                }
            });

            layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    //Close the window when clicked
                    window.dismiss();
                    return true;
                }
            });

        } catch (Exception e) {
        }
    }


    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            memoBookId = data.getIntExtra(Constants.MEMO_BOOK_ID, -1);
            if (memoBookId != -1) {
                memoBook = DbHelper.findMemoBook(memoBookId, getApplicationContext());
                memo_book_btn.setText(memoBook.getName());
            }
        }
    }
}
