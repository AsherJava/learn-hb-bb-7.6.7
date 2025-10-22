/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;
import java.io.Serializable;

public class DataSumAndCalcAllInfo
extends NRContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    private BatchDataSumInfo batchDataSumInfo = new BatchDataSumInfo();
    private BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
    private String formulaSchemeName = "";

    public DataSumAndCalcAllInfo() {
    }

    public DataSumAndCalcAllInfo(BatchCalculateInfo batchCalculateInfo, BatchDataSumInfo batchDataSumInfo, String formulaSchemeName) {
        this.batchCalculateInfo = batchCalculateInfo;
        this.batchDataSumInfo = batchDataSumInfo;
        this.formulaSchemeName = formulaSchemeName;
    }

    public void setBatchDataSumInfo(BatchDataSumInfo batchDataSumInfo) {
        this.batchDataSumInfo = batchDataSumInfo;
    }

    public BatchCalculateInfo getBatchCalculateInfo() {
        return this.batchCalculateInfo;
    }

    public void setBatchCalculateInfo(BatchCalculateInfo batchCalculateInfo) {
        this.batchCalculateInfo = batchCalculateInfo;
    }

    public BatchDataSumInfo getBatchDataSumInfo() {
        return this.batchDataSumInfo;
    }

    public String getFormulaSchemeName() {
        return this.formulaSchemeName;
    }

    public void setFormulaSchemeName(String formulaSchemeName) {
        this.formulaSchemeName = formulaSchemeName;
    }
}

