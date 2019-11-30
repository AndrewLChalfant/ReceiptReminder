package com.example.receiptreminder;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.*;
import java.util.ArrayList;

/**
 * Represents the new group page (user inputs the group name and member names).
 */
public class NewGroupPage extends AppCompatActivity {

    private ListView membersList;
    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;
    private ArrayList<String> spinnerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_page);
        membersList = findViewById(R.id.members);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        membersList.setAdapter(adapter);
        spinner = findViewById(R.id.spinner);

        // Setting up spinner
        spinnerList = new ArrayList<String>();
        spinnerList.add("Groups");
        spinnerList.add("Home");
        spinnerList.add("User Trends");
        spinnerList.add("Scan a Receipt");

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
                if (spinner.getItemAtPosition(i).equals("Scan a Receipt")) {
                    Intent intent = new Intent(getApplicationContext(), ScanPage.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void done(View view) {
        Intent intent = new Intent(getApplicationContext(), GroupPage.class);

        // Getting the groupname
        EditText textBox = findViewById(R.id.editText);
        String groupName = textBox.getText().toString();


        for (int i = 0; i < listItems.size(); i++) {
            intent.putExtra("" + i, listItems.get(i));
        }

        intent.putExtra("groupname", groupName);

        startActivity(intent);
    }

    public void newMember(View view) {
        EditText memberName = findViewById(R.id.memberName);
        String name = memberName.getText().toString();

        listItems.add(name);
        adapter.notifyDataSetChanged();

        memberName.getText().clear();

    }
}
