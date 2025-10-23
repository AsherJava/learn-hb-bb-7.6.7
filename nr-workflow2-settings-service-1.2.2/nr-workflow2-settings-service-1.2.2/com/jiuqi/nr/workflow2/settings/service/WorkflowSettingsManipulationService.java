/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO
 */
package com.jiuqi.nr.workflow2.settings.service;

import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO;

public interface WorkflowSettingsManipulationService {
    public boolean addWorkflowSettings(WorkflowSettingsDTO var1);

    public boolean deleteWorkflowSettings(String var1);

    public boolean updateWorkflowSettings(WorkflowSettingsDTO var1);
}

