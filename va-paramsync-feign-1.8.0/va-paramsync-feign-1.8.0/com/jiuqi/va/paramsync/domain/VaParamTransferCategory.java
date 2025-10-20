/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.domain;

public class VaParamTransferCategory {
    private String name;
    private String title;
    private boolean supportExport;
    private boolean supportExportData;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSupportExport() {
        return this.supportExport;
    }

    public void setSupportExport(boolean supportExport) {
        this.supportExport = supportExport;
    }

    public boolean isSupportExportData() {
        return this.supportExportData;
    }

    public void setSupportExportData(boolean supportExportData) {
        this.supportExportData = supportExportData;
    }
}

