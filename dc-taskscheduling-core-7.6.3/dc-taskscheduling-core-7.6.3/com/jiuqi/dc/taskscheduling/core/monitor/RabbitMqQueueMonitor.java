/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.taskschedule.stream.core.config.EntMqStreamConfig
 *  com.jiuqi.dc.base.common.utils.LogUtil
 *  org.springframework.amqp.core.QueueInformation
 *  org.springframework.amqp.rabbit.core.RabbitAdmin
 *  org.springframework.amqp.rabbit.listener.BlockingQueueConsumer
 *  org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
 */
package com.jiuqi.dc.taskscheduling.core.monitor;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.taskschedule.stream.core.config.EntMqStreamConfig;
import com.jiuqi.dc.base.common.utils.LogUtil;
import com.jiuqi.dc.taskscheduling.core.monitor.RabbitMessageQueueInfo;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.BlockingQueueConsumer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

public class RabbitMqQueueMonitor {
    private final Map<String, SimpleMessageListenerContainer> queueContainerMap = new ConcurrentHashMap<String, SimpleMessageListenerContainer>();
    private final Map<String, BlockingQueueConsumer> queueConsumerMap = new ConcurrentHashMap<String, BlockingQueueConsumer>();
    private static RabbitMqQueueMonitor rabbitMqQueueMonitor = new RabbitMqQueueMonitor();
    private Logger logger = LoggerFactory.getLogger(RabbitMqQueueMonitor.class);
    private int defaultConsumers;
    private boolean enable = true;

    private RabbitMqQueueMonitor() {
        EntMqStreamConfig entMqStreamConfig = SpringContextUtils.getApplicationContext().getBean(EntMqStreamConfig.class);
        this.defaultConsumers = entMqStreamConfig.getConcurrency();
    }

    public static RabbitMqQueueMonitor getInstance() {
        return rabbitMqQueueMonitor;
    }

    private SimpleMessageListenerContainer getContainerByQueueName(String queueName) {
        return this.queueContainerMap.get(queueName);
    }

    public void regisiterContainer(SimpleMessageListenerContainer container, BlockingQueueConsumer consumer) {
        Arrays.stream(container.getQueueNames()).forEach(queueName -> {
            this.queueContainerMap.putIfAbsent((String)queueName, container);
            this.queueConsumerMap.putIfAbsent((String)queueName, consumer);
        });
    }

    public void switchEnableState() {
        this.enable = !this.enable;
    }

    public void recordModuleQueueInfo(String moduleName) {
        if (!this.enable) {
            return;
        }
        List<RabbitMessageQueueInfo> infoList = this.listAllQueueStateByModule(moduleName);
        if (Objects.isNull(infoList) || infoList.isEmpty()) {
            return;
        }
        boolean needRecord = false;
        for (RabbitMessageQueueInfo info2 : infoList) {
            if (info2.getActiveConsumer() >= this.defaultConsumers) continue;
            needRecord = true;
            break;
        }
        if (needRecord) {
            StringJoiner log = new StringJoiner("\n");
            infoList.stream().forEach(info -> log.add(info.toString()));
            this.logger.info(log.toString());
        }
    }

    public void reocrdQueueInfo(String queueName) {
        if (!this.enable) {
            return;
        }
        RabbitMessageQueueInfo info = this.getQueueDetailInfo(queueName);
        this.logger.info(info.toString());
    }

    public RabbitMessageQueueInfo getQueueDetailInfo(String queueName) {
        RabbitMessageQueueInfo info = new RabbitMessageQueueInfo(queueName);
        SimpleMessageListenerContainer container = this.getContainerByQueueName(queueName);
        if (Objects.isNull(container)) {
            info.setErrorMsg(String.format("\u6ca1\u6709\u627e\u5230\u961f\u5217%1$s\u7684\u76d1\u542c\u5bb9\u5668SimpleMessageListenerContainer\n", queueName));
            return info;
        }
        info.setActiveConsumer(container.getActiveConsumerCount());
        info.setActive(container.isActive());
        info.setRunning(container.isRunning());
        try {
            RabbitAdmin rabbitAdmin = new RabbitAdmin(container.getConnectionFactory());
            QueueInformation queueInfo = rabbitAdmin.getQueueInfo(queueName);
            if (Objects.nonNull(queueInfo)) {
                info.setConsumer(queueInfo.getConsumerCount());
                info.setMessageCount(queueInfo.getMessageCount());
            } else {
                info.setErrorMsg(String.format("\u6ca1\u6709\u627e\u5230\u961f\u5217%1$s\u7684\u72b6\u6001\u4fe1\u606f\n", queueName));
            }
        }
        catch (Exception e) {
            info.setErrorMsg(LogUtil.getExceptionStackStr((Throwable)e));
        }
        return info;
    }

    public List<RabbitMessageQueueInfo> listAllQueueStateByModule(String moduleName) {
        return this.queueContainerMap.keySet().stream().filter(queueName -> queueName.contains(moduleName)).map(queueName -> this.getQueueDetailInfo((String)queueName)).sorted((o1, o2) -> o1.getQueueName().compareTo(o2.getQueueName())).collect(Collectors.toList());
    }

    public String restart(String queueName) {
        SimpleMessageListenerContainer container = this.getContainerByQueueName(queueName);
        if (Objects.isNull(container)) {
            return String.format("\u6ca1\u6709\u627e\u5230\u961f\u5217%1$s\u7684\u76d1\u542c\u5bb9\u5668SimpleMessageListenerContainer\n", queueName);
        }
        if (container.isActive() && container.isRunning()) {
            container.setConcurrentConsumers(this.defaultConsumers);
            return String.format("\u5df2\u91cd\u65b0\u8bbe\u7f6e\u961f\u5217%1$s\u7684\u6d88\u8d39\u8005\u6570\u91cf\u4e3a%2$d\uff0c\u8bf7\u7a0d\u540e\u91cd\u65b0\u67e5\u8be2\u961f\u5217\u72b6\u6001\n", queueName, this.defaultConsumers);
        }
        try {
            Thread.sleep(10000L);
        }
        catch (InterruptedException e) {
            this.logger.error("\u5f53\u524d\u7ebf\u7a0b\u7b49\u5f85\u91cd\u542f\u5f02\u5e38", e);
        }
        container.start();
        return String.format("\u5df2\u91cd\u65b0\u542f\u52a8\u961f\u5217%1$s\uff0c\u8bf7\u7a0d\u540e\u91cd\u65b0\u67e5\u8be2\u961f\u5217\u72b6\u6001\n", queueName);
    }
}

