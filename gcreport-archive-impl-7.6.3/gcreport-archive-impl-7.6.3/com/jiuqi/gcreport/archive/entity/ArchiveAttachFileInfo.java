/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.entity;

public class ArchiveAttachFileInfo {
    private String fileName;
    private String fileUrl;
    private Integer fileSize;
    private String digitalDigest;

    public ArchiveAttachFileInfo() {
    }

    public ArchiveAttachFileInfo(String fileName, String fileUrl, Integer fileSize, String digitalDigest) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
        this.digitalDigest = digitalDigest;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getDigitalDigest() {
        return this.digitalDigest;
    }

    public void setDigitalDigest(String digitalDigest) {
        this.digitalDigest = digitalDigest;
    }
}

