/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.query.client.balance.vo;

public class BalanceQueryDimVO {
    private String dimCode;
    private String dimName;
    private String startCode;
    private String endCode;
    private String referField;
    private boolean multiDim;

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public String getStartCode() {
        return this.startCode;
    }

    public void setStartCode(String startCode) {
        this.startCode = startCode;
    }

    public String getEndCode() {
        return this.endCode;
    }

    public void setEndCode(String endCode) {
        this.endCode = endCode;
    }

    public String getReferField() {
        return this.referField;
    }

    public void setReferField(String referField) {
        this.referField = referField;
    }

    public boolean getMultiDim() {
        return this.multiDim;
    }

    public void setMultiDim(boolean multiDim) {
        this.multiDim = multiDim;
    }
}

