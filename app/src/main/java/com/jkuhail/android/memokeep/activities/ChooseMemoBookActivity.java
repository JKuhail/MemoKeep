package com.jkuhail.android.memokeep.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jkuhail.android.memokeep.R;
import com.jkuhail.android.memokeep.adapters.ChooseMemoBookAdapter;
import com.jkuhail.android.memokeep.helpers.Constants;
import com.jkuhail.android.memokeep.helpers.DbHelper;
import com.jkuhail.android.memokeep.models.MemoBook;

import java.util.ArrayList;
import java.util.List;

import static com.jkuhail.android.memokeep.activities.MainActivity.getCurrentDate;

public class ChooseMemoBookActivity extends AppCompatActivity {
    Toolbar appBar;
    Button new_notebook_fragment , back_to_memo_btn;
    RecyclerView recyclerView;
    ChooseMemoBookAdapter adapter;
    private PopupWindow window;
    List<MemoBook> data = new ArrayList<>();
//    List<MemoBook> memoBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_memo_book);
        appBar = findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        new_notebook_fragment = findViewById(R.id.new_notebook_fragment2);
        back_to_memo_btn = findViewById(R.id.back_to_memo_btn);
        recyclerView = findViewById(R.id.recyclerView2);

        data = DbHelper.retrieveMemoBooks(getApplicationContext());
        adapter = new ChooseMemoBookAdapter(data , this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

//        memoBooks = MemoBook.listAll(MemoBook.class);

        new_notebook_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this is how we call a class activity in a fragment ;)
                ShowSecondPopupWindow();

            }
        });

        back_to_memo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void ShowSecondPopupWindow(){
        try {
            final Button ok;
            final EditText ed_new_notebook;
            final TextView error_message;

            LayoutInflater inflater = (LayoutInflater) ChooseMemoBookActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.new_memo_book_popup, null);

            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;

            window = new PopupWindow(layout, width, height, true);

            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setOutsideTouchable(true);
            window.showAtLocation(layout, Gravity.CENTER, 0, 0);
            //  window.showAtLocation(layout, 17, 100, 100);

            ed_new_notebook = layout.findViewById(R.id.ed_new_notebook);
            ok = layout.findViewById(R.id.ok);
            error_message = layout.findViewById(R.id.error_message);

            ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String memobook_name = ed_new_notebook.getText().toString().trim();

                        if (ed_new_notebook.length() == 0) {
                            error_message.setVisibility(View.VISIBLE);
                            error_message.setText("Please enter a name!");
                            ed_new_notebook.setBackgroundResource(R.drawable.error_edit_text_shape);
                        }else if(isDuplicated(memobook_name)){
                            error_message.setVisibility(View.VISIBLE);
                            error_message.setText("This Memo book is already exists!");
                            ed_new_notebook.setBackgroundResource(R.drawable.error_edit_text_shape);
                        } else {
                            //TODO: handle the date
                            String date = getCurrentDate();
                            int id = DbHelper.incrementMemoBookId(getApplicationContext());
                            MemoBook memoBook = new MemoBook(id, memobook_name, date);
                            DbHelper.saveMemoBook( memoBook, getApplicationContext());
                            Intent intent = new Intent();
                            intent.putExtra(Constants.MEMO_BOOK_ID, id);
                            setResult(Activity.RESULT_OK, intent);
                            window.dismiss();
                            finish();
                        }
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

    public final boolean isDuplicated(String word){
        ArrayList<MemoBook> memoBooks = DbHelper.retrieveMemoBooks(getApplicationContext());
        String memoBookName;
        if(!memoBooks.isEmpty()){
            for (MemoBook memoBook : memoBooks) {
                memoBookName = memoBook.getName();
                if(word.equals(memoBookName)){
                    return true;
                }
            }
        }
        return false;
    }
}