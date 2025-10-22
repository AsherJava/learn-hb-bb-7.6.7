/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.gather.gzw.service.entity;

public class BatchGatherExportData {
    private String fileName;
    private String alisFileName;
    private byte[] data;

    public BatchGatherExportData() {
    }

    public BatchGatherExportData(String fileName, String alisFileName, byte[] data) {
        this.fileName = fileName;
        this.alisFileName = alisFileName;
        this.data = data;
    }

    public BatchGatherExportData(String fileName, byte[] data) {
        this.fileName = fileName;
        this.data = data;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAlisFileName() {
        return this.alisFileName;
    }

    public void setAlisFileName(String alisFileName) {
        this.alisFileName = alisFileName;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

