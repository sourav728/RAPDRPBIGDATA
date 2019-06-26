package com.tvd.r_apdrpbigdata.Summarization.Abnormal_Subnormal_Summarization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.adapters.Abnormal_SubnormalAdapter;
import com.tvd.r_apdrpbigdata.models.Abnormal_Subnormal;

import java.util.ArrayList;

public class Abnormal_Summarization_Graph_Report extends AppCompatActivity {
    Abnormal_SubnormalAdapter abnormal_subnormalAdapter;
    RecyclerView recyclerView;
    ArrayList<Abnormal_Subnormal> myList = new ArrayList<>();
    Abnormal_Subnormal abnormal_subnormal_model;
    TextView txt_tot_billed, txt_tot_subnormal, txt_ad_sub_decider, txt_year_month_decider;
    double billed = 0, abnormal = 0;
    private String date_check_val = "", abnormal_subnormal_activity = "";
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormal__summarization__graph__report);
        myList = (ArrayList<Abnormal_Subnormal>) getIntent().getSerializableExtra("mylist");
        Intent intent = getIntent();
        date_check_val = intent.getStringExtra("DATE_CHECK");
        abnormal_subnormal_activity = intent.getStringExtra("ABNORMAL_SUBNORMAL");
        initialize();

        for (int i = 0; i < myList.size(); i++) {
            billed = billed + Double.parseDouble(myList.get(i).getBilled_installation());
            if (abnormal_subnormal_activity.equals("Abnormal")) {
                abnormal = abnormal + Double.parseDouble(myList.get(i).getAbnormal_installation());
                txt_ad_sub_decider.setText(R.string.abnormal_installation);
            } else {
                abnormal = abnormal + Double.parseDouble(myList.get(i).getSubnormal_installation());
                txt_ad_sub_decider.setText(R.string.subnormal_installation);
            }
        }
        if (date_check_val.equals("Y"))
            txt_year_month_decider.setText(R.string.transaction_month);
         else  txt_year_month_decider.setText(R.string.transaction_year);
        txt_tot_billed.setText(String.valueOf(billed));
        txt_tot_subnormal.setText(String.valueOf(abnormal));
    }

    private void initialize() {
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.action_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Abnormal Summarization Report");
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.textColorPrimary));

        txt_year_month_decider = findViewById(R.id.txt_year_month_decide);
        txt_ad_sub_decider = findViewById(R.id.txt_ab_sub);
        txt_tot_billed = findViewById(R.id.txt_tot_billed_installation);
        txt_tot_subnormal = findViewById(R.id.txt_tot_subnormal_installation);
        abnormal_subnormal_model = new Abnormal_Subnormal();
        recyclerView = findViewById(R.id.billing_summ_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        abnormal_subnormalAdapter = new Abnormal_SubnormalAdapter(myList, this, abnormal_subnormal_model, date_check_val, abnormal_subnormal_activity);
        recyclerView.setAdapter(abnormal_subnormalAdapter);
    }
}
