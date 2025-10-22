/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension.build;

import com.jiuqi.nr.dataservice.core.dimension.build.DimColBuildContext;
import com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder;

public class FixedDimBuilder
implements SpecificDimBuilder {
    @Override
    public boolean isUse(DimColBuildContext context, String entityId) {
        return true;
    }

    @Override
    public void setBuildInfo(DimColBuildContext context, String entityId, String dimensionName) {
        Object dimValues = context.getDimensionValueSet().getValue(dimensionName);
        if (dimValues == null) {
            return;
        }
        if (context.getMainDimId().equals(entityId)) {
            context.getDimensionCollectionBuilder().setDWValue(dimensionName, entityId, dimValues);
        } else {
            context.getDimensionCollectionBuilder().setEntityValue(dimensionName, entityId, dimValues);
        }
    }

    public static FixedDimBuilder getInstance() {
        return FixedDimBuilderInstance.INSTANCE;
    }

    private static class FixedDimBuilderInstance {
        private static final FixedDimBuilder INSTANCE = new FixedDimBuilder();

        private FixedDimBuilderInstance() {
        }
    }
}

