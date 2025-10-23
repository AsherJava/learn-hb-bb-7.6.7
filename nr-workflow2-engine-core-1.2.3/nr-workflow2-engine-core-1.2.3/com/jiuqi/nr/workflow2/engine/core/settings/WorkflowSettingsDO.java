/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings;

import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;

public interface WorkflowSettingsDO {
    public String getId();

    public String getTaskId();

    public String getWorkflowEngine();

    public String getWorkflowDefine();

    public boolean isWorkflowEnable();

    public boolean isTodoEnable();

    public WorkflowObjectType getWorkflowObjectType();

    public String getOtherConfig();

    public String getCreateTime();

    public String getUpdateTime();

    public String getOperator();
}

