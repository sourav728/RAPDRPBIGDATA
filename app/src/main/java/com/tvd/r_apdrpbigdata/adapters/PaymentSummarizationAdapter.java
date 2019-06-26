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
import com.tvd.r_apdrpbigdata.models.Payment_Summarization_model;
import java.util.ArrayList;

public class PaymentSummarizationAdapter extends RecyclerView.Adapter<PaymentSummarizationAdapter.PaymentViewHolder> {
    private ArrayList<Payment_Summarization_model> arrayList;
    private Context context;
    private Payment_Summarization_model payment_summarization_model;
    private String date_check;
    public PaymentSummarizationAdapter(ArrayList<Payment_Summarization_model>arrayList,Context context, Payment_Summarization_model payment_summarization_model,String date_check){
        this.arrayList = arrayList;
        this.context = context;
        this.payment_summarization_model = payment_summarization_model;
        this.date_check = date_check;
    }
    @NonNull
    @Override
    public PaymentSummarizationAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.payment_summarization_tariffwise_adapter_layout, viewGroup,false);
        PaymentSummarizationAdapter.PaymentViewHolder viewHolder = new PaymentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentSummarizationAdapter.PaymentViewHolder paymentViewHolder, int position) {
        Payment_Summarization_model payment_summarization_model = arrayList.get(position);
        paymentViewHolder.txt_year.setText(payment_summarization_model.getYear());
        if (date_check.equals("Y")) {
            Log.d("debug", "Billing_Date Equals " + date_check);
            paymentViewHolder.txt_month.setVisibility(View.VISIBLE);
            paymentViewHolder.txt_month.setText(payment_summarization_model.getMonth());
        } else {
            Log.d("debug", "Billing_Date Equals " + date_check);
            paymentViewHolder.txt_month.setVisibility(View.GONE);
        }
        paymentViewHolder.txt_instal.setText(payment_summarization_model.getInstallations());
        paymentViewHolder.txt_opening_balance.setText(payment_summarization_model.getOpening_balance());
        paymentViewHolder.txt_consumed_unit.setText(payment_summarization_model.getConsumed_unit());
        paymentViewHolder.txt_demand.setText(payment_summarization_model.getDemand());
        paymentViewHolder.txt_netpay_amt.setText(payment_summarization_model.getNet_pay_amount());
        paymentViewHolder.txt_collection_amt.setText(payment_summarization_model.getCollection_amt());
        paymentViewHolder.txt_closing_amt.setText(payment_summarization_model.getClosing_balance_amt());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_year,txt_month,txt_instal,txt_opening_balance,txt_consumed_unit,txt_demand,txt_netpay_amt,txt_collection_amt,txt_closing_amt;
        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_year = itemView.findViewById(R.id.txt_tranyear);
            txt_month = itemView.findViewById(R.id.txt_month);
            txt_instal = itemView.findViewById(R.id.txt_installation);
            txt_opening_balance = itemView.findViewById(R.id.txt_opening_balance);
            txt_consumed_unit = itemView.findViewById(R.id.txt_consumed_unit);
            txt_demand = itemView.findViewById(R.id.txt_demand);
            txt_netpay_amt = itemView.findViewById(R.id.txt_net_pay_amt);
            txt_collection_amt = itemView.findViewById(R.id.txt_collection_amt);
            txt_closing_amt = itemView.findViewById(R.id.txt_closing_balance_amt);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
