package com.tvd.r_apdrpbigdata.BDA_DashBoard;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
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
import com.tvd.r_apdrpbigdata.adapters.DashBoard_report_adapter;
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
import static com.tvd.r_apdrpbigdata.values.Constant.ZONE_ID;
import static com.tvd.r_apdrpbigdata.values.Constant.ZONE_NAME;

public class DashBoard_Overall extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner spinner_escom, spinner_zone, spinner_circle, spinner_division, spinner_subdivision,
            spinner_account, spinner_billed, spinner_payment;
    Databasehelper databasehelper;
    FunctionCall functionCall;
    ArrayList<GetSetValues> company_list, zone_list, circle_list, division_list, subdivision_list, tariff_list, account_list, billed_list, payment_list;
    RoleAdapter company_adapter, zone_adapter, circle_adapter, division_adapter, subdivision_adapter, tariff_adapter, account_adapter,
            billed_status_adapter, payment_status_adapter;
    EditText from_edit, to_edit;

    int year;
    Button submit;
    GetSetValues getSet;
    String COMPANY = "5400001", ZONE = "none", CIRCLE = "none", DIVISION = "none", SUB_DIVISION = "none", VALUE = "", TARIFF = "All",
            ACC_STATUS = "All", BILLED_STATUS = "All", PAY_STATUS = "All", FROM_DATE, TO_DATE;
    SendingData sendingData;
    ArrayList<Response> arrayList;
    ProgressDialog progressDialog;
    private BarChart chart;
    RecyclerView recyclerView;
    TextView total_instal, total_open_bal, total_cons_units, total_demands, total_net_pay, total_coll_amt, total_close_bal, tv_year;
    DashBoard_report_adapter report_adapter;
    LinearLayout lin1, lin2;
    private android.support.v7.widget.Toolbar toolbar;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case DIALOG_SUCCESS:
                    progressDialog.dismiss();
                    barChart(arrayList);
                    lin1.setVisibility(View.VISIBLE);
                    lin2.setVisibility(View.VISIBLE);
                    break;
                case DIALOG_FAILURE:
                    progressDialog.dismiss();
                    functionCall.showtoast(DashBoard_Overall.this, "No Data found");
                    break;
            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board__overall);

        initialize();
        setspinner_values();

        for (int i = 0; i < getResources().getStringArray(R.array.account_status).length; i++) {
            getSet = new GetSetValues();
            getSet.setCode(getResources().getStringArray(R.array.account_status)[i]);
            account_list.add(getSet);
            account_adapter.notifyDataSetChanged();
        }

        for (int i = 0; i < getResources().getStringArray(R.array.billed_status).length; i++) {
            getSet = new GetSetValues();
            getSet.setCode(getResources().getStringArray(R.array.billed_status)[i]);
            billed_list.add(getSet);
            billed_status_adapter.notifyDataSetChanged();
        }

        for (int i = 0; i < getResources().getStringArray(R.array.payment_status).length; i++) {
            getSet = new GetSetValues();
            getSet.setCode(getResources().getStringArray(R.array.payment_status)[i]);
            payment_list.add(getSet);
            payment_status_adapter.notifyDataSetChanged();
        }

        from_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from_edit.setText("");
                showYearDialog(true);
            }
        });
        to_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_edit.setText("");
                showYearDialog(false);
            }
        });
    }

    //------------------------------------------------------------------------------------------------------------------------------
    private void initialize() {
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("DashBoard Overall");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        getSet = new GetSetValues();
        progressDialog = new ProgressDialog(this);
        arrayList = new ArrayList<>();
        sendingData = new SendingData();
        year = Calendar.getInstance().get(Calendar.YEAR);
        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(this);
        chart = findViewById(R.id.chart1);
        arrayList = new ArrayList<>();
        total_instal = findViewById(R.id.total_installation);
        total_open_bal = findViewById(R.id.total_open_bal);
        total_cons_units = findViewById(R.id.total_cons_units);
        total_demands = findViewById(R.id.total_demands);
        total_net_pay = findViewById(R.id.total_pay_amount);
        total_coll_amt = findViewById(R.id.total_coll_amt);
        total_close_bal = findViewById(R.id.total_close_bal_amt);
        recyclerView = findViewById(R.id.bda_dashboard_report);
        tv_year= findViewById(R.id.txt_year);
        lin1 = findViewById(R.id.lin_layout1);
        lin2 = findViewById(R.id.lin_layout2);

        functionCall = new FunctionCall();
        databasehelper = new Databasehelper(getApplicationContext());
        databasehelper.openDatabase();
        from_edit = findViewById(R.id.from_date);
        to_edit = findViewById(R.id.to_date);

        spinner_escom = findViewById(R.id.company);
        spinner_zone = findViewById(R.id.zone);
        spinner_circle = findViewById(R.id.circle);
        spinner_division = findViewById(R.id.division);
        spinner_subdivision = findViewById(R.id.subdivision);
        spinner_account = findViewById(R.id.account_status);
        spinner_billed = findViewById(R.id.billed_status);
        spinner_payment = findViewById(R.id.payment_status);

        spinner_escom.setOnItemSelectedListener(this);
        spinner_zone.setOnItemSelectedListener(this);
        spinner_circle.setOnItemSelectedListener(this);
        spinner_division.setOnItemSelectedListener(this);
        spinner_subdivision.setOnItemSelectedListener(this);
        spinner_account.setOnItemSelectedListener(this);
        spinner_billed.setOnItemSelectedListener(this);
        spinner_payment.setOnItemSelectedListener(this);

        company_list = new ArrayList<>();
        company_adapter = new RoleAdapter(company_list, getApplicationContext());
        spinner_escom.setAdapter(company_adapter);

        zone_list = new ArrayList<>();
        zone_adapter = new RoleAdapter(zone_list, getApplicationContext());
        spinner_zone.setAdapter(zone_adapter);

        circle_list = new ArrayList<>();
        circle_adapter = new RoleAdapter(circle_list, getApplicationContext());
        spinner_circle.setAdapter(circle_adapter);

        division_list = new ArrayList<>();
        division_adapter = new RoleAdapter(division_list, getApplicationContext());
        spinner_division.setAdapter(circle_adapter);

        subdivision_list = new ArrayList<>();
        subdivision_adapter = new RoleAdapter(subdivision_list, getApplicationContext());
        spinner_subdivision.setAdapter(subdivision_adapter);

        account_list = new ArrayList<>();
        account_adapter = new RoleAdapter(account_list, getApplicationContext());
        spinner_account.setAdapter(account_adapter);

        billed_list = new ArrayList<>();
        billed_status_adapter = new RoleAdapter(billed_list, getApplicationContext());
        spinner_billed.setAdapter(billed_status_adapter);

        payment_list = new ArrayList<>();
        payment_status_adapter = new RoleAdapter(payment_list, getApplicationContext());
        spinner_payment.setAdapter(payment_status_adapter);

        report_adapter = new DashBoard_report_adapter(arrayList, DashBoard_Overall.this, getSet.getMonth_flag());
    }

    //----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
        GetSetValues role;
        switch (parent.getId()) {
            case R.id.company:
                role = company_list.get(i);
                COMPANY = role.getCode();
                populate_zone_spinner(COMPANY);
                break;
            case R.id.zone:
                role = zone_list.get(i);
                ZONE = role.getCode();
                populate_circle_spinner(ZONE);
                break;
            case R.id.circle:
                role = circle_list.get(i);
                CIRCLE = role.getCode();
                populate_division_spinner(CIRCLE);
                break;
            case R.id.division:
                role = division_list.get(i);
                DIVISION = role.getCode();
                populate_subdivision_spinner(DIVISION);
                break;
            case R.id.subdivision:
                role = subdivision_list.get(i);
                SUB_DIVISION = role.getCode();
                break;
            case R.id.account_status:
                role = account_list.get(i);
                ACC_STATUS = role.getCode();
                break;
            case R.id.billed_status:
                role = billed_list.get(i);
                BILLED_STATUS = role.getCode();
                break;
            case R.id.payment_status:
                role = payment_list.get(i);
                PAY_STATUS = role.getCode();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    private void setspinner_values() {
        //Setting company spinner
        company_list.clear();
        Cursor company_data = databasehelper.getCompany_details();
        if (company_data.getCount() > 0) {
            while (company_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setCode(functionCall.getCursorValue(company_data, COMPANY_ID));
                getSetValues.setName(functionCall.getCursorValue(company_data, COMPANY_NAME));
                company_list.add(getSetValues);
            }
            spinner_escom.setAdapter(company_adapter);
            spinner_escom.setSelection(0);
        }
        company_data.close();
    }

    //setting circle spinner
    private void populate_zone_spinner(String company) {
        //setting zone spinner
        zone_list.clear();
        Cursor zone_data = databasehelper.getZone_details(company);
        if (zone_data.getCount() > 0) {
            while (zone_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setCode(functionCall.getCursorValue(zone_data, ZONE_ID));
                getSetValues.setName(functionCall.getCursorValue(zone_data, ZONE_NAME));
                zone_list.add(getSetValues);
            }
            spinner_zone.setAdapter(zone_adapter);
            spinner_zone.setSelection(0);
        }
        zone_data.close();
    }

    //setting circle spinner
    private void populate_circle_spinner(String zone) {
        circle_list.clear();
        Cursor circle_data = databasehelper.getCircle_details(zone);
        if (circle_data.getCount() > 0) {
            while (circle_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setCode(functionCall.getCursorValue(circle_data, CIRCLE_ID));
                getSetValues.setName(functionCall.getCursorValue(circle_data, CIRCLE_NAME));
                circle_list.add(getSetValues);
            }
            spinner_circle.setAdapter(circle_adapter);
            spinner_circle.setSelection(0);
        }
        circle_data.close();
    }

    //setting division spinner
    private void populate_division_spinner(String circle) {
        division_list.clear();
        Cursor division_data = databasehelper.getDivision_details(circle);
        if (division_data.getCount() > 0) {
            while (division_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setCode(functionCall.getCursorValue(division_data, DIVISION_ID));
                getSetValues.setName(functionCall.getCursorValue(division_data, DIVISION_NAME));
                division_list.add(getSetValues);
            }
            spinner_division.setAdapter(division_adapter);
            spinner_division.setSelection(0);
        }
        division_data.close();
    }

    //setting subdivision spinner
    private void populate_subdivision_spinner(String division) {
        subdivision_list.clear();
        Cursor sub_division_data = databasehelper.getSubDivision_details(division);
        if (sub_division_data.getCount() > 0) {
            while (sub_division_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setCode(functionCall.getCursorValue(sub_division_data, SUBDIV_ID));
                getSetValues.setName(functionCall.getCursorValue(sub_division_data, SUBDIV_NAME));
                subdivision_list.add(getSetValues);
            }
            spinner_subdivision.setAdapter(subdivision_adapter);
            spinner_subdivision.setSelection(0);
        }
        sub_division_data.close();
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public void showYearDialog(final boolean val) {
        final Dialog d = new Dialog(DashBoard_Overall.this);
        d.setTitle("Year Picker");
        d.setContentView(R.layout.yeardialog);
        Button set = d.findViewById(R.id.button1);
        Button cancel = d.findViewById(R.id.button2);
        TextView year_text = d.findViewById(R.id.year_text);
        year_text.setText(String.valueOf(year));
        final NumberPicker nopicker = d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(year + 10);
        nopicker.setMinValue(year - 10);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(year);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (val)
                    from_edit.setText(String.valueOf(nopicker.getValue()));
                else to_edit.setText(String.valueOf(nopicker.getValue()));
                d.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();

    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            FROM_DATE = from_edit.getText().toString();
            TO_DATE = to_edit.getText().toString();
            if (from_edit.getText().toString().equals(to_edit.getText().toString())){
                getSet.setMonth_flag("Y");
            } else{
                getSet.setMonth_flag("N");
            }
            if (TextUtils.isEmpty(FROM_DATE)) {
                functionCall.showtoast(this, "Please select date");
                return;
            }
            functionCall.showprogressdialog("Please wait to complete", "Loading Data", progressDialog);
            VALUE = "from=" + FROM_DATE + "&to=" + TO_DATE + "&company=" + "500001" + "&zone=" + ZONE + "&circle=" + CIRCLE + "&division=" + DIVISION + "&tariff=" + "undefined"
                    + "&billed_status=" + BILLED_STATUS + "&acc_status=" + ACC_STATUS + "&pay_mode=" + PAY_STATUS + "&subDivision=" + SUB_DIVISION;
            sendingData.new BDA_Tariffwise_value(handler, arrayList, VALUE, getSet).execute();
        }
    }

    //------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("SetTextI18n")
    private void barChart1(ArrayList<Response> arrayList) {
        double total_instal1 = 0.0, total_open_bal1 = 0.0, total_cons_units1 = 0.0, total_demands1 = 0.0,
                total_net_pay1 = 0.0, total_coll_amt1 = 0.0, total_close_bal1 = 0.0;
        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(11f);
        l.setWordWrapEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(0f));


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(15f);
        leftAxis.setStartAtZero(false);  // t// this replaces setStartAtZero(true)// this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setStartAtZero(false); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv);


        chart.setDrawGridBackground(false);
        float groupSpace = 0.02f;
        float barSpace = 0.02f; // x7 DataSet
        float barWidth = 0.12f;
        int startYear, endYear;
        if(getSet.getMonth_flag().equals("Y")){
            tv_year.setText("MONTH");
            startYear = Integer.parseInt(arrayList.get(0).getMonth());
            endYear = Integer.parseInt(arrayList.get(arrayList.size()-1).getMonth())+1;
        }else {
            startYear = Integer.parseInt(arrayList.get(0).getA1());
            endYear = Integer.parseInt(arrayList.get(arrayList.size()-1).getA1())+1;
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
        set1 = new BarDataSet(values1, "Installation");
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
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(startYear);
        chart.getXAxis().setAxisMaximum(endYear);
        chart.groupBars(startYear, groupSpace, barSpace);
        chart.invalidate();

        for (int i = 0; i < arrayList.size(); i++) {
            total_instal1 = total_instal1 + Double.parseDouble(arrayList.get(i).getA2());
            total_open_bal1 = total_open_bal1 + Double.parseDouble(arrayList.get(i).getA3());
            total_cons_units1 = total_cons_units1 + Double.parseDouble(arrayList.get(i).getA4());
            total_demands1 = total_demands1 + Double.parseDouble(arrayList.get(i).getA5());
            total_net_pay1 = total_net_pay1 + Double.parseDouble(arrayList.get(i).getA6());
            total_coll_amt1 = total_coll_amt1 + Double.parseDouble(arrayList.get(i).getA7());
            total_close_bal1 = total_close_bal1 + Double.parseDouble(arrayList.get(i).getA8());
        }

        total_instal.setText(functionCall.roundoff1(String.valueOf(total_instal1)));
        total_open_bal.setText(functionCall.roundoff1(String.valueOf(total_open_bal1)));
        total_cons_units.setText(functionCall.roundoff1(String.valueOf(total_cons_units1)));
        total_demands.setText(functionCall.roundoff1(String.valueOf(total_demands1)));
        total_net_pay.setText(functionCall.roundoff1(String.valueOf(total_net_pay1)));
        total_coll_amt.setText(functionCall.roundoff1(String.valueOf(total_coll_amt1)));
        total_close_bal.setText(functionCall.roundoff1(String.valueOf(total_close_bal1)));

        report_adapter = new DashBoard_report_adapter(arrayList, DashBoard_Overall.this, getSet.getMonth_flag());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(report_adapter);

    }

    //--------------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("SetTextI18n")
    public void barChart(ArrayList<Response> arrayList){
        double total_instal1 = 0.0, total_open_bal1 = 0.0, total_cons_units1 = 0.0, total_demands1 = 0.0,
                total_net_pay1 = 0.0, total_coll_amt1 = 0.0, total_close_bal1 = 0.0;
        ArrayList<BarEntry> yaxis = new ArrayList<>();
        ArrayList<BarEntry> yaxis2 = new ArrayList<>();
        ArrayList<BarEntry> yaxis3 = new ArrayList<>();
        ArrayList<BarEntry> yaxis4 = new ArrayList<>();
        ArrayList<BarEntry> yaxis5 = new ArrayList<>();
        ArrayList<BarEntry> yaxis6 = new ArrayList<>();
        ArrayList<BarEntry> yaxis7 = new ArrayList<>();
        final ArrayList<String> xaxis = new ArrayList<>();

        for (int i = 0, j = 0; i < arrayList.size(); i++) {
            yaxis.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA2()))));
            yaxis2.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA3()))));
            yaxis3.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA4()))));
            yaxis4.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA5()))));
            yaxis5.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA6()))));
            yaxis6.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA7()))));
            yaxis7.add(new BarEntry(j++, Float.parseFloat(functionCall.roundoff1(arrayList.get(i).getA8()))));
        }

        for (int i = 0, j = 0; i < arrayList.size(); i++) {
            if(getSet.getMonth_flag().equals("N")){
                xaxis.add(i, arrayList.get(i).getA1());
            }else {
                tv_year.setText("MONTH");
                xaxis.add(i, arrayList.get(i).getMonth());
            }
        }

        //X axis plotting
        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelRotationAngle(0);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xaxis));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7);

        BarDataSet set1, set2, set3, set4, set5,set6, set7;
        set1 = new BarDataSet(yaxis, "INSTALLATIONS");
        set1.setColors(Color.BLUE);
        set2 = new BarDataSet(yaxis2, "CONSUMED UNITS");
        set2.setColors(Color.GRAY);
        set3 = new BarDataSet(yaxis3, "OPENING BALANCE");
        set3.setColors(Color.MAGENTA);
        set4 = new BarDataSet(yaxis4, "DEMAND");
        set4.setColors(Color.BLACK);
        set5 = new BarDataSet(yaxis5, "NET PAYABLE");
        set5.setColors(Color.RED);
        set6 = new BarDataSet(yaxis6, "COLLECTION AMOUNT");
        set6.setColors(Color.GREEN);
        set7 = new BarDataSet(yaxis7, "CLOSING BALANCE");
        set7.setColors(Color.YELLOW);

        BarData data = new BarData(set1,set2,set3,set4,set5,set6,set7);
        data.setValueFormatter(new LargeValueFormatter());
        data.setDrawValues(false);
        chart.setData(data);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawMarkers(false);
        chart.isDrawValueAboveBarEnabled();
        chart.setMaxVisibleValueCount(200);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView( chart); // For bounds control
        chart.setMarker(mv);

        //left axis
        YAxis leftAxis =  chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setStartAtZero(false);// this replaces setStartAtZero(true)

        //right axis
        YAxis rightAxis =  chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setStartAtZero(false);
        rightAxis.setEnabled(false);// this replaces setStartAtZero(true)

        //legend
        Legend l =  chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(5f);
        l.setWordWrapEnabled(true);

        chart.animateY(1000);
        chart.invalidate();
        chart.setExtraOffsets(0f, 10f, 0f, 10f);
        chart.setViewPortOffsets(0f, 0f, 0f, 0f);

        for (int i = 0; i < arrayList.size(); i++) {
            total_instal1 = total_instal1 + Double.parseDouble(arrayList.get(i).getA2());
            total_open_bal1 = total_open_bal1 + Double.parseDouble(arrayList.get(i).getA3());
            total_cons_units1 = total_cons_units1 + Double.parseDouble(arrayList.get(i).getA4());
            total_demands1 = total_demands1 + Double.parseDouble(arrayList.get(i).getA5());
            total_net_pay1 = total_net_pay1 + Double.parseDouble(arrayList.get(i).getA6());
            total_coll_amt1 = total_coll_amt1 + Double.parseDouble(arrayList.get(i).getA7());
            total_close_bal1 = total_close_bal1 + Double.parseDouble(arrayList.get(i).getA8());
        }

        total_instal.setText(functionCall.roundoff1(String.valueOf(total_instal1)));
        total_open_bal.setText(functionCall.roundoff1(String.valueOf(total_open_bal1)));
        total_cons_units.setText(functionCall.roundoff1(String.valueOf(total_cons_units1)));
        total_demands.setText(functionCall.roundoff1(String.valueOf(total_demands1)));
        total_net_pay.setText(functionCall.roundoff1(String.valueOf(total_net_pay1)));
        total_coll_amt.setText(functionCall.roundoff1(String.valueOf(total_coll_amt1)));
        total_close_bal.setText(functionCall.roundoff1(String.valueOf(total_close_bal1)));

        report_adapter = new DashBoard_report_adapter(arrayList, DashBoard_Overall.this, getSet.getMonth_flag());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(report_adapter);
    }
}
