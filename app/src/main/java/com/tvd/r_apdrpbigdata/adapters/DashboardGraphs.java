package com.tvd.r_apdrpbigdata.adapters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.invoke.SendingData;
import com.tvd.r_apdrpbigdata.models.Response;
import com.tvd.r_apdrpbigdata.other.MyMarkerView;
import com.tvd.r_apdrpbigdata.values.FunctionCall;

import java.util.ArrayList;

import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_FAILURE1;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_FAILURE2;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_FAILURE3;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_FAILURE4;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_SUCCESS1;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_SUCCESS2;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_SUCCESS3;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_SUCCESS4;


public class DashboardGraphs extends AppCompatActivity {
    BarChart mBarChart1, mBarChart2, mBarChart3, mBarChart4;
    FunctionCall functionCall;
    SendingData sendingData;
    ArrayList<Response> responseArrayList1, responseArrayList2, responseArrayList3, responseArrayList4;
    String MAIN_ROLE = "";
    ProgressDialog progressDialog;
    String[] name = new String[50];
    Float[] percent = new Float[50];

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case DIALOG_SUCCESS1:
                    barchart1(responseArrayList1);
                    break;

                case DIALOG_FAILURE1:
                    functionCall.showtoast(DashboardGraphs.this, "No Data found");
                    break;

                case DIALOG_SUCCESS2:
                    barchart2(responseArrayList2);
                    break;

                case DIALOG_FAILURE2:
                    functionCall.showtoast(DashboardGraphs.this, "No Data found");
                    break;

                case DIALOG_SUCCESS3:
                    barchart3(responseArrayList3);
                    break;

                case DIALOG_FAILURE3:
                    functionCall.showtoast(DashboardGraphs.this, "No Data found");
                    break;

                case DIALOG_SUCCESS4:
                    progressDialog.dismiss();
                    barchart4(responseArrayList4);
                    break;

