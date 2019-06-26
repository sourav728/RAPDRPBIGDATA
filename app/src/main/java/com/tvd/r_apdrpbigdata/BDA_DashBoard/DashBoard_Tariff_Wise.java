package com.tvd.r_apdrpbigdata.BDA_DashBoard;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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

public class DashBoard_Tariff_Wise extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner spinner_escom, spinner_zone, spinner_circle, spinner_division, spinner_subdivision, spinner_tariff,
            spinner_account, spinner_billed, spinner_payment;
    Databasehelper databasehelper;
    FunctionCall functionCall;
    ArrayList<GetSetValues> company_list, zone_list, circle_list, division_list, subdivision_list, tariff_list, account_list, billed_list, payment_list;
    RoleAdapter company_adapter, zone_adapter, circle_adapter, division_adapter, subdivision_adapter, tariff_adapter, account_adapter,
            billed_status_adapter, payment_status_adapter;
    EditText from_edit, to_edit;
    int year, month, date;
    TextInputLayout to_date_textInputLayout;
    Button submit;
    GetSetValues getSet;
    RadioButton year_month, month_wise;
    String COMPANY = "500001%20-%20Hubli%20Electricity%20Supply%20Company%20Limited",
            ZONE = "none", CIRCLE = "none", DIVISION = "none", SUB_DIVISION = "none", TARIFF = "All", ACC_STATUS = "All",
            BILLED_STATUS = "All", PAY_STATUS = "All", FROM_DATE = "", TO_DATE = "", VALUE = "";
    SendingData sendingData;
    ArrayList<Response> arrayList;
    ProgressDialog progressDialog;
    Boolean from_date = false;
    private boolean userIsInteracting;
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
                    functionCall.showtoast(DashBoard_Tariff_Wise.this, "No Data found");
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board__tariff__wise);

        initialize();
        setspinner_values();
        populate_tariff_spinner();


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
                if (!from_date)
                    showYearDialog(true);
                else {
                    to_edit.setText("");
                    Calendar cal = Calendar.getInstance();
                    year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH);
                    date = cal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialog = new DatePickerDialog(DashBoard_Tariff_Wise.this,
                            dateSetListener, year, month, date);


                    dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
                    dialog.show();
                }
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

    public DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Date Starttime = null;
            from_edit.setText("");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            try {
                Starttime = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse((year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dateselected = sdf.format(Starttime);
            Log.d("date", dateselected);
            from_edit.setText(dateselected);
            from_edit.setSelection(from_edit.getText().length());
            FROM_DATE = from_edit.getText().toString();
        }
    };


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    private void initialize() {
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("DashBoard TariffWise");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        getSet = new GetSetValues();
        progressDialog = new ProgressDialog(this);
        arrayList = new ArrayList<>();
        sendingData = new SendingData();
        year = Calendar.getInstance().get(Calendar.YEAR);
        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(this);

        functionCall = new FunctionCall();
        databasehelper = new Databasehelper(getApplicationContext());
        databasehelper.openDatabase();
        from_edit = findViewById(R.id.from_date);
        to_edit = findViewById(R.id.to_date);
        to_date_textInputLayout = findViewById(R.id.to_date_textInputLayout);

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

        year_month = findViewById(R.id.year_month_wise);
        year_month.setChecked(true);
        month_wise = findViewById(R.id.month_wise);

        year_month.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    to_date_textInputLayout.setVisibility(View.VISIBLE);
                    from_date = false;
                }
            }
        });
        month_wise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    from_date = true;
                    tv_year.setText("TARIFF");
                    lin1.setVisibility(View.GONE);
                    lin2.setVisibility(View.GONE);
                    to_date_textInputLayout.setVisibility(View.GONE);
                    TO_DATE = "";
                    from_edit.setText("");
                }
            }
        });

        spinner_escom = findViewById(R.id.company);
        spinner_zone = findViewById(R.id.zone);
        spinner_circle = findViewById(R.id.circle);
        spinner_division = findViewById(R.id.division);
        spinner_subdivision = findViewById(R.id.subdivision);
        spinner_tariff = findViewById(R.id.tariff);
        spinner_account = findViewById(R.id.account_status);
        spinner_billed = findViewById(R.id.billed_status);
        spinner_payment = findViewById(R.id.payment_status);

        spinner_escom.setOnItemSelectedListener(this);
        spinner_zone.setOnItemSelectedListener(this);
        spinner_circle.setOnItemSelectedListener(this);
        spinner_division.setOnItemSelectedListener(this);
        spinner_subdivision.setOnItemSelectedListener(this);
        spinner_tariff.setOnItemSelectedListener(this);
        spinner_account.setOnItemSelectedListener(this);
        spinner_billed.setOnItemSelectedListener(this);
        spinner_payment.setOnItemSelectedListener(this);


        circle_list = new ArrayList<>();
        circle_adapter = new RoleAdapter(circle_list, getApplicationContext());
        spinner_circle.setAdapter(circle_adapter);

        division_list = new ArrayList<>();
        division_adapter = new RoleAdapter(division_list, getApplicationContext());
        spinner_division.setAdapter(circle_adapter);

        subdivision_list = new ArrayList<>();
        subdivision_adapter = new RoleAdapter(subdivision_list, getApplicationContext());
        spinner_subdivision.setAdapter(subdivision_adapter);

        tariff_list = new ArrayList<>();
        account_list = new ArrayList<>();
        account_adapter = new RoleAdapter(account_list, getApplicationContext());
        spinner_account.setAdapter(account_adapter);

        billed_list = new ArrayList<>();
        billed_status_adapter = new RoleAdapter(billed_list, getApplicationContext());
        spinner_billed.setAdapter(billed_status_adapter);

        payment_list = new ArrayList<>();
        payment_status_adapter = new RoleAdapter(payment_list, getApplicationContext());
        spinner_payment.setAdapter(payment_status_adapter);

        report_adapter = new DashBoard_report_adapter(arrayList, DashBoard_Tariff_Wise.this, getSet.getMonth_flag());
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
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
                    role = subdivision_list.get(i);
                    SUB_DIVISION = role.getCode();
                }
                break;

            case R.id.tariff:
                if (userIsInteracting) {
                    role = tariff_list.get(i);
                    TARIFF = role.getCode();
                }
                break;

            case R.id.account_status:
                if (userIsInteracting) {
                    role = account_list.get(i);
                    ACC_STATUS = role.getCode();
                }
                break;

            case R.id.billed_status:
                if (userIsInteracting) {
                    role = billed_list.get(i);
                    BILLED_STATUS = role.getCode();
                }
                break;

            case R.id.payment_status:
                if (userIsInteracting) {
                    role = payment_list.get(i);
                    PAY_STATUS = role.getCode();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //setting company spinner
    private void setspinner_values() {
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
            spinner_escom.setAdapter(company_adapter);
            company_adapter.notifyDataSetChanged();
            spinner_escom.setSelection(0);
        }
        company_data.close();

    }

    //setting zone spinner
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
            spinner_zone.setAdapter(zone_adapter);
            zone_adapter.notifyDataSetChanged();
            userIsInteracting = false;
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
            userIsInteracting = false;
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
            userIsInteracting = false;
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
            userIsInteracting = false;
            spinner_subdivision.setSelection(0);
        }
        sub_division_data.close();
    }

    //setting tariff spinner
    private void populate_tariff_spinner() {
        tariff_list.clear();
        Cursor tariffDetails = databasehelper.getTariff_details();
        if (tariffDetails.getCount() > 0) {
            while (tariffDetails.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setCode(functionCall.getCursorValue(tariffDetails, TARIFF_NAME));
                tariff_list.add(getSetValues);
            }
            tariff_adapter = new RoleAdapter(tariff_list, getApplicationContext());
            spinner_tariff.setAdapter(tariff_adapter);
            tariff_adapter.notifyDataSetChanged();
            userIsInteracting = false;
        }
        tariffDetails.close();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("SetTextI18n")
    public void showYearDialog(final boolean val) {
        final Dialog d = new Dialog(DashBoard_Tariff_Wise.this);
        d.setTitle("Year Picker");
        d.setContentView(R.layout.yeardialog);
        Button set = d.findViewById(R.id.button1);
        Button cancel = d.findViewById(R.id.button2);
        TextView year_text = d.findViewById(R.id.year_text);
        year_text.setText("" + year);
        final NumberPicker nopicker = d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(year + 10);
        nopicker.setMinValue(year - 10);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(year);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (val) {
                    from_edit.setText(String.valueOf(nopicker.getValue()));
                    from_edit.setSelection(from_edit.getText().length());
                } else {
                    to_edit.setText(String.valueOf(nopicker.getValue()));
                    to_edit.setSelection(to_edit.getText().length());
                }
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

    //----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        FROM_DATE = from_edit.getText().toString();
        TO_DATE = to_edit.getText().toString();
        if (v.getId() == R.id.btn_submit) {
            if (from_edit.getText().toString().equals(to_edit.getText().toString())) {
                getSet.setMonth_flag("Y");
            } else {
                getSet.setMonth_flag("N");
            }
            if (TextUtils.isEmpty(FROM_DATE)) {
                functionCall.showtoast(this, "Please select date");
                return;
            }
            functionCall.showprogressdialog("Please wait to complete", "Loading Data", progressDialog);
            VALUE = "subDivision=" + SUB_DIVISION + "&acc_status=" + ACC_STATUS + "&from=" + FROM_DATE + "&to=" + TO_DATE + "&company=" +
                    "500001%20-%20Hubli%20Electricity%20Supply%20Company%20Limited&zone=" + ZONE + "&circle=" + CIRCLE + "&division=" + DIVISION + "&" +
                    "tariff=" + TARIFF + "&billed_status=" + BILLED_STATUS + "&pay_mode=" + PAY_STATUS + "";
            sendingData.new BDA_Tariffwise_value(handler, arrayList, VALUE, getSet).
                    execute();
        }
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

        report_adapter = new DashBoard_report_adapter(arrayList, DashBoard_Tariff_Wise.this, getSet.getMonth_flag());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(report_adapter);
    }
}