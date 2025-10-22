/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.Map;

public class CustomQueryCondition
implements Serializable {
    private static final long serialVersionUID = 5030322694337707915L;
    private DimensionCollection dimensionCollection;
    private Map<Integer, Boolean> checkTypes;

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public Map<Integer, Boolean> getCheckTypes() {
        return this.checkTypes;
    }

    public void setCheckTypes(Map<Integer, Boolean> checkTypes) {
        this.checkTypes = checkTypes;
    }
}

