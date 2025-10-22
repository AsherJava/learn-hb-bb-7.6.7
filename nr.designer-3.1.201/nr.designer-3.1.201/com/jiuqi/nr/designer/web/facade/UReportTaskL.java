/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObject;

public class UReportTaskL {
    private String key;
    private String code;
    private String title;
    private Boolean canDesign;
    private String createUserName;
    private String createTime;
    private TaskPlanPublishObject taskPlanPublishObject;
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public Boolean getCanDesign() {
        return this.canDesign;
    }

    public void setCanDesign(Boolean canDesign) {
        this.canDesign = canDesign;
    }

    public String getCreateUserName() {
        return this.createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public TaskPlanPublishObject getTaskPlanPublishObject() {
        return this.taskPlanPublishObject;
    }

    public void setTaskPlanPublishObject(TaskPlanPublishObject taskPlanPublishObject) {
        this.taskPlanPublishObject = taskPlanPublishObject;
    }
}

