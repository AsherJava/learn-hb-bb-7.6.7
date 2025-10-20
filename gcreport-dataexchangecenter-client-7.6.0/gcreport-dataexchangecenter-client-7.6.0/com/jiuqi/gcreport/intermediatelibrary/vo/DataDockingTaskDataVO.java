/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingFormVO;
import java.util.List;
import java.util.Map;

public class DataDockingTaskDataVO {
    private String unitCode;
    private String year;
    private String period;
    private Map<String, Object> dimension;
    private List<DataDockingFormVO> forms;

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Map<String, Object> getDimension() {
        return this.dimension;
    }

    public void setDimension(Map<String, Object> dimension) {
        this.dimension = dimension;
    }

    public List<DataDockingFormVO> getForms() {
        return this.forms;
    }

    public void setForms(List<DataDockingFormVO> forms) {
        this.forms = forms;
    }
}

