/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.web.vo;

import com.jiuqi.nr.dafafill.entity.DataFillDefinition;

public class DataFillDefinitionVO
extends DataFillDefinition {
    private String taskCode;
    private String taskTitle;

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
}

