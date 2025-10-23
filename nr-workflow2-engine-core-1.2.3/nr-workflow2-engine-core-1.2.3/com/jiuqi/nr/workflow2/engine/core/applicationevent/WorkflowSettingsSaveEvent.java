/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.applicationevent;

import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import org.springframework.context.ApplicationEvent;

public class WorkflowSettingsSaveEvent
extends ApplicationEvent {
    private final String taskId;
    private final WorkflowSettingsDO originalSettingsDO;
    private final WorkflowSettingsDO targetSettingsDO;

    public WorkflowSettingsSaveEvent(String taskId, WorkflowSettingsDO originalSettingsDO, WorkflowSettingsDO targetSettingsDO) {
        super("\u586b\u62a5\u8ba1\u5212\u8bbe\u7f6e\u4fdd\u5b58\u4e8b\u4ef6");
        this.taskId = taskId;
        this.originalSettingsDO = originalSettingsDO;
        this.targetSettingsDO = targetSettingsDO;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public WorkflowSettingsDO getOriginalSettingsDO() {
        return this.originalSettingsDO;
    }

    public WorkflowSettingsDO getTargetSettingsDO() {
        return this.targetSettingsDO;
    }
}

