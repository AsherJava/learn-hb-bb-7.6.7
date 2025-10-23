/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowEngineConfigSaveExecutor
 */
package com.jiuqi.nr.workflow2.settings.factory;

import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowEngineConfigSaveExecutor;
import java.util.List;

public interface IConfigSaveExecutorFactory {
    public WorkflowEngineConfigSaveExecutor getConfigSaveExecutor(String var1);

    public List<WorkflowEngineConfigSaveExecutor> getAllConfigSaveExecutors();
}

