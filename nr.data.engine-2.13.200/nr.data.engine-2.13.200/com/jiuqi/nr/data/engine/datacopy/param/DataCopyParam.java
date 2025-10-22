/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.engine.datacopy.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.io.Serializable;
import java.util.List;

public class DataCopyParam
implements Serializable {
    private static final long serialVersionUID = 7434639889617141357L;
    private String taskKey;
    private List<String> formKeys;
    private DimensionValueSet currDimValueSet;
    private DimensionValueSet sourceDimValueSet;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public DimensionValueSet getCurrDimValueSet() {
        return this.currDimValueSet;
    }

    public void setCurrDimValueSet(DimensionValueSet currDimValueSet) {
        this.currDimValueSet = currDimValueSet;
    }

    public DimensionValueSet getSourceDimValueSet() {
        return this.sourceDimValueSet;
    }

    public void setSourceDimValueSet(DimensionValueSet sourceDimValueSet) {
        this.sourceDimValueSet = sourceDimValueSet;
    }
}

