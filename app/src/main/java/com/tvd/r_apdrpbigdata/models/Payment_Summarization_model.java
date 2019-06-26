package com.tvd.r_apdrpbigdata.models;

import java.io.Serializable;

public class Payment_Summarization_model implements Serializable {
    private String year="";
    private String month="";
    private String installations = "";
    private String opening_balance = "";
    private String consumed_unit="";
    private String demand = "";
    private String net_pay_amount="";
    private String collection_amt="";
    private String closing_balance_amt="";
    private String value = "";

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getInstallations() {
        return installations;
    }

    public void setInstallations(String installations) {
        this.installations = installations;
    }

    public String getOpening_balance() {
        return opening_balance;
    }

    public void setOpening_balance(String opening_balance) {
        this.opening_balance = opening_balance;
    }

    public String getConsumed_unit() {
        return consumed_unit;
    }

    public void setConsumed_unit(String consumed_unit) {
        this.consumed_unit = consumed_unit;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getNet_pay_amount() {
        return net_pay_amount;
    }

    public void setNet_pay_amount(String net_pay_amount) {
        this.net_pay_amount = net_pay_amount;
    }

    public String getCollection_amt() {
        return collection_amt;
    }

    public void setCollection_amt(String collection_amt) {
        this.collection_amt = collection_amt;
    }

    public String getClosing_balance_amt() {
        return closing_balance_amt;
    }

    public void setClosing_balance_amt(String closing_balance_amt) {
        this.closing_balance_amt = closing_balance_amt;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