                case DIALOG_FAILURE4:
                    progressDialog.dismiss();
                    functionCall.showtoast(DashboardGraphs.this, "No Data found");
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_graphs);
        initialization();
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    public void initialization() {
        Intent intent = getIntent();
        MAIN_ROLE = intent.getStringExtra("Value");
        sendingData = new SendingData();
        progressDialog = new ProgressDialog(this);
        functionCall = new FunctionCall();
        mBarChart1 = findViewById(R.id.barChart1);
        mBarChart2 = findViewById(R.id.barChart2);
        mBarChart3 = findViewById(R.id.barChart3);
        mBarChart4 = findViewById(R.id.barChart4);

        responseArrayList1 = new ArrayList<>();
        responseArrayList2 = new ArrayList<>();
        responseArrayList3 = new ArrayList<>();
        responseArrayList4 = new ArrayList<>();


        functionCall.showprogressdialog("Please wait to complete", "Data Loading", progressDialog);
        SendingData.Dashborad_details2 dashborad_details = sendingData.new Dashborad_details2(handler, responseArrayList1, MAIN_ROLE);
        dashborad_details.execute();
    }

    //For BarChart part--------------------------------------------------------------------------------------------------
    private void barchart1(ArrayList<Response> arrayList) {
        mBarChart1.clear();
        double total_instal1 = 0.0, total_open_bal1 = 0.0, total_cons_units1 = 0.0, total_demands1 = 0.0, total_net_pay1 = 0.0,
                total_coll_amt1 = 0.0, total_close_bal1 = 0.0;
        name[0] = "INSTA";
        name[1] = "OB";
        name[2] = "CU";
        name[3] = "DEMAND";
        name[4] = "NP";
        name[5] = "CA";
        name[6] = "CB";

        if (MAIN_ROLE.equals("")) {
            total_instal1 = total_instal1 + Double.parseDouble(arrayList.get(0).getA1());
            total_open_bal1 = total_open_bal1 + Double.parseDouble(arrayList.get(0).getA2());
            total_cons_units1 = total_cons_units1 + Double.parseDouble(arrayList.get(0).getA3());
            total_demands1 = total_demands1 + Double.parseDouble(arrayList.get(0).getA4());
            total_net_pay1 = total_net_pay1 + Double.parseDouble(arrayList.get(0).getA5());
            total_coll_amt1 = total_coll_amt1 + Double.parseDouble(arrayList.get(0).getA6());
            total_close_bal1 = total_close_bal1 + Double.parseDouble(arrayList.get(0).getA7());
        } else {
            for (int i = 0, j = 0; i < arrayList.size(); i++) {
                total_instal1 = total_instal1 + Double.parseDouble(arrayList.get(i).getA1());
                total_open_bal1 = total_open_bal1 + Double.parseDouble(arrayList.get(i).getA2());
                total_cons_units1 = total_cons_units1 + Double.parseDouble(arrayList.get(i).getA3());
                total_demands1 = total_demands1 + Double.parseDouble(arrayList.get(i).getA4());
                total_net_pay1 = total_net_pay1 + Double.parseDouble(arrayList.get(i).getA5());
                total_coll_amt1 = total_coll_amt1 + Double.parseDouble(arrayList.get(i).getA6());
                total_close_bal1 = total_close_bal1 + Double.parseDouble(arrayList.get(i).getA7());
            }
        }

        percent[0] = Float.parseFloat(functionCall.roundoff1(total_instal1 + ""));
        percent[1] = Float.parseFloat(functionCall.roundoff1(total_open_bal1 + ""));
        percent[2] = Float.parseFloat(functionCall.roundoff1(total_cons_units1 + ""));
        percent[3] = Float.parseFloat(functionCall.roundoff1(total_demands1 + ""));
        percent[4] = Float.parseFloat(functionCall.roundoff1(total_net_pay1 + ""));
        percent[5] = Float.parseFloat(functionCall.roundoff1(total_coll_amt1 + ""));
        percent[6] = Float.parseFloat(functionCall.roundoff1(total_close_bal1 + ""));

        ArrayList<BarEntry> yaxis1 = new ArrayList<>();
        ArrayList<BarEntry> yaxis2 = new ArrayList<>();
        ArrayList<BarEntry> yaxis3 = new ArrayList<>();
        ArrayList<BarEntry> yaxis4 = new ArrayList<>();
        ArrayList<BarEntry> yaxis5 = new ArrayList<>();
        ArrayList<BarEntry> yaxis6 = new ArrayList<>();
        ArrayList<BarEntry> yaxis7 = new ArrayList<>();
        final ArrayList<String> xaxis = new ArrayList<>();
        //Setting data
        for (int i = 0; i < 7; i++) {

            if (name[i] == null)
                break;

            //Y axis plotting
            yaxis1.add(new BarEntry(0, percent[0]));
            yaxis2.add(new BarEntry(1, percent[1]));
            yaxis3.add(new BarEntry(2, percent[2]));
            yaxis4.add(new BarEntry(3, percent[3]));
            yaxis5.add(new BarEntry(4, percent[4]));
            yaxis6.add(new BarEntry(5, percent[5]));
            yaxis7.add(new BarEntry(6, percent[6]));
            xaxis.add(name[i]);
        }

        //X axis plotting
        XAxis xAxis = mBarChart1.getXAxis();
        xAxis.setLabelRotationAngle(-90);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xaxis));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7);

        //Bar chart title and colors
        BarDataSet barDataSet1 = new BarDataSet(yaxis1, "INSTALLATIONS");
        barDataSet1.setColor(Color.BLUE);
        BarDataSet barDataSet2 = new BarDataSet(yaxis2, "OPENING BALANCE");
        barDataSet2.setColor(Color.BLACK);
        BarDataSet barDataSet3 = new BarDataSet(yaxis3, "CONSUMED UNITS");
        barDataSet3.setColor(Color.CYAN);
        BarDataSet barDataSet4 = new BarDataSet(yaxis4, "DEMAND");
        barDataSet4.setColor(Color.RED);
        BarDataSet barDataSet5 = new BarDataSet(yaxis5, "NET PAYABLE");
        barDataSet5.setColor(Color.MAGENTA);
        BarDataSet barDataSet6 = new BarDataSet(yaxis6, "COLLECTION AMOUNT");
        barDataSet6.setColor(Color.GREEN);
        BarDataSet barDataSet7 = new BarDataSet(yaxis7, "CLOSING BALANCE");
        barDataSet7.setColor(Color.YELLOW);

        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3, barDataSet4, barDataSet5, barDataSet6, barDataSet7);
        data.setValueTextSize(10f);
        data.setDrawValues(false);
        data.setHighlightEnabled(true);
        mBarChart1.setData(data);
        mBarChart1.setDrawBarShadow(false);
        mBarChart1.setDrawValueAboveBar(true);
        mBarChart1.getDescription().setEnabled(false);
        mBarChart1.setMaxVisibleValueCount(60);
        mBarChart1.setPinchZoom(false);
        mBarChart1.setDrawGridBackground(false);

        //left axis
        YAxis leftAxis = mBarChart1.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setStartAtZero(false); // this replaces setStartAtZero(true)

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mBarChart2); // For bounds control
        mBarChart1.setMarker(mv);

        //right axis
        YAxis rightAxis = mBarChart1.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setStartAtZero(false);
        rightAxis.setEnabled(false);// this replaces setStartAtZero(true)

        //legend
        Legend l = mBarChart3.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(true);
        l.setWordWrapEnabled(true);


        mBarChart1.animateY(1000);
        mBarChart1.invalidate();

        SendingData.Dashborad_details3 dashborad_details3 = sendingData.new Dashborad_details3(handler, responseArrayList2, MAIN_ROLE);
        dashborad_details3.execute();
    }

    //For BarChart part--------------------------------------------------------------------------------------------------
    private void barchart2(ArrayList<Response> arrayList) {
        double total_instal1 = 0.0, total_open_bal1 = 0.0, total_cons_units1 = 0.0, total_demands1 = 0.0, total_net_pay1 = 0.0,
                total_coll_amt1 = 0.0, total_close_bal1 = 0.0;
        name[0] = "INSTA";
        name[1] = "OB";
        name[2] = "CU";
        name[3] = "DEMAND";
        name[4] = "NP";
        name[5] = "CA";
        name[6] = "CB";

        if (MAIN_ROLE.equals("")) {
            total_instal1 = total_instal1 + Double.parseDouble(arrayList.get(0).getA1());
            total_open_bal1 = total_open_bal1 + Double.parseDouble(arrayList.get(0).getA2());
            total_cons_units1 = total_cons_units1 + Double.parseDouble(arrayList.get(0).getA3());
            total_demands1 = total_demands1 + Double.parseDouble(arrayList.get(0).getA4());
            total_net_pay1 = total_net_pay1 + Double.parseDouble(arrayList.get(0).getA5());
            total_coll_amt1 = total_coll_amt1 + Double.parseDouble(arrayList.get(0).getA6());
            total_close_bal1 = total_close_bal1 + Double.parseDouble(arrayList.get(0).getA7());
        } else {
            for (int i = 0, j = 0; i < arrayList.size(); i++) {
                total_instal1 = total_instal1 + Double.parseDouble(arrayList.get(i).getA1());
                total_open_bal1 = total_open_bal1 + Double.parseDouble(arrayList.get(i).getA2());
                total_cons_units1 = total_cons_units1 + Double.parseDouble(arrayList.get(i).getA3());
                total_demands1 = total_demands1 + Double.parseDouble(arrayList.get(i).getA4());
                total_net_pay1 = total_net_pay1 + Double.parseDouble(arrayList.get(i).getA5());
                total_coll_amt1 = total_coll_amt1 + Double.parseDouble(arrayList.get(i).getA6());
                total_close_bal1 = total_close_bal1 + Double.parseDouble(arrayList.get(i).getA7());
            }
        }

        percent[0] = Float.parseFloat(functionCall.roundoff1(total_instal1 + ""));
        percent[1] = Float.parseFloat(functionCall.roundoff1(total_open_bal1 + ""));
        percent[2] = Float.parseFloat(functionCall.roundoff1(total_cons_units1 + ""));
        percent[3] = Float.parseFloat(functionCall.roundoff1(total_demands1 + ""));
        percent[4] = Float.parseFloat(functionCall.roundoff1(total_net_pay1 + ""));
        percent[5] = Float.parseFloat(functionCall.roundoff1(total_coll_amt1 + ""));
        percent[6] = Float.parseFloat(functionCall.roundoff1(total_close_bal1 + ""));

        ArrayList<BarEntry> yaxis1 = new ArrayList<>();
        ArrayList<BarEntry> yaxis2 = new ArrayList<>();
        ArrayList<BarEntry> yaxis3 = new ArrayList<>();
        ArrayList<BarEntry> yaxis4 = new ArrayList<>();
        ArrayList<BarEntry> yaxis5 = new ArrayList<>();
        ArrayList<BarEntry> yaxis6 = new ArrayList<>();
        ArrayList<BarEntry> yaxis7 = new ArrayList<>();
        final ArrayList<String> xaxis = new ArrayList<>();
        //Setting data
        for (int i = 0; i < 7; i++) {

            if (name[i] == null)
                break;

            //Y axis plotting
            yaxis1.add(new BarEntry(0, percent[0]));
            yaxis2.add(new BarEntry(1, percent[1]));
            yaxis3.add(new BarEntry(2, percent[2]));
            yaxis4.add(new BarEntry(3, percent[3]));
            yaxis5.add(new BarEntry(4, percent[4]));
            yaxis6.add(new BarEntry(5, percent[5]));
            yaxis7.add(new BarEntry(6, percent[6]));
            xaxis.add(name[i]);
        }

        //X axis plotting
        XAxis xAxis = mBarChart2.getXAxis();
        xAxis.setLabelRotationAngle(-90);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xaxis));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7);

        //Bar chart title and colors
        BarDataSet barDataSet1 = new BarDataSet(yaxis1, "INSTALLATIONS");
        barDataSet1.setColor(Color.BLUE);
        BarDataSet barDataSet2 = new BarDataSet(yaxis2, "OPENING BALANCE");
        barDataSet2.setColor(Color.BLACK);
        BarDataSet barDataSet3 = new BarDataSet(yaxis3, "CONSUMED UNITS");
        barDataSet3.setColor(Color.CYAN);
        BarDataSet barDataSet4 = new BarDataSet(yaxis4, "DEMAND");
        barDataSet4.setColor(Color.RED);
        BarDataSet barDataSet5 = new BarDataSet(yaxis5, "NET PAYABLE");
        barDataSet5.setColor(Color.MAGENTA);
        BarDataSet barDataSet6 = new BarDataSet(yaxis6, "COLLECTION AMOUNT");
        barDataSet6.setColor(Color.GREEN);
        BarDataSet barDataSet7 = new BarDataSet(yaxis7, "CLOSING BALANCE");
        barDataSet7.setColor(Color.YELLOW);

        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3, barDataSet4, barDataSet5, barDataSet6, barDataSet7);
        data.setValueTextSize(10f);
        data.setDrawValues(false);
        data.setHighlightEnabled(true);
        mBarChart2.setData(data);
        mBarChart2.setDrawBarShadow(false);
        mBarChart2.setDrawValueAboveBar(true);
        mBarChart2.getDescription().setEnabled(false);
        mBarChart2.setMaxVisibleValueCount(60);
        mBarChart2.setPinchZoom(false);
        mBarChart2.setDrawGridBackground(false);

        //left axis
        YAxis leftAxis = mBarChart2.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setStartAtZero(false); // this replaces setStartAtZero(true)

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mBarChart2); // For bounds control
        mBarChart2.setMarker(mv);

        //right axis
        YAxis rightAxis = mBarChart2.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setStartAtZero(false);
        rightAxis.setEnabled(false);// this replaces setStartAtZero(true)

        //legend
        Legend l = mBarChart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(true);
        l.setWordWrapEnabled(true);

        mBarChart2.animateY(1000);
        mBarChart2.invalidate();

        SendingData.Dashborad_details4 dashborad_details4 = sendingData.new Dashborad_details4(handler, responseArrayList3, MAIN_ROLE);
        dashborad_details4.execute();

    }

    //For BarChart part--------------------------------------------------------------------------------------------------
    private void barchart3(ArrayList<Response> arrayList) {

        ArrayList<BarEntry> yaxis1 = new ArrayList<>();
        ArrayList<BarEntry> yaxis2 = new ArrayList<>();
        ArrayList<BarEntry> yaxis3 = new ArrayList<>();
        ArrayList<BarEntry> yaxis4 = new ArrayList<>();
        ArrayList<BarEntry> yaxis5 = new ArrayList<>();
        ArrayList<BarEntry> yaxis6 = new ArrayList<>();
        ArrayList<BarEntry> yaxis7 = new ArrayList<>();
        final ArrayList<String> xaxis = new ArrayList<>();

        for (int i = 0, j = 0; i < arrayList.size(); i++) {
            yaxis1.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA1()))));
            yaxis2.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA2()))));
            yaxis3.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA3()))));
            yaxis4.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA4()))));
            yaxis5.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA5()))));
            yaxis6.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA6()))));
            yaxis7.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA7()))));
            xaxis.add(i, arrayList.get(i).getA9());
        }

        //X axis plotting
        XAxis xAxis = mBarChart3.getXAxis();
        xAxis.setLabelRotationAngle(-45);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xaxis));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7);

        //Bar chart title and colors
        BarDataSet barDataSet1 = new BarDataSet(yaxis1, "INSTALLATIONS");
        barDataSet1.setColor(Color.BLUE);
        BarDataSet barDataSet2 = new BarDataSet(yaxis2, "OPENING BALANCE");
        barDataSet2.setColor(Color.BLACK);
        BarDataSet barDataSet3 = new BarDataSet(yaxis3, "CONSUMED UNITS");
        barDataSet3.setColor(Color.CYAN);
        BarDataSet barDataSet4 = new BarDataSet(yaxis4, "DEMAND");
        barDataSet4.setColor(Color.RED);
        BarDataSet barDataSet5 = new BarDataSet(yaxis5, "NET PAYABLE");
        barDataSet5.setColor(Color.MAGENTA);
        BarDataSet barDataSet6 = new BarDataSet(yaxis6, "COLLECTION AMOUNT");
        barDataSet6.setColor(Color.GREEN);
        BarDataSet barDataSet7 = new BarDataSet(yaxis7, "CLOSING BALANCE");
        barDataSet7.setColor(Color.YELLOW);
        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3, barDataSet4, barDataSet5, barDataSet6, barDataSet7);
        data.setValueTextSize(10f);
        data.setDrawValues(false);
        data.setValueTextSize(10f);
        data.setDrawValues(false);
        mBarChart3.setData(data);
        mBarChart3.setDrawBarShadow(false);
        mBarChart3.setDrawValueAboveBar(true);
        mBarChart3.getDescription().setEnabled(false);
        mBarChart3.setMaxVisibleValueCount(60);
        mBarChart3.setPinchZoom(false);
        mBarChart3.setDrawGridBackground(false);

        //left axis
        YAxis leftAxis = mBarChart3.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setStartAtZero(false); // this replaces setStartAtZero(true)

        //right axis
        YAxis rightAxis = mBarChart3.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setStartAtZero(false);
        rightAxis.setEnabled(false);// this replaces setStartAtZero(true)

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mBarChart3); // For bounds control
        mBarChart3.setMarker(mv);

        //legend
        Legend l = mBarChart3.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(true);
        l.setWordWrapEnabled(true);

        //animation of the bar chart
        mBarChart3.animateY(1000);
        mBarChart3.invalidate();

        SendingData.Dashborad_details5 dashborad_details5 = sendingData.new Dashborad_details5(handler, responseArrayList4, MAIN_ROLE);
        dashborad_details5.execute();
    }

    //For BarChart part--------------------------------------------------------------------------------------------------
    private void barchart4(ArrayList<Response> arrayList) {
        ArrayList<BarEntry> yaxis1 = new ArrayList<>();
        ArrayList<BarEntry> yaxis2 = new ArrayList<>();
        ArrayList<BarEntry> yaxis3 = new ArrayList<>();
        ArrayList<BarEntry> yaxis4 = new ArrayList<>();
        ArrayList<BarEntry> yaxis5 = new ArrayList<>();
        ArrayList<BarEntry> yaxis6 = new ArrayList<>();
        ArrayList<BarEntry> yaxis7 = new ArrayList<>();
        final ArrayList<String> xaxis = new ArrayList<>();

        for (int i = 0, j = 0; i < arrayList.size(); i++) {
            yaxis1.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA1()))));
            yaxis2.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA2()))));
            yaxis3.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA3()))));
            yaxis4.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA4()))));
            yaxis5.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA5()))));
            yaxis6.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA6()))));
            yaxis7.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA7()))));
            xaxis.add(i, arrayList.get(i).getA9());
        }
        //X axis plotting
        XAxis xAxis = mBarChart4.getXAxis();
        xAxis.setLabelRotationAngle(-45);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xaxis));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7);

        //Bar chart title and colors
        BarDataSet barDataSet1 = new BarDataSet(yaxis1, "INSTALLATIONS");
        barDataSet1.setColor(Color.BLUE);
        BarDataSet barDataSet2 = new BarDataSet(yaxis2, "OPENING BALANCE");
        barDataSet2.setColor(Color.BLACK);
        BarDataSet barDataSet3 = new BarDataSet(yaxis3, "CONSUMED UNITS");
        barDataSet3.setColor(Color.CYAN);
        BarDataSet barDataSet4 = new BarDataSet(yaxis4, "DEMAND");
        barDataSet4.setColor(Color.RED);
        BarDataSet barDataSet5 = new BarDataSet(yaxis5, "NET PAYABLE");
        barDataSet5.setColor(Color.MAGENTA);
        BarDataSet barDataSet6 = new BarDataSet(yaxis6, "COLLECTION AMOUNT");
        barDataSet6.setColor(Color.GREEN);
        BarDataSet barDataSet7 = new BarDataSet(yaxis7, "CLOSING BALANCE");
        barDataSet7.setColor(Color.YELLOW);
        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3, barDataSet4, barDataSet5, barDataSet6, barDataSet7);
        data.setValueTextSize(10f);
        data.setDrawValues(false);

        data.setValueTextSize(10f);
        data.setDrawValues(false);
        mBarChart4.setData(data);
        mBarChart4.setDrawBarShadow(false);
        mBarChart4.setDrawValueAboveBar(true);
        mBarChart4.getDescription().setEnabled(false);
        mBarChart4.setMaxVisibleValueCount(60);
        mBarChart4.setPinchZoom(false);
        mBarChart4.setDrawGridBackground(false);

        //left axis
        YAxis leftAxis = mBarChart4.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setStartAtZero(false); // this replaces setStartAtZero(true)

        //right axis
        YAxis rightAxis = mBarChart4.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setStartAtZero(false);
        rightAxis.setEnabled(false);// this replaces setStartAtZero(true)

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mBarChart4); // For bounds control
        mBarChart4.setMarker(mv);

        //legend
        Legend l = mBarChart4.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(true);
        l.setWordWrapEnabled(true);

        //animation of the bar chart
        mBarChart4.animateY(1000);
        mBarChart4.invalidate();

    }
}
