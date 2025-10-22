/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.batch.audit.entity;

import java.sql.Blob;
import java.util.List;

public class ConversionBatchAuditFileEntity {
    private List<String> idList;
    private Blob fileData;
    private String fileName;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getIdList() {
        return this.idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public Blob getFileData() {
        return this.fileData;
    }

    public void setFileData(Blob fileData) {
        this.fileData = fileData;
    }
}

