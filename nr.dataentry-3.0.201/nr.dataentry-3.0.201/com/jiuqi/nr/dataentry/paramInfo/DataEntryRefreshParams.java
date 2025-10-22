/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

public class DataEntryRefreshParams {
    private String paramKey;
    private Object paramValue;

    public DataEntryRefreshParams() {
    }

    public DataEntryRefreshParams(String paramKey, Object paramValue) {
        this.paramKey = paramKey;
        this.paramValue = paramValue;
    }

    public String getParamKey() {
        return this.paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public Object getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(Object paramValue) {
        this.paramValue = paramValue;
    }
}

