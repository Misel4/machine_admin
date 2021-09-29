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

import java.util.Calendar;

public class EditItemActivity extends AppCompatActivity {
    EditText editText,editNotes;
    TextView dateTxt;
    Button saveBtn,deleteBtn,dateBtn;
    DatabaseHelper db;
    private String SelectedMachine;
    private int SelectedID;
    private String selectedDate;
    private String selectedNote;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        db = new DatabaseHelper(this);

        editText = findViewById(R.id.history_edit);
        saveBtn = findViewById(R.id.but_save);
        deleteBtn = findViewById(R.id.but_delete);
        editNotes = findViewById(R.id.history_editNotes);
        dateBtn = findViewById(R.id.but_date_edit);
        dateTxt = findViewById(R.id.date_edit);

        Intent receiveIntent = getIntent();

        SelectedID = receiveIntent.getIntExtra("id",-1);

        SelectedMachine = receiveIntent.getStringExtra("part3");

        selectedDate = receiveIntent.getStringExtra("finalDate");

        selectedNote = receiveIntent.getStringExtra("finalNote");

        editText.setText(SelectedMachine);

        dateTxt.setText(selectedDate);

        editNotes.setText(selectedNote);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteItem(SelectedID,SelectedMachine);
                editText.setText("");
                Intent back = new Intent(EditItemActivity.this,HistoryActivity.class);
                startActivity(back);
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editText.getText().toString();
                String date = dateTxt.getText().toString();
                String note = editNotes.getText().toString();
                if(!item.equals("")){
                    db.updateName(item,SelectedID,SelectedMachine);
                    db.updateDate(date,SelectedID,selectedDate);
                    db.updateNote(note,SelectedID,selectedNote);
                    Toast.makeText(EditItemActivity.this,"Αποθηκεύτηκε επιτυχώς",Toast.LENGTH_LONG).show();
                    Intent back = new Intent(EditItemActivity.this,HistoryActivity.class);
                    startActivity(back);
                    finish();
                }else{
                    Toast.makeText(EditItemActivity.this,"Συμπληρώστε όνομα μηχανήματος",Toast.LENGTH_LONG).show();
                }
            }
        });

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) ;
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(EditItemActivity.this,
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
