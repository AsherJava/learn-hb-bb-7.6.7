/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings;

import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO;

public interface WorkflowSettingsDao {
    public boolean addWorkflowSettings(WorkflowSettingsDTO var1);

    public boolean deleteWorkflowSettings(String var1);

    public boolean updateWorkflowSettings(WorkflowSettingsDTO var1);

    public WorkflowSettingsDO queryWorkflowSettings(String var1);
}

