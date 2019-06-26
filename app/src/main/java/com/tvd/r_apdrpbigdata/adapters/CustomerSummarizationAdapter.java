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
import com.tvd.r_apdrpbigdata.models.Customer_Summarization_model;

import java.util.ArrayList;

public class CustomerSummarizationAdapter extends RecyclerView.Adapter<CustomerSummarizationAdapter.Customer_ViewHolder> {
    private ArrayList<Customer_Summarization_model> arrayList;
    private Context context;
    private Customer_Summarization_model customer_summarization_model;
    private String date_check;
    public CustomerSummarizationAdapter(ArrayList<Customer_Summarization_model> arrayList, Context context, Customer_Summarization_model customer_summarization_model,String date_check) {
        this.arrayList = arrayList;
        this.context = context;
        this.customer_summarization_model = customer_summarization_model;
        this.date_check = date_check;
    }

    @NonNull
    @Override
    public CustomerSummarizationAdapter.Customer_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_summarization_adapter_layout, viewGroup, false);
        CustomerSummarizationAdapter.Customer_ViewHolder viewHolder = new CustomerSummarizationAdapter.Customer_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerSummarizationAdapter.Customer_ViewHolder customer_viewHolder, int position) {
        Customer_Summarization_model customer_summarization_model = arrayList.get(position);
        customer_viewHolder.txt_year.setText(customer_summarization_model.getYear());
        if (date_check.equals("Y")) {
            Log.d("debug", "Customer_Date Equals " + date_check);
            customer_viewHolder.txt_month.setVisibility(View.VISIBLE);
            customer_viewHolder.txt_month.setText(customer_summarization_model.getMonth());
        } else {
            Log.d("debug", "Customer_Date Equals " + date_check);
            customer_viewHolder.txt_month.setVisibility(View.GONE);
        }
        customer_viewHolder.txt_installation.setText(customer_summarization_model.getInstallation());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Customer_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_year, txt_month, txt_installation;

        Customer_ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_year = itemView.findViewById(R.id.txt_tranyear);
            txt_installation = itemView.findViewById(R.id.txt_installation);
            txt_month = itemView.findViewById(R.id.txt_month);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
