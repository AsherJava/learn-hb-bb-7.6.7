/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fileupload;

public class FileChunkCacheInfo {
    private String fileKey;
    private int shardTotal;
    private String fileName;
    private double mergeProgress = -1.0;

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public double getMergeProgress() {
        return this.mergeProgress;
    }

    public void setMergeProgress(double mergeProgress) {
        this.mergeProgress = mergeProgress;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getShardTotal() {
        return this.shardTotal;
    }

    public void setShardTotal(int shardTotal) {
        this.shardTotal = shardTotal;
    }
}

