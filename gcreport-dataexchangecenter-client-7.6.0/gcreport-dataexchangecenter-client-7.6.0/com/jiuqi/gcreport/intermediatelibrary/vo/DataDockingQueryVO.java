/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import java.util.List;
import java.util.Map;

public class DataDockingQueryVO {
    private String sysCode;
    private String taskCode;
    private List<String> unitCodes;
    private Integer year;
    private Integer period;
    private Map<String, Object> dimension;
    private List<String> forms;
    private Boolean unitAllSelect;
    private Boolean formAllSelect;

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPeriod() {
        return this.period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Map<String, Object> getDimension() {
        return this.dimension;
    }

    public void setDimension(Map<String, Object> dimension) {
        this.dimension = dimension;
    }

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public Boolean getUnitAllSelect() {
        return this.unitAllSelect;
    }

    public void setUnitAllSelect(Boolean unitAllSelect) {
        this.unitAllSelect = unitAllSelect;
    }

    public Boolean getFormAllSelect() {
        return this.formAllSelect;
    }

    public void setFormAllSelect(Boolean formAllSelect) {
        this.formAllSelect = formAllSelect;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }
}

