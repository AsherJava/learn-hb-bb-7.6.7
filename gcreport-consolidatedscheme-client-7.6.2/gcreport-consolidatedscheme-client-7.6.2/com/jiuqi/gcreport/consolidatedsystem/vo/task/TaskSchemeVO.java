/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.task;

import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;

public class TaskSchemeVO
extends BaseDataVO {
    private String taskTitle;
    private String taskId;

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}

