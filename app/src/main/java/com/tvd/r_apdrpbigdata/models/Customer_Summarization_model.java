package com.tvd.r_apdrpbigdata.models;

import java.io.Serializable;

public class Customer_Summarization_model implements Serializable {
    private String year="";
    private String month="";
    private String installation="";
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

    public String getInstallation() {
        return installation;
    }

    public void setInstallation(String installation) {
        this.installation = installation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
