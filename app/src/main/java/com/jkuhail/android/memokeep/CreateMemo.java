package com.jkuhail.android.memokeep;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

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
import com.jkuhail.android.memokeep.models.Memo;
import com.jkuhail.android.memokeep.models.MemoBook;
import com.jkuhail.android.memokeep.models.TmpMemo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.TimeZone;


public class CreateMemo extends AppCompatActivity  {
    private Button back_btn , save_btn ;
    private EditText memo_title , memo_content;
    private TextView memo_book_btn;
    private ImageView color_picker , star;
    private PopupWindow window;
    private String memoTitle , memoContent , memoDate , tmpTitle , tmpContent;
    private MemoBook memoBook;
    private Memo memo1;
    private boolean memoImportance , tmpImportance;
    private int memoColor , tmpColor;
    public static final String DATE_FORMAT = "MMM dd, yyyy";
    TmpMemo tmpMemo;
    List<TmpMemo> tmpMemos;
    boolean isUpdate , isEdit;
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
        color_picker=findViewById(R.id.color_picker);
        memo_book_btn=findViewById(R.id.memo_book_btn);
        star = findViewById(R.id.star);
        main_view = findViewById(R.id.main_view);


        tmpMemos = TmpMemo.listAll(TmpMemo.class);
        if(!tmpMemos.isEmpty()){
            tmpMemo = tmpMemos.get(0);
            memo_title.setText(tmpMemo.getTitle());
            memo_content.setText(tmpMemo.getContent());
            memoColor = tmpMemo.getColor();
            memoImportance = tmpMemo.isImportance();
            tmpMemo.delete();
        }else{
            memoColor = 0;
            memoImportance = false;
        }

        memoImportance =  getIntent().getBooleanExtra("importance" , memoImportance);

        //edit memo
        memo1 = (Memo) getIntent().getSerializableExtra("MemoObject");
        if(memo1 != null){
            isUpdate = true;
            isEdit = true;
            tmpId = getIntent().getStringExtra("memoId");
            memo_title.setText(memo1.getTitle());
            memo_content.setText(memo1.getContent());
            memo_book_btn.setText(memo1.getMemoBookName());
            memoColor = memo1.getColor();
            memoImportance = memo1.isImportance();

            memo_title.setFocusable(false);
            memo_content.setFocusable(false);
            star.setVisibility(View.INVISIBLE);
            color_picker.setVisibility(View.INVISIBLE);
            save_btn.setVisibility(View.INVISIBLE);
        }
        while(isEdit){
            main_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    memo_title.setFocusableInTouchMode(true);
                    memo_content.setFocusableInTouchMode(true);
                    star.setVisibility(View.VISIBLE);
                    color_picker.setVisibility(View.VISIBLE);
                    save_btn.setVisibility(View.VISIBLE);
                    memo_content.requestFocus();
                    isEdit = false;
                }
            });

        }

        memoBook = (MemoBook) getIntent().getSerializableExtra("MemoBookObject");
        //(condition) ? expressionTrue :  expressionFalse;
        //memo_book_btn.setText(memoBook != null ? memoBook.getName() : "Choose Memo book");
        if(memoBook != null){
            memo_book_btn.setText(memoBook.getName());
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUpdate = false;
                if(!tmpMemos.isEmpty()) {
                    tmpMemo.delete();
                }
                finish();
            }
        });

        memo_book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save current memo state temporally
                tmpTitle = memo_title.getText().toString().trim();
                tmpContent = memo_content.getText().toString().trim();
                tmpImportance = memoImportance;
                tmpColor = memoColor;
                tmpMemo = new TmpMemo(tmpTitle , tmpContent , tmpImportance , tmpColor);
                tmpMemo.save();

                Intent intent = new Intent(getApplicationContext() , ChooseMemoBook.class);
                startActivity(intent);
            }
        });


        color_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowThirdPopupWindow();
            }
        });

        if(memoImportance)
            star.setImageResource(R.drawable.ic_star_light);

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!memoImportance){
                    star.setImageResource(R.drawable.ic_star_light);
                    memoImportance = true;
                }else{
                    star.setImageResource(R.drawable.ic_star_dark);
                    memoImportance = false;
                }
            }
        });

        switch (memoColor){
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

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memoTitle = memo_title.getText().toString().trim();
                memoContent = memo_content.getText().toString().trim();

                memoBook = (MemoBook) getIntent().getSerializableExtra("MemoBookObject");
                memoDate = getCurrentDate();

                if(memo_title.length() == 0 && memo_content.length() == 0 ){
                    Toast.makeText(CreateMemo.this , "No content to save. Memo discarded." , Toast.LENGTH_LONG).show();
                }else{
                    if(!isUpdate){
                        if(memoBook != null){
                            Memo memo = new Memo(memoTitle , memoContent , memoDate , memoImportance , memoColor , memoBook.getName());
                            memo.save();
                        }else{
                            Memo memo = new Memo(memoTitle , memoContent , memoDate , memoImportance , memoColor , "");
                            memo.save();
                        }
                        Toast.makeText(CreateMemo.this , "Memo saved." , Toast.LENGTH_LONG).show();
                    }else{
                        Memo memo = Memo.findById(Memo.class , Long.parseLong(tmpId));
                        memo.setTitle(memo_title.getText().toString().trim());
                        memo.setContent(memo_content.getText().toString().trim());
                        memo.setColor(memoColor);
                        memo.setImportance(memoImportance);
                        memo.setMemoBookName(memoBook != null ? memoBook.getName() : memo1.getMemoBookName());
                        memo.save();
                        Toast.makeText(CreateMemo.this , "Memo updated." , Toast.LENGTH_LONG).show();
                        isUpdate = false;
                    }
                    }
                Intent intent = new Intent(CreateMemo.this , MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void ShowThirdPopupWindow(){
        try {
            final CardView color1 , color3 , color4 , color5 , color6;
            final Button btn_default;

            hideSoftKeyboard(CreateMemo.this);

            LayoutInflater inflater = (LayoutInflater) CreateMemo.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        }catch (Exception e){}
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
        if(!tmpMemos.isEmpty()) {
            tmpMemo.delete();
        }
        Intent intent = new Intent(getApplicationContext() , MainActivity.class);
        getApplicationContext().startActivity(intent);
        isUpdate = false;
    }
}
