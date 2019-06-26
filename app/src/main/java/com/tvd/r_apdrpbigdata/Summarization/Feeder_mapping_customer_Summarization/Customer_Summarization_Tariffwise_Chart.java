package com.tvd.r_apdrpbigdata.Summarization.Feeder_mapping_customer_Summarization;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.models.Customer_Summarization_model;
import com.tvd.r_apdrpbigdata.values.FunctionCall;

import java.util.ArrayList;

public class Customer_Summarization_Tariffwise_Chart extends AppCompatActivity implements OnChartValueSelectedListener {

    String[] name = new String[50];
    float[] percent = new float[50];
    private BarChart mBarChart;
    private int count;
    private CheckBox chk_install;
    String date_check_val = "";
    private Button buttn_apply;
    ArrayList<Customer_Summarization_model> myList = new ArrayList<>();
    Customer_Summarization_model customer_summarization_model;
    FunctionCall functionCall;
    private android.support.v7.widget.Toolbar toolbar;
    private int count1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__summarization__tariffwise__chart);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Customer Summarization Chart");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        chk_install = findViewById(R.id.checkBox1);
        mBarChart = findViewById(R.id.chart1);
        myList = (ArrayList<Customer_Summarization_model>) getIntent().getSerializableExtra("mylist");
        customer_summarization_model = new Customer_Summarization_model();
        functionCall = new FunctionCall();
        buttn_apply = findViewById(R.id.btn_apply);

        Intent intent = getIntent();
        date_check_val = intent.getStringExtra("DATE_CHECK");

        addListenerOnButtonClick();
        initialize_normal_bar(date_check_val);
    }

    private void addListenerOnButtonClick() {
        count1 = 0;
        buttn_apply.setOnClickListener(view -> {
            if (chk_install.isChecked()) {
                count1++;
            } else {
                count1 = 0;
            }
            if (count1 > 0) {
                mBarChart.clearValues();
            } else {
                initialize_normal_bar(date_check_val);
            }
        });
    }

    private void initialize_normal_bar(String date_check_val) {
        count = myList.size();
        for (int i = 0; i < myList.size(); i++) {
            customer_summarization_model = myList.get(i);
            if (date_check_val.equals("Y")) {
                percent[i] = Float.parseFloat(customer_summarization_model.getInstallation());
                name[i] = customer_summarization_model.getMonth();
            } else {
                percent[i] = Float.parseFloat(customer_summarization_model.getInstallation());
                name[i] = customer_summarization_model.getYear();
            }

        }
        barchart();
    }

    //For BarChart part
    private void barchart() {
        ArrayList<BarEntry> yaxis = new ArrayList<>();
        final ArrayList<String> xaxis = new ArrayList<>();
        //Setting data
        for (int i = 0, j = 0; i < count; i++) {

            if (name[i] == null)
                break;
            if (percent[i] == 0.0)
                continue;

            //Y axis plotting
            yaxis.add(new BarEntry(j++, percent[i]));
            //Checking the length of the name string to shorten it.
            if (name[i].length() >= 5) {
                xaxis.add(name[i].substring(0, 4));
            } else {
                xaxis.add(name[i]);
            }
        }

        //X axis plotting
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xaxis));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(4);

        //Bar chart title and colors
        BarDataSet barDataSet = new BarDataSet(yaxis, "Installations");
        barDataSet.setColor(Color.rgb(102, 178, 255));
        /*barDataSet.setDrawIcons(false);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);*/
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        mBarChart.setData(data);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mBarChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawGridBackground(false);
        //  mChart.setDrawYLabels(false);
        IAxisValueFormatter custom = (value, axis) -> value + "";

        //left axis
        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setLabelCount(4, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        //right axis
        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(4, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        //legend
        Legend l = mBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        //animation of the bar chart
        mBarChart.animateY(1500);
        mBarChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
