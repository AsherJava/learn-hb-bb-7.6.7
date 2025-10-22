/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.efdc.extract.impl.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.efdc.extract.impl.response.FixExpResult;
import com.jiuqi.nr.efdc.extract.impl.response.FloatExpResult;
import java.util.ArrayList;
import java.util.List;

public class ResultListing {
    private String unitCode;
    private String orgCode;
    private boolean isSuccess;
    private String errMsg;
    private List<FixExpResult> fixExpResults;
    private List<FloatExpResult> floatExpResults = new ArrayList<FloatExpResult>();
    private boolean isFormatNeeded;

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @JsonProperty(value="isSuccess")
    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<FixExpResult> getFixExpResults() {
        return this.fixExpResults;
    }

    public void setFixExpResults(List<FixExpResult> fixExpResults) {
        this.fixExpResults = fixExpResults;
    }

    public List<FloatExpResult> getFloatExpResults() {
        return this.floatExpResults;
    }

    public boolean addFloatExpResult(FloatExpResult result) {
        if (result == null) {
            return false;
        }
        return this.floatExpResults.add(result);
    }

    @JsonProperty(value="isFormatNeeded")
    public boolean isFormatNeeded() {
        return this.isFormatNeeded;
    }

    public void setFormatNeeded(boolean isFormatNeeded) {
        this.isFormatNeeded = isFormatNeeded;
    }
}

