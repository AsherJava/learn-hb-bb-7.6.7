/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.access;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.io.Serializable;

public class DataPermissionResource
implements Serializable {
    private static final long serialVersionUID = -7145602026554032184L;
    private DimensionCombination dimensionCombination;
    private String resourceId;
    private String message;

    public DataPermissionResource() {
    }

    public DataPermissionResource(DimensionCombination dimensionCombination, String resourceId) {
        this.dimensionCombination = dimensionCombination;
        this.resourceId = resourceId;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public String getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

