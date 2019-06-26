package com.tvd.r_apdrpbigdata.Summarization.DepositSummarization;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.adapters.Deposit_Summarization_Report_Adapter;
import com.tvd.r_apdrpbigdata.models.Billing_Summarization_model;

import java.text.DecimalFormat;
import java.util.ArrayList;

@SuppressLint("Registered")
public class Deposit_Summarization_Report extends AppCompatActivity {
    String appbarTitle;
    ArrayList<Billing_Summarization_model> arrayList;
    RecyclerView recyclerView;
    TextView txt_total_instal,txt_total_demand_amt;
    double total_instal1=0.0,total_deposit_amt1=0.0;
    String date_check_val = "";
    TextView txt_month,txt_totmonth;
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_summarization_report);
        initialize();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            arrayList = (ArrayList<Billing_Summarization_model>) extras.getSerializable("test");
            appbarTitle = extras.getString("appbarTitle");
            date_check_val = extras.getString("DATE_CHECK");
        }

        if (date_check_val.equals("Y")) {
            txt_month.setVisibility(View.VISIBLE);
            txt_totmonth.setVisibility(View.VISIBLE);
        } else {
            txt_month.setVisibility(View.GONE);
            txt_totmonth.setVisibility(View.GONE);
        }

        setTitle(appbarTitle);

        for (int i = 0; i < arrayList.size(); i++) {
            total_instal1 = total_instal1 + Double.parseDouble(arrayList.get(i).getInstallations());
            total_deposit_amt1 = total_deposit_amt1 + Double.parseDouble(arrayList.get(i).getDeposit_amt());

        }

        txt_total_instal.setText(getdecimal(String.valueOf(total_instal1)));
        txt_total_demand_amt.setText(getdecimal(String.valueOf(total_deposit_amt1)));

        Deposit_Summarization_Report_Adapter adapter = new Deposit_Summarization_Report_Adapter(arrayList,Deposit_Summarization_Report.this,date_check_val);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private String getdecimal(String value) {
        DecimalFormat num = new DecimalFormat("##0.00");
        return num.format(Double.parseDouble(value));
    }

    private void initialize(){
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("Deposit Summarization Report");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));
        txt_month = findViewById(R.id.month);
        txt_totmonth = findViewById(R.id.total_month);
        txt_total_instal = findViewById(R.id.total_installation);
        txt_total_demand_amt = findViewById(R.id.total_deposit_amount);
        recyclerView = findViewById(R.id.deposit_summ_dashboard_report);
    }
}
