/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

import com.jiuqi.nr.migration.transferdata.log.XmlDataExportLog;

public class FileDownloadResponse {
    private String downloadUrl;
    private String filename;
    private XmlDataExportLog result;

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public XmlDataExportLog getResult() {
        return this.result;
    }

    public void setResult(XmlDataExportLog result) {
        this.result = result;
    }
}

