package com.example.receiptreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Home Page.
 */
public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private static TextView dateOne;
    private static TextView dateTwo;
    private static TextView dateThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Spinner
        spinner = findViewById(R.id.dropdownMenu);

        dateOne = findViewById(R.id.date_1);
        dateTwo = findViewById(R.id.date_2);
        dateThree = findViewById(R.id.date_3);

        Intent intent = getIntent();
        if (intent.getStringExtra("Date") != null)
        {
            dateThree.setText(dateTwo.getText().toString());
            dateTwo.setText(dateOne.getText().toString());
            dateOne.setText(intent.getStringExtra("Date"));
        }
        else {
            dateOne.setText("2019/11/30");
            dateTwo.setText("2019/11/19");
            dateThree.setText("2019/11/01");
        }

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
        //ImageView img = findViewById(R.id.imageView8);

        //Button goToGroups = findViewById(R.id.scan_new_receipt_button);
        //goToGroups.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //       Intent intent = new Intent(getApplicationContext(), ScanPage.class);
        //        startActivity(intent);
        //    }
        //});
        Button Camera_Button;
        Camera_Button = findViewById(R.id.scan_new_receipt_button);
        ImageView imageView = findViewById(R.id.imageView8);

        Camera_Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager())!=null) {
                    startActivityForResult(intent, 0);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // This runs after the camera returns an image
        Intent intent = new Intent(getApplicationContext(), ScanPage.class);
        startActivity(intent);
        super.onActivityResult(requestCode, requestCode, data);
    }
}
