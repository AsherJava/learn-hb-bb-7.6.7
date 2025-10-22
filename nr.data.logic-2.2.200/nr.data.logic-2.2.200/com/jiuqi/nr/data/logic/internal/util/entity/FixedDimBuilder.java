/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 */
package com.jiuqi.nr.data.logic.internal.util.entity;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.extend.SpecificDimBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class FixedDimBuilder
implements SpecificDimBuilder {
    private final String mainDimId;
    private final Map<String, DimensionValue> dimensions;
    private final DimensionValueSet dimensionValueSet;

    public FixedDimBuilder(String mainDimId, Map<String, DimensionValue> dimensions) {
        this.mainDimId = mainDimId;
        this.dimensions = dimensions;
        this.dimensionValueSet = null;
    }

    public FixedDimBuilder(String mainDimId, DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
        this.mainDimId = mainDimId;
        this.dimensions = null;
    }

    @Override
    public boolean isUse(String entityId) {
        return true;
    }

    @Override
    public void setBuildInfo(DimensionCollectionBuilder builder, String entityId, String entityName) {
        Object dimValues = this.getDimValues(entityName);
        if (this.mainDimId.equals(entityId)) {
            builder.setDWValue(entityName, entityId, new Object[]{dimValues});
        } else {
            builder.setEntityValue(entityName, entityId, new Object[]{dimValues});
        }
    }

    private Object getDimValues(String entityName) {
        if (this.dimensions != null) {
            DimensionValue dimensionValue = this.dimensions.get(entityName);
            String[] split = dimensionValue.getValue().split(";");
            if (split.length == 1) {
                return split[0];
            }
            return new ArrayList<String>(Arrays.asList(split));
        }
        if (this.dimensionValueSet != null) {
            return this.dimensionValueSet.getValue(entityName);
        }
        return null;
    }
}

