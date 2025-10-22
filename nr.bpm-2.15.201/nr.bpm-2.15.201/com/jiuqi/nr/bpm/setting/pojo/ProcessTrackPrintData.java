/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.pojo;

public class ProcessTrackPrintData {
    private String fileName;
    private byte[] data;

    public ProcessTrackPrintData(String fileName, byte[] data) {
        this.fileName = fileName;
        this.data = data;
    }

    public ProcessTrackPrintData() {
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

