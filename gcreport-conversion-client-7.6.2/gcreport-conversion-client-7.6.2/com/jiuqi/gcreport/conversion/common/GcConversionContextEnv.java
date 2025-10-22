/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.gcreport.conversion.common;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class GcConversionContextEnv
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskId;
    private String schemeId;
    private List<String> orgIds;
    private List<String> formIds;
    private String orgVersionType;
    private String orgTypeId;
    private String periodStr;
    private String beforeCurrencyCode;
    private String afterCurrencyCode;
    private String selectAdjustCode;
    private Boolean allowConversionBWB = false;
    private Boolean afterConversionoperation = true;
    private Boolean afterConversionRealTimeOffset = true;
    private Boolean conversionInputData = true;
    private Boolean allowUploadStateConversion = false;
    private String sn;
    private double currentProgress;
    private double stepProgress;
    private Set<String> conversionMessages = new CopyOnWriteArraySet<String>();
    private AsyncTaskMonitor asyncTaskMonitor;

    public GcConversionContextEnv(String sn, String taskId, String schemeId, List<String> orgIds, List<String> formIds, String orgVersionType, String orgTypeId, String periodStr, String beforeCurrencyCode, String afterCurrencyCode, Boolean afterConversionoperation, Boolean afterConversionRealTimeOffset, Boolean allowUploadStateConversion, Boolean conversionInputData, String adjustCode) {
        this.sn = sn;
        this.taskId = taskId;
        this.schemeId = schemeId;
        this.orgIds = orgIds;
        this.formIds = formIds;
        this.orgVersionType = orgVersionType;
        this.orgTypeId = orgTypeId;
        this.periodStr = periodStr;
        this.beforeCurrencyCode = beforeCurrencyCode;
        this.afterCurrencyCode = afterCurrencyCode;
        this.afterConversionoperation = afterConversionoperation;
        this.afterConversionRealTimeOffset = afterConversionRealTimeOffset;
        this.allowConversionBWB = allowUploadStateConversion;
        this.conversionInputData = conversionInputData;
        this.selectAdjustCode = adjustCode;
    }

    public String getOrgVersionType() {
        return this.orgVersionType;
    }

    public void setOrgVersionType(String orgVersionType) {
        this.orgVersionType = orgVersionType;
    }

    public String getOrgTypeId() {
        return this.orgTypeId;
    }

    public void setOrgTypeId(String orgTypeId) {
        this.orgTypeId = orgTypeId;
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

    public Boolean getAfterConversionRealTimeOffset() {
        return this.afterConversionRealTimeOffset;
    }

    public void setAfterConversionRealTimeOffset(Boolean afterConversionRealTimeOffset) {
        this.afterConversionRealTimeOffset = afterConversionRealTimeOffset;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public double getCurrentProgress() {
        return this.currentProgress;
    }

    public void setCurrentProgress(double currentProgress) {
        this.currentProgress = currentProgress;
    }

    public double getStepProgress() {
        return this.stepProgress;
    }

    public void setStepProgress(double stepProgress) {
        this.stepProgress = stepProgress;
    }

    public Set<String> getConversionMessages() {
        return this.conversionMessages;
    }

    public AsyncTaskMonitor getAsyncTaskMonitor() {
        return this.asyncTaskMonitor;
    }

    public void setAsyncTaskMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    public Boolean getAllowUploadStateConversion() {
        return this.allowUploadStateConversion;
    }

    public void setAllowUploadStateConversion(Boolean allowUploadStateConversion) {
        this.allowUploadStateConversion = allowUploadStateConversion;
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

