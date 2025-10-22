/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import com.jiuqi.gcreport.intermediatelibrary.vo.Scheme;
import java.util.List;

public class TaskConditionBoxVO {
    private String taskId;
    private String taskTitle;
    private List<Scheme> schemeList;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public List<Scheme> getSchemeList() {
        return this.schemeList;
    }

    public void setSchemeList(List<Scheme> schemeList) {
        this.schemeList = schemeList;
    }
}

