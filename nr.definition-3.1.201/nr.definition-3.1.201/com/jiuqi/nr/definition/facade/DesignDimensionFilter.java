/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.DimensionFilterListType
 *  com.jiuqi.np.definition.common.DimensionFilterType
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.common.DimensionFilterListType;
import com.jiuqi.np.definition.common.DimensionFilterType;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import java.util.List;

public interface DesignDimensionFilter
extends IDimensionFilter {
    public void setKey(String var1);

    public void setEntityId(String var1);

    public void setTaskKey(String var1);

    public void setType(DimensionFilterType var1);

    public void setListType(DimensionFilterListType var1);

    public void setList(List<String> var1);

    public void setValue(String var1);
}

