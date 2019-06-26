package com.tvd.r_apdrpbigdata.Summarization.UnBilled_Summerization;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.adapters.RoleAdapter1;
import com.tvd.r_apdrpbigdata.database.Databasehelper;
import com.tvd.r_apdrpbigdata.invoke.SendingData;
import com.tvd.r_apdrpbigdata.models.Billing_Summarization_model;
import com.tvd.r_apdrpbigdata.values.FunctionCall;
import com.tvd.r_apdrpbigdata.values.GetSetValues;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.tvd.r_apdrpbigdata.values.Constant.BILL_SUMMARIZATION_DIALOG;
import static com.tvd.r_apdrpbigdata.values.Constant.CIRCLE_ID;
import static com.tvd.r_apdrpbigdata.values.Constant.COMPANY_ID;
import static com.tvd.r_apdrpbigdata.values.Constant.DIVISION_ID;
import static com.tvd.r_apdrpbigdata.values.Constant.SUBDIV_ID;
import static com.tvd.r_apdrpbigdata.values.Constant.TARIFF;
import static com.tvd.r_apdrpbigdata.values.Constant.UNBILLED_SUMMARIZATION_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.UNBILLED_SUMMARIZATION_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.ZONE_ID;

public class UnBilled_Summerization_Tariff extends AppCompatActivity  implements  AdapterView.OnItemSelectedListener{
    Spinner spinner_escom, spinner_zone, spinner_circle, spinner_division, spinner_subdivision, spinner_tariff;
    Databasehelper databasehelper;
    FunctionCall functionCall;
    ArrayList<GetSetValues> company_list, zone_list, circle_list, division_list, subdivision_list, tariff_list;
    RoleAdapter1 company_adapter, zone_adapter, circle_adapter, division_adapter, subdivision_adapter, tariff_adapter;
    String main_role = "";
    EditText from_edit, to_edit;
    int year,month,date;
    TextInputLayout to_date_textInputLayout;
    Button submit;
    GetSetValues getSet;
    RadioButton year_month, month_wise;
    String COMPANY="", ZONE="", CIRCLE="", DIVISION="", SUB_DIVISION="", MAIN_ROLE = "", TARIFF1="", FROM_DATE, TO_DATE;
    SendingData sendingData;
    ArrayList<Billing_Summarization_model> arrayList;
    ProgressDialog progressDialog;
    Boolean from_date = false;
    private android.support.v7.widget.Toolbar toolbar;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case UNBILLED_SUMMARIZATION_SUCCESS:
                    progressDialog.dismiss();
                    show_dialog(BILL_SUMMARIZATION_DIALOG);
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                    break;
                case UNBILLED_SUMMARIZATION_FAILURE:
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_LONG).show();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_billed_summerization_tariff);

        initialize();
        setspinner_values();

        from_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!from_date)
                    showYearDialog(true);
                else {
                    to_edit.setText("");
                    Calendar cal = Calendar.getInstance();
                    year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH);
                    date = cal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialog = new DatePickerDialog(UnBilled_Summerization_Tariff.this,
                            dateSetListener, year, month, date);


                    dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
                    dialog.show();
                }
            }
        });
        to_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showYearDialog(false);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!from_edit.getText().toString().isEmpty()) {
                    if (from_edit.getText().toString().equals(to_edit.getText().toString())){
                        getSet.setDates_equal("Y");
                        Log.d("debug","Date " + "Both Dates equals");
                    }
                    else{
                        getSet.setDates_equal("N");
                        Log.d("debug","Date " + "Both Dates different");
                    }


                    FROM_DATE = from_edit.getText().toString();
                    TO_DATE = to_edit.getText().toString();
                    if(ZONE.equals("") || ZONE.equals("SELECT"))
                        ZONE = "none";
                    if(CIRCLE.equals("") || CIRCLE.equals("SELECT"))
                        CIRCLE = "none";
                    if(DIVISION.equals("") || DIVISION.equals("SELECT"))
                        DIVISION = "none";
                    if(SUB_DIVISION.equals("") || SUB_DIVISION.equals("SELECT"))
                        SUB_DIVISION = "none";

                    String value = "from="+FROM_DATE+"&to="+TO_DATE+"&subDivision="+SUB_DIVISION+"&tariff="+TARIFF1+"&" +
                            "company=500001%20-%20Hubli%20Electricity%20Supply%20Company%20Limited&" +
                            "zone="+ZONE+"&circle="+CIRCLE+"&division="+DIVISION;
                    setProgressDialog(progressDialog, "Fetching Data", "Please Wait");
                        sendingData.new UnBilled_Summerization_Tariff(handler,arrayList,value,getSet).execute();
                }else {
                    Toast.makeText(getApplicationContext(),"Enter From Date",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void initialize() {
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("Unbilled Summarization Tariff");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        getSet = new GetSetValues();
        progressDialog = new ProgressDialog(this);
        arrayList = new ArrayList<>();
        sendingData = new SendingData();
        year = Calendar.getInstance().get(Calendar.YEAR);
        submit = findViewById(R.id.btn_submit1);

        functionCall = new FunctionCall();
        databasehelper = new Databasehelper(getApplicationContext());
        databasehelper.openDatabase();
        from_edit = findViewById(R.id.from_date);
        to_edit = findViewById(R.id.to_date);
        to_date_textInputLayout = findViewById(R.id.to_date_textInputLayout);

        year_month = findViewById(R.id.year_month_wise);
        year_month.setChecked(true);

        month_wise = findViewById(R.id.month_wise);

        year_month.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    to_date_textInputLayout.setVisibility(View.VISIBLE);
                    from_date = false;
                    Toast.makeText(getApplicationContext(), "year_month", Toast.LENGTH_LONG).show();
                }
            }
        });
        month_wise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    from_date = true;
                    to_date_textInputLayout.setVisibility(View.GONE);
                    TO_DATE = "";
                    from_edit.setText("");
                }
            }
        });

        spinner_escom = findViewById(R.id.spr_escom);
        spinner_zone = findViewById(R.id.spr_zone);
        spinner_circle = findViewById(R.id.spr_circle);
        spinner_division = findViewById(R.id.spr_division);
        spinner_subdivision = findViewById(R.id.spr_subdivision);
        spinner_tariff = findViewById(R.id.spr_tariff);


        spinner_escom.setOnItemSelectedListener(this);
        spinner_zone.setOnItemSelectedListener(this);
        spinner_circle.setOnItemSelectedListener(this);
        spinner_division.setOnItemSelectedListener(this);
        spinner_subdivision.setOnItemSelectedListener(this);
        spinner_tariff.setOnItemSelectedListener(this);


        company_list = new ArrayList<>();
        company_adapter = new RoleAdapter1(company_list, getApplicationContext());
        spinner_escom.setAdapter(company_adapter);

        zone_list = new ArrayList<>();
        zone_adapter = new RoleAdapter1(zone_list, getApplicationContext());
        spinner_zone.setAdapter(zone_adapter);

        circle_list = new ArrayList<>();
        circle_adapter = new RoleAdapter1(circle_list, getApplicationContext());
        spinner_circle.setAdapter(circle_adapter);

        division_list = new ArrayList<>();
        division_adapter = new RoleAdapter1(division_list, getApplicationContext());
        spinner_division.setAdapter(circle_adapter);

        subdivision_list = new ArrayList<>();
        subdivision_adapter = new RoleAdapter1(subdivision_list, getApplicationContext());
        spinner_subdivision.setAdapter(subdivision_adapter);

        tariff_list = new ArrayList<>();
        populate_tariff_spinner();
    }

    private void setProgressDialog(ProgressDialog progressDialog,String title,String message){
        progressDialog.setMessage(message);
        progressDialog.setTitle(title);
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
        GetSetValues role;
        switch (parent.getId()) {
            case R.id.spr_escom:
                role = company_list.get(i);
                COMPANY = role.getRole();
                populate_zone_spinner(COMPANY);
                break;
            case R.id.spr_zone:
                role = zone_list.get(i);
                ZONE = role.getRole();
                populate_circle_spinner(ZONE);
                break;
            case R.id.spr_circle:
                role = circle_list.get(i);
                CIRCLE = role.getRole();
                populate_division_spinner(CIRCLE);
                break;
            case R.id.spr_division:
                role = division_list.get(i);
                DIVISION = role.getRole();
                populate_subdivision_spinner(DIVISION);
                break;
            case R.id.spr_subdivision:
                role = subdivision_list.get(i);
                SUB_DIVISION = role.getRole();
                break;
            case R.id.spr_tariff:
                role = tariff_list.get(i);
                TARIFF1 = role.getRole();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setspinner_values() {
        //Setting company spinner
        company_list.clear();
        Cursor company_data = databasehelper.getCompany_details();
        if (company_data.getCount() > 0) {
            while (company_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setRole(functionCall.getCursorValue(company_data, COMPANY_ID));
                company_list.add(getSetValues);
            }
            spinner_escom.setAdapter(company_adapter);
            spinner_escom.setSelection(0);
        }
        company_data.close();

        //setting zone spinner
    }

    private void populate_zone_spinner(String company){
        zone_list.clear();
        Cursor zone_data = databasehelper.getZone_details(company);
        if (zone_data.getCount() > 0) {
            while (zone_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setRole(functionCall.getCursorValue(zone_data, ZONE_ID));
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
                getSetValues.setRole(functionCall.getCursorValue(circle_data, CIRCLE_ID));
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
                getSetValues.setRole(functionCall.getCursorValue(division_data, DIVISION_ID));
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
                getSetValues.setRole(functionCall.getCursorValue(sub_division_data, SUBDIV_ID));
                subdivision_list.add(getSetValues);
            }
            spinner_subdivision.setAdapter(subdivision_adapter);
            spinner_subdivision.setSelection(0);
        }
        sub_division_data.close();
    }

    private void populate_tariff_spinner() {
        tariff_list.clear();
        Cursor tariffDetails = databasehelper.getTariff_details();
        if (tariffDetails.getCount() > 0) {
            while (tariffDetails.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setRole(functionCall.getCursorValue(tariffDetails, TARIFF));
                tariff_list.add(getSetValues);
            }
            tariff_adapter = new RoleAdapter1(tariff_list, getApplicationContext());
            spinner_tariff.setAdapter(tariff_adapter);
            tariff_adapter.notifyDataSetChanged();
        }
        tariffDetails.close();
    }

    public void showYearDialog(final boolean val) {
        final Dialog d = new Dialog(UnBilled_Summerization_Tariff.this);
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
                if (val){
                    from_edit.setText(String.valueOf(nopicker.getValue()));
                    from_edit.setSelection(from_edit.getText().length());
                }
                else {
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

    public DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Date Starttime = null;
            from_edit.setText("");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            try {
                Starttime = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse((  year  + "-" + (monthOfYear + 1) + "-"  +dayOfMonth));
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

    private void show_dialog(int id) {
        switch (id) {
            case BILL_SUMMARIZATION_DIALOG:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("View Report")
                        .setCancelable(false)
                        .setPositiveButton("Chart", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(UnBilled_Summerization_Tariff.this, UnBilled_Summerization_Tariff_chart.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("test", arrayList);
                                bundle.putString("DATE_CHECK", getSet.getDates_equal());
                                bundle.putString("appbarTitle", "DashBoard_Tariff_Wise_Chart");
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Report", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(UnBilled_Summerization_Tariff.this, UnBilled_Summerization_Tariff_Report.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("test", arrayList);
                                bundle.putString("DATE_CHECK", getSet.getDates_equal());
                                bundle.putString("appbarTitle", "UnBilled_Summerization_Tariff_Report");
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                builder.setCancelable(true);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;
        }
    }
}
