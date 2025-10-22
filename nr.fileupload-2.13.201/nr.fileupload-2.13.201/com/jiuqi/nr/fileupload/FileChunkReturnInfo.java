/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fileupload;

public class FileChunkReturnInfo {
    private boolean isCompleted = false;
    private Integer[] uploadChunks;
    private boolean canMerge = false;

    public boolean isCanMerge() {
        return this.canMerge;
    }

    public void setCanMerge(boolean canMerge) {
        this.canMerge = canMerge;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    public Integer[] getUploadChunks() {
        return this.uploadChunks;
    }

    public void setUploadChunks(Integer[] uploadChunks) {
        this.uploadChunks = uploadChunks;
    }
}

