/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 */
package com.jiuqi.nr.workflow2.settings.service;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.workflow2.settings.dto.WorkflowSettingsManipulationContext;

public interface WorkflowSettingsPersistService {
    public boolean persistConfig(WorkflowSettingsManipulationContext var1);

    public void persistConfig(WorkflowSettingsManipulationContext var1, IProgressMonitor var2) throws RuntimeException;
}

