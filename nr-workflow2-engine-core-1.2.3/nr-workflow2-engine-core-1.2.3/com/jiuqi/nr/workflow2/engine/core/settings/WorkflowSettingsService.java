/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings;

import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;

public interface WorkflowSettingsService {
    public WorkflowSettingsDO queryWorkflowSettings(String var1);

    public boolean queryTaskWorkflowEnable(String var1);

    public boolean queryTaskTodoEnable(String var1);

    public String queryTaskWorkflowEngine(String var1);

    public String queryTaskWorkflowDefine(String var1);

    public WorkflowObjectType queryTaskWorkflowObjectType(String var1);

    public WorkflowOtherSettings queryTaskWorkflowOtherSettings(String var1);
}

