package com.tvd.r_apdrpbigdata;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.tvd.r_apdrpbigdata.adapters.DashboardAdapter;
import com.tvd.r_apdrpbigdata.adapters.DashboardGraphs;
import com.tvd.r_apdrpbigdata.adapters.RoleAdapter;
import com.tvd.r_apdrpbigdata.database.Databasehelper;
import com.tvd.r_apdrpbigdata.invoke.SendingData;
import com.tvd.r_apdrpbigdata.models.Response;
import com.tvd.r_apdrpbigdata.other.MyMarkerView;
import com.tvd.r_apdrpbigdata.values.FunctionCall;
import com.tvd.r_apdrpbigdata.values.GetSetValues;

import java.util.ArrayList;


import static com.tvd.r_apdrpbigdata.values.Constant.CIRCLE_ID;
import static com.tvd.r_apdrpbigdata.values.Constant.CIRCLE_NAME;
import static com.tvd.r_apdrpbigdata.values.Constant.COMPANY_ID;
import static com.tvd.r_apdrpbigdata.values.Constant.COMPANY_NAME;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.DIVISION_ID;
import static com.tvd.r_apdrpbigdata.values.Constant.DIVISION_NAME;
import static com.tvd.r_apdrpbigdata.values.Constant.SUBDIV_ID;
import static com.tvd.r_apdrpbigdata.values.Constant.SUBDIV_NAME;
import static com.tvd.r_apdrpbigdata.values.Constant.ZONE_ID;
import static com.tvd.r_apdrpbigdata.values.Constant.ZONE_NAME;

