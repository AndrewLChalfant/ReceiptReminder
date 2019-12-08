package com.example.receiptreminder;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeScanPage extends AppCompatActivity {

    private int position;
    private EditText newItemName;
    private EditText newItemPrice;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_receipt_page);

        newItemName = findViewById(R.id.item_name_input);
        newItemPrice = findViewById(R.id.item_price_input);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);


        Button confirm = findViewById(R.id.save_changes);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScanPage.class);
                intent.putExtra("Change", "change");
                intent.putExtra("Name", newItemName.getText().toString());
                intent.putExtra("Price", newItemPrice.getText().toString());
                intent.putExtra("Position", position);
                startActivity(intent);
            }
        });

        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScanPage.class);
                startActivity(intent);
            }
        });


    }
}
