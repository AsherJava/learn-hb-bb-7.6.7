/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.cspro.data;

public class DataRow {
    private String recordType;
    private Object[] data;

    public DataRow(String recordType, Object[] data) {
        this.recordType = recordType;
        this.data = data;
    }

    public String getRecordType() {
        return this.recordType;
    }

    public Object[] getData() {
        return this.data;
    }
}

