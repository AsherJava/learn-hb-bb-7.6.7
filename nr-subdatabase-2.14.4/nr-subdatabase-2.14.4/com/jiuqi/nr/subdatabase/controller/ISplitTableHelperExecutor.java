/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.nr.subdatabase.controller;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import java.util.Map;
import org.springframework.core.Ordered;

public interface ISplitTableHelperExecutor
extends Ordered {
    public boolean isEnable(ExecutorContext var1, String var2);

    public String getCurrentSplitTableName(ExecutorContext var1, String var2);

    default public Map<String, String> getSumSchemeKey(ExecutorContext context, String splitTableName) {
        return null;
    }
}

