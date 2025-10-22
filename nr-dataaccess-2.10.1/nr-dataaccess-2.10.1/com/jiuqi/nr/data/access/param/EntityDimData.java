/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.param;

public class EntityDimData {
    private String dimensionName;
    private String entityId;

    public EntityDimData(String dimensionName, String entityId) {
        this.dimensionName = dimensionName;
        this.entityId = entityId;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}

