package com.tvd.r_apdrpbigdata.Summarization.UnBilled_Summerization;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.models.Billing_Summarization_model;
import com.tvd.r_apdrpbigdata.other.MyMarkerView;

import java.util.ArrayList;

public class UnBilled_Summerization_Tariff_chart extends AppCompatActivity {
    BarChart barChart;
    String[] name = new String[50];
    float[] percent = new float[50];
    ArrayList<Billing_Summarization_model> arrayList;
    String appbarTitle;
    String date_check_val = "";
    private android.support.v7.widget.Toolbar toolbar;
    private CheckBox chk_install;
    private int count1 = 0;
    private Button buttn_apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_billed_summerization_tariff_chart);
        initialize();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            arrayList = (ArrayList<Billing_Summarization_model>) extras.getSerializable("test");
            appbarTitle = extras.getString("appbarTitle");
            date_check_val = extras.getString("DATE_CHECK");
        }
        setTitle(appbarTitle);
        addListenerOnButtonClick();
        barchart();
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
                barChart.clearValues();
            } else {
                barchart();
            }
        });
    }
    private void initialize() {
        buttn_apply = findViewById(R.id.btn_apply);
        chk_install = findViewById(R.id.checkBox1);
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Unbilled Summarization Chart");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        barChart = findViewById(R.id.chart1);
        arrayList = new ArrayList<>();
        FloatingActionButton mShowSnackbarButton = findViewById(R.id.fab);

        mShowSnackbarButton.setOnClickListener(view -> {
            Intent intent = new Intent(UnBilled_Summerization_Tariff_chart.this, UnBilled_Summerization_Tariff_Report.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("test", arrayList);
            bundle.putString("appbarTitle", "UnBilled_Summerization_Tariff_Report");
            bundle.putString("DATE_CHECK",date_check_val);
            intent.putExtras(bundle);
            startActivity(intent);

        });
    }

    private void barchart() {

        if(date_check_val.equals("Y")){
            for (int i = 0; i < arrayList.size(); i++) {
                name[i] = arrayList.get(i).getMonth();
                percent[i] = Float.parseFloat(arrayList.get(i).getInstallations());
            }
        }else {
            for (int i = 0; i < arrayList.size(); i++) {
                name[i] = arrayList.get(i).getYear();
                percent[i] = Float.parseFloat(arrayList.get(i).getInstallations());
            }
        }

        ArrayList<BarEntry> yaxis = new ArrayList<>();
        final ArrayList<String> xaxis = new ArrayList<>();

        //Setting data
        for (int i = 0, j = 0; i < arrayList.size(); i++) {

            if (name[i] == null)
                break;
            if (percent[i] == 0.0)
                continue;

            //Y axis plotting
            yaxis.add(new BarEntry(j++, percent[i]));
            //Checking the length of the name string to shorten it.
            if (name[i].length() >= 16) {
                xaxis.add(name[i].substring(0, 16));
            } else {
                xaxis.add(name[i]);
            }
        }

        //X axis plotting
        XAxis xAxis = barChart.getXAxis();
        xAxis.setLabelRotationAngle(-90);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xaxis));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7);

        MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);
        mv.setChartView(barChart); // For bounds control
        barChart.setMarker(mv);


        //Bar chart title and colors
        BarDataSet barDataSet = new BarDataSet(yaxis, "Installation");
        barDataSet.setColor(Color.rgb(102, 178, 255));
        /*barDataSet.setDrawIcons(false);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);*/

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        barChart.setData(data);
        barChart.setDrawBarShadow(false);
        // billingViewHolder.mBarChart.setDrawValueAboveBar(false);
        barChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        //  mChart.setDrawYLabels(false);


        //left axis
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setStartAtZero(false);  // t// this replaces setStartAtZero(true)
        //right axis
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setStartAtZero(false); // this replaces setStartAtZero(true)
        //legend
        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(0f);

        //animation of the bar chart
        barChart.animateY(1500);

        barChart.invalidate();
    }
}
