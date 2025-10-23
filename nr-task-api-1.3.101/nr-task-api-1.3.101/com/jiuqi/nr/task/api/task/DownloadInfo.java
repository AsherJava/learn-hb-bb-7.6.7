/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.task;

import java.io.Serializable;

public class DownloadInfo
implements Serializable {
    private String key;
    private String fileName;

    public DownloadInfo() {
    }

    public DownloadInfo(String key, String fileName) {
        this.key = key;
        this.fileName = fileName;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

