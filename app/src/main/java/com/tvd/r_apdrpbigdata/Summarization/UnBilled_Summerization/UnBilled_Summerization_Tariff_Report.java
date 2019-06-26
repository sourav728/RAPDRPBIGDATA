package com.tvd.r_apdrpbigdata.Summarization.UnBilled_Summerization;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.adapters.UnBilled_Summ_adapter;
import com.tvd.r_apdrpbigdata.models.Billing_Summarization_model;
import com.tvd.r_apdrpbigdata.values.FunctionCall;

import java.util.ArrayList;

public class UnBilled_Summerization_Tariff_Report extends AppCompatActivity {

    String appbarTitle;
    ArrayList<Billing_Summarization_model> arrayList;
    RecyclerView recyclerView;
    TextView txt_total_instal;
    double total_instal1=0.0;
    FunctionCall functionCall = new FunctionCall();
    String date_check_val = "";
    TextView txt_month,txt_totmonth;
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_billed_summerization_tariff_report);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            arrayList = (ArrayList<Billing_Summarization_model>) extras.getSerializable("test");
            appbarTitle = extras.getString("appbarTitle");
            date_check_val = extras.getString("DATE_CHECK");
        }

        setTitle(appbarTitle);
        initialize();

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

        UnBilled_Summ_adapter adapter = new UnBilled_Summ_adapter(arrayList, UnBilled_Summerization_Tariff_Report.this,date_check_val);
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
        toolbar.setTitle("Unbilled Summarization Report");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        txt_month = findViewById(R.id.month);
        txt_totmonth = findViewById(R.id.total_month);
        txt_total_instal = findViewById(R.id.total_installation);
        recyclerView = findViewById(R.id.unbilled_summ_tariff_report);
    }
}
