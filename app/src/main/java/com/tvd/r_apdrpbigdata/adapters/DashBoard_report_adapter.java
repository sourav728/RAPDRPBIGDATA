package com.tvd.r_apdrpbigdata.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.models.Response;

import java.util.ArrayList;

public class DashBoard_report_adapter extends RecyclerView.Adapter<DashBoard_report_adapter.Holder> {
    ArrayList<Response> arrayList;
    Context context;
    private String month_flag;

    public DashBoard_report_adapter(ArrayList<Response> arrayList, Context context, String month_flag) {
        this.arrayList = arrayList;
        this.context = context;
        this.month_flag = month_flag;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dash_board_report_adapter, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        if (month_flag.equals("Y")) {
            holder.txt_year.setText(arrayList.get(i).getMonth());
        } else {
            holder.txt_year.setText(arrayList.get(i).getA1());
        }
        holder.txt_install.setText(arrayList.get(i).getA2());
        holder.txt_open_bal.setText(arrayList.get(i).getA3());
        holder.txt_consumedunit.setText(arrayList.get(i).getA4());
        holder.demand.setText(arrayList.get(i).getA5());
        holder.txt_netpay_amt.setText(arrayList.get(i).getA6());
        holder.txt_col_amt.setText(arrayList.get(i).getA7());
        holder.txt_close_amt.setText(arrayList.get(i).getA8());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txt_year, txt_install, txt_consumedunit, txt_open_bal, demand, txt_netpay_amt, txt_col_amt, txt_close_amt;
        TableLayout tableLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txt_year = itemView.findViewById(R.id.year);
            txt_install = itemView.findViewById(R.id.installation);
            txt_consumedunit = itemView.findViewById(R.id.consumed_unit);
            txt_open_bal = itemView.findViewById(R.id.open_bal);
            demand = itemView.findViewById(R.id.demands);
            txt_netpay_amt = itemView.findViewById(R.id.net_pat_amt);
            txt_col_amt = itemView.findViewById(R.id.coll_amt);
            txt_close_amt = itemView.findViewById(R.id.closing_bal_amt);
            tableLayout = itemView.findViewById(R.id.table_layout);
        }
    }
}
