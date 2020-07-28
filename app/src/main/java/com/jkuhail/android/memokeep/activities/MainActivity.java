package com.jkuhail.android.memokeep.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jkuhail.android.memokeep.fragments.MemoBooksFragment;
import com.jkuhail.android.memokeep.fragments.MemosFragment;
import com.jkuhail.android.memokeep.R;
import com.jkuhail.android.memokeep.helpers.DbHelper;
import com.jkuhail.android.memokeep.models.MemoBook;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    Toolbar appBar;
    SpaceNavigationView navigationView;
    private PopupWindow window;
    public static final String DATE_FORMAT = "MMM dd, yyyy";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appBar = findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        context = getApplicationContext();

        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MemosFragment()).commit();
        navigationView = findViewById(R.id.space);
        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("Memos", R.drawable.ic_note));
        navigationView.addSpaceItem(new SpaceItem("Memo books", R.drawable.ic_notebook));

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent intent = new Intent(context, CreateMemoActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MemosFragment()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MemoBooksFragment()).commit();
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                switch (itemIndex) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MemosFragment()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MemoBooksFragment()).commit();
                        break;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.archive) {
            Intent intent = new Intent(context, ArchiveActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public void ShowSecondPopupWindow() {
        try {
            final Button ok;
            final EditText ed_new_notebook;
            final TextView error_message;

            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.new_memo_book_popup, null);

            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;

            window = new PopupWindow(layout, width, height, true);

            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setOutsideTouchable(true);
            window.showAtLocation(layout, Gravity.CENTER, 0, 0);


            ed_new_notebook = layout.findViewById(R.id.ed_new_notebook);
            ok = layout.findViewById(R.id.ok);
            error_message = layout.findViewById(R.id.error_message);
            ok.setOnClickListener(v -> {
                String memo_book_name = ed_new_notebook.getText().toString().trim();

                if (ed_new_notebook.length() == 0) {
                    error_message.setVisibility(View.VISIBLE);
                    error_message.setText("Please enter a name!");
                    ed_new_notebook.setBackgroundResource(R.drawable.error_edit_text_shape);
                } else if (isDuplicated(memo_book_name)) {
                    error_message.setVisibility(View.VISIBLE);
                    error_message.setText("This Memo book is already exists!");
                    ed_new_notebook.setBackgroundResource(R.drawable.error_edit_text_shape);
                } else {
                    String date = getCurrentDate();
                    int id = DbHelper.incrementMemoBookId(context);
                    MemoBook memoBook = new MemoBook(id, memo_book_name, date);
                    DbHelper.saveMemoBook(memoBook, context);
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

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public final boolean isDuplicated(String word) {
        ArrayList<MemoBook> memoBooks = DbHelper.retrieveMemoBooks(context);
        String memoBookName;
        if (!memoBooks.isEmpty()) {
            for (MemoBook memoBook : memoBooks) {
                memoBookName = memoBook.getName();
                if (word.equals(memoBookName)) {
                    return true;
                }
            }
        }
        return false;
    }
}