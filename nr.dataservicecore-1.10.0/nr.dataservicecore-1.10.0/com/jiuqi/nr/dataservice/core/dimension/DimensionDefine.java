/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionValue;

public class DimensionDefine
implements DimensionValue {
    private static final long serialVersionUID = 5844637833549632872L;
    private final String entityID;
    private final String name;

    public DimensionDefine(String name, String entityID) {
        this.name = name;
        this.entityID = entityID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEntityID() {
        return this.entityID;
    }
}

