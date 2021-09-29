package com.example.kritikos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<String> categories = new ArrayList<>();
    DatabaseHelper myDb;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        listView = findViewById(R.id.List1);
        display = findViewById(R.id.display);
        myDb = new DatabaseHelper(this);

        categories.add("Τσαπάκια");
        categories.add("Φορτωτάκια");
        categories.add("Ανυψωτικά πετρελαίου");
        categories.add("Ανυψωτικά ηλεκτρικά");
        categories.add("Οδοστρωτήρες");
        categories.add("Κομπρεσέρ");
        categories.add("Telehandler");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.activity_main,R.id.display,categories);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                Intent machineIntent = new Intent(MainActivity.this,InsertMachineActivity.class);
                machineIntent.putExtra("category",category);
                startActivity(machineIntent);
//                if(category.contains("Άλλο")){
//                    Intent catIntent = new Intent(MainActivity.this,OtherActivity.class);
//                    catIntent.putExtra("other",category);
//                    startActivity(catIntent);
//                }
            }
        });
    }

}
