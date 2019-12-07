package com.example.receiptreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.Text;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User views user trends.
 */
public class UserTrendsPage extends AppCompatActivity {

    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;
    private ArrayList<String> spinnerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_trends_page);

        spinner = findViewById(R.id.spinner);

        // Setting up spinner
        spinnerList = new ArrayList<String>();
        spinnerList.add("User Trends");
        spinnerList.add("Home");
        spinnerList.add("Groups");
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
                if (spinner.getItemAtPosition(i).equals("Groups")) {
                    Intent intent = new Intent(getApplicationContext(), GroupPage.class);
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
        TextView tv = findViewById(R.id.averageText);
        //setContentView(R.layout.activity_user_trends_page);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);

        //SETUP GRAPH STYLING
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("User Purchasing Habits");
        cartesian.yAxis(0).title("Dollars Spent");
        cartesian.xAxis(0).title("Date Purchased");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        //CREATE DUMMY DATA
        List<String> temp = new ArrayList<>();
        List<String> temp2 = new ArrayList<>();

        Random rand = new Random();
        int total = 0;

        for (int i=1; i <= 12; i++) {
            int val = rand.nextInt(600);
            String val2 = val + "";
            temp.add(i + "/1," + val2);

            int tempVal = val  - rand.nextInt(300);
            if (tempVal < 0) {
                tempVal = 0;
            }
            temp2.add(i + "/1," + (tempVal + ""));

            total+= val;
            int r = rand.nextInt(2);
            if (r == 1) {
                val -= rand.nextInt(200);
            } else {
                val += rand.nextInt(200);
            }
            total += val;
            temp.add(i + "/15," + val + "");
            int tempVal2 = val  - rand.nextInt(300);
            if (tempVal2 < 0) {
                tempVal2 = 0;
            }
            temp2.add(i + "/15," + (tempVal2 + ""));


        }
        //CONVERT DATA POINTS
        List<DataEntry> seriesData = new ArrayList<>();
        List<DataEntry> seriesData2 = new ArrayList<>();

        for (String a : temp) {
            String[] split = a.split(",");
            String date = split[0];
            Integer val = Integer.parseInt(split[1]);
            seriesData.add(new CustomDataEntry(date, val));
        }

        for (String b : temp2) {
            String[] split = b.split(",");
            String date = split[0];
            Integer val = Integer.parseInt(split[1]);
            seriesData2.add(new CustomDataEntry(date, val));
        }

        //seriesData.add(new CustomDataEntry("2009", 12.0, 22.5, 8.9)); //extend for multiple lines

        Set set = Set.instantiate();
        Set set2 = Set.instantiate();

        set.data(seriesData);
        set2.data(seriesData2);

        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set2.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        Line series2 = cartesian.line(series2Mapping);

        series1.name("Total Spending");
        series2.name("Example Spending Category");

        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
        tv.setText("Average Spending Per Trip: $" + total/30);


    }

    public void monthButton(View v) {
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        Toast.makeText(getApplicationContext(), "Month Click", Toast.LENGTH_SHORT).show();
        anyChartView.getBaseline();
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value) {
            super(x, value);
        }

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
