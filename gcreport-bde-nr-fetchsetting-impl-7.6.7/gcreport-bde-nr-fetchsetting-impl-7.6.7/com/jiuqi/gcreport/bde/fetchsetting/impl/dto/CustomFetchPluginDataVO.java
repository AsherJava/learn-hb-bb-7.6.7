/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dto;

import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.CustomFetchPluginDimension;
import java.util.List;

public class CustomFetchPluginDataVO {
    private String fetchSourceCode;
    private List<CustomFetchPluginDimension> dimensionMapping;
    private boolean cleanZeroRecords;

    public String getFetchSourceCode() {
        return this.fetchSourceCode;
    }

    public void setFetchSourceCode(String fetchSourceCode) {
        this.fetchSourceCode = fetchSourceCode;
    }

    public List<CustomFetchPluginDimension> getDimensionMapping() {
        return this.dimensionMapping;
    }

    public void setDimensionMapping(List<CustomFetchPluginDimension> dimensionMapping) {
        this.dimensionMapping = dimensionMapping;
    }

    public boolean isCleanZeroRecords() {
        return this.cleanZeroRecords;
    }

    public void setCleanZeroRecords(boolean cleanZeroRecords) {
        this.cleanZeroRecords = cleanZeroRecords;
    }
}

