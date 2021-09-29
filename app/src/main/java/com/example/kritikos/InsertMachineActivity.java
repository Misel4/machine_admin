package com.example.kritikos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InsertMachineActivity extends AppCompatActivity {
    EditText machineName,notes;
    TextView dateTxt,categoryTxt;
    Button addBtn, historyBtn,dateBtn;
    DatabaseHelper db;
    DatePickerDialog datePickerDialog;
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_machine);
        db = new DatabaseHelper(this);

        machineName = findViewById(R.id.txt_machine);
        notes = findViewById(R.id.txt_notes);
        dateTxt = findViewById(R.id.date);
        addBtn = findViewById(R.id.but_addData);
        historyBtn = findViewById(R.id.but_history);
        dateBtn = findViewById(R.id.but_date);
        categoryTxt = findViewById(R.id.category_text);

        Intent receiveIntent = getIntent();
        selectedCategory = receiveIntent.getStringExtra("category");
        categoryTxt.setText(selectedCategory);

        addData();
        getDate();
        showHistory();
    }

    public void addData(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = db.insertData(dateTxt.getText().toString(),machineName.getText().toString(),notes.getText().toString(),selectedCategory);
                if(isInserted = true)
                    Toast.makeText(InsertMachineActivity.this,"Επιτυχής καταχώρηση",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(InsertMachineActivity.this,"Απέτυχε",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dates = sdf.format(new Date());
        dateTxt.setText(dates);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) ;
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(InsertMachineActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                int selectedMonth = month+1;
                                dateTxt.setText(day + "/" + selectedMonth + "/" + year);
                            }
                        }, year, month , dayOfMonth);
                datePickerDialog.show();
            }
        });
    }

    public void showHistory(){
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history = new Intent(InsertMachineActivity.this,HistoryActivity.class);
                startActivity(history);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
