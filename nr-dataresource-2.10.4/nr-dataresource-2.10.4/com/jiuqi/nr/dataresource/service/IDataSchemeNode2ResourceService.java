/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 */
package com.jiuqi.nr.dataresource.service;

import com.jiuqi.nr.dataresource.web.param.CheckedParam;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import java.util.Set;

public interface IDataSchemeNode2ResourceService {
    public void add(CheckedParam var1);

    public Set<RuntimeDataSchemeNodeDTO> queryAllChildren(DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> var1, NodeFilter var2);
}

