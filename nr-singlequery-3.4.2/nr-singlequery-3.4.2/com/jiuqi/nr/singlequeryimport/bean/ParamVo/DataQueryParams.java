/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.bean.ParamVo;

import java.util.ArrayList;
import java.util.List;

public class DataQueryParams {
    private String formSchemeKey = new String();
    private String period = new String();
    private String filter = new String();
    private List<String> dw = new ArrayList<String>();
    private List<String> fields = new ArrayList<String>();

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<String> getDw() {
        return this.dw;
    }

    public void setDw(List<String> dw) {
        this.dw = dw;
    }

    public List<String> getFields() {
        return this.fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}

