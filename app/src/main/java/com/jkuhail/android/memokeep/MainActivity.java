package com.jkuhail.android.memokeep;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.jkuhail.android.memokeep.models.Notebook;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    SpaceNavigationView navigationView;
    private PopupWindow window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer , new NotesFragment()).commit();
        navigationView = findViewById(R.id.space);
        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("Notes", R.drawable.ic_note));
        navigationView.addSpaceItem(new SpaceItem("Notebooks", R.drawable.ic_notebook));

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                ShowFirstPopupWindow();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer , new NotesFragment()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer , new NotebooksFragment()).commit();
                        break;
                }

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer , new NotesFragment()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer , new NotebooksFragment()).commit();
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
            Intent intent = new Intent(getApplicationContext(), Archive.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void ShowFirstPopupWindow(){
        try {
            Button new_note, new_notebook;

            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_window, null);

            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;

            window = new PopupWindow(layout, width, height, true);

            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setOutsideTouchable(true);
            window.showAtLocation(layout, Gravity.CENTER, 0, 0);
            //  window.showAtLocation(layout, 17, 100, 100);

            new_note = layout.findViewById(R.id.new_note);
            new_notebook = layout.findViewById(R.id.new_notebook);

            new_note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext() , CreateNote.class);
                    startActivity(intent);
                    window.dismiss();
                }

            });
            new_notebook.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    window.dismiss();
                    ShowSecondPopupWindow();

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

    private void ShowSecondPopupWindow(){
        try {
            final Button ok;
            final EditText ed_new_notebook;
            final TextView error_message;

            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.new_notebook_popup, null);

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
                    String notebook_name = ed_new_notebook.getText().toString().trim();

                    if(ed_new_notebook.length() == 0){
                        error_message.setVisibility(View.VISIBLE);
                        ed_new_notebook.setBackgroundResource(R.drawable.error_edit_text_shape);
                    }else{
                        Notebook notebook = new Notebook(notebook_name);
                        notebook.save();
                        window.dismiss();
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
}
