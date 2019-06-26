package com.tvd.r_apdrpbigdata.models;

import java.io.Serializable;

public class Billing_Summarization_model implements Serializable {
    private String year="";
    private String installations = "";
    private String consumed_unit="";
    private String current_bill_amt="";
    private String month="";
    private String deposit_amt="";
    private String connecton_type = "";
    private String value = "";

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getConsumed_unit() {
        return consumed_unit;
    }

    public void setConsumed_unit(String consumed_unit) {
        this.consumed_unit = consumed_unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInstallations() {
        return installations;
    }

    public void setInstallations(String installations) {
        this.installations = installations;
    }

    public String getCurrent_bill_amt() {
        return current_bill_amt;
    }

    public void setCurrent_bill_amt(String current_bill_amt) {
        this.current_bill_amt = current_bill_amt;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDeposit_amt() {
        return deposit_amt;
    }

    public String getConnecton_type() {
        return connecton_type;
    }

    public void setConnecton_type(String connecton_type) {
        this.connecton_type = connecton_type;
    }

    public void setDeposit_amt(String deposit_amt) {
        this.deposit_amt = deposit_amt;
    }
}
