package com.tvd.r_apdrpbigdata.adapters;

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
import com.tvd.r_apdrpbigdata.values.FunctionCall;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.Approvals_ViewHolder> {
    private Context context;
    private ArrayList<Response> arrayList;
    FunctionCall fcall = new FunctionCall();


    public DashboardAdapter(Context context, ArrayList<Response> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Approvals_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_adapter, viewGroup, false);
        return new Approvals_ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Approvals_ViewHolder approvals_viewHolder, int i) {
        Response dashboard = arrayList.get(i);
        double gap_amt=Double.parseDouble(dashboard.getA4())-Double.parseDouble(dashboard.getA6());
        approvals_viewHolder.tv_installation.setText(dashboard.getA1());
        approvals_viewHolder.tv_ob.setText(fcall.roundoff(dashboard.getA2()));
        approvals_viewHolder.tv_cons_units.setText(fcall.roundoff(dashboard.getA3()));
        approvals_viewHolder.tv_demand.setText(fcall.roundoff(dashboard.getA4()));
        approvals_viewHolder.tv_net_payable.setText(fcall.roundoff(dashboard.getA5()));
        approvals_viewHolder.tv_coll_amt.setText(fcall.roundoff(dashboard.getA6()));
        approvals_viewHolder.tv_cb.setText(fcall.roundoff(dashboard.getA7()));
        approvals_viewHolder.tv_level.setText(dashboard.getA8());
        approvals_viewHolder.tv_gap_amount.setText(fcall.roundoff(gap_amt+""));
//        approvals_viewHolder.tv_moth_year.setText(dashboard.getA9());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class Approvals_ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_level, tv_installation, tv_ob, tv_cons_units, tv_demand, tv_net_payable, tv_coll_amt, tv_cb, tv_moth_year, tv_gap_amount;

        Approvals_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_level = itemView.findViewById(R.id.txt_level);
            tv_installation = itemView.findViewById(R.id.txt_installation);
            tv_ob = itemView.findViewById(R.id.txt_opening_balance);
            tv_cons_units = itemView.findViewById(R.id.txt_consumed_units);
            tv_demand = itemView.findViewById(R.id.txt_demand);
            tv_net_payable = itemView.findViewById(R.id.txt_net_payable);
            tv_coll_amt = itemView.findViewById(R.id.txt_collection_amt);
            tv_cb = itemView.findViewById(R.id.txt_closing_balance);
            tv_moth_year = itemView.findViewById(R.id.tv_month_year);
            tv_gap_amount = itemView.findViewById(R.id.txt_gap_amount);
        }
    }
}
