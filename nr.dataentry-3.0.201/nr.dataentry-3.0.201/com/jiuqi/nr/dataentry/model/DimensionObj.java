/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.model;

import com.jiuqi.nr.dataentry.model.EntityDataObj;
import java.io.Serializable;
import java.util.List;

public class DimensionObj
implements Serializable {
    private static final long serialVersionUID = -9013301157822661073L;
    private String dimensionName;
    private String dimensionTitle;
    private List<EntityDataObj> dimensionValue;

    public DimensionObj(String dimensionName, String dimensionTitle, List<EntityDataObj> dimensionValue) {
        this.dimensionName = dimensionName;
        this.dimensionTitle = dimensionTitle;
        this.dimensionValue = dimensionValue;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getDimensionTitle() {
        return this.dimensionTitle;
    }

    public void setDimensionTitle(String dimensionTitle) {
        this.dimensionTitle = dimensionTitle;
    }

    public List<EntityDataObj> getDimensionValue() {
        return this.dimensionValue;
    }

    public void setDimensionValue(List<EntityDataObj> dimensionValue) {
        this.dimensionValue = dimensionValue;
    }
}

