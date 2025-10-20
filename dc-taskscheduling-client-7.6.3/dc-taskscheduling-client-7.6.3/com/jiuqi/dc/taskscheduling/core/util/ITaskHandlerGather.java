/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.core.util;

import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import java.util.List;
import java.util.Map;

public interface ITaskHandlerGather {
    public Map<String, ITaskHandler> getHandlerNameMap();

    public List<TaskHandlerVO> getHandlerList();

    public ITaskHandler getITaskHandler(String var1);
}

