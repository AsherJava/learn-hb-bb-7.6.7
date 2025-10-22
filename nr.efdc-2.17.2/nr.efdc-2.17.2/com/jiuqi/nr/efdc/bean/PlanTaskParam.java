/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.bean;

import com.jiuqi.nr.efdc.bean.PlanTaskEntityParam;
import com.jiuqi.nr.efdc.bean.PlanTaskFormParam;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PlanTaskParam
implements Serializable {
    private String task;
    private String formScheme;
    private String period;
    private String periodDimension;
    private int periodType;
    private int periodConfig;
    private boolean certificate;
    private String formChooseType;
    private Map<String, PlanTaskEntityParam> entity;
    private List<PlanTaskFormParam> forms;
    private String adjustDate;
    private boolean changeBefore;
    private String unitCorporate;

    public String getUnitCorporate() {
        return this.unitCorporate;
    }

    public void setUnitCorporate(String unitCorporate) {
        this.unitCorporate = unitCorporate;
    }

    public boolean isChangeBefore() {
        return this.changeBefore;
    }

    public void setChangeBefore(boolean changeBefore) {
        this.changeBefore = changeBefore;
    }

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

    public Map<String, PlanTaskEntityParam> getEntity() {
        return this.entity;
    }

    public void setEntity(Map<String, PlanTaskEntityParam> entity) {
        this.entity = entity;
    }

    public List<PlanTaskFormParam> getForms() {
        return this.forms;
    }

    public void setForms(List<PlanTaskFormParam> forms) {
        this.forms = forms;
    }

    public String getPeriodDimension() {
        return this.periodDimension;
    }

    public void setPeriodDimension(String periodDimension) {
        this.periodDimension = periodDimension;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public int getPeriodConfig() {
        return this.periodConfig;
    }

    public void setPeriodConfig(int periodConfig) {
        this.periodConfig = periodConfig;
    }

    public String getAdjustDate() {
        return this.adjustDate;
    }

    public void setAdjustDate(String adjustDate) {
        this.adjustDate = adjustDate;
    }
}

