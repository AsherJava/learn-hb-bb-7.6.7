/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;

public class MinorityRecoveryParamsVO
extends QueryParamsVO {
    private String currShowTypeValue;
    private String minorityTotalType;
    private Integer offsetType;
    private Boolean lossGain = false;

    public String getCurrShowTypeValue() {
        return this.currShowTypeValue;
    }

    public void setCurrShowTypeValue(String currShowTypeValue) {
        this.currShowTypeValue = currShowTypeValue;
    }

    public String getMinorityTotalType() {
        return this.minorityTotalType;
    }

    public void setMinorityTotalType(String minorityTotalType) {
        this.minorityTotalType = minorityTotalType;
    }

    public Integer getOffsetType() {
        return this.offsetType;
    }

    public void setOffsetType(Integer offsetType) {
        this.offsetType = offsetType;
    }

    public Boolean getLossGain() {
        return this.lossGain;
    }

    public void setLossGain(Boolean lossGain) {
        this.lossGain = lossGain;
    }
}

