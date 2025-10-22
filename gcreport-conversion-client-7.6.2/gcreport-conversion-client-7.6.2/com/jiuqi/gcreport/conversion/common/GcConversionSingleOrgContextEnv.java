/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 */
package com.jiuqi.gcreport.conversion.common;

import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import java.util.List;

public class GcConversionSingleOrgContextEnv {
    private String taskId;
    private String schemeId;
    private String orgId;
    private String orgTitle;
    private String orgType;
    private String orgTypeId;
    private String periodStr;
    private String adjtypeId;
    private String beforeCurrencyId;
    private String afterCurrencyId;
    private String beforeCurrencyCode;
    private String afterCurrencyCode;
    private String selectAdjustCode;
    private List<String> formIds;
    private String formulaSchemeKey;
    private DataEntryContext envContext;
    private Boolean afterConversionoperation = true;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgTypeId() {
        return this.orgTypeId;
    }

    public void setOrgTypeId(String orgTypeId) {
        this.orgTypeId = orgTypeId;
    }

    public String getAdjtypeId() {
        return this.adjtypeId;
    }

    public void setAdjtypeId(String adjtypeId) {
        this.adjtypeId = adjtypeId;
    }

    public String getBeforeCurrencyId() {
        return this.beforeCurrencyId;
    }

    public void setBeforeCurrencyId(String beforeCurrencyId) {
        this.beforeCurrencyId = beforeCurrencyId;
    }

    public String getAfterCurrencyId() {
        return this.afterCurrencyId;
    }

    public void setAfterCurrencyId(String afterCurrencyId) {
        this.afterCurrencyId = afterCurrencyId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getBeforeCurrencyCode() {
        return this.beforeCurrencyCode;
    }

    public void setBeforeCurrencyCode(String beforeCurrencyCode) {
        this.beforeCurrencyCode = beforeCurrencyCode;
    }

    public String getAfterCurrencyCode() {
        return this.afterCurrencyCode;
    }

    public void setAfterCurrencyCode(String afterCurrencyCode) {
        this.afterCurrencyCode = afterCurrencyCode;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public DataEntryContext getEnvContext() {
        return this.envContext;
    }

    public void setEnvContext(DataEntryContext envContext) {
        this.envContext = envContext;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public List<String> getFormIds() {
        return this.formIds;
    }

    public void setFormIds(List<String> formIds) {
        this.formIds = formIds;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public Boolean getAfterConversionoperation() {
        return this.afterConversionoperation;
    }

    public void setAfterConversionoperation(Boolean afterConversionoperation) {
        this.afterConversionoperation = afterConversionoperation;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }
}

