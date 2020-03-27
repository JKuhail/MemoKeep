package com.jkuhail.android.memokeep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class CreateMemo extends AppCompatActivity {
    Button back_btn , save_btn;
    CheckBox importance;
    EditText memo_title , memo_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_memo);

        back_btn = findViewById(R.id.back_btn);
        save_btn = findViewById(R.id.save_btn);
        importance = findViewById(R.id.importance);
        memo_title = findViewById(R.id.memo_title);
        memo_content = findViewById(R.id.memo_content);


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
                Toast.makeText(getBaseContext() , "item clicked!" , Toast.LENGTH_LONG).show();
            }
        });
    }
}
