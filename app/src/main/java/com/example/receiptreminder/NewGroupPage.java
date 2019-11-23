package com.example.receiptreminder;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.*;
import java.util.ArrayList;

public class NewGroupPage extends AppCompatActivity {

    private ListView membersList;
    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_page);
        membersList = findViewById(R.id.members);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        membersList.setAdapter(adapter);
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

    }
}
