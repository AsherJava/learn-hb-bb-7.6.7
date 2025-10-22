/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.integritycheck.common;

import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;

public class ImpErrDesFileParam {
    private ParamsMapping mapping;
    private DimensionCollection dims;
    private boolean fullImport = false;

    public ParamsMapping getMapping() {
        return this.mapping;
    }

    public void setMapping(ParamsMapping mapping) {
        this.mapping = mapping;
    }

    public DimensionCollection getDims() {
        return this.dims;
    }

    public void setDims(DimensionCollection dims) {
        this.dims = dims;
    }

    public boolean isFullImport() {
        return this.fullImport;
    }

    public void setFullImport(boolean fullImport) {
        this.fullImport = fullImport;
    }
}

