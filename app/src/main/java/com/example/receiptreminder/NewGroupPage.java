package com.example.receiptreminder;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    private CustomAdapter adapter;
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;
    private ArrayList<String> spinnerList;
    private ArrayList<Integer> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_page);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        membersList = findViewById(R.id.members);
        adapter = new CustomAdapter();
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

    class CustomAdapter extends BaseAdapter {
        public int getCount() { return listItems.size(); }
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
            text.setText(listItems.get(position));

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
