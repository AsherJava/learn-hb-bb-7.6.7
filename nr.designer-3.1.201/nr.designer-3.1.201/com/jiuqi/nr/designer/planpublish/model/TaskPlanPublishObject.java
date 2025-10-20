/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish
 */
package com.jiuqi.nr.designer.planpublish.model;

import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;

public class TaskPlanPublishObject {
    private String key;
    private String taskKey;
    private String jobKey;
    private String publishStatus;
    private String publishDate;

    public TaskPlanPublishObject(TaskPlanPublish taskPlanPublish) {
        this.key = taskPlanPublish.getKey();
        this.taskKey = taskPlanPublish.getTaskKey();
        this.jobKey = taskPlanPublish.getJobKey();
        this.publishStatus = taskPlanPublish.getPublishStatus();
        this.publishDate = taskPlanPublish.getPublishDate();
    }

    public TaskPlanPublishObject() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getJobKey() {
        return this.jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    public String getPublishStatus() {
        return this.publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}

