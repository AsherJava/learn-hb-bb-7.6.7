/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.taskscheduling.api.gather;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.util.ITaskHandlerGather;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaskHandlerGather
implements InitializingBean,
ITaskHandlerGather {
    @Autowired(required=false)
    private List<ITaskHandler> handlerList;
    @Value(value="${spring.application.name}")
    private String currAppName;
    private final Map<String, ITaskHandler> taskHandlerNameMap = new ConcurrentHashMap<String, ITaskHandler>();
    private final List<TaskHandlerVO> taskHandlerList = new ArrayList<TaskHandlerVO>();
    private static Logger logger = LoggerFactory.getLogger(TaskHandlerGather.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        this.initTaskHandler();
    }

    private void initTaskHandler() {
        if (this.handlerList == null) {
            return;
        }
        for (ITaskHandler handler2 : this.handlerList) {
            if (StringUtils.isEmpty((String)handler2.getName())) {
                logger.warn(String.format("\u6570\u636e\u5904\u7406\u4efb\u52a1\u3010%1$s\u3011\u7684\u4efb\u52a1\u540d\u79f0\u4e3a\u7a7a", handler2.getClass().toString()));
                continue;
            }
            if (StringUtils.isEmpty((String)handler2.getTitle())) {
                logger.warn(String.format("\u6570\u636e\u5904\u7406\u4efb\u52a1\u3010%1$s\u3011\u7684\u4efb\u52a1\u6807\u9898\u4e3a\u7a7a", handler2.getClass().toString()));
                continue;
            }
            if (StringUtils.isEmpty((String)handler2.getModule())) {
                logger.warn(String.format("\u6570\u636e\u5904\u7406\u4efb\u52a1\u3010%1$s\u3011\u7684\u4efb\u52a1\u6240\u5c5e\u6a21\u5757\u4e3a\u7a7a", handler2.getClass().toString()));
                continue;
            }
            if (handler2.getTaskType() == null) {
                logger.warn(String.format("\u6570\u636e\u5904\u7406\u4efb\u52a1\u3010%1$s\u3011\u7684\u4efb\u52a1\u7c7b\u578b\u4e3a\u7a7a", handler2.getClass().toString()));
                continue;
            }
            if (handler2.getDimType() == null) {
                logger.warn(String.format("\u6570\u636e\u5904\u7406\u4efb\u52a1\u3010%1$s\u3011\u7684\u5904\u7406\u7ef4\u5ea6\u7c7b\u578b\u4e3a\u7a7a", handler2.getClass().toString()));
                continue;
            }
            if (this.taskHandlerNameMap.containsKey(handler2.getName())) {
                ITaskHandler repeatHandler = this.taskHandlerNameMap.get(handler2.getName());
                if (handler2.getClass().isAssignableFrom(repeatHandler.getClass())) continue;
                if (repeatHandler.getClass().isAssignableFrom(handler2.getClass())) {
                    this.taskHandlerNameMap.put(handler2.getName(), handler2);
                    continue;
                }
                logger.error(String.format("\u6570\u636e\u5904\u7406\u4efb\u52a1\u3010%1$s\u3011\u7684\u4efb\u52a1\u540d\u79f0\u4e0e\u3010%2$s\u3011\u91cd\u590d", handler2.getClass().toString(), this.taskHandlerNameMap.get(repeatHandler.getName()).getClass().toString()));
                continue;
            }
            this.taskHandlerNameMap.put(handler2.getName(), handler2);
        }
        this.taskHandlerList.addAll(this.taskHandlerNameMap.values().stream().map(handler -> new TaskHandlerVO((ITaskHandler)handler, this.currAppName)).collect(Collectors.toList()));
    }

    @Override
    public Map<String, ITaskHandler> getHandlerNameMap() {
        return this.taskHandlerNameMap;
    }

    @Override
    public List<TaskHandlerVO> getHandlerList() {
        return this.taskHandlerList;
    }

    @Override
    public ITaskHandler getITaskHandler(String taskName) {
        return this.taskHandlerNameMap.get(taskName);
    }
}

