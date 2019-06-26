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
import com.tvd.r_apdrpbigdata.models.Abnormal_Subnormal;

import java.util.ArrayList;

public class Abnormal_SubnormalAdapter2 extends RecyclerView.Adapter<Abnormal_SubnormalAdapter2.AbnormalSubnormalViewHolder> {
    private ArrayList<Abnormal_Subnormal> arrayList;
    private Context context;
    private Abnormal_Subnormal abnormal_subnormal_model;
    private String date_check;
    public Abnormal_SubnormalAdapter2(ArrayList<Abnormal_Subnormal> arrayList, Context context, Abnormal_Subnormal abnormal_subnormal_model,String date_check) {
        this.arrayList = arrayList;
        this.context = context;
        this.abnormal_subnormal_model = abnormal_subnormal_model;
        this.date_check = date_check;
    }

    @NonNull
    @Override
    public Abnormal_SubnormalAdapter2.AbnormalSubnormalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.abnormal_subnormal_adapter2_layout, viewGroup,false);
        Abnormal_SubnormalAdapter2.AbnormalSubnormalViewHolder viewHolder = new Abnormal_SubnormalAdapter2.AbnormalSubnormalViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Abnormal_SubnormalAdapter2.AbnormalSubnormalViewHolder abnormalSubnormalViewHolder, int position) {
        Abnormal_Subnormal abnormal_subnormal = arrayList.get(position);
        if (date_check.equals("Y")) {
            Log.d("debug", "Billing_Date Equals " + date_check);
            abnormalSubnormalViewHolder.txt_year.setText(abnormal_subnormal.getMonth());
        } else {
            Log.d("debug", "Billing_Date Equals " + date_check);
            abnormalSubnormalViewHolder.txt_year.setText(abnormal_subnormal.getYear());
        }
        abnormalSubnormalViewHolder.txt_billed_installation.setText(abnormal_subnormal.getBilled_installation());
        abnormalSubnormalViewHolder.txt_subnormal_installation.setText(abnormal_subnormal.getSubnormal_installation());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class AbnormalSubnormalViewHolder extends RecyclerView.ViewHolder {
        TextView txt_year,txt_billed_installation,txt_subnormal_installation;
        AbnormalSubnormalViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_year = itemView.findViewById(R.id.txt_tranyear);
            txt_billed_installation = itemView.findViewById(R.id.txt_billed_installation);
            txt_subnormal_installation = itemView.findViewById(R.id.txt_subnormal_installation);
        }
    }
}
