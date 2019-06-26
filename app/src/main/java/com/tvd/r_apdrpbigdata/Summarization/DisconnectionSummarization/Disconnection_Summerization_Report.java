package com.tvd.r_apdrpbigdata.Summarization.DisconnectionSummarization;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.adapters.Disconnection_summ_report_adapter;
import com.tvd.r_apdrpbigdata.models.Billing_Summarization_model;
import com.tvd.r_apdrpbigdata.values.FunctionCall;

import java.util.ArrayList;

@SuppressLint("Registered")
public class Disconnection_Summerization_Report extends AppCompatActivity {
    String appbarTitle;
    ArrayList<Billing_Summarization_model> arrayList;
    RecyclerView recyclerView;
    TextView txt_total_instal,txt_total_connection_type;
    double total_instal1=0.0;
    FunctionCall functionCall = new FunctionCall();
    String date_check_val = "";
    TextView txt_month,txt_totmonth;
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disconnection_summerization_report);
        initialize();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            arrayList = (ArrayList<Billing_Summarization_model>) extras.getSerializable("test");
            appbarTitle = extras.getString("appbarTitle");
            date_check_val = extras.getString("DATE_CHECK");

        }

        setTitle(appbarTitle);

        if (date_check_val.equals("Y")) {
            txt_month.setVisibility(View.VISIBLE);
            txt_totmonth.setVisibility(View.VISIBLE);
        } else {
            txt_month.setVisibility(View.GONE);
            txt_totmonth.setVisibility(View.GONE);
        }

        for (int i = 0; i < arrayList.size(); i++) {
            total_instal1 = total_instal1 + Double.parseDouble(arrayList.get(i).getInstallations());
        }

        txt_total_instal.setText(functionCall.decimalroundoff(Double.parseDouble(String.valueOf(total_instal1))));
        txt_total_connection_type.setText("---");

        Disconnection_summ_report_adapter adapter = new Disconnection_summ_report_adapter(arrayList,Disconnection_Summerization_Report.this,date_check_val);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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
        toolbar.setTitle("Disconnection Summarization Report");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        txt_month = findViewById(R.id.month);
        txt_totmonth = findViewById(R.id.month1);
        txt_total_instal = findViewById(R.id.total_installation);
        txt_total_connection_type = findViewById(R.id.total_disconnection_type);
        recyclerView = findViewById(R.id.disconnection_summ_report);
    }
}
