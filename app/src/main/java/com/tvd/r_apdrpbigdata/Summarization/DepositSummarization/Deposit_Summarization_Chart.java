package com.tvd.r_apdrpbigdata.Summarization.DepositSummarization;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.models.Billing_Summarization_model;
import com.tvd.r_apdrpbigdata.other.MyMarkerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Deposit_Summarization_Chart extends AppCompatActivity {
    ArrayList<Billing_Summarization_model> arrayListl;
    String appbarTitle;
    private BarChart chart;
    String date_check_val = "";
    private android.support.v7.widget.Toolbar toolbar;
    int count = 0;
    int a = 0, b = 0;
    private Button buttn_apply;
    private CheckBox chk_billed_install, chk_deposit_amt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_summarization_chart);
        initialize();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            arrayListl = (ArrayList<Billing_Summarization_model>) extras.getSerializable("test");
            appbarTitle = extras.getString("appbarTitle");
            date_check_val = extras.getString("DATE_CHECK");
        }
        setTitle(appbarTitle);

        addListenerOnButtonClick();
        set_value();

    }

    private void initialize() {
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Deposit Summarization Chart");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        chk_billed_install = findViewById(R.id.checkBox1);
        chk_deposit_amt = findViewById(R.id.checkBox2);

        buttn_apply = findViewById(R.id.btn_apply);
        chart = findViewById(R.id.chart1);
        arrayListl = new ArrayList<>();

        FloatingActionButton mShowSnackbarButton = findViewById(R.id.fab);

        mShowSnackbarButton.setOnClickListener(view -> {

            Intent intent = new Intent(Deposit_Summarization_Chart.this, Deposit_Summarization_Report.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("test", arrayListl);
            bundle.putString("DATE_CHECK", date_check_val);
            bundle.putString("appbarTitle", "DashBoard_TariffWise_Report");
            intent.putExtras(bundle);
            startActivity(intent);

        });

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
            if (chk_deposit_amt.isChecked()) {
                b = 1;
                count++;
            } else {
                b = 0;
            }

            chart.clearValues();
            set_value();

        });
    }

    private void set_value() {
        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(11f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        IAxisValueFormatter custom = (value, axis) -> {
            DecimalFormat num = new DecimalFormat("##0.00");
            return num.format(value);
        };

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(15f);
        leftAxis.setValueFormatter(custom);
//        leftAxis.setAxisMinimum(0f);
        leftAxis.setStartAtZero(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
//        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setStartAtZero(false);

        chart.getAxisRight().setEnabled(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(chart);
        chart.setMarker(mv);


        chart.setDrawGridBackground(false);
        float groupSpace = 0.12f;
        float barSpace = 0.04f; // x2 DataSet
        float barWidth = 0.40f;
        // (0.04 + 0.40) * 2 + 0.12 = 1.00 -> interval per "group"

        int startYear;
        int endYear;

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();

        if (date_check_val.equals("Y")) {
            startYear = Integer.parseInt(arrayListl.get(0).getMonth());
            endYear = Integer.parseInt(arrayListl.get(arrayListl.size() - 1).getMonth()) + 1;
        } else {
            startYear = Integer.parseInt(arrayListl.get(0).getYear());
            endYear = Integer.parseInt(arrayListl.get(arrayListl.size() - 1).getYear()) + 1;
        }


        for (int i = 0; i < arrayListl.size(); i++) {
            if (a == 0)
                values1.add(new BarEntry(i, Float.parseFloat(arrayListl.get(i).getInstallations())));
            if (b == 0)
                values2.add(new BarEntry(i, Float.parseFloat(arrayListl.get(i).getDeposit_amt())));
        }

        BarDataSet set1 = null, set2 = null;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            if (a == 0)
                set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            if (b == 0)
                set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);
            if (a == 0)
                set1.setValues(values1);
            if (b == 0)
                set2.setValues(values2);

            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {

            set1 = new BarDataSet(values1, "Installation");
            set1.setColor(Color.rgb(51, 153, 255));
            set2 = new BarDataSet(values2, "Deposit Amount");
            set2.setColor(Color.rgb(0, 128, 0));
            BarData data = new BarData(set1, set2);
            data.setDrawValues(false);
            chart.setData(data);
        }

        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(startYear);
        chart.getXAxis().setAxisMaximum(endYear);
        chart.groupBars(startYear, groupSpace, barSpace);
        chart.invalidate();
    }
}
