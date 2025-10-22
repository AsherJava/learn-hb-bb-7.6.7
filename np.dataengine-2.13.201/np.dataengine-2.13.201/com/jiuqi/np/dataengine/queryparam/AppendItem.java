/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.queryparam;

public class AppendItem {
    private int insertIndex;
    private int appendCount;

    public AppendItem() {
    }

    public AppendItem(int appendCount, int insertIndex) {
        this.appendCount = appendCount;
        this.insertIndex = insertIndex;
    }

    public int getInsertIndex() {
        return this.insertIndex;
    }

    public void inrcInsertIndex() {
        ++this.insertIndex;
    }

    public int getAppendCount() {
        return this.appendCount;
    }

    public void inrcAppendCount() {
        ++this.appendCount;
    }
}

