package com.example.receiptreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.ArrayList;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.*;

/**
 * More in depth look at one particular group.
 */
public class AdvancedGroupPage extends AppCompatActivity {

    private String groupName;
    private ArrayList<String> members = new ArrayList<String>();
    private TextView textView;
    private ListView membersListView;
    private CustomAdapter adapter;
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;
    private ArrayList<String> spinnerList;

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
        adapter = new CustomAdapter();

        membersListView.setAdapter(adapter);

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

    class CustomAdapter extends BaseAdapter {
        public int getCount() { return members.size(); }
        public Object getItem(int position) {
            return null;
        }
        public long getItemId(int position) {
            return 0;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.new_group_list_item, null);
            TextView text = view.findViewById(R.id.title);
            ImageView image = view.findViewById(R.id.avatar);

            image.setImageResource(R.drawable.member_icon);
            text.setText(members.get(position));

            return view;
        }
    }
}
