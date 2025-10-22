/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.planpublish.dao;

import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import java.util.List;

public interface TaskPlanPublishDao {
    public TaskPlanPublish queryWorkPlan(String var1) throws Exception;

    default public String queryWorkPlanKey(String taskKey) throws Exception {
        TaskPlanPublish taskPlanPublish = this.queryWorkPlan(taskKey);
        return null == taskPlanPublish ? null : taskPlanPublish.getKey();
    }

    public List<TaskPlanPublish> queryAllProtectingWorkPlan() throws Exception;

    public TaskPlanPublish getByTask(String var1);

    public List<TaskPlanPublish> listTask(String var1);

    public TaskPlanPublish query(String var1) throws Exception;

    public List<TaskPlanPublish> queryAllPublishing() throws Exception;

    public void insert(TaskPlanPublish var1) throws Exception;

    public void update(TaskPlanPublish var1) throws Exception;

    default public void updatePublishStatus(String key, String publishStatus) throws Exception {
        TaskPlanPublish taskPlanPublish = this.query(key);
        if (null != taskPlanPublish) {
            taskPlanPublish.setPublishStatus(publishStatus);
            this.update(taskPlanPublish);
        }
    }

    default public void undoPlan(String planKey) throws Exception {
        TaskPlanPublish taskPlanPublish = this.query(planKey);
        if (null != taskPlanPublish) {
            taskPlanPublish.setWorkStatus("undo");
            this.update(taskPlanPublish);
        }
    }

    default public void updateMessageJobKeys(String planKey, String messageJobKeys) throws Exception {
        TaskPlanPublish taskPlanPublish = this.query(planKey);
        if (null != taskPlanPublish) {
            taskPlanPublish.setMessagejobKeys(messageJobKeys);
            this.update(taskPlanPublish);
        }
    }

    public void undoOtherPlan(String var1, String var2) throws Exception;

    public void deleteByTaskKey(String var1);
}

