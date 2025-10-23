/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.nr.subdatabase.controller.Impl;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.subdatabase.controller.ISplitTableHelperExecutor;
import org.springframework.stereotype.Component;

@Component(value="defaultSplitTableExecutor")
public class DefaultSplitTableExecutorImpl
implements ISplitTableHelperExecutor {
    @Override
    public boolean isEnable(ExecutorContext context, String tableName) {
        return true;
    }

    @Override
    public int getOrder() {
        return 10;
    }

    @Override
    public String getCurrentSplitTableName(ExecutorContext context, String tableName) {
        return tableName;
    }
}

