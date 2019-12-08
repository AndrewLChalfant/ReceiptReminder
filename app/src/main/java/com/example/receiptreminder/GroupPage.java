package com.example.receiptreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Represents the group page.
 */
public class GroupPage extends AppCompatActivity {

    private ListView groupListView;
    private static HashMap<String, ArrayList<String>> groupMap = new HashMap<>();
    private static ArrayList<String> groups = new ArrayList<String>();
    private static ArrayList<ArrayList<String>> groupMembers = new ArrayList<>();
    private ArrayList<String> spinnerList;
    private static CustomAdapter adapter;
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        groupListView = findViewById(R.id.groupList);
        adapter = new CustomAdapter();
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
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(intent.resolveActivity(getPackageManager())!=null) {
                        startActivityForResult(intent, 0);
                    }
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
            groupMembers.add(members);
            adapter.notifyDataSetChanged();
        }

        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Does not get the name
                TextView groupName = (TextView) groupListView.getItemAtPosition(position);
                System.out.println(groupName.getText());
                Intent advancedGroupPage = new Intent(getApplicationContext(), AdvancedGroupPage.class);
                advancedGroupPage.putExtra("groupName", groupName.getText());
                ArrayList<String> groupMembers = groupMap.get(groupName.getText());
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

    class CustomAdapter extends BaseAdapter {
        public int getCount() { return groups.size(); }
        public Object getItem(int position) {
            return groupListView.getChildAt(position).findViewById(R.id.group_name);
        }
        public long getItemId(int position) {
            return 0;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.group_list_item, null);
            TextView text = view.findViewById(R.id.group_name);
            ImageView image = view.findViewById(R.id.group_icon);
            TextView members = view.findViewById(R.id.members);
            ArrayList<String> membersList = groupMembers.get(position);

            String memberString = "";

            for (int i = 0; i < membersList.size(); i++) {
                if (i == membersList.size() - 1) {
                    memberString += membersList.get(i);
                } else {
                    memberString += membersList.get(i) + ", ";
                }
            }


            image.setImageResource(R.drawable.group_icon);
            text.setText(groups.get(position));
            members.setText(memberString);

            return view;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // This runs after the camera returns an image
        Intent intent = new Intent(getApplicationContext(), ScanPage.class);
        startActivity(intent);
        super.onActivityResult(requestCode, requestCode, data);
    }
}
