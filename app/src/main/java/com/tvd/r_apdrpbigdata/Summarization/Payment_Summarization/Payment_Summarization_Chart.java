package com.tvd.r_apdrpbigdata.Summarization.Payment_Summarization;

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
import com.tvd.r_apdrpbigdata.models.Payment_Summarization_model;
import com.tvd.r_apdrpbigdata.other.MyMarkerView;

import java.util.ArrayList;

public class Payment_Summarization_Chart extends AppCompatActivity implements OnChartValueSelectedListener {

    private String date_check_val = "";
    float groupSpace = 0.02f;
    float barSpace = 0.02f; // 7 DataSet
    float barWidth = 0.12f;//7 DataSet
    int startMonth, endMonth;
    private ArrayList<BarEntry> values1;
    private ArrayList<BarEntry> values2;
    private ArrayList<BarEntry> values3;
    private ArrayList<BarEntry> values4;
    private ArrayList<BarEntry> values5;
    private ArrayList<BarEntry> values6;
    private ArrayList<BarEntry> values7;

    int groupCount;
    private CheckBox chk_instal, chk_open_bal, chk_consume_units, chk_demands, chk_netpayamt, chk_colamt, chk_clobalamt;
    private Button buttn_apply;
    ArrayList<Payment_Summarization_model> myList = new ArrayList<>();
    Payment_Summarization_model payment_summarization_model;
    int count;
    private BarChart mBarChart;
    private BarData data;
    int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__summarization__chart);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Billing Summarization Graph");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        initialize();
        Intent intent = getIntent();
        date_check_val = intent.getStringExtra("DATE_CHECK");


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
        //leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setStartAtZero(false);

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setStartAtZero(false); // this replaces setStartAtZero(true)

        mBarChart.getAxisRight().setEnabled(false);
        addListenerOnButtonClick();
        set_value2();
    }

    private void initialize() {
        myList = (ArrayList<Payment_Summarization_model>) getIntent().getSerializableExtra("mylist");
        groupCount = myList.size();
        payment_summarization_model = new Payment_Summarization_model();
        chk_instal = findViewById(R.id.checkBox1);
        chk_open_bal = findViewById(R.id.checkBox2);
        chk_consume_units = findViewById(R.id.checkBox3);
        chk_demands = findViewById(R.id.checkBox4);
        chk_netpayamt = findViewById(R.id.checkBox5);
        chk_colamt = findViewById(R.id.checkBox6);
        chk_clobalamt = findViewById(R.id.checkBox7);

        mBarChart = findViewById(R.id.chart1);
        buttn_apply = findViewById(R.id.btn_apply);


    }

    private void addListenerOnButtonClick() {
        buttn_apply.setOnClickListener(view -> {
            //first checkbox
            count = 0;
            if (chk_instal.isChecked()) {
                a = 1;
                count++;
            } else {
                a = 0;
            }

            //second checkbox
            if (chk_open_bal.isChecked()) {
                b = 1;
                count++;
            } else {
                b = 0;
            }
            //third checkbox
            if (chk_consume_units.isChecked()) {
                c = 1;
                count++;
            } else {
                c = 0;
            }

            if (chk_demands.isChecked()) {
                d = 1;
                count++;
            } else{
                d = 0;
            }

            if (chk_netpayamt.isChecked()) {
                e = 1;
                count++;
            } else{
                e = 0;
            }

            if (chk_colamt.isChecked()) {
                f = 1;
                count++;
            } else {
                f = 0;
            }

            if (chk_clobalamt.isChecked()) {
                g = 1;
                count++;
            } else {
                g = 0;
            }
            mBarChart.clearValues();
            set_value2();
        });
    }

    private void set_value2() {

        values1 = new ArrayList<>();

        values2 = new ArrayList<>();

        values3 = new ArrayList<>();

        values4 = new ArrayList<>();

        values5 = new ArrayList<>();

        values6 = new ArrayList<>();

        values7 = new ArrayList<>();

        if (date_check_val.equals("Y"))
            startMonth = Integer.parseInt(myList.get(0).getMonth());
        else startMonth = Integer.parseInt(myList.get(0).getYear());

        endMonth = startMonth + myList.size();
        for (int i = 0; i < myList.size(); i++) {
            payment_summarization_model = myList.get(i);
            Log.d("debug", "month " + startMonth);
            if (a == 0)
                values1.add(new BarEntry(i, Float.parseFloat(payment_summarization_model.getInstallations())));
            if (b == 0)
                values2.add(new BarEntry(i, Float.parseFloat(payment_summarization_model.getOpening_balance())));
            if (c == 0)
                values3.add(new BarEntry(i, Float.parseFloat(payment_summarization_model.getConsumed_unit())));
            if (d == 0)
                values4.add(new BarEntry(i, Float.parseFloat(payment_summarization_model.getDemand())));
            if (e == 0)
                values5.add(new BarEntry(i, Float.parseFloat(payment_summarization_model.getNet_pay_amount())));
            if (f == 0)
                values6.add(new BarEntry(i, Float.parseFloat(payment_summarization_model.getCollection_amt())));
            if (g == 0)
                values7.add(new BarEntry(i, Float.parseFloat(payment_summarization_model.getClosing_balance_amt())));

        }

        BarDataSet set1 = null, set2 = null, set3 = null, set4 = null, set5 = null, set6 = null, set7 = null;

        if (mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0) {
            try {
                if (a == 0)
                    set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
                if (b == 0)
                    set2 = (BarDataSet) mBarChart.getData().getDataSetByIndex(1);
                if (c == 0)
                    set3 = (BarDataSet) mBarChart.getData().getDataSetByIndex(2);
                if (d == 0)
                    set4 = (BarDataSet) mBarChart.getData().getDataSetByIndex(3);
                if (e == 0)
                    set5 = (BarDataSet) mBarChart.getData().getDataSetByIndex(4);
                if (f == 0)
                    set6 = (BarDataSet) mBarChart.getData().getDataSetByIndex(5);
                if (g == 0)
                    set7 = (BarDataSet) mBarChart.getData().getDataSetByIndex(6);

                if (a == 0)
                    set1.setValues(values1);
                if (b == 0)
                    set2.setValues(values2);
                if (c == 0)
                    set3.setValues(values3);
                if (d == 0)
                    set4.setValues(values4);
                if (e == 0)
                    set5.setValues(values5);
                if (f == 0)
                    set6.setValues(values6);
                if (g == 0)
                    set7.setValues(values7);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
            mBarChart.invalidate();
        } else {
            // create 3 DataSets

            set1 = new BarDataSet(values1, "Installations");
            set1.setColor(Color.rgb(51, 153, 255));

            set2 = new BarDataSet(values2, "Opening_Balance");
            set2.setColor(Color.rgb(255, 204, 229));

            set3 = new BarDataSet(values3, "ConsumedUnit");
            set3.setColor(Color.rgb(0, 0, 51));

            set4 = new BarDataSet(values4, "Demand");
            set4.setColor(Color.rgb(255, 255, 0));

            set5 = new BarDataSet(values5, "Net Pay Amount");
            set5.setColor(Color.rgb(204, 0, 204));

            set6 = new BarDataSet(values6, "Collection Amount");
            set6.setColor(Color.rgb(0, 153, 0));

            set7 = new BarDataSet(values7, "ClosingAmt");
            set7.setColor(Color.rgb(255, 0, 0));

            data = new BarData(set1, set2, set3, set4, set5, set6, set7);
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
