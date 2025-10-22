/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuqi.nr.unit.uselector.web.service;

import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.uselector.web.request.FilterSetRemoveParam;
import com.jiuqi.nr.unit.uselector.web.request.FilterSetSortParam;
import com.jiuqi.nr.unit.uselector.web.request.FilterSetTagParam;
import java.util.List;

public interface IUSelectorResultSetService {
    public List<IBaseNodeData> getPageFilterSet(String var1, Integer var2, Integer var3);

    public String sortFilterSet(FilterSetSortParam var1);

    public int removeListFromFilterSet(FilterSetRemoveParam var1);

    public int tagFilterSet(FilterSetTagParam var1);
}

