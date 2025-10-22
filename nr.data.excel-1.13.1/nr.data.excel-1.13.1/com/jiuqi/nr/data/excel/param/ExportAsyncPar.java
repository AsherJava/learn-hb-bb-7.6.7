/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.excel.param.BatchExpParSer;
import java.io.Serializable;

public class ExportAsyncPar
implements Serializable {
    private static final long serialVersionUID = -860868289321961525L;
    private BatchExpParSer batchExpParSer;
    private String filePath;
    private ParamsMapping paramsMapping;

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public BatchExpParSer getBatchExpParSer() {
        return this.batchExpParSer;
    }

    public void setBatchExpParSer(BatchExpParSer batchExpParSer) {
        this.batchExpParSer = batchExpParSer;
    }

    public ParamsMapping getParamsMapping() {
        return this.paramsMapping;
    }

    public void setParamsMapping(ParamsMapping paramsMapping) {
        this.paramsMapping = paramsMapping;
    }
}

