package com.tvd.r_apdrpbigdata.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.tvd.r_apdrpbigdata.BDA_DashBoard.DashBoard_Overall;
import com.tvd.r_apdrpbigdata.BDA_DashBoard.DashBoard_Tariff_Wise;
import com.tvd.r_apdrpbigdata.HomeDashboard;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Billed_Summerization.OBCBBilledCustomer1;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Billed_Summerization.OBCBBilledCustomer2;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Billed_Summerization.OBCBBilledCustomer3;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Billed_Summerization.OBCBBilledCustomer4;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Customer_Billed_Summerization.OBCBCustomer1;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Customer_Billed_Summerization.OBCBCustomer2;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Customer_Billed_Summerization.OBCBCustomer3;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Customer_Billed_Summerization.OBCBCustomer4;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.UnBilled_Summerization.OBCBUnBilledCustomer1;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.UnBilled_Summerization.OBCBUnBilledCustomer2;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.UnBilled_Summerization.OBCBUnBilledCustomer3;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.UnBilled_Summerization.OBCBUnBilledCustomer4;
import com.tvd.r_apdrpbigdata.R;
import com.tvd.r_apdrpbigdata.Summarization.Abnormal_Subnormal_Summarization.Abnormal_Summarization_Graph;
import com.tvd.r_apdrpbigdata.Summarization.Abnormal_Subnormal_Summarization.Subnormal_Summarization_Graph;
import com.tvd.r_apdrpbigdata.Summarization.Billing_Summarization.BillingSummarization_TariffWise;
import com.tvd.r_apdrpbigdata.Summarization.Billing_Summarization.Billing_sum_yearwise;
import com.tvd.r_apdrpbigdata.Summarization.DepositSummarization.Deposit_Summarization;
import com.tvd.r_apdrpbigdata.Summarization.DisconnectionSummarization.DisConnection_Summerization;
import com.tvd.r_apdrpbigdata.Summarization.Feeder_mapping_customer_Summarization.Customer_Summarization_Overall;
import com.tvd.r_apdrpbigdata.Summarization.Feeder_mapping_customer_Summarization.Customer_Summarization_TariffWise;
import com.tvd.r_apdrpbigdata.Summarization.Payment_Summarization.Payment_Summarization_Overall;
import com.tvd.r_apdrpbigdata.Summarization.Payment_Summarization.Payment_Summarization_TariffWise;
import com.tvd.r_apdrpbigdata.Summarization.UnBilled_Summerization.UnBilled_Summerization_Tariff;
import com.tvd.r_apdrpbigdata.Summarization.UnBilled_Summerization.UnBilled_Summerization_without_Tariff;
import com.tvd.r_apdrpbigdata.adapters.CustomExpandableListAdapter;
import com.tvd.r_apdrpbigdata.other.ExpandableListDataPump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        expandableListView = view.findViewById(R.id.listView1);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
             /*   Toast.makeText(
                        getContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/

                switch (groupPosition) {
                    case 0:
                        switch (childPosition){
                            case 0:
                                startActivity(new Intent(getActivity(), DashBoard_Tariff_Wise.class));
                                break;
                            case 1:
                                startActivity(new Intent(getActivity(), DashBoard_Overall.class));
                                break;
                        }

                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), HomeDashboard.class));
                        break;
                    case 2:
                        switch (childPosition) {
                            case 0:
                                startActivity(new Intent(getActivity(), BillingSummarization_TariffWise.class));
                                break;
                            case 1:
                                startActivity(new Intent(getActivity(), Billing_sum_yearwise.class));
                                break;
                            case 2:
                                startActivity(new Intent(getActivity(), Payment_Summarization_TariffWise.class));
                                break;
                            case 3:
                                startActivity(new Intent(getActivity(), Payment_Summarization_Overall.class));
                                break;
                            case 4:
                                startActivity(new Intent(getActivity(), Customer_Summarization_TariffWise.class));
                                break;
                            case 5:
                                startActivity(new Intent(getActivity(), Customer_Summarization_Overall.class));
                                break;
                            case 6:
                                startActivity(new Intent(getActivity(), Abnormal_Summarization_Graph.class));
                                break;
                            case 7:
                                startActivity(new Intent(getActivity(), Subnormal_Summarization_Graph.class));
                                break;
                            case 8:
                                startActivity(new Intent(getActivity(), UnBilled_Summerization_Tariff.class));
                                break;
                            case 9:
                                startActivity(new Intent(getActivity(), UnBilled_Summerization_without_Tariff.class));
                                break;
                            case 10:
                                startActivity(new Intent(getActivity(), DisConnection_Summerization.class));
                                break;
                            case 11:
                                startActivity(new Intent(getActivity(), Deposit_Summarization.class));
                                break;
                        }
                        break;
                    case 3:
                        switch (childPosition) {
                            case 0:
                                startActivity(new Intent(getActivity(), OBCBCustomer1.class));
                                break;
                            case 1:
                                startActivity(new Intent(getActivity(), OBCBCustomer2.class));
                                break;
                            case 2:
                                startActivity(new Intent(getActivity(), OBCBCustomer3.class));
                                break;
                            case 3:
                                startActivity(new Intent(getActivity(), OBCBCustomer4.class));
                                break;
                            case 4:
                                startActivity(new Intent(getActivity(), OBCBBilledCustomer1.class));
                                break;
                            case 5:
                                startActivity(new Intent(getActivity(), OBCBBilledCustomer2.class));
                                break;
                            case 6:
                                startActivity(new Intent(getActivity(), OBCBBilledCustomer3.class));
                                break;
                            case 7:
                                startActivity(new Intent(getActivity(), OBCBBilledCustomer4.class));
                                break;
                            case 8:
                                startActivity(new Intent(getActivity(), OBCBUnBilledCustomer1.class));
                                break;
                            case 9:
                                startActivity(new Intent(getActivity(), OBCBUnBilledCustomer2.class));
                                break;
                            case 10:
                                startActivity(new Intent(getActivity(), OBCBUnBilledCustomer3.class));
                                break;
                            case 11:
                                startActivity(new Intent(getActivity(), OBCBUnBilledCustomer4.class));
                                break;
                        }

                        break;

                }
                return false;
            }
        });

        return view;
    }

}
