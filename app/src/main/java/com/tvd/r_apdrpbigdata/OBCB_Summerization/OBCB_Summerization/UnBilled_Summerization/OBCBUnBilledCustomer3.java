package com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.UnBilled_Summerization;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.adapters.OBCBUnBilledAdapter3;
import com.tvd.r_apdrpbigdata.adapters.RoleAdapter;
import com.tvd.r_apdrpbigdata.database.Databasehelper;
import com.tvd.r_apdrpbigdata.invoke.SendingData;
import com.tvd.r_apdrpbigdata.models.Response;
import com.tvd.r_apdrpbigdata.other.MyMarkerView;
import com.tvd.r_apdrpbigdata.values.FunctionCall;
import com.tvd.r_apdrpbigdata.values.GetSetValues;

import java.util.ArrayList;
import java.util.Calendar;

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
import static com.tvd.r_apdrpbigdata.values.Constant.TARIFF_NAME;
import static com.tvd.r_apdrpbigdata.values.Constant.ZONE_ID;
import static com.tvd.r_apdrpbigdata.values.Constant.ZONE_NAME;

public class OBCBUnBilledCustomer3 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ArrayList<GetSetValues> company_list, zone_list, circle_list, division_list, subdiv_list, tariff_list, status_list;
    RoleAdapter company_adapter, zone_adapter, circle_adapter, div_adapter, subdiv_adapter, tariff_adapter, status_adapter;
    Spinner sp_company, sp_zone, sp_circle, sp_division, sp_subdiv, sp_tariff, sp_status;
    GetSetValues getsetValues;
    ArrayList<GetSetValues> arrayList;
    String COMPANY = "500001", ZONE = "none", CIRCLE = "none", DIVISION = "none", SUB_DIVISION = "none", TARIFF = "All", STATUS = "All";
    Databasehelper databasehelper;
    FunctionCall functionCall;
    SendingData sendingData;
    ArrayList<Response> responseArrayList;
    private boolean userIsInteracting;
    EditText tv_from, tv_to;
    TextView tv_total1, tv_total2, tv_total3, tv_total4, tv_total5, tv_total6, tv_total7, tv_year;
    Calendar mcalender;
    int day, month, year;
    String dd, FROM_DATE, TO_DATE;
    BarChart mBarChart;
    Button submit;
    String value = "";
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    OBCBUnBilledAdapter3 obcbAdapter;
    LinearLayout lin1, lin2;
    private android.support.v7.widget.Toolbar toolbar;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case DIALOG_SUCCESS:
                    progressDialog.dismiss();
                    barChart(responseArrayList);
                    lin1.setVisibility(View.VISIBLE);
                    lin2.setVisibility(View.VISIBLE);
                    break;

                case DIALOG_FAILURE:
                    progressDialog.dismiss();
                    functionCall.showtoast(OBCBUnBilledCustomer3.this, "No Data found");
                    break;

            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obcb_unbilled_customer3);
        initialization();
        populate_company_spinner();
        populate_tariff_spinner();
        populate_status_spinner();
    }

    //----------------------------------------------------------------------------------------------------------------------------------
    public void initialization() {
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("OBCBUnbilled Customer");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        tv_year = findViewById(R.id.txt_year);
        tv_total1 = findViewById(R.id.txt_total1);
        tv_total2 = findViewById(R.id.txt_total2);
        tv_total3 = findViewById(R.id.txt_total3);
        tv_total4 = findViewById(R.id.txt_total4);
        tv_total5 = findViewById(R.id.txt_total5);
        tv_total6 = findViewById(R.id.txt_total6);
        tv_total7 = findViewById(R.id.txt_total7);
        sendingData = new SendingData();
        functionCall = new FunctionCall();
        databasehelper = new Databasehelper(this);
        databasehelper.openDatabase();
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
        sp_tariff = findViewById(R.id.tariff);
        sp_tariff.setOnItemSelectedListener(this);
        sp_status = findViewById(R.id.status);
        sp_status.setOnItemSelectedListener(this);
        tv_from = findViewById(R.id.from_date);
        tv_from.setOnClickListener(this);
        tv_to = findViewById(R.id.to_date);
        tv_to.setOnClickListener(this);
        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(this);
        mBarChart = findViewById(R.id.barChart);
        arrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        responseArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.obcb_recycle);
        lin1 = findViewById(R.id.lin_layout1);
        lin2 = findViewById(R.id.lin_layout2);
        obcbAdapter = new OBCBUnBilledAdapter3(this, responseArrayList, getsetValues.getMonth_flag());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.from_date) {
            DateDialog1();
        }
        if (v.getId() == R.id.to_date) {
            DateDialog2();
        }
        if (v.getId() == R.id.btn_submit) {
            submit();
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }

    //----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        GetSetValues role;
        switch (adapterView.getId()) {
            case R.id.company:
                if (userIsInteracting) {
                    role = company_list.get(i);
                    COMPANY = role.getCode();
                    populate_zone_spinner(COMPANY);
                }
                break;

            case R.id.zone:
                if (userIsInteracting) {
                    role = zone_list.get(i);
                    ZONE = role.getCode();
                    populate_circle_spinner(ZONE);
                }
                break;

            case R.id.circle:
                if (userIsInteracting) {
                    role = circle_list.get(i);
                    CIRCLE = role.getCode();
                    populate_division_spinner(CIRCLE);
                }
                break;

            case R.id.division:
                if (userIsInteracting) {
                    role = division_list.get(i);
                    DIVISION = role.getCode();
                    populate_subdivision_spinner(DIVISION);
                }
                break;

            case R.id.subdivision:
                if (userIsInteracting) {
                    role = subdiv_list.get(i);
                    SUB_DIVISION = role.getCode();
                }
                break;

            case R.id.tariff:
                if (userIsInteracting) {
                    role = tariff_list.get(i);
                    TARIFF = role.getCode();
                }
                break;

            case R.id.status:
                if (userIsInteracting) {
                    role = status_list.get(i);
                    STATUS = role.getCode();
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

    //setting tariff spinner--------------------------------------------------------------------------------------------------
    private void populate_tariff_spinner() {
        tariff_list = new ArrayList<>();
        tariff_list.clear();
        Cursor tariffDetails = databasehelper.getTariff_details();
        if (tariffDetails.getCount() > 0) {
            while (tariffDetails.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setCode(functionCall.getCursorValue(tariffDetails, TARIFF_NAME));
                tariff_list.add(getSetValues);
            }
            tariff_adapter = new RoleAdapter(tariff_list, getApplicationContext());
            sp_tariff.setAdapter(tariff_adapter);
            tariff_adapter.notifyDataSetChanged();
        }
        tariffDetails.close();
    }

    //setting status spinner--------------------------------------------------------------------------------------------------
    private void populate_status_spinner() {
        status_list = new ArrayList<>();
        status_list.clear();
        for (int i = 0; i < getResources().getStringArray(R.array.account_status).length; i++) {
            GetSetValues getSetValues = new GetSetValues();
            String value = getResources().getStringArray(R.array.account_status)[i];
            getSetValues.setCode(value);
            status_list.add(getSetValues);
        }
        status_adapter = new RoleAdapter(status_list, getApplicationContext());
        sp_status.setAdapter(status_adapter);
        status_adapter.notifyDataSetChanged();
    }

    //----------------------------------------------------------------------------------------------------------------------------------
    public void DateDialog1() {
        mcalender = Calendar.getInstance();
        day = mcalender.get(Calendar.DAY_OF_MONTH);
        year = mcalender.get(Calendar.YEAR);
        month = mcalender.get(Calendar.MONTH);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                dd = (year + "-" + (month + 1) + "-" + dayOfMonth);
                FROM_DATE = String.valueOf(year);
                tv_from.setText(FROM_DATE);
            }
        };
        DatePickerDialog dpdialog = new DatePickerDialog(this, listener, year, month, day);
        mcalender.add(Calendar.MONTH, -1);
        dpdialog.show();
    }

    public void DateDialog2() {
        mcalender = Calendar.getInstance();
        day = mcalender.get(Calendar.DAY_OF_MONTH);
        year = mcalender.get(Calendar.YEAR);
        month = mcalender.get(Calendar.MONTH);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                dd = (year + "-" + (month + 1) + "-" + dayOfMonth);
                TO_DATE = String.valueOf(year);
                tv_to.setText(TO_DATE);
            }
        };
        DatePickerDialog dpdialog = new DatePickerDialog(this, listener, year, month, day);
        mcalender.add(Calendar.MONTH, -1);
        dpdialog.show();
    }

    //----------------------------------------------------------------------------------------------------------------------------------
    public void submit() {
        if (TextUtils.isEmpty(FROM_DATE) || TextUtils.isEmpty(TO_DATE)) {
            functionCall.showtoast(this, "Please select Year");
            return;
        }
        if (tv_from.getText().toString().equals(tv_to.getText().toString())) {
            getsetValues.setMonth_flag("Y");
        } else {
            getsetValues.setMonth_flag("N");
        }
        functionCall.showprogressdialog("Please wait to complete", "Data Loading", progressDialog);
        value = "from=" + FROM_DATE + "&subDivision=" + SUB_DIVISION + "&acc_status=" + STATUS + "&company=500001%20-%20Hubli%20Electricity%20Supply%20Company%20Limited" +
                "&zone=" + ZONE + "&circle=" + CIRCLE + "&division=" + DIVISION + "&to=" + TO_DATE;
        SendingData.OB_CB_Details11 ob_cb_details = sendingData.new OB_CB_Details11(handler, responseArrayList, value, obcbAdapter, getsetValues);
        ob_cb_details.execute();
    }

    //------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("SetTextI18n")
    private void barChart(ArrayList<Response> arrayList) {
        double total1 = 0, total2 = 0, total3 = 0, total4 = 0, total5 = 0, total6 = 0, total7 = 0;

        mBarChart.setPinchZoom(true);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawGridBackground(false);

        Legend l = mBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(11f);
        l.setWordWrapEnabled(true);


        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawLabels(true);

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(15f);
        leftAxis.setStartAtZero(false);

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(true);
        rightAxis.setSpaceTop(15f);
        rightAxis.setStartAtZero(false); // this replaces setStartAtZero(true)

        mBarChart.getAxisRight().setEnabled(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mBarChart); // For bounds control
        mBarChart.setMarker(mv);
        mBarChart.setDrawGridBackground(false);
        float groupSpace = 0.02f;
        float barSpace = 0.02f; // x7 DataSet
        float barWidth = 0.12f;

        int startYear, endYear;
        if (getsetValues.getMonth_flag().equals("N")) {
            startYear = Integer.parseInt(arrayList.get(0).getA1());
            endYear = Integer.parseInt(arrayList.get(arrayList.size() - 1).getA1()) + 1;
        } else {
            tv_year.setText("MONTH");
            startYear = Integer.parseInt(arrayList.get(0).getMonth());
            endYear = Integer.parseInt(arrayList.get(arrayList.size() - 1).getMonth()) + 1;
        }
        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();
        ArrayList<BarEntry> values4 = new ArrayList<>();
        ArrayList<BarEntry> values5 = new ArrayList<>();
        ArrayList<BarEntry> values6 = new ArrayList<>();
        ArrayList<BarEntry> values7 = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++) {
            values1.add(new BarEntry(i, Float.parseFloat(arrayList.get(i).getA2())));
            values2.add(new BarEntry(i, Float.parseFloat(arrayList.get(i).getA3())));
            values3.add(new BarEntry(i, Float.parseFloat(arrayList.get(i).getA4())));
            values4.add(new BarEntry(i, Float.parseFloat(arrayList.get(i).getA5())));
            values5.add(new BarEntry(i, Float.parseFloat(arrayList.get(i).getA6())));
            values6.add(new BarEntry(i, Float.parseFloat(arrayList.get(i).getA7())));
            values7.add(new BarEntry(i, Float.parseFloat(arrayList.get(i).getA8())));
        }

        BarDataSet set1, set2, set3, set4, set5, set6, set7;
        set1 = new BarDataSet(values1, "Installations");
        set1.setColor(Color.rgb(104, 241, 175));
        set2 = new BarDataSet(values2, "Opening Balance");
        set2.setColor(Color.rgb(164, 228, 251));
        set3 = new BarDataSet(values3, "Consumed Units");
        set3.setColor(Color.rgb(242, 247, 158));
        set4 = new BarDataSet(values4, "Demand");
        set4.setColor(Color.rgb(255, 102, 0));
        set5 = new BarDataSet(values5, "Net Pay Amount");
        set5.setColor(Color.parseColor("#FF00FF"));
        set6 = new BarDataSet(values6, "Collection Amount");
        set6.setColor(Color.parseColor("#4B0082"));
        set7 = new BarDataSet(values7, "Collection Amount");
        set7.setColor(Color.parseColor("#DC143C"));
        BarData data = new BarData(set1, set2, set3, set4, set5, set6, set7);
        data.setValueFormatter(new LargeValueFormatter());
        data.setDrawValues(false);
        mBarChart.setData(data);

        mBarChart.getBarData().setBarWidth(barWidth);
        mBarChart.getXAxis().setAxisMinimum(startYear);
        mBarChart.getXAxis().setAxisMaximum(endYear);
        mBarChart.groupBars(startYear, groupSpace, barSpace);
        mBarChart.invalidate();
        mBarChart.setExtraOffsets(0f, 10f, 0f, 10f);
        mBarChart.setViewPortOffsets(0f, 20f, 0f, 20f);

        for (int i = 0; i < arrayList.size(); i++) {
            total1 = total1 + Double.parseDouble(arrayList.get(i).getA2());
            total2 = total2 + Double.parseDouble(arrayList.get(i).getA3());
            total3 = total3 + Double.parseDouble(arrayList.get(i).getA4());
            total4 = total4 + Double.parseDouble(arrayList.get(i).getA5());
            total5 = total5 + Double.parseDouble(arrayList.get(i).getA6());
            total6 = total6 + Double.parseDouble(arrayList.get(i).getA7());
            total7 = total7 + Double.parseDouble(arrayList.get(i).getA8());
        }

        tv_total1.setText(functionCall.roundoff1(String.valueOf(total1)));
        tv_total2.setText(functionCall.roundoff1(String.valueOf(total2)));
        tv_total3.setText(functionCall.roundoff1(String.valueOf(total3)));
        tv_total4.setText(functionCall.roundoff1(String.valueOf(total4)));
        tv_total5.setText(functionCall.roundoff1(String.valueOf(total5)));
        tv_total6.setText(functionCall.roundoff1(String.valueOf(total6)));
        tv_total7.setText(functionCall.roundoff1(String.valueOf(total7)));

        obcbAdapter = new OBCBUnBilledAdapter3(this, responseArrayList, getsetValues.getMonth_flag());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(obcbAdapter);
    }
}