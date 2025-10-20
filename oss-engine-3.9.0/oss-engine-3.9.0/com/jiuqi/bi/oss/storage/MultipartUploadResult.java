/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

public class MultipartUploadResult {
    private int partNum;
    private String md5;
    private long totalSize;

    public int getPartNum() {
        return this.partNum;
    }

    public void setPartNum(int partNum) {
        this.partNum = partNum;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
}

