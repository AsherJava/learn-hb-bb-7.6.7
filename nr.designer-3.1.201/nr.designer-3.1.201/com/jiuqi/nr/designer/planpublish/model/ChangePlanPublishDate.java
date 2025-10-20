/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.planpublish.model;

import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObj;

public class ChangePlanPublishDate {
    private String planKey;
    private String taskKey;
    private String jobKey;
    private TaskPlanPublishObj taskPlanPublishObj;

    public String getPlanKey() {
        return this.planKey;
    }

    public void setPlanKey(String planKey) {
        this.planKey = planKey;
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

    public TaskPlanPublishObj getTaskPlanPublishObj() {
        return this.taskPlanPublishObj;
    }

    public void setTaskPlanPublishObj(TaskPlanPublishObj taskPlanPublishObj) {
        this.taskPlanPublishObj = taskPlanPublishObj;
    }
}

