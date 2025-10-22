/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.impl.ITableConditionProvider;
import java.util.Map;

public interface SplitTableHelper
extends ITableConditionProvider {
    public String getCurrentSplitTableName(ExecutorContext var1, String var2);

    default public Map<String, String> getSumSchemeKey(ExecutorContext context, String splitTableName) {
        return null;
    }

    @Override
    default public Map<String, String> getTableCondition(ExecutorContext context, String tableName) {
        return this.getSumSchemeKey(context, tableName);
    }
}

