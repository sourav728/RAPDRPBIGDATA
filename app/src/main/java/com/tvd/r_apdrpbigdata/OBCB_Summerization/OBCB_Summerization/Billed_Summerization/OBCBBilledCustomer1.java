package com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Billed_Summerization;

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
import android.support.v7.widget.Toolbar;
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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.adapters.OBCBBilledAdapter1;
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

public class OBCBBilledCustomer1 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
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
    EditText tv_month;
    TextView tv_total1, tv_total2,tv_total3, tv_total4, tv_total5, tv_total6, tv_total7, tv_total8, tv_total9, tv_total10;
    Calendar mcalender;
    int day, month, year;
    String dd, date1;
    BarChart mBarChart;
    Button submit;
    String value = "";
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    OBCBBilledAdapter1 obcbAdapter;
    LinearLayout lin1, lin2;
    Toolbar toolbar;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case DIALOG_SUCCESS:
                    progressDialog.dismiss();
                    barchart(responseArrayList);
                    lin1.setVisibility(View.VISIBLE);
                    lin2.setVisibility(View.VISIBLE);
                    break;

                case DIALOG_FAILURE:
                    progressDialog.dismiss();
                    functionCall.showtoast(OBCBBilledCustomer1.this, "No Data found");
                    break;

            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obcbbilled_customer1);
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
        toolbar.setTitle("OB-CB Billed Customer Summarization Monthly Tariff-Wise");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        tv_total1 = findViewById(R.id.txt_total1);
        tv_total2 = findViewById(R.id.txt_total2);
        tv_total3 = findViewById(R.id.txt_total3);
        tv_total4 = findViewById(R.id.txt_total4);
        tv_total5 = findViewById(R.id.txt_total5);
        tv_total6 = findViewById(R.id.txt_total6);
        tv_total7 = findViewById(R.id.txt_total7);
        tv_total8 = findViewById(R.id.txt_total8);
        tv_total9 = findViewById(R.id.txt_total9);
        tv_total10 = findViewById(R.id.txt_total10);
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
        tv_month = findViewById(R.id.from_date);
        tv_month.setOnClickListener(this);
        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(this);
        mBarChart = findViewById(R.id.barChart);
        arrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        responseArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.obcb_recycle);
        lin1 = findViewById(R.id.lin_layout1);
        lin2 = findViewById(R.id.lin_layout2);

        obcbAdapter = new OBCBBilledAdapter1(this, responseArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(obcbAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.from_date) {
            DateDialog1();
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

    //----------------------------------------------------------------------------------------------------------------------------------------------
    public void DateDialog1() {
        mcalender = Calendar.getInstance();
        day = mcalender.get(Calendar.DAY_OF_MONTH);
        year = mcalender.get(Calendar.YEAR);
        month = mcalender.get(Calendar.MONTH);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                dd = (year + "-" + (month + 1) + "-" + dayOfMonth);
                date1 = functionCall.Parse_Date(dd);
                tv_month.setText(date1);
            }
        };
        DatePickerDialog dpdialog = new DatePickerDialog(this, listener, year, month, day);
        mcalender.add(Calendar.MONTH, -1);
        dpdialog.show();
    }


    //-------------------------------------------------------------------------------------------------------------------------------
    public void submit() {
        if (TextUtils.isEmpty(date1)) {
            functionCall.showtoast(this, "Please select Year");
            return;
        }
        functionCall.showprogressdialog("Please wait to complete", "Data Loading", progressDialog);
        value = "from=" + date1 + "&subDivision=" + SUB_DIVISION + "&tariff=" + TARIFF + "&acc_status=" + STATUS + "&company=500001%20-" +
                "%20Hubli%20Electricity%20Supply%20Company%20Limited&zone=" + ZONE + "&circle=" + CIRCLE + "&division=" + DIVISION + "";
        SendingData.OB_CB_Details5 ob_cb_details = sendingData.new OB_CB_Details5(handler, responseArrayList, value,obcbAdapter);
        ob_cb_details.execute();
    }

    //For BarChart part--------------------------------------------------------------------------------------------------
    private void barchart(ArrayList<Response> arrayList) {
        double total1 = 0, total2 = 0, total3 = 0, total4 = 0, total5 = 0, total6 = 0, total7 = 0, total8 = 0, total9 = 0, total10 = 0;

        ArrayList<BarEntry> yaxis = new ArrayList<>();
        ArrayList<BarEntry> yaxis2 = new ArrayList<>();
        ArrayList<BarEntry> yaxis3 = new ArrayList<>();
        ArrayList<BarEntry> yaxis4 = new ArrayList<>();
        ArrayList<BarEntry> yaxis5 = new ArrayList<>();
        ArrayList<BarEntry> yaxis6 = new ArrayList<>();
        ArrayList<BarEntry> yaxis7 = new ArrayList<>();
        ArrayList<BarEntry> yaxis8 = new ArrayList<>();
        ArrayList<BarEntry> yaxis9 = new ArrayList<>();
        ArrayList<BarEntry> yaxis10 = new ArrayList<>();
        final ArrayList<String> xaxis = new ArrayList<>();

        for (int i = 0, j = 0; i < arrayList.size(); i++) {
            yaxis.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA4()))));
            yaxis2.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA5()))));
            yaxis3.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA6()))));
            yaxis4.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA7()))));
            yaxis5.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA8()))));
            yaxis6.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA9()))));
            yaxis7.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA10()))));
            yaxis8.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA11()))));
            yaxis9.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA12()))));
            yaxis10.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA13()))));
        }

        for (int i = 0, j = 0; i < arrayList.size(); i++) {
            xaxis.add(i, arrayList.get(i).getA3());
        }

        //X axis plotting
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setLabelRotationAngle(0);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xaxis));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(12);

        BarDataSet set1, set2, set3, set4, set5, set6, set7, set8, set9, set10;
        set1 = new BarDataSet(yaxis, "INSTALLATIONS");
        set1.setColors(Color.BLUE);
        set2 = new BarDataSet(yaxis2, "BILLED INSTALLATIONS");
        set2.setColors(Color.GRAY);
        set3 = new BarDataSet(yaxis3, "CONSUMED UNITS");
        set3.setColors(Color.MAGENTA);
        set4 = new BarDataSet(yaxis4, "BILLED CONSUMED UNITS");
        set4.setColors(Color.BLACK);
        set5 = new BarDataSet(yaxis5, "OPENING BALANCE");
        set5.setColors(Color.RED);
        set6 = new BarDataSet(yaxis6, "DEMAND");
        set6.setColors(Color.GREEN);
        set7 = new BarDataSet(yaxis7, "BILLED DEMAND");
        set7.setColors(Color.YELLOW);
        set8 = new BarDataSet(yaxis8, "NET PAYABLE");
        set8.setColors(Color.CYAN);
        set9 = new BarDataSet(yaxis9, "COLLECTION AMOUNT");
        set9.setColors(Color.LTGRAY);
        set10 = new BarDataSet(yaxis10, "CLOSING BALANCE");
        set10.setColors(Color.WHITE);

        BarData data = new BarData(set1, set2, set3, set4, set5, set6, set7, set8, set9, set10);
        data.setValueFormatter(new LargeValueFormatter());
        data.setDrawValues(false);
        mBarChart.setData(data);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(false);
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setDrawMarkers(false);
        mBarChart.isDrawValueAboveBarEnabled();
        mBarChart.setMaxVisibleValueCount(60);
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawGridBackground(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mBarChart); // For bounds control
        mBarChart.setMarker(mv);

        //left axis
        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setLabelCount(12, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setStartAtZero(false);// this replaces setStartAtZero(true)

        //right axis
        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(12, false);
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
        l.setXEntrySpace(5f);
        l.setWordWrapEnabled(true);

        mBarChart.animateY(1000);
        mBarChart.invalidate();
        mBarChart.setExtraOffsets(0f, 10f, 0f, 10f);
        mBarChart.setViewPortOffsets(0f, 0f, 0f, 0f);

        for (int i = 0; i < arrayList.size(); i++) {
            total1 = total1 + Double.parseDouble(arrayList.get(i).getA4());
            total2 = total2 + Double.parseDouble(arrayList.get(i).getA5());
            total3 = total3 + Double.parseDouble(arrayList.get(i).getA6());
            total4 = total4 + Double.parseDouble(arrayList.get(i).getA7());
            total5 = total5 + Double.parseDouble(arrayList.get(i).getA8());
            total6 = total6 + Double.parseDouble(arrayList.get(i).getA9());
            total7 = total7 + Double.parseDouble(arrayList.get(i).getA10());
            total8 = total8 + Double.parseDouble(arrayList.get(i).getA11());
            total9 = total9 + Double.parseDouble(arrayList.get(i).getA12());
            total10 = total10 + Double.parseDouble(arrayList.get(i).getA13());
        }

        tv_total1.setText(functionCall.roundoff1(String.valueOf(total1)));
        tv_total2.setText(functionCall.roundoff1(String.valueOf(total2)));
        tv_total3.setText(functionCall.roundoff1(String.valueOf(total3)));
        tv_total4.setText(functionCall.roundoff1(String.valueOf(total4)));
        tv_total5.setText(functionCall.roundoff1(String.valueOf(total5)));
        tv_total6.setText(functionCall.roundoff1(String.valueOf(total6)));
        tv_total7.setText(functionCall.roundoff1(String.valueOf(total7)));
        tv_total8.setText(functionCall.roundoff1(String.valueOf(total8)));
        tv_total9.setText(functionCall.roundoff1(String.valueOf(total9)));
        tv_total10.setText(functionCall.roundoff1(String.valueOf(total10)));
    }
}