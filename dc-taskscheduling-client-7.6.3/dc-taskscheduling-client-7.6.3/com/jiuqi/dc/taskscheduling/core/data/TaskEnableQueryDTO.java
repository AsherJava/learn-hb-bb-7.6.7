/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.core.data;

import java.io.Serializable;

public class TaskEnableQueryDTO
implements Serializable {
    private static final long serialVersionUID = -1926144017468188228L;
    private String taskName;
    private String preTaskName;
    private String preParam;

    public TaskEnableQueryDTO() {
    }

    public TaskEnableQueryDTO(String taskName, String preTaskName, String preParam) {
        this.taskName = taskName;
        this.preTaskName = preTaskName;
        this.preParam = preParam;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPreTaskName() {
        return this.preTaskName;
    }

    public void setPreTaskName(String preTaskName) {
        this.preTaskName = preTaskName;
    }

    public String getPreParam() {
        return this.preParam;
    }

    public void setPreParam(String preParam) {
        this.preParam = preParam;
    }
}

