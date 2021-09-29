package com.example.kritikos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    DatabaseHelper db;
    ListView hListView;
    TextView historyTxt;
    Button exportBtn;
    List<String> appendData = new ArrayList<>();
    List<String> machine = new ArrayList<>();
    List<String> date = new ArrayList<>();
    List<String> notes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        setContentView(R.layout.activity_main);
        setContentView(R.layout.history_listview_layout);

        db = new DatabaseHelper(this);
        historyTxt = findViewById(R.id.history_text);
        hListView = findViewById(R.id.ListHistory);
        exportBtn = findViewById(R.id.but_export);
        historyData();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.activity_history,R.id.history_text,appendData);
        hListView.setAdapter(arrayAdapter);

        hListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String machine1 = parent.getItemAtPosition(position).toString();
                String part3= "";
                String date="";
                String finalDate="";
                String finalNote="";
                if(machine1.contains(":") ){
                    String[] parts = machine1.split(":",2);
                    String part1 = parts[0];
                    String part2 = parts[1];
                    if(part2.contains("\n")){
                        String parts1[] =  part2.split("\n",2);
                        part3 = parts1[0];
                        date = parts1[1];
                        if(date.contains(":")){
                            String parts2[] = date.split(":",2);
                            String part5 = parts2[0];
                            String part6 = parts2[1];
                            if(part6.contains("\n")){
                                String parts3[] = part6.split("\n",2);
                                finalDate = parts3[0];
                                String part8 = parts3[1];
                                if(part8.contains(":")){
                                    String parts4[] = part8.split(":",2);
                                    String part9 = parts4[0];
                                    finalNote = parts4[1];
                                }
                            }
                        }
                    }


                }
                Cursor data = db.getItemID(part3);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                if(itemID>-1){
                    Intent deleteScreen = new Intent(HistoryActivity.this, EditItemActivity.class);
                    deleteScreen.putExtra("id",itemID);
                    deleteScreen.putExtra("machine1",machine1);
                    deleteScreen.putExtra("part3",part3);
                    deleteScreen.putExtra("finalDate",finalDate);
                    deleteScreen.putExtra("finalNote",finalNote);
                    startActivity(deleteScreen);
                    finish();
                }
            }
        });

        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportDB();
            }
        });
    }


    public void historyData(){
        Cursor res = db.getAllData();

        if(res.getCount() == 0){
            ShowMessage("Error","No data");
            return;
        }

        while (res.moveToNext()) {
            machine.add(res.getString(1));
            date.add(res.getString(2));
            notes.add(res.getString(4));
        }

        for(int i=0 ; i<machine.size();i++){
            String mixanima = machine.get(i);
            String imerominia = date.get(i);
            String note = notes.get(i);
            appendData.add("Μηχάνημα:"+mixanima+"\n"+"Ημερομηνία:"+imerominia+"\n"+"Σημειώσεις:"+note);
        }
    }
    public void ShowMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void exportDB() {

        File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "");
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "kritikos.csv");
        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            Cursor curCSV = db.getAllData();
            csvWrite.writeNext(curCSV.getColumnNames());
            while(curCSV.moveToNext())
            {
                //Which column you want to exprort
                String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2),curCSV.getString(4),curCSV.getString(3)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
            Toast.makeText(HistoryActivity.this,"done",Toast.LENGTH_LONG).show();
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
    }
}
