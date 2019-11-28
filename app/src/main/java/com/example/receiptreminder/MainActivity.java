package com.example.receiptreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ImageView;
import java.util.ArrayList;

/**
 * Home Page.
 */
public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Spinner
        spinner = findViewById(R.id.dropdownMenu);

        ArrayList<String> menuItems = new ArrayList<String>();
        menuItems.add("Home");
        menuItems.add("Groups");
        menuItems.add("User Trends");
        menuItems.add("Scan a Receipt");

        // Creating an adapter for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, menuItems);

        // Setting the adapter
        spinner.setAdapter(adapter);

        // Hooking to other pages
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getItemAtPosition(i).equals("Groups")) {
                    Intent intent = new Intent(getApplicationContext(), GroupPage.class);
                    startActivity(intent);
                }
                if (spinner.getItemAtPosition(i).equals("User Trends")) {
                    Intent intent = new Intent(getApplicationContext(), UserTrendsPage.class);
                    startActivity(intent);
                }
                if (spinner.getItemAtPosition(i).equals("Scan a Receipt")) {
                    Intent intent = new Intent(getApplicationContext(), ScanPage.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ImageView img = findViewById(R.id.imageView8);

        Button goToGroups = findViewById(R.id.scan_new_receipt_button);
        goToGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScanPage.class);
                startActivity(intent);
            }
        });
    }

}
