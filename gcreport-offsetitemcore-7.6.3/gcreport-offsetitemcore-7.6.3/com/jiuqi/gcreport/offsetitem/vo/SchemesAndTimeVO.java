/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.np.period.PeriodWrapper;
import java.util.ArrayList;
import java.util.List;

public class SchemesAndTimeVO {
    private String taskId;
    private String schemeId;
    private String title;
    private int periodType;
    List<Integer> acctYears = new ArrayList<Integer>();
    List<Integer> acctPeriods = new ArrayList<Integer>();

    public SchemesAndTimeVO() {
    }

    public SchemesAndTimeVO(String taskId, String schemeId, String title, String fromPeriod, String toPeriod) {
        int i;
        this.taskId = taskId;
        this.schemeId = schemeId;
        this.title = title;
        int fromAcctYear = 0;
        int toAcctYear = 0;
        int fromAcctPeriod = 0;
        int toAcctPeriod = 0;
        double random = Math.random();
        fromAcctYear = 2012;
        toAcctYear = 2018;
        fromAcctPeriod = 1;
        toAcctPeriod = 12;
        this.periodType = 1;
        for (i = fromAcctYear; i <= toAcctYear; ++i) {
            this.acctYears.add(i);
        }
        for (i = fromAcctPeriod; i <= toAcctPeriod; ++i) {
            this.acctPeriods.add(i);
        }
    }

    public SchemesAndTimeVO(String taskId, String schemeId, String title, String periodString) {
        this.taskId = taskId;
        this.schemeId = schemeId;
        this.title = title;
        PeriodWrapper pw = new PeriodWrapper(periodString);
    }

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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public List<Integer> getAcctYears() {
        return this.acctYears;
    }

    public void setAcctYears(List<Integer> acctYears) {
        this.acctYears = acctYears;
    }

    public List<Integer> getAcctPeriods() {
        return this.acctPeriods;
    }

    public void setAcctPeriods(List<Integer> acctPeriods) {
        this.acctPeriods = acctPeriods;
    }
}

