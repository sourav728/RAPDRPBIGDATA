package com.tvd.r_apdrpbigdata.other;

public class ContentItem {

    public final String name;
    public final String desc;
    public boolean isSection = false;

    public ContentItem(String n) {
        name = n;
        desc = "";
        isSection = true;
    }

    public ContentItem(String n, String d) {
        name = n;
        desc = d;
    }
}