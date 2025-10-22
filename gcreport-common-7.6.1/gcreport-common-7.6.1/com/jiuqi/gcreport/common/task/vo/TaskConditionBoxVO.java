/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.task.vo;

import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.common.task.vo.TaskOrgDataVO;
import java.util.List;

public class TaskConditionBoxVO {
    private String taskId;
    private String taskTitle;
    private List<Scheme> schemeList;
    private Integer enableMultiOrg = 0;
    private List<TaskOrgDataVO> entityScopeList;

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

    public Integer getEnableMultiOrg() {
        return this.enableMultiOrg;
    }

    public void setEnableMultiOrg(Integer enableMultiOrg) {
        this.enableMultiOrg = enableMultiOrg;
    }

    public List<TaskOrgDataVO> getEntityScopeList() {
        return this.entityScopeList;
    }

    public void setEntityScopeList(List<TaskOrgDataVO> entityScopeList) {
        this.entityScopeList = entityScopeList;
    }
}

