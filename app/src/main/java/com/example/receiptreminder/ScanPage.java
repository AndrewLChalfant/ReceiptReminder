package com.example.receiptreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.time.LocalDateTime;


/**
 * Where the user scans a receipt.
 */
public class ScanPage extends AppCompatActivity {

    private ListView receiptList;
    public static CustomAdapter adapter;
    private ArrayList<String> products;
    private ArrayList<String> prices;
    public static ArrayList<String> productsToDisplay;
    public static ArrayList<String> pricesToDisplay;
    private String storeToDisplay;
    private ArrayList<String> stores = new ArrayList<String>();
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_page);
        receiptList = findViewById(R.id.receipt_list);
        adapter = new CustomAdapter();
        receiptList.setAdapter(adapter);

        stores.add("Walmart");
        stores.add("Giant");
        stores.add("Target");
        stores.add("Shoppers");

        products = new ArrayList<String>();
        products.add("Milk");
        products.add("Butter");
        products.add("Celery");
        products.add("Yogurt");
        products.add("Eggs");
        products.add("Water Bottles");
        products.add("Granola Bars");
        products.add("Cream Cheese");
        products.add("Bread");
        products.add("Lettuce");
        products.add("Apples");
        products.add("Spinach");
        products.add("Pears");
        products.add("Oranges");
        products.add("Broccoli");
        products.add("Asparagus");
        products.add("Bananas");
        products.add("Strawberries");

        prices = new ArrayList<String>();
        prices.add("$1.00");
        prices.add("$1.25");
        prices.add("$1.50");
        prices.add("$1.75");
        prices.add("$2.00");
        prices.add("$2.25");
        prices.add("$2.05");
        prices.add("$2.50");
        prices.add("$2.30");
        prices.add("$2.75");
        prices.add("$3.00");
        prices.add("$3.25");
        prices.add("$3.05");
        prices.add("$3.50");
        prices.add("$3.30");
        prices.add("$3.75");

        // Creating random items
        productsToDisplay = new ArrayList<String>();
        for (int i = 0; i < 10; i++)
        {
            Random random = new Random();
            int randomInt = random.nextInt(16);
            productsToDisplay.add(products.get(randomInt));
        }

        // Creating random prices
        pricesToDisplay = new ArrayList<String>();
        for (int i = 0; i < 10; i++)
        {
            Random random = new Random();
            int randomInt = random.nextInt(16);
            pricesToDisplay.add(prices.get(randomInt));
        }

        // Picking random store
        Random random = new Random();
        int randomInt = random.nextInt(4);
        storeToDisplay = stores.get(randomInt);

        TextView storeName = findViewById(R.id.store_name);
        storeName.setText(storeToDisplay);

        TextView totalPriceAndDate = findViewById(R.id.date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        date = formatter.format(LocalDateTime.now());
        totalPriceAndDate.setText(date);

        Intent intent = getIntent();
        if (intent.getStringExtra("Change") != null)
        {
            String newName = intent.getStringExtra("Name");
            String newPrice = intent.getStringExtra("Price");
            int position = intent.getIntExtra("Position", 0);

            productsToDisplay.remove(position);
            productsToDisplay.add(position, newName);
            pricesToDisplay.remove(position);
            pricesToDisplay.add(position, newPrice);
            adapter.notifyDataSetChanged();
        }
    }

    public void confirmScan(View view)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("Date", date);
        startActivity(intent);
    }

    class CustomAdapter extends BaseAdapter {
        public int getCount() { return 10; }
        public Object getItem(int position) {
            return null;
        }
        public long getItemId(int position) {
            return 0;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.receipt_list_item, null);
            TextView itemName = view.findViewById(R.id.item_name);
            TextView itemPrice = view.findViewById(R.id.item_price);
            Button change = view.findViewById(R.id.change_button);

            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ChangeScanPage.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
            itemName.setText(productsToDisplay.get(position));
            itemPrice.setText(pricesToDisplay.get(position));

            return view;
        }
    }
}
