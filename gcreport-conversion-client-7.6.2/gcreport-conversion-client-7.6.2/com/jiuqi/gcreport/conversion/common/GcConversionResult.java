/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.common;

public class GcConversionResult {
    private int deleteCount;
    private int insertCount;
    private int updateCount;

    public GcConversionResult(int deleteCount, int insertCount, int updateCount) {
        this.deleteCount = deleteCount;
        this.insertCount = insertCount;
        this.updateCount = updateCount;
    }

    public int getUpdateCount() {
        return this.updateCount;
    }

    public int getDeleteCount() {
        return this.deleteCount;
    }

    public int getInsertCount() {
        return this.insertCount;
    }
}

