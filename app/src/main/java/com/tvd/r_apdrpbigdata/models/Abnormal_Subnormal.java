package com.tvd.r_apdrpbigdata.models;

import java.io.Serializable;

public class Abnormal_Subnormal implements Serializable {
    private String year="";
    private String month="";
    private String billed_installation="";
    private String abnormal_installation="";
    private String subnormal_installation="";
    private String value = "";

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getBilled_installation() {
        return billed_installation;
    }

    public void setBilled_installation(String billed_installation) {
        this.billed_installation = billed_installation;
    }

    public String getAbnormal_installation() {
        return abnormal_installation;
    }

    public void setAbnormal_installation(String abnormal_installation) {
        this.abnormal_installation = abnormal_installation;
    }

    public String getSubnormal_installation() {
        return subnormal_installation;
    }

    public void setSubnormal_installation(String subnormal_installation) {
        this.subnormal_installation = subnormal_installation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
