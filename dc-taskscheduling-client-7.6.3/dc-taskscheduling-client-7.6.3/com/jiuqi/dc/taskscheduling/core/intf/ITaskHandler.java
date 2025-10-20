/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.dc.taskscheduling.core.intf;

import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.core.service.MessageHandleService;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Map;
import java.util.Objects;
import org.apache.shiro.util.ThreadContext;

public interface ITaskHandler {
    public String getName();

    public String getTitle();

    public String getPreTask();

    public TaskTypeEnum getTaskType();

    public Map<String, String> getHandleParams(String var1);

    @Deprecated
    public TaskHandleResult handleTask(String var1);

    public TaskHandleResult handleTask(String var1, ITaskProgressMonitor var2);

    public InstanceTypeEnum getInstanceType();

    public String getModule();

    public IDimType getDimType();

    public boolean enable(String var1, String var2);

    public String getSpecialQueueFlag();

    default public void afterSubtasksHandle(String param) {
    }

    default public boolean sendPostTaskMsgWhileHandleTask() {
        return false;
    }

    default public void sendPostTaskMsg(String preParam) throws Exception {
        TaskHandleMsg msg = (TaskHandleMsg)ThreadContext.get((Object)"TASKHANDLEMSG_KEY");
        if (Objects.isNull(msg)) {
            return;
        }
        MessageHandleService messageHandleService = (MessageHandleService)ApplicationContextRegister.getBean(MessageHandleService.class);
        messageHandleService.sendPostTaskMsg(msg, preParam);
    }

    default public Boolean isDimSerialExecute() {
        return false;
    }
}

