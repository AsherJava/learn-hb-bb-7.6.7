/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.impl.ITableConditionProvider;
import com.jiuqi.np.dataengine.queryparam.TableNameFindParam;

public interface ITableNameFinder
extends ITableConditionProvider {
    public String findTableName(ExecutorContext var1, TableNameFindParam var2);
}

