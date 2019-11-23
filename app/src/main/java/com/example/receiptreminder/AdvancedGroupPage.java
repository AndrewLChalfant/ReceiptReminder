package com.example.receiptreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.*;

public class AdvancedGroupPage extends AppCompatActivity {

    private String groupName;
    private ArrayList<String> members = new ArrayList<String>();
    private TextView textView;
    private ListView membersListView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_group_page);
        Intent intent = getIntent();
        groupName = intent.getStringExtra("groupName");
        int i = 0;
        while (intent.getStringExtra("" + i) != null) {
            members.add(intent.getStringExtra("" + i));
            i++;
        }

        textView = findViewById(R.id.group_name_label);
        textView.setText(groupName);

        membersListView = findViewById(R.id.group_members_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, members);

        membersListView.setAdapter(adapter);
    }
}
