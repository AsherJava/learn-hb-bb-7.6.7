/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.taskschedule.stream.util.EntMqStreamDynamicUtils
 *  com.jiuqi.dc.base.common.utils.Pair
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO
 *  com.jiuqi.np.cache.message.MessageSubscriber
 *  com.jiuqi.np.cache.message.Subscriber
 *  com.jiuqi.nvwa.remote.event.RemoteSeviceChangeEvent
 *  com.jiuqi.nvwa.remote.util.ServiceStatus
 */
package com.jiuqi.dc.taskscheduling.core.subscribe;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.taskschedule.stream.util.EntMqStreamDynamicUtils;
import com.jiuqi.dc.base.common.utils.Pair;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import com.jiuqi.dc.taskscheduling.core.consumer.TaskHandlerStreamConsumer;
import com.jiuqi.dc.taskscheduling.core.util.TaskHandlerManager;
import com.jiuqi.np.cache.message.MessageSubscriber;
import com.jiuqi.np.cache.message.Subscriber;
import com.jiuqi.nvwa.remote.event.RemoteSeviceChangeEvent;
import com.jiuqi.nvwa.remote.util.ServiceStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Subscriber(channels={"TASKSCHEDULING_REGIST_CHANNEL"})
public class TaskHandlerRegistSubscriber
implements MessageSubscriber {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Value(value="${jiuqi.dc.task.regist.retrylimit:10}")
    private Integer registRetryLimit = 10;
    @Value(value="${spring.application.name}")
    private String currAppName;

    public void onMessage(String channel, Object message, boolean fromThisInstance) {
        try {
            this.logger.debug("\u5f00\u59cb\u52a8\u6001\u521b\u5efa\u961f\u5217\uff0c\u961f\u5217{}\uff0c\u6d88\u606f{}", (Object)channel, message);
            if (Objects.nonNull(message) && message instanceof RemoteSeviceChangeEvent) {
                RemoteSeviceChangeEvent event = (RemoteSeviceChangeEvent)message;
                ServiceStatus status = event.getStatus();
                String applicationName = event.getServiceName();
                if (Objects.equals(status, ServiceStatus.OFFLINE) && !Objects.equals(applicationName, this.currAppName)) {
                    List<TaskHandlerVO> offLineAppHandlers = TaskHandlerManager.getHandlers().stream().filter(task -> task.getApplicationNames().size() == 1 && Objects.equals(task.getApplicationNames().get(0), applicationName)).collect(Collectors.toList());
                    this.execute();
                    HashSet taskConsumerSet = new HashSet();
                    offLineAppHandlers.forEach(taskHandler -> taskConsumerSet.addAll(this.getBindingDestinations((TaskHandlerVO)taskHandler)));
                    for (String taskConsumer : taskConsumerSet) {
                        EntMqStreamDynamicUtils.removeQueueBinding((String)taskConsumer);
                        this.logger.info("\u3010{}\u3011\u670d\u52a1\u7684\u3010{}\u3011\u961f\u5217\u7684\u6d88\u606f\u751f\u4ea7\u8005\u5df2\u7ecf\u89e3\u9664\u7ed1\u5b9a\u3002\n", (Object)applicationName, (Object)taskConsumerSet);
                    }
                } else {
                    this.execute();
                }
            } else {
                this.execute();
            }
            this.logger.debug("\u5b8c\u6210\u52a8\u6001\u521b\u5efa\u961f\u5217\uff0c\u961f\u5217{}\uff0c\u6d88\u606f{}", (Object)channel, message);
        }
        catch (Exception e) {
            this.logger.error("\u52a8\u6001\u521b\u5efa\u961f\u5217\u53d1\u751f\u9519\u8bef", e);
        }
    }

    private void execute() {
        TaskHandlerClient curHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        Map<String, TaskHandlerVO> handlerMap = new ArrayList((Collection)curHandlerClient.getHandlerList().getData()).stream().collect(Collectors.toMap(TaskHandlerVO::getName, v -> v));
        List remoteServiceNames = this.taskHandlerFactory.getRemoteServiceApplicationNames();
        if (!CollectionUtils.isEmpty((Collection)remoteServiceNames)) {
            for (String applicationName : this.taskHandlerFactory.getRemoteServiceApplicationNames()) {
                List<TaskHandlerVO> taskHandlerList = this.findRegistTaskHandler(applicationName);
                if (CollectionUtils.isEmpty(taskHandlerList)) {
                    this.logger.info("\u5fae\u670d\u52a1\u3010{}\u3011\u83b7\u53d6\u5230\u7684\u4efb\u52a1\u5217\u8868\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c\u3002\n", (Object)applicationName);
                    continue;
                }
                taskHandlerList.forEach(handler -> {
                    if (handlerMap.containsKey(handler.getName())) {
                        ((TaskHandlerVO)handlerMap.get(handler.getName())).addApplicationNames(handler.getApplicationNames());
                    } else {
                        handlerMap.put(handler.getName(), (TaskHandlerVO)handler);
                    }
                });
            }
        }
        List<TaskHandlerVO> handlerList = handlerMap.values().stream().collect(Collectors.toList());
        HashSet taskConsumerSet = new HashSet();
        HashSet onlyInputConsumerSet = new HashSet();
        handlerList.forEach(taskHandler -> {
            if (!taskHandler.getApplicationNames().contains(this.currAppName)) {
                onlyInputConsumerSet.addAll(this.getBindingDestinations((TaskHandlerVO)taskHandler));
            } else {
                taskConsumerSet.addAll(this.getBindingDestinations((TaskHandlerVO)taskHandler));
            }
        });
        TaskHandlerManager.getInstance().refreshTreeNode(handlerList);
        for (String taskConsumer : taskConsumerSet) {
            EntMqStreamDynamicUtils.addQueueToStream((String)taskConsumer, (boolean)true, TaskHandlerStreamConsumer.class);
        }
        for (String taskConsumer : onlyInputConsumerSet) {
            EntMqStreamDynamicUtils.addQueueToStream((String)taskConsumer, (boolean)false, TaskHandlerStreamConsumer.class);
        }
    }

    private List<String> getBindingDestinations(TaskHandlerVO handler) {
        if (!StringUtils.isEmpty((String)handler.getSpecialQueueFlag())) {
            return CollectionUtils.newArrayList((Object[])new String[]{TaskHandlerManager.getBindingDestination(handler, 0)});
        }
        ArrayList bindingDestinations = CollectionUtils.newArrayList();
        switch (handler.getTaskType()) {
            case LEVEL: {
                bindingDestinations.add(TaskHandlerManager.getBindingDestination(handler, 1));
                bindingDestinations.add(TaskHandlerManager.getBindingDestination(handler, 2));
                bindingDestinations.add(TaskHandlerManager.getBindingDestination(handler, 3));
                return bindingDestinations;
            }
            case POST: {
                bindingDestinations.add(TaskHandlerManager.getBindingDestination(handler, 1));
                bindingDestinations.add(TaskHandlerManager.getBindingDestination(handler, 2));
                bindingDestinations.add(TaskHandlerManager.getBindingDestination(handler, 3));
                bindingDestinations.add(TaskHandlerManager.getBindingDestination(handler, 4));
                bindingDestinations.add(TaskHandlerManager.getBindingDestination(handler, 5));
                return bindingDestinations;
            }
        }
        throw new RuntimeException(String.format("\u4efb\u52a1\u5904\u7406\u5668\u3010%1$s\u3011\u4efb\u52a1\u7c7b\u578b\u5c5e\u6027\u5f02\u5e38", handler.getName()));
    }

    public List<TaskHandlerVO> findRegistTaskHandler(String applicationName) {
        for (int i = 1; i <= this.registRetryLimit; ++i) {
            Pair<Boolean, List<TaskHandlerVO>> registTaskHandlerResult = this.findTaskHandlerByModule(applicationName, i);
            if (Boolean.TRUE.equals(registTaskHandlerResult.getFirst())) {
                return (List)registTaskHandlerResult.getSecond();
            }
            try {
                Thread.sleep(60000L);
                continue;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                this.logger.error("\u5fae\u670d\u52a1\u3010{}\u3011\u7b2c{}\u6b21\u4efb\u52a1\u83b7\u53d6\u4ea7\u751f\u5f02\u5e38\uff0c\u8fd4\u56de\u7a7a\u503c\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a{}\n", applicationName, i, e);
                return null;
            }
        }
        this.logger.error("\u5fae\u670d\u52a1\u3010{}\u3011\u5c1d\u8bd5{}\u6b21\u6ce8\u518c\u5747\u5931\u8d25\uff0c\u81ea\u52a8\u8df3\u8fc7\u6ce8\u518c\n", (Object)applicationName, (Object)this.registRetryLimit);
        return null;
    }

    public Pair<Boolean, List<TaskHandlerVO>> findTaskHandlerByModule(String applicationName, int attempts) {
        try {
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getTaskHandlerClientByAppName(applicationName);
            BusinessResponseEntity response = taskHandlerClient.getHandlerList();
            if (response.isSuccess()) {
                this.logger.info("\u5fae\u670d\u52a1\u3010{}\u3011\u7b2c{}\u6b21\u4efb\u52a1\u6ce8\u518c\u5b8c\u6210\uff0c\u4efb\u52a1\u8be6\u7ec6\u4fe1\u606f\uff1a{}\n", applicationName, attempts, JsonUtils.writeValueAsString((Object)response.getData()));
                return new Pair((Object)true, response.getData());
            }
            this.logger.error("\u5fae\u670d\u52a1\u3010{}\u3011\u7b2c{}\u6b21\u4efb\u52a1\u83b7\u53d6\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a{}\n", applicationName, attempts, response.getErrorDetail());
        }
        catch (Exception e) {
            this.logger.error("\u5fae\u670d\u52a1\u3010{}\u3011\u7b2c{}\u6b21\u63a5\u53e3\u8c03\u7528\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a{}\n", applicationName, attempts, e.getMessage(), e);
        }
        return new Pair((Object)false, null);
    }
}

