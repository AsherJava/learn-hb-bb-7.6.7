/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.queryscheme.common;

public enum QuerySchemeStoreTypeEnum {
    CURRENT_TABLE(1),
    CLOB_TABLE(0);

    private Integer storeValue;

    private QuerySchemeStoreTypeEnum(Integer storeValue) {
        this.storeValue = storeValue;
    }

    public Integer getStoreValue() {
        return this.storeValue;
    }

    public void setStoreValue(Integer storeValue) {
        this.storeValue = storeValue;
    }
}

