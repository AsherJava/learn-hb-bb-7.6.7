/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine;

import com.jiuqi.nr.bql.dataengine.ICommonQuery;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public interface IGroupingQuery
extends ICommonQuery {
    public void addGroupColumn(int var1);

    public int addGroupColumn(ColumnModelDefine var1);

    public void setWantDetail(boolean var1);

    public AggrType getGatherType(int var1);

    public void setGatherType(int var1, AggrType var2);
}

