/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 */
package com.jiuqi.nr.subdatabase.adapter;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.nr.subdatabase.controller.ISplitTableHelperExecutor;
import com.jiuqi.nr.subdatabase.controller.Impl.DefaultSplitTableExecutorImpl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SplitTableAdapter
implements SplitTableHelper {
    @Autowired
    @Qualifier(value="defaultSplitTableExecutor")
    private DefaultSplitTableExecutorImpl splitTableExecutor;
    @Autowired
    private List<ISplitTableHelperExecutor> extendExecutors;

    public String getCurrentSplitTableName(ExecutorContext context, String tableName) {
        return this.getExecutor(context, tableName).getCurrentSplitTableName(context, tableName);
    }

    public Map<String, String> getSumSchemeKey(ExecutorContext context, String splitTableName) {
        return this.getExecutor(context, splitTableName).getSumSchemeKey(context, splitTableName);
    }

    private ISplitTableHelperExecutor getExecutor(ExecutorContext context, String tableName) {
        for (ISplitTableHelperExecutor executor : this.extendExecutors) {
            if (!executor.isEnable(context, tableName)) continue;
            return executor;
        }
        return this.splitTableExecutor;
    }
}

