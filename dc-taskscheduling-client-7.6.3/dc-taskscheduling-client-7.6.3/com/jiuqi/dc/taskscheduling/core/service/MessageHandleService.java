/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.core.service;

import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;

public interface MessageHandleService {
    public void handlerMsg(TaskHandleMsg var1);

    public void sendPostTaskMsg(TaskHandleMsg var1, String var2);
}

