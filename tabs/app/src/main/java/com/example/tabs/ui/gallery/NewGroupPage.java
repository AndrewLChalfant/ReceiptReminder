package com.example.tabs.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NewGroupPage extends AppCompatActivity {

    private ListView membersList;
    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.tabs.R.layout.activity_new_group_page);
        membersList = findViewById(com.example.tabs.R.id.members);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        membersList.setAdapter(adapter);
    }

    public void done(View view) {
        Intent intent = new Intent(getApplicationContext(), GroupPage.class);

        // Getting the groupname
        EditText textBox = findViewById(com.example.tabs.R.id.editText);
        String groupName = textBox.getText().toString();


        for (int i = 0; i < listItems.size(); i++) {
            intent.putExtra("" + i, listItems.get(i));
        }

        intent.putExtra("groupname", groupName);

        startActivity(intent);
    }

    public void newMember(View view) {
        EditText memberName = findViewById(com.example.tabs.R.id.memberName);
        String name = memberName.getText().toString();

        listItems.add(name);
        adapter.notifyDataSetChanged();

    }
}
