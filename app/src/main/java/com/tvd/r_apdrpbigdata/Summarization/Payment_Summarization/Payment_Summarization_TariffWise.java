package com.tvd.r_apdrpbigdata.Summarization.Payment_Summarization;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.adapters.RoleAdapter1;
import com.tvd.r_apdrpbigdata.database.Databasehelper;
import com.tvd.r_apdrpbigdata.invoke.SendingData;
import com.tvd.r_apdrpbigdata.models.Payment_Summarization_model;
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
import static com.tvd.r_apdrpbigdata.values.Constant.PAYMENT_SUMMARIZATION_TARIFF_WISE_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.PAYMENT_SUMMARIZATION_TARIFF_WISE_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.STATUS;
import static com.tvd.r_apdrpbigdata.values.Constant.SUBDIV_ID;
import static com.tvd.r_apdrpbigdata.values.Constant.TARIFF;
import static com.tvd.r_apdrpbigdata.values.Constant.ZONE_ID;

public class Payment_Summarization_TariffWise extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button submit;
    Spinner spinner_escom, spinner_zone, spinner_circle, spinner_division, spinner_subdivision, spinner_tariff, spinner_acc_status, spinner_billed_status;
    Databasehelper databasehelper;
    FunctionCall functionCall;
    ArrayList<GetSetValues> company_list, zone_list, circle_list, division_list, subdivision_list, tariff_list, acc_status_list, billed_status_list;
    RoleAdapter1 company_adapter, zone_adapter, circle_adapter, division_adapter, subdivision_adapter, tariff_adapter, acc_status_adapter, billed_status_adapter;
    String main_role = "";
    EditText from_edit, to_edit;
    ArrayList<Payment_Summarization_model> payment_summarization_arraylist;
    SendingData sendingData;
    LinearLayout root_layout;
    String company = "500001 - Hubli Electricity Supply Company Limited", COMPANY = "", zone = "", circle = "", division = "", sub_division = "", tariff = "", acc_status = "", billed_status = "";
    GetSetValues getSetValues;
    ProgressDialog progressDialog;
    private android.support.v7.widget.Toolbar toolbar;
    int year, month, date;
    boolean from_date = false;
    TextInputLayout to_date_textInputLayout;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case PAYMENT_SUMMARIZATION_TARIFF_WISE_SUCCESS:
                    progressDialog.dismiss();
                    Toast.makeText(Payment_Summarization_TariffWise.this, "Success..", Toast.LENGTH_SHORT).show();
                    show_dialog(BILL_SUMMARIZATION_DIALOG);
                    break;
                case PAYMENT_SUMMARIZATION_TARIFF_WISE_FAILURE:
                    progressDialog.dismiss();
                    Toast.makeText(Payment_Summarization_TariffWise.this, "Failure!!", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__summarization__yearwise);
        initialize();
        setspinner_values();
        from_edit.setOnClickListener(view -> {
            if (!from_date) {
                Log.d("debug", "from_date " + from_date);
                showYearDialog(true);
            } else {
                getSetValues.setSingle_month("Y");
                Log.d("debug", "from_date " + from_date);
                to_edit.setText("");
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                date = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Payment_Summarization_TariffWise.this,
                        dateSetListener, year, month, date);
                dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
                dialog.show();

            }
        });
        to_edit.setOnClickListener(view -> showYearDialog(false));
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
        }
    };

    public void initialize() {
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Payment Summarization TariffWise");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        progressDialog = new ProgressDialog(this);
        getSetValues = new GetSetValues();
        root_layout = findViewById(R.id.lin_layout);
        year = Calendar.getInstance().get(Calendar.YEAR);
        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(this);
        functionCall = new FunctionCall();
        databasehelper = new Databasehelper(getApplicationContext());
        databasehelper.openDatabase();
        payment_summarization_arraylist = new ArrayList<>();
        from_edit = findViewById(R.id.from_date);
        to_edit = findViewById(R.id.to_date);
        to_date_textInputLayout = findViewById(R.id.to_date_textInputLayout);

        spinner_escom = findViewById(R.id.spr_escom);
        spinner_zone = findViewById(R.id.spr_zone);
        spinner_circle = findViewById(R.id.spr_circle);
        spinner_division = findViewById(R.id.spr_division);
        spinner_subdivision = findViewById(R.id.spr_subdivision);
        spinner_acc_status = findViewById(R.id.spr_accountstatus);
        spinner_billed_status = findViewById(R.id.spr_billedstatus);
        spinner_tariff = findViewById(R.id.spr_tariff);

        spinner_escom.setOnItemSelectedListener(this);
        spinner_zone.setOnItemSelectedListener(this);
        spinner_circle.setOnItemSelectedListener(this);
        spinner_division.setOnItemSelectedListener(this);
        spinner_subdivision.setOnItemSelectedListener(this);
        spinner_acc_status.setOnItemSelectedListener(this);
        spinner_billed_status.setOnItemSelectedListener(this);
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

        acc_status_list = new ArrayList<>();
        acc_status_adapter = new RoleAdapter1(acc_status_list, getApplicationContext());
        spinner_acc_status.setAdapter(acc_status_adapter);

        billed_status_list = new ArrayList<>();
        billed_status_adapter = new RoleAdapter1(billed_status_list, getApplicationContext());
        spinner_billed_status.setAdapter(billed_status_adapter);

        tariff_list = new ArrayList<>();
        tariff_adapter = new RoleAdapter1(tariff_list, getApplicationContext());
        spinner_tariff.setAdapter(tariff_adapter);

        final RadioGroup group = findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.year_month_wise:
                        from_date = false;
                        to_date_textInputLayout.setVisibility(View.VISIBLE);
                        from_edit.setText("");
                        Toast.makeText(getApplicationContext(), "year_month", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.month_wise:
                        from_date = true;
                        to_date_textInputLayout.setVisibility(View.GONE);
                        from_edit.setText("");
                        break;

                }
            }
        });

        sendingData = new SendingData();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_submit) {
            call_api();
        }
    }

    private void show_dialog(int id) {
        if (id == BILL_SUMMARIZATION_DIALOG) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("View Report")
                    .setCancelable(false)
                    .setPositiveButton("Chart", (dialog, id1) -> {
                        Intent intent = new Intent(Payment_Summarization_TariffWise.this, Payment_Summarization_Chart.class);
                        intent.putExtra("SINGLE_MONTH",getSetValues.getSingle_month());
                        intent.putExtra("DATE_CHECK", getSetValues.getDates_equal());
                        intent.putExtra("mylist", payment_summarization_arraylist);
                        startActivity(intent);
                    })
                    .setNegativeButton("Report", (dialog, id12) -> {
                        Intent intent = new Intent(Payment_Summarization_TariffWise.this, Payment_Summarization_Overall_Report.class);
                        intent.putExtra("SINGLE_MONTH",getSetValues.getSingle_month());
                        intent.putExtra("DATE_CHECK", getSetValues.getDates_equal());
                        intent.putExtra("mylist", payment_summarization_arraylist);
                        startActivity(intent);
                    })
                    .setNeutralButton("Cancel", (dialog, which) -> builder.setCancelable(true));
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();
        GetSetValues role;
        switch (id) {
            case R.id.spr_escom:
                role = company_list.get(i);
                COMPANY = role.getRole();
                populate_zone_spinner(COMPANY);
            case R.id.spr_zone:
                role = zone_list.get(i);
                main_role = role.getRole();
                zone = main_role;
                populate_circle_spinner(main_role);
                break;
            case R.id.spr_circle:
                role = circle_list.get(i);
                main_role = role.getRole();
                circle = main_role;
                populate_division_spinner(main_role);
                break;
            case R.id.spr_division:
                role = division_list.get(i);
                main_role = role.getRole();
                division = main_role;
                populate_subdivision_spinner(main_role);
                break;
            case R.id.spr_subdivision:
                role = subdivision_list.get(i);
                main_role = role.getRole();
                sub_division = main_role;
                populate_tariff_spinner();
                break;
            case R.id.spr_tariff:
                role = tariff_list.get(i);
                main_role = role.getRole();
                tariff = main_role;
                populate_accountstatus_spinner();
                break;
            case R.id.spr_accountstatus:
                role = acc_status_list.get(i);
                main_role = role.getRole();
                acc_status = main_role;
                populate_billed_status_spinner();
                break;
            case R.id.spr_billedstatus:
                role = billed_status_list.get(i);
                main_role = role.getRole();
                billed_status = main_role;
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

    private void populate_zone_spinner(String company) {
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

    //setting tariff_spinner
    private void populate_tariff_spinner() {
        tariff_list.clear();
        Cursor tariff_data = databasehelper.getTariff_details();
        if (tariff_data.getCount() > 0) {
            while (tariff_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setRole(functionCall.getCursorValue(tariff_data, TARIFF));
                tariff_list.add(getSetValues);
            }
            spinner_tariff.setAdapter(tariff_adapter);
            spinner_tariff.setSelection(0);
        }
        tariff_data.close();
    }

    //setting acc_status_spinner
    private void populate_accountstatus_spinner() {
        acc_status_list.clear();
        Cursor acc_status_data = databasehelper.getAccstatus_details();
        if (acc_status_data.getCount() > 0) {
            while (acc_status_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setRole(functionCall.getCursorValue(acc_status_data, STATUS));
                acc_status_list.add(getSetValues);
            }
            spinner_acc_status.setAdapter(acc_status_adapter);
            spinner_acc_status.setSelection(0);
        }
        acc_status_data.close();
    }

    //setting billed_status_spinner
    private void populate_billed_status_spinner() {
        billed_status_list.clear();
        Cursor billed_status_data = databasehelper.getBilledstatus_details();
        if (billed_status_data.getCount() > 0) {
            while (billed_status_data.moveToNext()) {
                GetSetValues getSetValues = new GetSetValues();
                getSetValues.setRole(functionCall.getCursorValue(billed_status_data, STATUS));
                billed_status_list.add(getSetValues);
            }
            spinner_billed_status.setAdapter(billed_status_adapter);
            spinner_billed_status.setSelection(0);
        }
        billed_status_data.close();
    }

    @SuppressLint("SetTextI18n")
    public void showYearDialog(final boolean val) {
        final Dialog d = new Dialog(Payment_Summarization_TariffWise.this);
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

    private void call_api() {
        if (from_edit.getText().toString().equals(to_edit.getText().toString())) {
            getSetValues.setDates_equal("Y");
            Log.d("debug", "Date " + "Both Dates equals");
        } else {
            getSetValues.setDates_equal("N");
            Log.d("debug", "Date " + "Both Dates different");
        }

        if (!TextUtils.isEmpty(company)) {
            if (!TextUtils.isEmpty(zone)) {
                if (!TextUtils.isEmpty(circle)) {
                    if (!TextUtils.isEmpty(division)) {
                        if (!TextUtils.isEmpty(sub_division)) {
                            if (!TextUtils.isEmpty(tariff)) {
                                if (!TextUtils.isEmpty(from_edit.getText().toString())) {
                                    if (!TextUtils.isEmpty(acc_status)) {
                                        if (!TextUtils.isEmpty(billed_status)) {
                                            functionCall.setProgressDialog(progressDialog, "Fetching Data", "Please Wait");
                                            SendingData.Get_payment_summarization_tariffwise_value get_payment_summarization_tariffwise_value = sendingData.new Get_payment_summarization_tariffwise_value(handler, payment_summarization_arraylist, getSetValues);
                                            get_payment_summarization_tariffwise_value.execute(from_edit.getText().toString(), to_edit.getText().toString(), sub_division, tariff, company, zone, circle, division, acc_status, billed_status);
                                        } else
                                            functionCall.setSnackBar(Payment_Summarization_TariffWise.this, root_layout, "Please select Billed_Status!!");
                                    } else
                                        functionCall.setSnackBar(Payment_Summarization_TariffWise.this, root_layout, "Please select Acc_Status!!");
                                } else
                                    functionCall.setSnackBar(Payment_Summarization_TariffWise.this, root_layout, "Please select From Date!!");
                            } else
                                functionCall.setSnackBar(Payment_Summarization_TariffWise.this, root_layout, "Please select Tariff!!");
                        } else
                            functionCall.setSnackBar(Payment_Summarization_TariffWise.this, root_layout, "Please select Sub Division!!");
                    } else
                        functionCall.setSnackBar(Payment_Summarization_TariffWise.this, root_layout, "Please select Division!!");
                } else
                    functionCall.setSnackBar(Payment_Summarization_TariffWise.this, root_layout, "Please select Circle!!");
            } else
                functionCall.setSnackBar(Payment_Summarization_TariffWise.this, root_layout, "Please select Zone!!");
        } else
            functionCall.setSnackBar(Payment_Summarization_TariffWise.this, root_layout, "Please select Escom!!");
    }

}
