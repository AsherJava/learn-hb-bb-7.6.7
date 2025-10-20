/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl.anslysis;

import com.jiuqi.nr.definition.facade.analysis.DimensionConfig;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import java.util.List;

public class DimensionConfigImpl
implements DimensionConfig {
    private List<DimensionInfo> destDims;
    private List<DimensionInfo> srcDims;

    @Override
    public List<DimensionInfo> getDestDims() {
        return this.destDims;
    }

    @Override
    public void setDestDims(List<DimensionInfo> destDims) {
        this.destDims = destDims;
    }

    @Override
    public List<DimensionInfo> getSrcDims() {
        return this.srcDims;
    }

    @Override
    public void setSrcDims(List<DimensionInfo> srcDims) {
        this.srcDims = srcDims;
    }
}

