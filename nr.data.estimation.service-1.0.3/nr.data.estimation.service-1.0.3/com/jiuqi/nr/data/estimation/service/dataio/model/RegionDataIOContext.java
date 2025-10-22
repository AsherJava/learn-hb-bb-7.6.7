/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.IRegionDataIOContext;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public class RegionDataIOContext
implements IRegionDataIOContext {
    private String forSchemeKey;
    private List<String> rangeFormKeys;
    private DimensionCollection dimValueCollection;

    @Override
    public String getForSchemeKey() {
        return this.forSchemeKey;
    }

    public void setForSchemeKey(String forSchemeKey) {
        this.forSchemeKey = forSchemeKey;
    }

    @Override
    public List<String> getRangeFormKeys() {
        return this.rangeFormKeys;
    }

    public void setRangeFormKeys(List<String> rangeFormKeys) {
        this.rangeFormKeys = rangeFormKeys;
    }

    @Override
    public DimensionCollection getDimValueCollection() {
        return this.dimValueCollection;
    }

    public void setDimValueCollection(DimensionCollection dimValueCollection) {
        this.dimValueCollection = dimValueCollection;
    }
}

