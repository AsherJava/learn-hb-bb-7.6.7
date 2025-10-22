/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import java.util.Map;

public interface ITableConditionProvider {
    default public Map<String, String> getTableCondition(ExecutorContext context, String tableName) {
        return null;
    }
}

