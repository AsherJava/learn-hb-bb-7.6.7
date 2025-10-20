/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto;

import com.jiuqi.bde.common.intf.BaseDataDimension;
import java.util.List;

public class BaseDataSimpleBizModelPluginDataV0 {
    private String fetchSourceCode;
    private List<BaseDataDimension> dimensionMapping;
    private boolean cleanZeroRecords;

    public String getFetchSourceCode() {
        return this.fetchSourceCode;
    }

    public void setFetchSourceCode(String fetchSourceCode) {
        this.fetchSourceCode = fetchSourceCode;
    }

    public List<BaseDataDimension> getDimensionMapping() {
        return this.dimensionMapping;
    }

    public void setDimensionMapping(List<BaseDataDimension> dimensionMapping) {
        this.dimensionMapping = dimensionMapping;
    }

    public boolean isCleanZeroRecords() {
        return this.cleanZeroRecords;
    }

    public void setCleanZeroRecords(boolean cleanZeroRecords) {
        this.cleanZeroRecords = cleanZeroRecords;
    }
}

