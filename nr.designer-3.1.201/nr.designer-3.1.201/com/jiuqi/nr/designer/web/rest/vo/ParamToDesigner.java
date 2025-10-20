/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.jiuqi.nr.definition.common.TaskType
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.definition.common.TaskType;
import java.util.List;

public class ParamToDesigner {
    @JsonInclude
    private String activedTaskId;
    @JsonInclude
    private String activedSchemeId;
    @JsonInclude
    private String activedGroupId;
    @JsonInclude
    private String activedFormId;
    @JsonInclude
    private TaskType taskType = TaskType.TASK_TYPE_DEFAULT;
    @JsonInclude
    private String dataScheme;
    @JsonInclude
    private String dimKey;
    @JsonInclude
    private List<String> orgList;
    @JsonInclude
    private String dw;

    public String getDw() {
        return this.dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public List<String> getOrgList() {
        return this.orgList;
    }

    public void setOrgList(List<String> orgList) {
        this.orgList = orgList;
    }

    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public String getActivedTaskId() {
        return this.activedTaskId;
    }

    public void setActivedTaskId(String activedTaskId) {
        this.activedTaskId = activedTaskId;
    }

    public String getActivedSchemeId() {
        return this.activedSchemeId;
    }

    public void setActivedSchemeId(String activedSchemeId) {
        this.activedSchemeId = activedSchemeId;
    }

    public String getActivedGroupId() {
        return this.activedGroupId;
    }

    public void setActivedGroupId(String activedGroupId) {
        this.activedGroupId = activedGroupId;
    }

    public String getActivedFormId() {
        return this.activedFormId;
    }

    public void setActivedFormId(String activedFormId) {
        this.activedFormId = activedFormId;
    }

    public TaskType getTaskType() {
        return this.taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
}

