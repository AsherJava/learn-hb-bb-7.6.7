/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.DataFieldMappingConverter
 *  com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter
 */
package com.jiuqi.nr.attachment.provider;

import com.jiuqi.nr.dataservice.core.common.DataFieldMappingConverter;
import com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter;

public interface IFileCopyParamProvider {
    public DataFieldMappingConverter getDataFieldMappingConverter();

    public DimensionMappingConverter getDimensionMappingConverter();
}

