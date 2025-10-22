/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.dao;

public class TableDefineItem {
    private String tableIndex;
    private boolean isAuto;
    private String key;

    public boolean isAuto() {
        return this.isAuto;
    }

    public void setAuto(boolean isAuto) {
        this.isAuto = isAuto;
    }

    public String getTableIndex() {
        return this.tableIndex;
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

