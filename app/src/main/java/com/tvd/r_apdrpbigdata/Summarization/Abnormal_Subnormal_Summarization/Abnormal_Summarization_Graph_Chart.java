package com.tvd.r_apdrpbigdata.Summarization.Abnormal_Subnormal_Summarization;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.models.Abnormal_Subnormal;
import com.tvd.r_apdrpbigdata.other.MyMarkerView;

import java.util.ArrayList;

public class Abnormal_Summarization_Graph_Chart extends AppCompatActivity implements OnChartValueSelectedListener {

    ArrayList<BarEntry> values1;
    ArrayList<BarEntry> values2;

    private BarChart mBarChart;
    private String date_check_val = "", abnormal_subnormal_activity = "";
    float groupSpace = 0.14f;
    float barSpace = 0.03f; // x4 DataSet
    float barWidth = 0.4f; // x4 DataSet
    int startYear, endYear;
    int groupCount;
    ArrayList<Abnormal_Subnormal> myList = new ArrayList<>();
    Abnormal_Subnormal abnormal_subnormal_model;
    private CheckBox chk_billed_install, chk_abnormal_install;
    private Button buttn_apply;
    private android.support.v7.widget.Toolbar toolbar;
    int count = 0;
    int a = 0, b = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormal__summarization__graph__chart);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Summarization Chart");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        chk_billed_install = findViewById(R.id.checkBox1);
        chk_abnormal_install = findViewById(R.id.checkBox2);

        buttn_apply = findViewById(R.id.btn_apply);

        mBarChart = findViewById(R.id.chart1);//Group Bar chart
        initialize();

        Intent intent = getIntent();
        date_check_val = intent.getStringExtra("DATE_CHECK");
        abnormal_subnormal_activity = intent.getStringExtra("ABNORMAL_SUBNORMAL");

        if (abnormal_subnormal_activity.equals("Abnormal"))
            chk_abnormal_install.setText("Abnormal Summarization");
        else  chk_abnormal_install.setText("Subnormal Summarization");

        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.getDescription().setEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawGridBackground(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mBarChart); // For bounds control
        mBarChart.setMarker(mv); // Set the marker to the chart

        Legend l = mBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mBarChart.getAxisRight().setEnabled(false);

        addListenerOnButtonClick();

        set_value2();
    }

    private void initialize() {
        myList = (ArrayList<Abnormal_Subnormal>) getIntent().getSerializableExtra("mylist");
        groupCount = myList.size();
        abnormal_subnormal_model = new Abnormal_Subnormal();
    }

    private void addListenerOnButtonClick() {
        buttn_apply.setOnClickListener(view -> {
            //first checkbox
            count = 0;
            if (chk_billed_install.isChecked()) {
                a = 1;
                count++;
            } else {
                a = 0;
            }

            //second checkbox
            if (chk_abnormal_install.isChecked()) {
                b = 1;
                count++;
            } else {
                b = 0;
            }

            mBarChart.clearValues();
            set_value2();

        });
    }


    private void set_value2() {
            values1 = new ArrayList<>();
            values2 = new ArrayList<>();

        if (date_check_val.equals("Y")) {
            startYear = Integer.parseInt(myList.get(0).getMonth());
        } else {
            startYear = Integer.parseInt(myList.get(0).getYear());
        }
        endYear = startYear + myList.size();
        for (int i = 0; i < myList.size(); i++) {
            abnormal_subnormal_model = myList.get(i);
            endYear = startYear + groupCount;
            Log.d("debug", "year " + startYear);
            if (a == 0)
                values1.add(new BarEntry(i, Float.parseFloat(abnormal_subnormal_model.getBilled_installation())));
            if (b == 0) {
                if (abnormal_subnormal_activity.equals("Abnormal"))
                    values2.add(new BarEntry(i, Float.parseFloat(abnormal_subnormal_model.getAbnormal_installation())));
                else
                    values2.add(new BarEntry(i, Float.parseFloat(abnormal_subnormal_model.getSubnormal_installation())));
            }

        }

        BarDataSet set1 = null, set2 = null;

        if (mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0) {
            if (a == 0)
                set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            if (b == 0)
                set2 = (BarDataSet) mBarChart.getData().getDataSetByIndex(1);
            if (a == 0)
                set1.setValues(values1);
            if (b == 0)
                set2.setValues(values2);

            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();

        } else {
            // create 2 DataSets
                set1 = new BarDataSet(values1, "BilledInstallations");
                set1.setColor(Color.rgb(51, 153, 255));

                if (abnormal_subnormal_activity.equals("Abnormal")) {
                    set2 = new BarDataSet(values2, "AbnormalInstallations");
                    set2.setColor(Color.rgb(0, 51, 102));
                } else {
                    set2 = new BarDataSet(values2, "SubnormalInstallations");
                    set2.setColor(Color.rgb(0, 51, 102));
                }


            BarData data = new BarData(set1, set2);
            data.setValueFormatter(new LargeValueFormatter());
            mBarChart.setData(data);
        }

        // specify the width each bar should have
        mBarChart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        mBarChart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        mBarChart.getXAxis().setAxisMaximum(endYear);
        mBarChart.groupBars(startYear, groupSpace, barSpace);
        mBarChart.invalidate();
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
