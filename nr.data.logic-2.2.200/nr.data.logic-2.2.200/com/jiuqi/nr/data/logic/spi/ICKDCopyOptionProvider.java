/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter
 */
package com.jiuqi.nr.data.logic.spi;

import com.jiuqi.nr.data.logic.spi.IFmlMappingProvider;
import com.jiuqi.nr.data.logic.spi.IUnsupportedDesHandler;
import com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter;

public interface ICKDCopyOptionProvider {
    public boolean isPullMode();

    public DimensionMappingConverter getDimensionMappingConverter();

    public IFmlMappingProvider getFmlMappingProvider();

    public IUnsupportedDesHandler getUnsupportedDesHandler();

    public boolean copyBJFml();

    public boolean updateUserTime();
}

