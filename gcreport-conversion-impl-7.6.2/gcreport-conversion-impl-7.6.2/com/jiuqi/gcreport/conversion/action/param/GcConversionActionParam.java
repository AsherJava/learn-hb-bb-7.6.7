/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 */
package com.jiuqi.gcreport.conversion.action.param;

import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import java.util.List;

public class GcConversionActionParam {
    private String sn;
    private String orgVersionType;
    private String beforeCurrencyId;
    private String afterCurrencyId;
    private List<String> orgIds;
    private List<String> formIds;
    private Boolean afterConversionoperation;
    private Boolean allowConversionBWB;
    private Boolean autoConversion;
    private Boolean afterConversionRealTimeOffset;
    private Boolean conversionInputData;
    private String selectAdjustCode;
    private DataEntryContext envContext;

    public DataEntryContext getEnvContext() {
        return this.envContext;
    }

    public void setEnvContext(DataEntryContext envContext) {
        this.envContext = envContext;
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

    public List<String> getOrgIds() {
        return this.orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    public List<String> getFormIds() {
        return this.formIds;
    }

    public void setFormIds(List<String> formIds) {
        this.formIds = formIds;
    }

    public Boolean getAfterConversionoperation() {
        return this.afterConversionoperation;
    }

    public void setAfterConversionoperation(Boolean afterConversionoperation) {
        this.afterConversionoperation = afterConversionoperation;
    }

    public Boolean getAutoConversion() {
        return this.autoConversion;
    }

    public void setAutoConversion(Boolean autoConversion) {
        this.autoConversion = autoConversion;
    }

    public Boolean getAfterConversionRealTimeOffset() {
        return this.afterConversionRealTimeOffset;
    }

    public void setAfterConversionRealTimeOffset(Boolean afterConversionRealTimeOffset) {
        this.afterConversionRealTimeOffset = afterConversionRealTimeOffset;
    }

    public String getOrgVersionType() {
        return this.orgVersionType;
    }

    public void setOrgVersionType(String orgVersionType) {
        this.orgVersionType = orgVersionType;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Boolean getConversionInputData() {
        return this.conversionInputData;
    }

    public void setConversionInputData(Boolean conversionInputData) {
        this.conversionInputData = conversionInputData;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public Boolean getAllowConversionBWB() {
        return this.allowConversionBWB;
    }

    public void setAllowConversionBWB(Boolean allowConversionBWB) {
        this.allowConversionBWB = allowConversionBWB;
    }
}

