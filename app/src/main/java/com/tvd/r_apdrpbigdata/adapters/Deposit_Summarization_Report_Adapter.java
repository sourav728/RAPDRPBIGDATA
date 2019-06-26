package com.tvd.r_apdrpbigdata.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.models.Billing_Summarization_model;

import java.util.ArrayList;

public class Deposit_Summarization_Report_Adapter extends RecyclerView.Adapter<Deposit_Summarization_Report_Adapter.Holder>{
    private ArrayList<Billing_Summarization_model> arrayList;
    private Context context;
    private String date_check;

    public Deposit_Summarization_Report_Adapter(ArrayList<Billing_Summarization_model> arrayList, Context context, String date_check) {
        this.arrayList = arrayList;
        this.context = context;
        this.date_check = date_check;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.deposit_summarization_report_adapter,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        if(i%2==0){
            holder.tableLayout.setBackgroundColor(context.getResources().getColor(R.color.table1));
        }else  holder.tableLayout.setBackgroundColor(context.getResources().getColor(R.color.table2));

        if (date_check.equals("Y")) {
            Log.d("debug", "Date Equals " + date_check);
            holder.txt_month.setVisibility(View.VISIBLE);
            holder.txt_month.setText(arrayList.get(i).getMonth());
        } else {
            Log.d("debug", "Date Equals " + date_check);
            holder.txt_month.setVisibility(View.GONE);
        }


        holder.txt_year.setText(arrayList.get(i).getYear());
        holder.txt_install.setText(arrayList.get(i).getInstallations());
        holder.txt_deposit_amt.setText(arrayList.get(i).getDeposit_amt());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txt_year,txt_install,txt_deposit_amt,txt_month;
        TableLayout tableLayout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txt_year = itemView.findViewById(R.id.year);
            txt_install = itemView.findViewById(R.id.installation);
            txt_month = itemView.findViewById(R.id.month);
            txt_deposit_amt = itemView.findViewById(R.id.deposit_amount);
            tableLayout = itemView.findViewById(R.id.table_layout);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
