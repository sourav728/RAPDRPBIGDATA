package com.tvd.r_apdrpbigdata.Summarization.Billing_Summarization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.adapters.BillingSummarizationTariffWiseAdapter;
import com.tvd.r_apdrpbigdata.models.Billing_Summarization_model;
import com.tvd.r_apdrpbigdata.values.FunctionCall;

import java.util.ArrayList;

public class BillingSummarization_Tariffwise_Report extends AppCompatActivity {
    BillingSummarizationTariffWiseAdapter billingSummarizationTariffWiseAdapter;
    RecyclerView recyclerView;
    ArrayList<Billing_Summarization_model> myList = new ArrayList<>();
    Billing_Summarization_model billing_summarization_model;
    String date_check_val = "";
    TextView txt_month, txt_install, txt_consumed, txt_current, txt_totmonth;
    double install = 0, consumed = 0, current = 0;
    private android.support.v7.widget.Toolbar toolbar;
    private FunctionCall functionCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_summarization__tariffwise__report);
        myList = (ArrayList<Billing_Summarization_model>) getIntent().getSerializableExtra("mylist");
        Intent intent = getIntent();
        date_check_val = intent.getStringExtra("DATE_CHECK");
        Log.d("debug", "date_check_val in report " + date_check_val);
        initialize();

        for (int i = 0; i < myList.size(); i++) {
            install = install + Double.parseDouble(myList.get(i).getInstallations());
            consumed = consumed + Double.parseDouble(myList.get(i).getConsumed_unit());
            current = current + Double.parseDouble(myList.get(i).getCurrent_bill_amt());
        }
        if (date_check_val.equals("Y")) {
            txt_month.setVisibility(View.VISIBLE);
            txt_totmonth.setVisibility(View.VISIBLE);
        } else {
            txt_month.setVisibility(View.GONE);
            txt_totmonth.setVisibility(View.GONE);
        }
        txt_install.setText(String.valueOf(functionCall.decimalroundoff(install)));
        txt_consumed.setText(String.valueOf(functionCall.decimalroundoff(consumed)));
        txt_current.setText(String.valueOf(functionCall.decimalroundoff(current)));
    }

    private void initialize() {
        functionCall = new FunctionCall();
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Billing Summarization Report");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        txt_install = findViewById(R.id.txt_tot_instal);
        txt_consumed = findViewById(R.id.txt_tot_consumed_unit);
        txt_current = findViewById(R.id.txt_tot_current_billed_amt);
        txt_month = findViewById(R.id.txt_month);
        txt_totmonth = findViewById(R.id.txt_tot_month);

        billing_summarization_model = new Billing_Summarization_model();
        recyclerView = findViewById(R.id.billing_summ_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        billingSummarizationTariffWiseAdapter = new BillingSummarizationTariffWiseAdapter(myList, this, billing_summarization_model, date_check_val);
        recyclerView.setAdapter(billingSummarizationTariffWiseAdapter);
    }

}
