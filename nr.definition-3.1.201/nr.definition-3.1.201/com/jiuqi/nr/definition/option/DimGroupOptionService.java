/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option;

import com.jiuqi.nr.definition.option.dto.DimensionGroupDTO;
import com.jiuqi.nr.definition.option.treegroup.GroupInfo;

public interface DimGroupOptionService {
    public DimensionGroupDTO getDimensionGroup(String var1);

    public String[] getGroupOptionValue(String var1);

    public String[] getFilterOptionValue(String var1);

    public GroupInfo getGroupInfo(String var1);
}

