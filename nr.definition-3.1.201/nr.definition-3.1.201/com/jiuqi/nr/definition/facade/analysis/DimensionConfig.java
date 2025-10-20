/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.analysis;

import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import java.util.List;

public interface DimensionConfig {
    public List<DimensionInfo> getDestDims();

    public void setDestDims(List<DimensionInfo> var1);

    public List<DimensionInfo> getSrcDims();

    public void setSrcDims(List<DimensionInfo> var1);
}

