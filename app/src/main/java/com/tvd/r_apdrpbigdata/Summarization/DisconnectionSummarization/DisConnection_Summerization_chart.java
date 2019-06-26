package com.tvd.r_apdrpbigdata.Summarization.DisconnectionSummarization;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.models.Billing_Summarization_model;
import com.tvd.r_apdrpbigdata.other.MyMarkerView;
import com.tvd.r_apdrpbigdata.values.FunctionCall;

import java.util.ArrayList;

public class DisConnection_Summerization_chart extends AppCompatActivity {
    BarChart barChart;
    String[] name = new String[50];
    float[] percent = new float[50];
    ArrayList<Billing_Summarization_model> arrayList;
    String appbarTitle;
    int a = 0;
    private CheckBox chk_instat;
    private Button buttn_apply;
    int count = 0;
    String date_check_val = "";
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_connection_summerization_chart);
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
        buttn_apply.setOnClickListener(view -> {
            count = 0;
            if (chk_instat.isChecked()) {
                a = 1;
                count++;
            } else {
                a = 0;
            }

            if (count == 1) {
                barChart.clearValues();
            } else if (count == 0) {
                barChart.setVisibility(View.VISIBLE);
                barchart();
            }
        });
    }

    private void initialize() {
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Disconnection Summarization Chart");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        barChart = findViewById(R.id.chart1);
        arrayList = new ArrayList<>();
        FloatingActionButton mShowSnackbarButton = findViewById(R.id.fab);

        mShowSnackbarButton.setOnClickListener(view -> {

            Intent intent = new Intent(DisConnection_Summerization_chart.this, Disconnection_Summerization_Report.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("test",arrayList);
            bundle.putString("appbarTitle","Disconnection_Summerization_Report");
            bundle.putString("DATE_CHECK",date_check_val);
            intent.putExtras(bundle);
            startActivity(intent);

        });

        chk_instat = findViewById(R.id.checkBox1);
        buttn_apply = findViewById(R.id.btn_apply);
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

        for (int i = 0, j = 0; i < arrayList.size(); i++) {

            if (name[i] == null)
                break;
            if (percent[i] == 0.0)
                continue;

            yaxis.add(new BarEntry(j++, percent[i]));
            if (name[i].length() >= 16) {
                xaxis.add(name[i].substring(0, 16));
            } else {
                xaxis.add(name[i]);
            }
        }

        XAxis xAxis = barChart.getXAxis();
        xAxis.setLabelRotationAngle(-90);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xaxis));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7);

        MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);
        mv.setChartView(barChart);
        barChart.setMarker(mv);

        BarDataSet barDataSet = new BarDataSet(yaxis, "Installation");
        barDataSet.setDrawIcons(false);
        barDataSet.setColor(Color.rgb(102, 178, 255));
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        barChart.setData(data);
        barChart.setDrawBarShadow(false);
//        billingViewHolder.mBarChart.setDrawValueAboveBar(false);
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);

        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setStartAtZero(false);
        //right axis
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setStartAtZero(false);
        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(0f);

        barChart.animateY(1500);
        barChart.invalidate();
    }
}
