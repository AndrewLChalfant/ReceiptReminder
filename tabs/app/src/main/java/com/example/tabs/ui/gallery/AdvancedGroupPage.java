package com.example.tabs.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdvancedGroupPage extends AppCompatActivity {

    private String groupName;
    private ArrayList<String> members = new ArrayList<String>();
    private TextView textView;
    private ListView membersListView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.tabs.R.layout.activity_advanced_group_page);
        Intent intent = getIntent();
        groupName = intent.getStringExtra("groupName");
        int i = 0;
        while (intent.getStringExtra("" + i) != null) {
            members.add(intent.getStringExtra("" + i));
            i++;
        }

        textView = findViewById(com.example.tabs.R.id.group_name_label);
        textView.setText(groupName);

        membersListView = findViewById(com.example.tabs.R.id.group_members_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, members);

        membersListView.setAdapter(adapter);
    }
}
