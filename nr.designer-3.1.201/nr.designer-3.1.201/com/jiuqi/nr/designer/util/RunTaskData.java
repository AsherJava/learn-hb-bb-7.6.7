/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.designer.util;

import com.jiuqi.nr.definition.facade.TaskDefine;

public class RunTaskData {
    private TaskDefine taskDefine;
    private String order;

    public RunTaskData(TaskDefine taskDefine, String order) {
        this.taskDefine = taskDefine;
        this.order = order;
    }

    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

