/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.core.service;

import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import java.util.List;
import java.util.Map;

public interface TaskSendService {
    public String startTask(String var1, String var2);

    public String startTask(String var1, String var2, Map<String, String> var3);

    public List<String> startSubTask(String var1, String var2, String var3, String var4);

    public void sendMessage(TaskHandleMsg var1);

    public void waitTaskFinished(String var1);
}

