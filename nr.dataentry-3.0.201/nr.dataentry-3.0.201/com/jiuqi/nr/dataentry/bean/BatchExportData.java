/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.ExportData;

public class BatchExportData {
    private ExportData data;
    private String location;

    public ExportData getData() {
        return this.data;
    }

    public void setData(ExportData data) {
        this.data = data;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

