/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.BatchExportData;
import java.util.List;
import java.util.Vector;

public class BatchExportTask {
    private List<BatchExportData> exportDatas;
    private Vector<String> unitCodes;

    public Vector<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(Vector<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public BatchExportTask(List<BatchExportData> exportDatas) {
        this.exportDatas = exportDatas;
    }

    public List<BatchExportData> getExportDatas() {
        return this.exportDatas;
    }

    public void setExportDatas(List<BatchExportData> exportDatas) {
        this.exportDatas = exportDatas;
    }
}

