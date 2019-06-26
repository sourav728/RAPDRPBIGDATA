package com.tvd.r_apdrpbigdata.Summarization.Payment_Summarization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.adapters.PaymentSummarizationAdapter;
import com.tvd.r_apdrpbigdata.models.Payment_Summarization_model;
import com.tvd.r_apdrpbigdata.values.FunctionCall;

import java.util.ArrayList;

public class Payment_Summarization_Overall_Report extends AppCompatActivity {
    PaymentSummarizationAdapter paymentSummarizationAdapter;
    RecyclerView recyclerView;
    ArrayList<Payment_Summarization_model> myList = new ArrayList<>();
    Payment_Summarization_model payment_summarization_model;
    TextView txt_month_sow_hide,txt_tot_month,txt_tot_install, txt_tot_opening_bls, txt_tot_con_unit, txt_tot_demand, txt_tot_net_amt, txt_tot_col_amt, txt_tot_clos_amt;
    double install=0, opening_bls=0,con_unit=0,demand=0,net_amt=0,col_amt=0,clos_amt=0;
    String date_check_val = "";
    FunctionCall functionCall;
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__summarization__overall__report);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("Payment Summarization Report");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        Intent intent = getIntent();
        date_check_val = intent.getStringExtra("DATE_CHECK");
        myList = (ArrayList<Payment_Summarization_model>) getIntent().getSerializableExtra("mylist");
        initialize();

        for (int i=0;i < myList.size(); i++)
        {
            install = install + Double.parseDouble(myList.get(i).getInstallations());
            opening_bls = opening_bls + Double.parseDouble(myList.get(i).getOpening_balance());
            con_unit = con_unit + Double.parseDouble(myList.get(i).getConsumed_unit());
            demand = demand + Double.parseDouble(myList.get(i).getDemand());
            net_amt = net_amt + Double.parseDouble(myList.get(i).getNet_pay_amount());
            col_amt = col_amt + Double.parseDouble(myList.get(i).getCollection_amt());
            clos_amt = clos_amt + Double.parseDouble(myList.get(i).getClosing_balance_amt());
        }
        if (date_check_val.equals("Y")) {
            txt_tot_month.setVisibility(View.VISIBLE);
            txt_month_sow_hide.setVisibility(View.VISIBLE);
        } else {
            txt_tot_month.setVisibility(View.GONE);
            txt_month_sow_hide.setVisibility(View.GONE);
        }

        txt_tot_install.setText(String.valueOf(functionCall.decimalroundoff(install)));
        txt_tot_opening_bls.setText(String.valueOf(functionCall.decimalroundoff(opening_bls)));
        txt_tot_con_unit.setText(String.valueOf(functionCall.decimalroundoff(con_unit)));
        txt_tot_demand.setText(String.valueOf(functionCall.decimalroundoff(demand)));
        txt_tot_net_amt.setText(String.valueOf(functionCall.decimalroundoff(net_amt)));
        txt_tot_col_amt.setText(String.valueOf(functionCall.decimalroundoff(col_amt)));
        txt_tot_clos_amt.setText(String.valueOf(functionCall.decimalroundoff(clos_amt)));
    }

    private void initialize() {
        functionCall = new FunctionCall();
        txt_month_sow_hide = findViewById(R.id.txt_tot_month);
        txt_tot_month = findViewById(R.id.txt_month);
        txt_tot_install = findViewById(R.id.txt_tot_install);
        txt_tot_opening_bls = findViewById(R.id.txt_tot_opening_bls);
        txt_tot_con_unit = findViewById(R.id.txt_tot_con_unit);
        txt_tot_demand = findViewById(R.id.txt_tot_demand);
        txt_tot_net_amt = findViewById(R.id.txt_tot_net_amt);
        txt_tot_col_amt = findViewById(R.id.txt_tot_coll_amt);
        txt_tot_clos_amt = findViewById(R.id.txt_tot_closing_amt);

        payment_summarization_model = new Payment_Summarization_model();
        recyclerView = findViewById(R.id.payment_summ_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        paymentSummarizationAdapter = new PaymentSummarizationAdapter(myList, this, payment_summarization_model,date_check_val);
        recyclerView.setAdapter(paymentSummarizationAdapter);
    }
}
