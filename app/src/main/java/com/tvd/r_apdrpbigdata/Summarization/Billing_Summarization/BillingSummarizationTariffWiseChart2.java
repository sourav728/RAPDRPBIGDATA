package com.tvd.r_apdrpbigdata.Summarization.Billing_Summarization;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.models.Billing_Summarization_model;
import com.tvd.r_apdrpbigdata.other.MyMarkerView;
import com.tvd.r_apdrpbigdata.values.FunctionCall;

import java.util.ArrayList;

public class BillingSummarizationTariffWiseChart2 extends AppCompatActivity implements OnChartValueSelectedListener {
    String[] name = new String[50];
    float[] percent = new float[50];
    private BarChart mBarChart;
    //group bar chart variabble initialization
    float groupSpace = 0.13f;
    float barSpace = 0.04f; // x3 DataSet
    float barWidth = 0.25f; // x3 DataSet

    int startMonth, endMonth;
    int groupCount;
    String date_check_val = "", single_month = "";

    ArrayList<Billing_Summarization_model> myList;
    Billing_Summarization_model billing_summarization_model;
    FunctionCall functionCall;
    private android.support.v7.widget.Toolbar toolbar;
    int a = 0, b = 0, c = 0;
    private CheckBox chk_install, chk_consumed_unit, chk_current_billed_amt;
    private Button buttn_apply;
    private BarData data;
    private ArrayList<BarEntry> values1;
    private ArrayList<BarEntry> values2;
    private ArrayList<BarEntry> values3;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_summarization_tariff_wise_chart2);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Billing Summarization Graph");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        chk_install = findViewById(R.id.checkBox1);
        chk_consumed_unit = findViewById(R.id.checkBox2);
        chk_current_billed_amt = findViewById(R.id.checkBox3);
        buttn_apply = findViewById(R.id.btn_apply);

        mBarChart = findViewById(R.id.chart1);//Group Bar chart

        myList = (ArrayList<Billing_Summarization_model>) getIntent().getSerializableExtra("mylist");
        Log.d("debug", "BillingSumArrayListSize " + myList.size());
        billing_summarization_model = new Billing_Summarization_model();
        functionCall = new FunctionCall();

        Intent intent = getIntent();
        date_check_val = intent.getStringExtra("DATE_CHECK");
        single_month = intent.getStringExtra("SINGLE_MONTH");
        //initialize_group_bar();
        groupCount = myList.size();

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
        set_value();
    }

    private void addListenerOnButtonClick() {
        buttn_apply.setOnClickListener(view -> {
            count = 0;
            //first checkbox
            if (chk_install.isChecked()) {
                a = 1;
                count++;
            } else {
                a = 0;
            }

            //second checkbox
            if (chk_consumed_unit.isChecked()) {
                b = 1;
                count++;
            } else {
                b = 0;
            }

            //third checkbox
            if (chk_current_billed_amt.isChecked()) {
                c = 1;
                count++;
            } else {
                c = 0;
            }

            mBarChart.clearValues();
            set_value();
        });
    }

    private void set_value() {
        values1 = new ArrayList<>();
        values2 = new ArrayList<>();
        values3 = new ArrayList<>();

        if (!single_month.equals("Y")) {
            if (date_check_val.equals("Y"))
                startMonth = Integer.parseInt(myList.get(0).getMonth());
            else {
                startMonth = Integer.parseInt(myList.get(0).getYear());
            }
        } else {
            startMonth = 0;
        }

        endMonth = startMonth + myList.size();
        for (int i = 0; i < myList.size(); i++) {
            billing_summarization_model = myList.get(i);
            Log.d("debug", "month " + startMonth);
            if (a == 0)
                values1.add(new BarEntry(i, Float.parseFloat(billing_summarization_model.getInstallations())));
            if (b == 0)
                values2.add(new BarEntry(i, Float.parseFloat(billing_summarization_model.getConsumed_unit())));
            if (c == 0)
                values3.add(new BarEntry(i, Float.parseFloat(billing_summarization_model.getCurrent_bill_amt())));
        }

        BarDataSet set1 = null, set2 = null, set3 = null;

        if (mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0) {
            if (a == 0)
                set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            if (b == 0)
                set2 = (BarDataSet) mBarChart.getData().getDataSetByIndex(1);
            if (c == 0)
                set3 = (BarDataSet) mBarChart.getData().getDataSetByIndex(2);

            if (a == 0)
                set1.setValues(values1);
            if (b == 0)
                set2.setValues(values2);
            if (c == 0)
                set3.setValues(values3);

            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
            mBarChart.invalidate();
        } else {
            // create 3 DataSets
            set1 = new BarDataSet(values1, "Installations");
            set1.setColor(Color.rgb(102, 178, 255));

            set2 = new BarDataSet(values2, "ConsumedUnit");
            set2.setColor(Color.rgb(0, 51, 102));

            set3 = new BarDataSet(values3, "CurrentBilledAmount");
            set3.setColor(Color.rgb(255, 255, 0));

            data = new BarData(set1, set2, set3);

            data.setValueFormatter(new LargeValueFormatter());
            mBarChart.setData(data);
        }

        // specify the width each bar should have
        mBarChart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        mBarChart.getXAxis().setAxisMinimum(startMonth);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        mBarChart.getXAxis().setAxisMaximum(endMonth);
        mBarChart.groupBars(startMonth, groupSpace, barSpace);

        mBarChart.notifyDataSetChanged();
        mBarChart.invalidate();

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
