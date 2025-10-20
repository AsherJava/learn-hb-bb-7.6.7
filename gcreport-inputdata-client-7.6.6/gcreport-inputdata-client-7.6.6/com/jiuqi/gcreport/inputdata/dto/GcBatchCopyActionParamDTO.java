/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 */
package com.jiuqi.gcreport.inputdata.dto;

import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import java.io.Serializable;
import java.util.List;

public class GcBatchCopyActionParamDTO
implements Serializable {
    private String sn;
    private String orgVersionType;
    private String srcPeriod;
    private String srcSelectAdjustCode;
    private List<String> currencyIds;
    private List<String> orgIds;
    private List<String> formIds;
    private Boolean afterRealTimeOffset;
    private DataEntryContext envContext;

    public String getSrcPeriod() {
        return this.srcPeriod;
    }

    public void setSrcPeriod(String srcPeriod) {
        this.srcPeriod = srcPeriod;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public List<String> getCurrencyIds() {
        return this.currencyIds;
    }

    public void setCurrencyIds(List<String> currencyIds) {
        this.currencyIds = currencyIds;
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

    public Boolean getAfterRealTimeOffset() {
        return this.afterRealTimeOffset;
    }

    public void setAfterRealTimeOffset(Boolean afterRealTimeOffset) {
        this.afterRealTimeOffset = afterRealTimeOffset;
    }

    public DataEntryContext getEnvContext() {
        return this.envContext;
    }

    public void setEnvContext(DataEntryContext envContext) {
        this.envContext = envContext;
    }

    public String getOrgVersionType() {
        return this.orgVersionType;
    }

    public void setOrgVersionType(String orgVersionType) {
        this.orgVersionType = orgVersionType;
    }

    public String getSrcSelectAdjustCode() {
        return this.srcSelectAdjustCode;
    }

    public void setSrcSelectAdjustCode(String srcSelectAdjustCode) {
        this.srcSelectAdjustCode = srcSelectAdjustCode;
    }
}

