/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.planpublish.service;

import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObj;

public interface SendMessageService {
    public void sendMessageToUser(String var1, String var2) throws Exception;

    public void createSendMessageJobs(String var1, TaskPlanPublishObj var2) throws Exception;
}

