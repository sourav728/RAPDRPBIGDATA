package com.tvd.r_apdrpbigdata.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.models.Billing_Summarization_model;

import java.util.ArrayList;

public class BillingSummarizationTariffWiseAdapter extends RecyclerView.Adapter<BillingSummarizationTariffWiseAdapter.BillingViewHolder> {
    private ArrayList<Billing_Summarization_model> arrayList;
    private Context context;
    private Billing_Summarization_model billing_summarization_model;
    private String date_check;

    public BillingSummarizationTariffWiseAdapter(ArrayList<Billing_Summarization_model> arrayList, Context context, Billing_Summarization_model billing_summarization_model, String date_check) {
        this.arrayList = arrayList;
        this.context = context;
        this.billing_summarization_model = billing_summarization_model;
        this.date_check = date_check;
    }

    @NonNull
    @Override
    public BillingSummarizationTariffWiseAdapter.BillingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.billing_summarization_tariffwise_adapter, viewGroup, false);
        return new BillingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillingSummarizationTariffWiseAdapter.BillingViewHolder billingViewHolder, int position) {
        Billing_Summarization_model billing_summarization_model = arrayList.get(position);
        billingViewHolder.txt_year.setText(billing_summarization_model.getYear());
        if (date_check.equals("Y")) {
            Log.d("debug", "Billing_Date Equals " + date_check);
            billingViewHolder.txt_month.setVisibility(View.VISIBLE);
            billingViewHolder.txt_month.setText(billing_summarization_model.getMonth());
        } else {
            Log.d("debug", "Billing_Date Equals " + date_check);
            billingViewHolder.txt_month.setVisibility(View.GONE);
        }
        billingViewHolder.txt_install.setText(billing_summarization_model.getInstallations());
        billingViewHolder.txt_consumedunit.setText(billing_summarization_model.getConsumed_unit());
        billingViewHolder.txt_currentamt.setText(billing_summarization_model.getCurrent_bill_amt());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class BillingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_year, txt_month, txt_install, txt_consumedunit, txt_currentamt;

        BillingViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_year = itemView.findViewById(R.id.txt_tranyear);
            txt_month = itemView.findViewById(R.id.txt_month);
            txt_install = itemView.findViewById(R.id.txt_installation);
            txt_consumedunit = itemView.findViewById(R.id.txt_consumed_unit);
            txt_currentamt = itemView.findViewById(R.id.txt_current_amt);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
