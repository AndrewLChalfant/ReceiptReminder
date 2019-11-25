package com.example.receiptreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Where the user scans a receipt.
 */
public class ScanPage extends AppCompatActivity {

    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;
    private ArrayList<String> spinnerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_page);

        spinner = findViewById(R.id.spinner);

        // Setting up spinner
        spinnerList = new ArrayList<String>();
        spinnerList.add("Scan a Receipt");
        spinnerList.add("Groups");
        spinnerList.add("Home");
        spinnerList.add("User Trends");

        spinnerAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, spinnerList);

        spinner.setAdapter(spinnerAdapter);

        // Hooking up other pages to the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getItemAtPosition(i).equals("Home")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                if (spinner.getItemAtPosition(i).equals("User Trends")) {
                    Intent intent = new Intent(getApplicationContext(), UserTrendsPage.class);
                    startActivity(intent);
                }
                if (spinner.getItemAtPosition(i).equals("Groups")) {
                    Intent intent = new Intent(getApplicationContext(), GroupPage.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
