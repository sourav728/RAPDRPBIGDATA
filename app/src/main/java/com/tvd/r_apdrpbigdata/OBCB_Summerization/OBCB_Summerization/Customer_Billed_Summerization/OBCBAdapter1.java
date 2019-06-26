package com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Customer_Billed_Summerization;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.models.Response;

import java.util.ArrayList;

public class OBCBAdapter1 extends RecyclerView.Adapter<OBCBAdapter1.Approvals_ViewHolder> {
    private Context context;
    private ArrayList<Response> arrayList;


    public OBCBAdapter1(Context context, ArrayList<Response> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Approvals_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.obcb_adapter1, viewGroup, false);
        return new Approvals_ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Approvals_ViewHolder approvals_viewHolder, int i) {
        Response dashboard = arrayList.get(i);
        approvals_viewHolder.tv_tariff.setText(dashboard.getA3());
        approvals_viewHolder.tv_installation.setText(dashboard.getA2());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class Approvals_ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tariff, tv_installation;

        Approvals_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tariff = itemView.findViewById(R.id.txt_tariff);
            tv_installation = itemView.findViewById(R.id.txt_installation);
        }
    }
}
