/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.importdata;

import com.jiuqi.nr.common.importdata.ImportResultRegionObject;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImportResultReportObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID reportKey;
    private String reportName;
    private ResultErrorInfo reportError;
    private List<ImportResultRegionObject> importResultRegionObjectList;

    public UUID getReportKey() {
        return this.reportKey;
    }

    public void setReportKey(UUID reportKey) {
        this.reportKey = reportKey;
    }

    public String getReportName() {
        return this.reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public List<ImportResultRegionObject> getImportResultRegionObjectList() {
        if (null == this.importResultRegionObjectList) {
            this.importResultRegionObjectList = new ArrayList<ImportResultRegionObject>();
        }
        return this.importResultRegionObjectList;
    }

    public void setImportResultRegionObjectList(List<ImportResultRegionObject> importResultRegionObjectList) {
        this.importResultRegionObjectList = importResultRegionObjectList;
    }

    public ResultErrorInfo getReportError() {
        if (null == this.reportError) {
            this.reportError = new ResultErrorInfo();
        }
        return this.reportError;
    }

    public void setReportError(ResultErrorInfo reportError) {
        this.reportError = reportError;
    }
}

