package com.tvd.r_apdrpbigdata.Summarization.Feeder_mapping_customer_Summarization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.adapters.CustomerSummarizationAdapter;
import com.tvd.r_apdrpbigdata.models.Customer_Summarization_model;

import java.util.ArrayList;

public class Customer_Summarization_TariffWise_report extends AppCompatActivity {

    CustomerSummarizationAdapter customerSummarizationAdapter;
    RecyclerView recyclerView;
    ArrayList<Customer_Summarization_model> myList = new ArrayList<>();
    Customer_Summarization_model customer_summarization_model;
    TextView txt_yearmonth, txt_tot_install;
    String date_check_val = "", single_month = "";
    double install = 0;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__summarization__tariff_wise_report);
        myList = (ArrayList<Customer_Summarization_model>) getIntent().getSerializableExtra("mylist");
        Intent intent = getIntent();
        date_check_val = intent.getStringExtra("DATE_CHECK");
        single_month = intent.getStringExtra("SINGLE_MONTH");
        Log.d("debug", "date_check_val in report " + date_check_val);
        initialize();
        for (int i = 0; i < myList.size(); i++) {
            install = install + Double.parseDouble(myList.get(i).getInstallation());
        }

        if (!single_month.equals("Y")) {
            if (date_check_val.equals("Y")) {
                txt_yearmonth.setText(R.string.transaction_month);
            } else {
                txt_yearmonth.setText(R.string.transaction_year);
            }
        } else {
            txt_yearmonth.setText("Tariff");
        }
        txt_tot_install.setText(String.valueOf(install));
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
        toolbar.setTitle("Customer Summarization report");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        txt_yearmonth = findViewById(R.id.txt_year_month);
        txt_tot_install = findViewById(R.id.txt_instal_total);
        customer_summarization_model = new Customer_Summarization_model();
        recyclerView = findViewById(R.id.billing_summ_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        customerSummarizationAdapter = new CustomerSummarizationAdapter(myList, this, customer_summarization_model, date_check_val);
        recyclerView.setAdapter(customerSummarizationAdapter);
    }
}
