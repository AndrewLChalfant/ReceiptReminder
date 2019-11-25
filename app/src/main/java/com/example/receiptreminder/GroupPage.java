package com.example.receiptreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Represents the group page.
 */
public class GroupPage extends AppCompatActivity {

    private ListView groupListView;
    private HashMap<String, ArrayList<String>> groupMap;
    private static ArrayList<String> groups = new ArrayList<String>();
    private ArrayList<String> spinnerList;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        groupMap = new HashMap<String, ArrayList<String>>();
        groupListView = findViewById(R.id.groupList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groups);
        groupListView.setAdapter(adapter);
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


        Intent intent = getIntent();
        String groupName = intent.getStringExtra("groupname");
        if (groupName != null) {

            ArrayList<String> members = new ArrayList<String>();
            int i = 0;
            while (intent.getStringExtra("" + i) != null) {
                members.add(intent.getStringExtra("" + i));
                i++;
            }
            groupMap.put(groupName, members);
            groups.add(groupName);
            adapter.notifyDataSetChanged();
        }

        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String groupName = (String) groupListView.getItemAtPosition(position);
                Intent advancedGroupPage = new Intent(getApplicationContext(), AdvancedGroupPage.class);
                advancedGroupPage.putExtra("groupName", groupName);
                ArrayList<String> groupMembers = groupMap.get(groupName);
                for (int i = 0; i < groupMembers.size(); i++) {
                    advancedGroupPage.putExtra("" + i, groupMembers.get(i));
                }
                startActivity(advancedGroupPage);
            }
        });
    }

    public void newGroup(View view) {
        // Going to the new Group page
        Intent intent = new Intent(getApplicationContext(), NewGroupPage.class);
        startActivity(intent);
    }
}
