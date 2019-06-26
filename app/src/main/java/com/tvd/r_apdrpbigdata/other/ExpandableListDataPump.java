package com.tvd.r_apdrpbigdata.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        List<String> bda_Dashboard = new ArrayList<>();
        bda_Dashboard.add("DashBoard TariffWise");
        bda_Dashboard.add("DashBoard OverAll");

        List<String> summarization = new ArrayList<>();
        summarization.add("Billing Summarization Tariff wise");
        summarization.add("Billing Summarization OverAll");
        summarization.add("Payment Summarization Tariff wise");
        summarization.add("Payment Summarization OverAll");
        summarization.add("Customer Summarization Tariff wise");
        summarization.add("Customer Summarization OverAll");
        summarization.add("Abnormal Summarization Graph");
        summarization.add("Subnormal Summarization Graph");
        summarization.add("Unbilled Summarization Tariff");
        summarization.add("Unbilled Summarization Without Tariff");
        summarization.add("Disconnection Summarization");
        summarization.add("Deposit Summarization");

        List<String> homedashboard = new ArrayList<>();
        homedashboard.add("Home DashBoard");

        List<String> OBCB_Summerization = new ArrayList<>();
        OBCB_Summerization.add("OB-CB Customer Summarization Monthly Tariff-Wise");
        OBCB_Summerization.add("OB-CB Customer Summarization Tariff-Wise");
        OBCB_Summerization.add("OB-CB Customer Summarization Status Wise");
        OBCB_Summerization.add("OB-CB Customer Summarization Overall");
        OBCB_Summerization.add("OB-CB Billed Customer Summarization Monthly Tariff-Wise");
        OBCB_Summerization.add("OB-CB Billed Customer Summarization Tariff-Wise");
        OBCB_Summerization.add("OB-CB Billed Customer Summarization Overall");
        OBCB_Summerization.add("OB-CB Billed Status Wise Customer Summarization Overall");
        OBCB_Summerization.add("OB-CB UnBilled Customer Summarization Monthly Tariff-Wise");
        OBCB_Summerization.add("OB-CB UnBilled Customer Summarization Tariff-Wise");
        OBCB_Summerization.add("OB-CB UnBilled Customer Summarization Account Status Wise");
        OBCB_Summerization.add("OB-CB UnBilled Customer Summarization Overall");



        expandableListDetail.put("BDA Dashboard", bda_Dashboard);
        expandableListDetail.put("Summarization", summarization);
        expandableListDetail.put("Home DashBoard",homedashboard);
        expandableListDetail.put("OB-CB Summerization",OBCB_Summerization);
        return expandableListDetail;
    }
}