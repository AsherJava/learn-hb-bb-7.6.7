/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.basedata.domain;

public class BaseDataImportProcess {
    private String rsKey = null;
    private int total = 0;
    private int currIndex = 0;

    public String getRsKey() {
        return this.rsKey;
    }

    public void setRsKey(String rsKey) {
        this.rsKey = rsKey;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrIndex() {
        return this.currIndex;
    }

    public void setCurrIndex(int currIndex) {
        this.currIndex = currIndex;
    }
}

