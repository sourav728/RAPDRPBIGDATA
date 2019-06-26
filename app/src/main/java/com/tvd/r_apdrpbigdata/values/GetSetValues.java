package com.tvd.r_apdrpbigdata.values;

import java.io.Serializable;

public class GetSetValues implements Serializable {
    private String role="";
    private String dates_equal="";
    private String code="";
    private String month_flag="";
    private String name="";
    private String single_month = "";
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonth_flag() {
        return month_flag;
    }

    public void setMonth_flag(String month_flag) {
        this.month_flag = month_flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDates_equal() {
        return dates_equal;
    }

    public void setDates_equal(String dates_equal) {
        this.dates_equal = dates_equal;
    }

    public String getSingle_month() {
        return single_month;
    }

    public void setSingle_month(String single_month) {
        this.single_month = single_month;
    }
}
