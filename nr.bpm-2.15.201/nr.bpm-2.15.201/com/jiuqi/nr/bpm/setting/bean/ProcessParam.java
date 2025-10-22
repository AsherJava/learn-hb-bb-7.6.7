/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.bpm.setting.bean;

import com.jiuqi.nr.bpm.setting.bean.ProcessEntityParam;
import com.jiuqi.nr.context.infc.impl.NRContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ProcessParam
extends NRContext
implements Serializable {
    private static final long serialVersionUID = -4177339994009643587L;
    private String task;
    private String formScheme;
    private String period;
    private String periodDimension;
    private boolean certificate;
    private String formChooseType;
    private Map<String, ProcessEntityParam> entity;
    private List<String> forms;
    private boolean reportAll;
    private int periodSort;
    private int offsetValue;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public boolean isCertificate() {
        return this.certificate;
    }

    public void setCertificate(boolean certificate) {
        this.certificate = certificate;
    }

    public String getFormChooseType() {
        return this.formChooseType;
    }

    public void setFormChooseType(String formChooseType) {
        this.formChooseType = formChooseType;
    }

    public String getPeriodDimension() {
        return this.periodDimension;
    }

    public void setPeriodDimension(String periodDimension) {
        this.periodDimension = periodDimension;
    }

    public int getPeriodSort() {
        return this.periodSort;
    }

    public void setPeriodSort(int periodSort) {
        this.periodSort = periodSort;
    }

    public Map<String, ProcessEntityParam> getEntity() {
        return this.entity;
    }

    public void setEntity(Map<String, ProcessEntityParam> entity) {
        this.entity = entity;
    }

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public int getOffsetValue() {
        return this.offsetValue;
    }

    public void setOffsetValue(int offsetValue) {
        this.offsetValue = offsetValue;
    }

    public boolean isReportAll() {
        return this.reportAll;
    }

    public void setReportAll(boolean reportAll) {
        this.reportAll = reportAll;
    }
}

