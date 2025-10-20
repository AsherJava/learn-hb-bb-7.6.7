/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

public class ObjectSliceInfo {
    private int totalNum;
    private int partNum;
    private long totalSize;
    private long byteOffset;
    private long partSize;

    public ObjectSliceInfo() {
    }

    public ObjectSliceInfo(int totalNum, int partNum, long byteOffset, long partSize) {
        this.totalNum = totalNum;
        this.partNum = partNum;
        this.byteOffset = byteOffset;
        this.partSize = partSize;
    }

    public int getTotalNum() {
        return this.totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getPartNum() {
        return this.partNum;
    }

    public void setPartNum(int partNum) {
        this.partNum = partNum;
    }

    public long getByteOffset() {
        return this.byteOffset;
    }

    public void setByteOffset(long byteOffset) {
        this.byteOffset = byteOffset;
    }

    public long getPartSize() {
        return this.partSize;
    }

    public void setPartSize(long partSize) {
        this.partSize = partSize;
    }
}

