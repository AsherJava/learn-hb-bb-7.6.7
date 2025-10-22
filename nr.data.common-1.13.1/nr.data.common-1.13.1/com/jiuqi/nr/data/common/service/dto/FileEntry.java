/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.service.dto;

public class FileEntry {
    private String fileName;
    private String filePath;
    private boolean directory;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isDirectory() {
        return this.directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    public FileEntry(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public FileEntry() {
    }
}

