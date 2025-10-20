/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.importdata;

import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImportResultRegionObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String regionKey;
    private ResultErrorInfo regionError;
    private List<ImportErrorDataInfo> importErrorDataInfoList;
    private List<Map<String, DimensionValue>> importDataDimensionValues;

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public List<ImportErrorDataInfo> getImportErrorDataInfoList() {
        if (null == this.importErrorDataInfoList) {
            this.importErrorDataInfoList = new ArrayList<ImportErrorDataInfo>();
        }
        return this.importErrorDataInfoList;
    }

    public void setImportErrorDataInfoList(List<ImportErrorDataInfo> importErrorDataInfoList) {
        this.importErrorDataInfoList = importErrorDataInfoList;
    }

    public ResultErrorInfo getRegionError() {
        if (null == this.regionError) {
            this.regionError = new ResultErrorInfo();
        }
        return this.regionError;
    }

    public void setRegionError(ResultErrorInfo regionError) {
        this.regionError = regionError;
    }

    public List<Map<String, DimensionValue>> getImportDataDimensionValues() {
        return this.importDataDimensionValues;
    }

    public void setImportDataDimensionValues(List<Map<String, DimensionValue>> importDataDimensionValues) {
        this.importDataDimensionValues = importDataDimensionValues;
    }
}

