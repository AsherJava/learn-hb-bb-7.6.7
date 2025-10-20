/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto;

import com.jiuqi.bde.common.intf.Dimension;
import java.util.List;

public class SimpleCustomComposePluginDataVO {
    private String fetchSourceCode;
    private List<Dimension> dimensionMapping;
    private boolean cleanZeroRecords;

    public boolean isCleanZeroRecords() {
        return this.cleanZeroRecords;
    }

    public void setCleanZeroRecords(boolean cleanZeroRecords) {
        this.cleanZeroRecords = cleanZeroRecords;
    }

    public List<Dimension> getDimensionMapping() {
        return this.dimensionMapping;
    }

    public void setDimensionMapping(List<Dimension> dimensionMapping) {
        this.dimensionMapping = dimensionMapping;
    }

    public String getFetchSourceCode() {
        return this.fetchSourceCode;
    }

    public void setFetchSourceCode(String fetchSourceCode) {
        this.fetchSourceCode = fetchSourceCode;
    }
}

