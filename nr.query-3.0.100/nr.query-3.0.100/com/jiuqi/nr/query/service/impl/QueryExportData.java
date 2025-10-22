/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.service.impl;

class QueryExportData {
    private String fileName;
    private byte[] data;

    public QueryExportData(String fileName, byte[] data) {
        this.fileName = fileName;
        this.data = data;
    }

    public QueryExportData() {
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

