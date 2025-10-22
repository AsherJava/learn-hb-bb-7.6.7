/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fileupload;

import java.io.Serializable;

public class FileChunkParamInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int currentIndex;
    private int shardTotal;
    private String fileKey;
    private String fileName;
    private int fileSize;

    public int getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getShardTotal() {
        return this.shardTotal;
    }

    public void setShardTotal(int shardTotal) {
        this.shardTotal = shardTotal;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }
}

