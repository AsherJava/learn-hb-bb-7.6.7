/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.journalsingle.vo;

import java.util.Map;

public class JournalAdjustedFiguresVO {
    private Double beforNumber;
    private Double adjustedNumber;
    private Double afterNumber;
    private String beforZbId;
    private Map<String, Object> options;

    public Double getAfterNumber() {
        return this.afterNumber;
    }

    public void setAfterNumber(Double afterNumber) {
        this.afterNumber = afterNumber;
    }

    public Double getAdjustedNumber() {
        return this.adjustedNumber;
    }

    public void setAdjustedNumber(Double adjustedNumber) {
        this.adjustedNumber = adjustedNumber;
    }

    public Double getBeforNumber() {
        return this.beforNumber;
    }

    public void setBeforNumber(Double beforNumber) {
        this.beforNumber = beforNumber;
    }

    public Map<String, Object> getOptions() {
        return this.options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public String getBeforZbId() {
        return this.beforZbId;
    }

    public void setBeforZbId(String beforZbId) {
        this.beforZbId = beforZbId;
    }
}

