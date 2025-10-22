/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.estimation.service.dataio;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public interface IRegionDataIOContext {
    public String getForSchemeKey();

    public List<String> getRangeFormKeys();

    public DimensionCollection getDimValueCollection();
}

