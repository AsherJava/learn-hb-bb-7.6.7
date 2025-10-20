/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.task.vo;

import java.util.List;

public class TaskConditionVO {
    private String taskId;
    private String schemeId;
    private Integer acctYear;
    private Integer acctPeriod;
    private String periodType;
    private String periodStr;
    private String unitDefine;
    private String unitTitle;
    private String currencyDefine;
    private String gcorgtypeDefine;
    private String gcadjtypeDefine;
    private List<String> defines;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getUnitDefine() {
        return this.unitDefine;
    }

    public void setUnitDefine(String unitDefine) {
        this.unitDefine = unitDefine;
    }

    public List<String> getDefines() {
        return this.defines;
    }

    public void setDefines(List<String> defines) {
        this.defines = defines;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getCurrencyDefine() {
        return this.currencyDefine;
    }

    public void setCurrencyDefine(String currencyDefine) {
        this.currencyDefine = currencyDefine;
    }

    public String getGcorgtypeDefine() {
        return this.gcorgtypeDefine;
    }

    public void setGcorgtypeDefine(String gcorgtypeDefine) {
        this.gcorgtypeDefine = gcorgtypeDefine;
    }

    public String getGcadjtypeDefine() {
        return this.gcadjtypeDefine;
    }

    public void setGcadjtypeDefine(String gcadjtypeDefine) {
        this.gcadjtypeDefine = gcadjtypeDefine;
    }
}

