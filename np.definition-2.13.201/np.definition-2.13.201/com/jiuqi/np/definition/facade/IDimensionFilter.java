/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.common.DimensionFilterListType;
import com.jiuqi.np.definition.common.DimensionFilterType;
import com.jiuqi.np.definition.facade.IMetaItem;
import java.util.List;

public interface IDimensionFilter
extends IMetaItem,
Cloneable {
    public String getEntityId();

    public String getTaskKey();

    public String getFormSchemeKey();

    public DimensionFilterType getType();

    public DimensionFilterListType getListType();

    public List<String> getList();

    public String getValue();
}

