/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.dataengine;

import com.jiuqi.nr.bql.dataengine.ICommonQuery;
import com.jiuqi.nr.bql.dataengine.IGroupingQuery;

public interface IDataAccessProvider {
    public ICommonQuery newDataQuery();

    public IGroupingQuery newGroupingQuery();

    public ICommonQuery newAccountDataQuery();

    public IGroupingQuery newAccountGroupingQuery();
}

