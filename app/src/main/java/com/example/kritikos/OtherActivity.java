package com.example.kritikos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OtherActivity extends AppCompatActivity {
    EditText categoryEdit;
    Button continueBtn;
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        categoryEdit = findViewById(R.id.txt_otherCategory);
        continueBtn = findViewById(R.id.but_continue);
//
//        Intent receiveIntent = getIntent();
//        selectedCategory = receiveIntent.getStringExtra("other");
//


    }
}
