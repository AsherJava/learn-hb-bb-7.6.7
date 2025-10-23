/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.entity;

import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInEndTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.ManualTerminationConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowSelfControl;

public interface WorkflowOtherSettings {
    public FillInStartTimeConfig getFillInStartTimeConfig();

    public FillInEndTimeConfig getFillInEndTimeConfig();

    public ManualTerminationConfig getManualTerminationConfig();

    public WorkflowSelfControl getWorkflowSelfControl();
}

