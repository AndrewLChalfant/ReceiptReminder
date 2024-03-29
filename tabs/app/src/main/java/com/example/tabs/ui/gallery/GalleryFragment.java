package com.example.tabs.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tabs.R;

import java.util.ArrayList;
import java.util.HashMap;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    private ListView groupListView;
    private HashMap<String, ArrayList<String>> groupMap;
    private static ArrayList<String> groups = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        groupMap = new HashMap<String, ArrayList<String>>();
        groupListView = root.findViewById(com.example.tabs.R.id.groupList);
        adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, groups);
        groupListView.setAdapter(adapter);

        /*Intent intent = getIntent();
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
*/
        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String groupName = (String) groupListView.getItemAtPosition(position);
                Intent advancedGroupPage = new Intent(root.getContext(), AdvancedGroupPage.class);
                advancedGroupPage.putExtra("groupName", groupName);
                ArrayList<String> groupMembers = groupMap.get(groupName);
                for (int i = 0; i < groupMembers.size(); i++) {
                    advancedGroupPage.putExtra("" + i, groupMembers.get(i));
                }
                startActivity(advancedGroupPage);
            }
        });

        return root;
    }
}