/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.topic.extend.report.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class ReportConfig {
    public static final String MD_ORG = "P_MD_ORG";
    public static final String MD_PERIOD = "P_DATATIME";
    public static final String MD_DIM = "P_MD_";
    private boolean hasReadAuth;
    private String msg;
    private String org;
    private String orgEntity;
    private String orgEntityTitle;
    private String period;
    private String periodTitle;
    private String fromPeriod;
    private String toPeriod;
    private String periodEntity;
    private String periodEntityTitle;
    private String taskKey;
    private String formSchemeKey;
    private String formKey;
    private String formulaSchemeKey;
    private Map<String, DimensionValue> dimensionSet;
    private boolean periodJson;
    private boolean orgJson;
    private List<String> pmJsonList;
    private boolean initJTable = true;

    public boolean isHasReadAuth() {
        return this.hasReadAuth;
    }

    public void setHasReadAuth(boolean hasReadAuth) {
        this.hasReadAuth = hasReadAuth;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getMdOrg() {
        return MD_ORG;
    }

    public static String getMdPeriod() {
        return MD_PERIOD;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getOrgEntity() {
        return this.orgEntity;
    }

    public void setOrgEntity(String orgEntity) {
        this.orgEntity = orgEntity;
    }

    public String getOrgEntityTitle() {
        return this.orgEntityTitle;
    }

    public void setOrgEntityTitle(String orgEntityTitle) {
        this.orgEntityTitle = orgEntityTitle;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodTitle() {
        return this.periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public String getPeriodEntity() {
        return this.periodEntity;
    }

    public void setPeriodEntity(String periodEntity) {
        this.periodEntity = periodEntity;
    }

    public String getPeriodEntityTitle() {
        return this.periodEntityTitle;
    }

    public void setPeriodEntityTitle(String periodEntityTitle) {
        this.periodEntityTitle = periodEntityTitle;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public boolean isPeriodJson() {
        return this.periodJson;
    }

    public void setPeriodJson(boolean periodJson) {
        this.periodJson = periodJson;
    }

    public boolean isOrgJson() {
        return this.orgJson;
    }

    public void setOrgJson(boolean orgJson) {
        this.orgJson = orgJson;
    }

    public List<String> getPmJsonList() {
        return this.pmJsonList;
    }

    public void setPmJsonList(List<String> pmJsonList) {
        this.pmJsonList = pmJsonList;
    }

    public void addPmJsonList(String pmJson) {
        if (CollectionUtils.isEmpty(this.pmJsonList)) {
            this.pmJsonList = new ArrayList<String>();
        }
        this.pmJsonList.add(pmJson);
    }

    public boolean isInitJTable() {
        return this.initJTable;
    }

    public void setInitJTable(boolean initJTable) {
        this.initJTable = initJTable;
    }
}

