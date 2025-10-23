/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.listener;

import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.listener.WorkflowSettingsChangeVerification;

public interface IWorkflowSettingsChangeListenner {
    public WorkflowSettingsChangeVerification beforeChange(WorkflowSettingsDO var1, WorkflowSettingsDO var2);

    public void afterChange(WorkflowSettingsDO var1, WorkflowSettingsDO var2);
}

