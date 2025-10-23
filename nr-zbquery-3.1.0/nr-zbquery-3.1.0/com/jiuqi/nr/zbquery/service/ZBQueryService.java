/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 */
package com.jiuqi.nr.zbquery.service;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.nr.zbquery.ZBQueryException;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.PageInfo;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;

public interface ZBQueryService {
    public GridData query(ZBQueryModel var1, ConditionValues var2, PageInfo var3) throws ZBQueryException;
}

