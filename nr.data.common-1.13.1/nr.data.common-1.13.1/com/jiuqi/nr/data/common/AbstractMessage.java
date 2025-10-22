/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.common;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public abstract class AbstractMessage {
    protected List<String> forms;
    protected DimensionCollection dimensions;
    protected List<String> successDW;

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public DimensionCollection getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(DimensionCollection dimensions) {
        this.dimensions = dimensions;
    }

    public List<String> getSuccessDW() {
        return this.successDW;
    }

    public void setSuccessDW(List<String> successDW) {
        this.successDW = successDW;
    }
}