public class HomeDashboard extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ArrayList<GetSetValues> company_list, zone_list, circle_list, division_list, subdiv_list;
    RoleAdapter company_adapter, zone_adapter, circle_adapter, div_adapter, subdiv_adapter;
    Spinner sp_company, sp_zone, sp_circle, sp_division, sp_subdiv;
    GetSetValues getsetValues;
    DashboardAdapter dashboardAdapter;
    ArrayList<GetSetValues> arrayList;
    RecyclerView recyclerView;
    FloatingActionButton graphs;
    String COMPANY, ZONE, CIRCLE, DIVISION, SUB_DIVISION, MAIN_ROLE = "";
    Databasehelper databasehelper;
    FunctionCall functionCall;
    SendingData sendingData;
    private BarChart mBarChart;
    ArrayList<Response> responseArrayList;
    private boolean userIsInteracting;
    LinearLayout overall;

    String[] name = new String[50];
    Float[] percent = new Float[50];
    private android.support.v7.widget.Toolbar toolbar;
    private Handler handler = new Handler(new Handler.Callback() {
        @SuppressLint("RestrictedApi")
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case DIALOG_SUCCESS:
                    barchart(responseArrayList);
                    overall.setVisibility(View.VISIBLE);
                    graphs.setVisibility(View.VISIBLE);
                    break;

                case DIALOG_FAILURE:
                    functionCall.showtoast(HomeDashboard.this, "No Data found");
                    break;

            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dashboard);
        initialization();
        populate_company_spinner();
    }

    public void initialization() {
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("Home Dashboard");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        sendingData = new SendingData();
        functionCall = new FunctionCall();
        responseArrayList = new ArrayList<>();
        databasehelper = new Databasehelper(HomeDashboard.this);
        databasehelper.openDatabase();
        graphs = findViewById(R.id.btn_graphs);
        graphs.setOnClickListener(this);
        getsetValues = new GetSetValues();
        sp_company = findViewById(R.id.company);
        sp_company.setOnItemSelectedListener(this);
        sp_zone = findViewById(R.id.zone);
        sp_zone.setOnItemSelectedListener(this);
        sp_circle = findViewById(R.id.circle);
        sp_circle.setOnItemSelectedListener(this);
        sp_division = findViewById(R.id.division);
        sp_division.setOnItemSelectedListener(this);
        sp_subdiv = findViewById(R.id.subdivision);
        sp_subdiv.setOnItemSelectedListener(this);
        mBarChart = findViewById(R.id.barChart);
        overall = findViewById(R.id.lin_overall);

        recyclerView = findViewById(R.id.rv_dashboard);
        arrayList = new ArrayList<>();
        dashboardAdapter = new DashboardAdapter(this, responseArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dashboardAdapter);


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_graphs) {
            Intent intent = new Intent(HomeDashboard.this, DashboardGraphs.class);
            intent.putExtra("Value", MAIN_ROLE);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        GetSetValues role;
        switch (adapterView.getId()) {
            case R.id.company:
                if (userIsInteracting) {
                    role = company_list.get(i);
                    COMPANY = role.getCode();
                    populate_zone_spinner(COMPANY);
                    MAIN_ROLE = "AND%20COMPANY_ID=%27" + COMPANY + "%27%20GROUP%20BY%20ZONE_ID%20ORDER%20BY%20ZONE_ID";
                    loadData(MAIN_ROLE);
                }
                break;

            case R.id.zone:
                if (userIsInteracting) {
                    role = zone_list.get(i);
                    ZONE = role.getCode();
                    populate_circle_spinner(ZONE);
                    MAIN_ROLE = "AND%20ZONE_ID=%27" + ZONE + "%27%20GROUP%20BY%20CIRCLE_ID%20ORDER%20BY%20CIRCLE_ID";
                    loadData(MAIN_ROLE);
                }
                break;

            case R.id.circle:
                if (userIsInteracting) {
                    role = circle_list.get(i);
                    CIRCLE = role.getCode();
                    populate_division_spinner(CIRCLE);
                    MAIN_ROLE = "AND%20CIRCLE_ID=%27" + CIRCLE + "%27%20GROUP%20BY%20DIVISION_ID%20ORDER%20BY%20DIVISION_ID";
                    loadData(MAIN_ROLE);

                }
                break;

            case R.id.division:
                if (userIsInteracting) {
                    role = division_list.get(i);
                    DIVISION = role.getCode();
                    populate_subdivision_spinner(DIVISION);
                    MAIN_ROLE = "AND%20DIVISION_ID=%27" + DIVISION + "%27%20GROUP%20BY%20SUBDIV_ID%20ORDER%20BY%20SUBDIV_ID";
                    loadData(MAIN_ROLE);
                }
                break;

            case R.id.subdivision:
                if (userIsInteracting) {
                    role = subdiv_list.get(i);
                    SUB_DIVISION = role.getCode();
                    MAIN_ROLE = "AND%20SUBDIV_ID=%27" + SUB_DIVISION + "%27%20GROUP%20BY%20SUBDIV_ID";
                    loadData(MAIN_ROLE);
                }
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Setting company spinner--------------------------------------------------------------------------------------------------
    private void populate_company_spinner() {
        company_list = new ArrayList<>();
        company_list.clear();
        Cursor company_data = databasehelper.getCompany_details();
        if (company_data.getCount() > 0) {
            while (company_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setCode(functionCall.getCursorValue(company_data, COMPANY_ID));
                getSetValues.setName(functionCall.getCursorValue(company_data, COMPANY_NAME));
                company_list.add(getSetValues);
            }
            company_adapter = new RoleAdapter(company_list, getApplicationContext());
            sp_company.setAdapter(company_adapter);
            company_adapter.notifyDataSetChanged();
            sp_company.setSelection(0);
        }
        company_data.close();
        loadData(MAIN_ROLE);
    }

    //setting zone spinner--------------------------------------------------------------------------------------------------------
    private void populate_zone_spinner(String company) {
        zone_list = new ArrayList<>();
        zone_list.clear();
        Cursor zone_data = databasehelper.getZone_details(company);
        if (zone_data.getCount() > 0) {
            while (zone_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setCode(functionCall.getCursorValue(zone_data, ZONE_ID));
                getSetValues.setName(functionCall.getCursorValue(zone_data, ZONE_NAME));
                zone_list.add(getSetValues);
            }
            zone_adapter = new RoleAdapter(zone_list, getApplicationContext());
            sp_zone.setAdapter(zone_adapter);
            zone_adapter.notifyDataSetChanged();
            userIsInteracting = false;
            sp_zone.setSelection(0);
        }
        zone_data.close();
    }

    //setting circle spinner--------------------------------------------------------------------------------------------------
    private void populate_circle_spinner(String zone) {
        circle_list = new ArrayList<>();
        circle_list.clear();
        Cursor circle_data = databasehelper.getCircle_details(zone);
        if (circle_data.getCount() > 0) {
            while (circle_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setCode(functionCall.getCursorValue(circle_data, CIRCLE_ID));
                getSetValues.setName(functionCall.getCursorValue(circle_data, CIRCLE_NAME));
                circle_list.add(getSetValues);
            }
            circle_adapter = new RoleAdapter(circle_list, getApplicationContext());
            sp_circle.setAdapter(circle_adapter);
            circle_adapter.notifyDataSetChanged();
            userIsInteracting = false;
        }
        circle_data.close();
    }

    //setting division spinner--------------------------------------------------------------------------------------------------
    private void populate_division_spinner(String circle) {
        division_list = new ArrayList<>();
        division_list.clear();
        Cursor division_data = databasehelper.getDivision_details(circle);
        if (division_data.getCount() > 0) {
            while (division_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setCode(functionCall.getCursorValue(division_data, DIVISION_ID));
                getSetValues.setName(functionCall.getCursorValue(division_data, DIVISION_NAME));
                division_list.add(getSetValues);
            }
            div_adapter = new RoleAdapter(division_list, getApplicationContext());
            sp_division.setAdapter(div_adapter);
            div_adapter.notifyDataSetChanged();
            userIsInteracting = false;
        }
        division_data.close();
    }

    //setting subdivision spinner--------------------------------------------------------------------------------------------------
    private void populate_subdivision_spinner(String division) {
        subdiv_list = new ArrayList<>();
        subdiv_list.clear();
        Cursor sub_division_data = databasehelper.getSubDivision_details(division);
        if (sub_division_data.getCount() > 0) {
            while (sub_division_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setCode(functionCall.getCursorValue(sub_division_data, SUBDIV_ID));
                getSetValues.setName(functionCall.getCursorValue(sub_division_data, SUBDIV_NAME));
                subdiv_list.add(getSetValues);
            }
            subdiv_adapter = new RoleAdapter(subdiv_list, getApplicationContext());
            sp_subdiv.setAdapter(subdiv_adapter);
            subdiv_adapter.notifyDataSetChanged();
            userIsInteracting = false;
        }
        sub_division_data.close();
    }

    //For BarChart part--------------------------------------------------------------------------------------------------
    private void barchart(ArrayList<Response> arrayList) {
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
        XAxis xAxis = mBarChart.getXAxis();
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
        mBarChart.setData(data);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setMaxVisibleValueCount(60);
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawGridBackground(false);

        //left axis
        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setStartAtZero(false); // this replaces setStartAtZero(true)

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mBarChart); // For bounds control
        mBarChart.setMarker(mv);

        //right axis
        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setStartAtZero(false);
        rightAxis.setEnabled(false);// this replaces setStartAtZero(true)

        //legend
        Legend l = mBarChart.getLegend();
       l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(true);
        l.setWordWrapEnabled(true);

        mBarChart.animateY(1000);
        mBarChart.invalidate();
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------
    public void loadData(String value) {
        SendingData.Dashborad_details1 dashborad_details = sendingData.new Dashborad_details1(handler, responseArrayList, dashboardAdapter, value);
        dashborad_details.execute();
    }
}


