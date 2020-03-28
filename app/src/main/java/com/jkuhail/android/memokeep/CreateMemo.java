package com.jkuhail.android.memokeep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;


import com.jkuhail.android.memokeep.models.Memo;
import com.jkuhail.android.memokeep.models.MemoBook;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class CreateMemo extends AppCompatActivity {
    private Button back_btn , save_btn;
    private CheckBox importance;
    private EditText memo_title , memo_content;
    private PopupWindow window;
    private String memoTitle , memoContent , memoDate;
    private boolean memoImportance;
    private int memoColor;

    public static final String DATE_FORMAT = "MMM dd, yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_memo);

        back_btn = findViewById(R.id.back_btn);
        save_btn = findViewById(R.id.save_btn);
        importance = findViewById(R.id.importance);
        memo_title = findViewById(R.id.memo_title);
        memo_content = findViewById(R.id.memo_content);

        memoColor = 0;


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateMemo.this , MainActivity.class);
                startActivity(intent);
            }
        });


        importance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(importance.isChecked()){
                    ShowThirdPopupWindow();
                }


            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memoTitle = memo_title.getText().toString().trim();
                memoContent = memo_content.getText().toString().trim();
                memoImportance = importance.isChecked();

                memoDate = getCurrentDate();
                if(memo_title.length() == 0 && memo_content.length() == 0 ){
                    Toast.makeText(CreateMemo.this , "No content to save. Memo discarded." , Toast.LENGTH_LONG).show();
                }else{
                    //TODO: handle memoBook
                    Memo memo = new Memo(memoTitle , memoContent , memoDate , memoImportance , memoColor , null);
                    memo.save();
                    Toast.makeText(CreateMemo.this , "Memo saved." , Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(CreateMemo.this , MainActivity.class);
                startActivity(intent);

            }
        });
    }



    public void ShowThirdPopupWindow(){
        try {
            final CardView color1 , color2 , color3 , color4 , color5 , color6;


            LayoutInflater inflater = (LayoutInflater) CreateMemo.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.color_picker_popup_window, null);

            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;

            window = new PopupWindow(layout, width, height, true);

            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setOutsideTouchable(true);
            window.showAtLocation(layout, Gravity.CENTER, 0, 0);
            //  window.showAtLocation(layout, 17, 100, 100);

            color1 = layout.findViewById(R.id.memo_color_1);
            color2 = layout.findViewById(R.id.memo_color_2);
            color3 = layout.findViewById(R.id.memo_color_3);
            color4 = layout.findViewById(R.id.memo_color_4);
            color5 = layout.findViewById(R.id.memo_color_5);
            color6 = layout.findViewById(R.id.memo_color_6);

            color1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memoColor = 1;
                    window.dismiss();
                }
            });
            color2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memoColor = 2;
                    window.dismiss();
                }
            });
            color3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memoColor = 3;
                    window.dismiss();
                }
            });
            color4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memoColor = 4;
                    window.dismiss();
                }
            });
            color5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memoColor = 5;
                    window.dismiss();
                }
            });
            color6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memoColor = 6;
                    window.dismiss();
                }
            });

            layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    //Close the window when clicked
                    importance.setChecked(false);
                    memoColor = 0;
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




}
