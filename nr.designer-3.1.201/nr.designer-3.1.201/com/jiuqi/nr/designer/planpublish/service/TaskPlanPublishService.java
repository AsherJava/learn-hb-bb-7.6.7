/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.planpublish.service;

import com.jiuqi.nr.designer.planpublish.model.ChangePlanPublishDate;
import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObj;
import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObject;

public interface TaskPlanPublishService {
    public TaskPlanPublishObject queryPublishStatusByTaskKey(String var1) throws Exception;

    public String directProtectedPublish(TaskPlanPublishObj var1) throws Exception;

    public void planProtectedPublish(TaskPlanPublishObj var1) throws Exception;

    default public String publishTask(String taskID, String deployTaskID, String language, String activeFormId, String ownGroupId, String activedSchemeId) throws Exception {
        return this.publishTask(taskID, deployTaskID, language, activeFormId, ownGroupId, activedSchemeId, true);
    }

    public String publishTask(String var1, String var2, String var3, String var4, String var5, String var6, boolean var7) throws Exception;

    public void planPublishTask(String var1, String var2) throws Exception;

    public void changePlanPublishDate(ChangePlanPublishDate var1) throws Exception;

    public void cancelPlanPublishDate(ChangePlanPublishDate var1) throws Exception;

    public String beforeTaskPublish(String var1, String var2, String var3, String var4) throws Exception;

    public void afterTaskPublish(String var1, String var2, boolean var3, String var4) throws Exception;

    public String protectTask(String var1) throws Exception;

    public void cancelProtectTask(TaskPlanPublishObject var1) throws Exception;

    public int checkPublishStatusWhenSysStart() throws Exception;
}

