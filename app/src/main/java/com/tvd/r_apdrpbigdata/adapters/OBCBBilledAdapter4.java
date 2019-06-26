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

public class OBCBBilledAdapter4 extends RecyclerView.Adapter<OBCBBilledAdapter4.Approvals_ViewHolder> {
    private Context context;
    private ArrayList<Response> arrayList;
    FunctionCall fcall=new FunctionCall();


    public OBCBBilledAdapter4(Context context, ArrayList<Response> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Approvals_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.obcb_billed_adapter1, viewGroup, false);
//        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new Approvals_ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Approvals_ViewHolder approvals_viewHolder, int i) {
        Response dashboard = arrayList.get(i);
        approvals_viewHolder.tv_year.setVisibility(View.GONE);
        approvals_viewHolder.tv_status.setText(dashboard.getA1());
        approvals_viewHolder.tv_installation.setText(dashboard.getA2());
        approvals_viewHolder.tv_billed_installations.setText(dashboard.getA3());
        approvals_viewHolder.tv_cons_units.setText(fcall.roundoff1(dashboard.getA4()));
        approvals_viewHolder.tv_billed_cons_units.setText(fcall.roundoff1(dashboard.getA5()));
        approvals_viewHolder.tv_opening_bal.setText(fcall.roundoff1(dashboard.getA6()));
        approvals_viewHolder.tv_demand.setText(fcall.roundoff1(dashboard.getA7()));
        approvals_viewHolder.tv_billed_demand.setText(fcall.roundoff1(dashboard.getA8()));
        approvals_viewHolder.tv_net_payable.setText(fcall.roundoff1(dashboard.getA9()));
        approvals_viewHolder.tv_coll_amt.setText(fcall.roundoff1(dashboard.getA10()));
        approvals_viewHolder.tv_cb.setText(fcall.roundoff1(dashboard.getA11()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class Approvals_ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_year, tv_installation, tv_status, tv_billed_installations, tv_cons_units, tv_billed_cons_units, tv_opening_bal, tv_demand, tv_billed_demand, tv_net_payable,
        tv_coll_amt, tv_cb ;

        Approvals_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_year = itemView.findViewById(R.id.txt_tariff);
            tv_installation = itemView.findViewById(R.id.txt_installation);
            tv_status = itemView.findViewById(R.id.txt_status);
            tv_billed_installations = itemView.findViewById(R.id.txt_billed_installation);
            tv_cons_units = itemView.findViewById(R.id.txt_cons_units);
            tv_billed_cons_units = itemView.findViewById(R.id.txt_billed_cons_units);
            tv_opening_bal = itemView.findViewById(R.id.txt_opening_bal);
            tv_demand = itemView.findViewById(R.id.txt_demand);
            tv_billed_demand = itemView.findViewById(R.id.txt_billed_demand);
            tv_net_payable = itemView.findViewById(R.id.txt_net_payable);
            tv_coll_amt = itemView.findViewById(R.id.txt_collection_amt);
            tv_cb = itemView.findViewById(R.id.txt_closing_balance);
        }
    }
}
