/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.intf;

import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public interface ISqlConditionProcesser {
    public void beforeCondition(QueryTable var1, String var2, StringBuilder var3, String var4, String var5);

    public boolean acceptFieldCondition(ColumnModelDefine var1);
}

