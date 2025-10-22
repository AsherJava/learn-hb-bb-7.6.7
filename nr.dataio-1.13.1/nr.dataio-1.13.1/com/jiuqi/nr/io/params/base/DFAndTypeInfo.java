/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.params.base;

public class DFAndTypeInfo {
    private int dataFieldType;
    private boolean isEncrypted;

    public DFAndTypeInfo(int dataFieldType, boolean isEncrypted) {
        this.dataFieldType = dataFieldType;
        this.isEncrypted = isEncrypted;
    }

    public int getDataFieldType() {
        return this.dataFieldType;
    }

    public void setDataFieldType(int dataFieldType) {
        this.dataFieldType = dataFieldType;
    }

    public boolean isEncrypted() {
        return this.isEncrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.isEncrypted = encrypted;
    }
}

