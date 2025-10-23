/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.DataFieldMappingConverter
 *  com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter
 */
package com.jiuqi.nr.datacopy.param;

import com.jiuqi.nr.dataservice.core.common.DataFieldMappingConverter;
import com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter;

public class DataCopyParamProvider {
    private DataFieldMappingConverter dataFieldMappingConverter;
    private DimensionMappingConverter dimensionMappingConverter;

    public DataCopyParamProvider(DataFieldMappingConverter dataFieldMappingConverter, DimensionMappingConverter dimensionMappingConverter) {
        this.dataFieldMappingConverter = dataFieldMappingConverter;
        this.dimensionMappingConverter = dimensionMappingConverter;
    }

    public DataFieldMappingConverter getDataFieldMappingConverter() {
        return this.dataFieldMappingConverter;
    }

    public DimensionMappingConverter getDimensionMappingConverter() {
        return this.dimensionMappingConverter;
    }
}

