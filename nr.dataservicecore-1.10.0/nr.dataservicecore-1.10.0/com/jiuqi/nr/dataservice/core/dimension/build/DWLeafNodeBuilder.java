/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.common.TaskGatherType
 */
package com.jiuqi.nr.dataservice.core.dimension.build;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.build.DimColBuildContext;
import com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.definition.common.TaskGatherType;
import java.util.ArrayList;
import java.util.List;

public class DWLeafNodeBuilder
implements SpecificDimBuilder {
    @Override
    public boolean isUse(DimColBuildContext context, String entityId) {
        return context.getTaskDefine().getTaskGatherType() == TaskGatherType.TASK_GATHER_AUTO && context.getMainDimId().equals(entityId);
    }

    @Override
    public void setBuildInfo(DimColBuildContext context, String entityId, String dimensionName) {
        Object dimValues = context.getDimensionValueSet().getValue(dimensionName);
        if (dimValues == null) {
            return;
        }
        ArrayList<String> choosedValues = new ArrayList<String>();
        if (dimValues instanceof String) {
            String str = (String)dimValues;
            if (StringUtils.isNotEmpty((String)str)) {
                choosedValues.add(str);
            }
        } else if (dimValues instanceof List) {
            choosedValues.addAll((List)dimValues);
        }
        DimensionProviderData dimensionProviderData = new DimensionProviderData(choosedValues, context.getDataSchemeKey());
        VariableDimensionValueProvider dimensionProvider = context.getDimensionProviderFactory().getDimensionProvider("PROVIDER_LEAFNODE", dimensionProviderData);
        context.getDimensionCollectionBuilder().addVariableDW(dimensionName, entityId, dimensionProvider);
    }

    public static DWLeafNodeBuilder getInstance() {
        return DWLeafNodeBuilderInstance.INSTANCE;
    }

    private static class DWLeafNodeBuilderInstance {
        private static final DWLeafNodeBuilder INSTANCE = new DWLeafNodeBuilder();

        private DWLeafNodeBuilderInstance() {
        }
    }
}

