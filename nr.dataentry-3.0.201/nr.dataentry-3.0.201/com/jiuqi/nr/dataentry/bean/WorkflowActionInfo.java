/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.DUserActionParam;

public class WorkflowActionInfo {
    private String code;
    private String title;
    private String taskId;
    private String taskCode;
    private DUserActionParam userActionParam;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public DUserActionParam getUserActionParam() {
        return this.userActionParam;
    }

    public void setUserActionParam(DUserActionParam userActionParam) {
        this.userActionParam = userActionParam;
    }
}

