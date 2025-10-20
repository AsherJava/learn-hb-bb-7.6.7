/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.AutoTaskDTO
 *  com.jiuqi.va.domain.workflow.AutoTaskModule
 */
package com.jiuqi.va.workflow.domain;

import com.jiuqi.va.domain.biz.AutoTaskDTO;
import com.jiuqi.va.domain.workflow.AutoTaskModule;
import java.util.List;

public class AutoTaskTree {
    private AutoTaskModule autoTaskModule;
    private List<AutoTaskDTO> autoTasks;

    public AutoTaskModule getAutoTaskModule() {
        return this.autoTaskModule;
    }

    public void setAutoTaskModule(AutoTaskModule autoTaskModule) {
        this.autoTaskModule = autoTaskModule;
    }

    public List<AutoTaskDTO> getAutoTasks() {
        return this.autoTasks;
    }

    public void setAutoTasks(List<AutoTaskDTO> autoTasks) {
        this.autoTasks = autoTasks;
    }
}

