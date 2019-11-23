package com.example.tabs.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class GroupPage extends AppCompatActivity {

    private ListView groupListView;
    private HashMap<String, ArrayList<String>> groupMap;
    private static ArrayList<String> groups = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.tabs.R.layout.activity_group_page);
        groupMap = new HashMap<String, ArrayList<String>>();
        groupListView = findViewById(com.example.tabs.R.id.groupList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groups);
        groupListView.setAdapter(adapter);

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
