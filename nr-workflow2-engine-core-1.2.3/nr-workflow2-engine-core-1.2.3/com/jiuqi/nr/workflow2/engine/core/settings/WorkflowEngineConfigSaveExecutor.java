/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 */
package com.jiuqi.nr.workflow2.engine.core.settings;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;

public interface WorkflowEngineConfigSaveExecutor {
    public String getWorkflowEngineName();

    public boolean executeSave(WorkflowSettingsDO var1, String var2);

    public void asyncExecuteSave(WorkflowSettingsDO var1, String var2, IProgressMonitor var3);
}

