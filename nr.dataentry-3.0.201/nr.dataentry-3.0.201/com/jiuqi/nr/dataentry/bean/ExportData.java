/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

public class ExportData {
    private String fileName;
    private String alisFileName;
    private byte[] data;
    private String fileLocation;
    private boolean isEnclosure = false;
    private boolean empty;

    public boolean isEnclosure() {
        return this.isEnclosure;
    }

    public void setEnclosure(boolean enclosure) {
        this.isEnclosure = enclosure;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public ExportData(boolean empty) {
        this.empty = empty;
    }

    public ExportData(String fileName, byte[] data) {
        this.fileName = fileName;
        this.data = data;
    }

    public ExportData(String fileName, String alisFileName, byte[] data) {
        this.fileName = fileName;
        this.alisFileName = alisFileName;
        this.data = data;
    }

    public ExportData(String fileName, boolean isEnclosure, byte[] data) {
        this.fileName = fileName;
        this.isEnclosure = isEnclosure;
        this.data = data;
    }

    public ExportData(String fileName, String alisFileName, String fileLocation) {
        this.fileName = fileName;
        this.alisFileName = alisFileName;
        this.fileLocation = fileLocation;
    }

    public ExportData() {
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

    public String getFileLocation() {
        return this.fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}

