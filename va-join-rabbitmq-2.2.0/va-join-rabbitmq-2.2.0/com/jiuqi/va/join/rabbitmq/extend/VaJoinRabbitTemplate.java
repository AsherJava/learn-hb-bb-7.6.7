/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.join.api.common.JoinCacheUtil
 *  com.jiuqi.va.join.api.config.JoinApiConfig
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.join.api.domain.JoinMode
 *  com.jiuqi.va.join.api.domain.JoinSubDeclare
 *  com.jiuqi.va.join.api.domain.ReplyStatus
 *  com.jiuqi.va.join.api.domain.ReplyTo
 *  com.jiuqi.va.join.api.extend.JoinTemplateExtend
 *  com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent
 *  com.rabbitmq.client.Channel
 *  org.springframework.amqp.AmqpIOException
 *  org.springframework.amqp.core.AcknowledgeMode
 *  org.springframework.amqp.core.Address
 *  org.springframework.amqp.core.AmqpAdmin
 *  org.springframework.amqp.core.AmqpTemplate
 *  org.springframework.amqp.core.Binding
 *  org.springframework.amqp.core.BindingBuilder
 *  org.springframework.amqp.core.Message
 *  org.springframework.amqp.core.MessageListener
 *  org.springframework.amqp.core.MessageProperties
 *  org.springframework.amqp.core.Queue
 *  org.springframework.amqp.core.TopicExchange
 *  org.springframework.amqp.rabbit.connection.ConnectionFactory
 *  org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry
 *  org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
 *  org.springframework.amqp.rabbit.listener.adapter.AbstractAdaptableMessageListener
 *  org.springframework.amqp.support.converter.SimpleMessageConverter
 */
package com.jiuqi.va.join.rabbitmq.extend;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.join.api.common.JoinCacheUtil;
import com.jiuqi.va.join.api.config.JoinApiConfig;
import com.jiuqi.va.join.api.domain.JoinDeclare;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.JoinMode;
import com.jiuqi.va.join.api.domain.JoinSubDeclare;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.join.api.extend.JoinTemplateExtend;
import com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent;
import com.rabbitmq.client.Channel;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpIOException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaJoinRabbitTemplate")
@Order(value=-2147483647)
@EnableScheduling
public class VaJoinRabbitTemplate
implements JoinTemplateExtend,
ApplicationListener<StorageSyncFinishedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(VaJoinRabbitTemplate.class);
    @Autowired(required=false)
    private AmqpAdmin amqpAdmin;
    @Autowired(required=false)
    private ConnectionFactory connectionFactory;
    @Autowired(required=false)
    private RabbitListenerEndpointRegistry registry;
    @Autowired(required=false)
    private AmqpTemplate rabbitTemplate;
    @Autowired(required=false)
    @Qualifier(value="topicExchange")
    private TopicExchange topicExchange;
    @Value(value="${spring.rabbitmq.host:}")
    private String rabbitHost = "";
    @Value(value="${spring.rabbitmq.addresses:}")
    private String rabbitAddresses = "";
    private static ConcurrentHashMap<String, Queue> queueCache = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, SimpleMessageListenerContainer> containerCache = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> manualStopFlag = new ConcurrentHashMap();
    private static SimpleMessageConverter coverter = new SimpleMessageConverter();
    private static ConcurrentHashMap<String, String> formerNameMapping = new ConcurrentHashMap();

    public String getName() {
        return "rabbitmq";
    }

    public boolean isEnabled() {
        return StringUtils.hasText(this.rabbitHost) || StringUtils.hasText(this.rabbitAddresses);
    }

    @Override
    public void onApplicationEvent(StorageSyncFinishedEvent event) {
        try {
            if (this.isEnabled()) {
                this.registry.start();
            } else {
                this.registry.stop();
            }
        }
        catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

    public R send(String joinName, String message) {
        try {
            JoinDeclare declare = JoinCacheUtil.exist((String)joinName);
            if (declare.getJoinMode() == JoinMode.QUEUE) {
                this.rabbitTemplate.convertAndSend(joinName, (Object)message);
            } else {
                this.rabbitTemplate.convertAndSend(this.topicExchange.getName(), joinName, (Object)message);
            }
        }
        catch (Throwable e) {
            logger.error(joinName + "\u53d1\u9001\u6d88\u606f\u5f02\u5e38\uff1a" + message, e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    public ReplyTo sendAndReceive(String joinName, String message) {
        try {
            JoinDeclare declare = JoinCacheUtil.exist((String)joinName);
            if (declare.getJoinMode() == JoinMode.QUEUE) {
                Object obj = this.rabbitTemplate.convertSendAndReceive(joinName, (Object)message);
                if (obj == null) {
                    return new ReplyTo(ReplyStatus.SUCESS);
                }
                return (ReplyTo)JSONUtil.parseObject((String)obj.toString(), ReplyTo.class);
            }
            this.rabbitTemplate.convertAndSend(this.topicExchange.getName(), joinName, (Object)message);
            return new ReplyTo(ReplyStatus.SUCESS);
        }
        catch (Throwable e) {
            logger.error(joinName + "\u53d1\u9001\u6d88\u606f\u5f02\u5e38\uff1a" + message, e);
            return new ReplyTo(null, e.getMessage());
        }
    }

    public R addDeclare(JoinDeclare declare) {
        return this.registDeclare(declare, false);
    }

    private R reAddDeclare(JoinDeclare declare) {
        return this.registDeclare(declare, true);
    }

    private R registDeclare(JoinDeclare declare, boolean repeat) {
        R rs;
        if (!repeat && (rs = JoinCacheUtil.addDeclare((JoinDeclare)declare)).getCode() != 0) {
            return rs;
        }
        Queue queue = new Queue(declare.getName(), declare.isDurable(), false, declare.isAutoDelete());
        if (declare.getMessageTTL() > 0L) {
            queue.addArgument("x-message-ttl", (Object)declare.getMessageTTL());
        }
        try {
            this.amqpAdmin.declareQueue(queue);
        }
        catch (AmqpIOException e) {
            try {
                this.amqpAdmin.deleteQueue(declare.getName(), true, true);
                this.amqpAdmin.declareQueue(queue);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        queueCache.putIfAbsent(declare.getName(), queue);
        return R.ok();
    }

    public R addListener(final JoinListener listener) {
        final String joinName = listener.getJoinName();
        SimpleMessageListenerContainer container = containerCache.get(joinName);
        if (container != null) {
            return R.error((String)"\u5df2\u5b58\u5728\u7684\u76d1\u542c");
        }
        Queue queue = queueCache.get(joinName);
        if (queue == null) {
            return R.error((String)("\u672a\u58f0\u660e\u7684\u63a5\u5165\u70b9\uff1a" + joinName));
        }
        R rs = JoinCacheUtil.addListener((JoinListener)listener);
        if (rs.getCode() != 0) {
            return rs;
        }
        Queue subQueue = null;
        final JoinDeclare declare = JoinCacheUtil.getDelcare((String)joinName);
        JoinMode joinModel = declare.getJoinMode();
        AcknowledgeMode ackModel = AcknowledgeMode.NONE;
        if (joinModel == JoinMode.QUEUE) {
            ackModel = AcknowledgeMode.MANUAL;
        } else {
            long messageTTL;
            final String subName = joinModel == JoinMode.TOPIC_SERVER ? JoinApiConfig.getAppName() : JoinApiConfig.getAppInstanceId();
            JoinSubDeclare subDeclare = new JoinSubDeclare(messageTTL = declare.getMessageTTL()){
                final /* synthetic */ long val$messageTTL;
                {
                    this.val$messageTTL = l;
                }

                public String getTitle() {
                    return declare.getTitle() + "@" + subName;
                }

                public String getName() {
                    return joinName + "_" + subName;
                }

                public String getBindingName() {
                    return joinName;
                }

                public boolean isDurable() {
                    return false;
                }

                public boolean isAutoDelete() {
                    return true;
                }

                public long getMessageTTL() {
                    return this.val$messageTTL > 5000L ? this.val$messageTTL : 10000L;
                }
            };
            rs = this.addDeclare((JoinDeclare)subDeclare);
            if (rs.getCode() != 0) {
                return rs;
            }
            subQueue = queueCache.get(subDeclare.getName());
            Binding binding = BindingBuilder.bind((Queue)subQueue).to(this.topicExchange).with(joinName);
            this.amqpAdmin.declareBinding(binding);
        }
        container = new SimpleMessageListenerContainer(this.connectionFactory);
        container.setConcurrentConsumers(listener.getMaxConsumers());
        container.setMaxConcurrentConsumers(listener.getMaxConsumers());
        container.setAcknowledgeMode(ackModel);
        if (listener.getPrefetchCount() > 0) {
            container.setPrefetchCount(listener.getPrefetchCount());
        } else {
            container.setPrefetchCount(JoinApiConfig.getListenerPrefetch());
        }
        if (subQueue != null) {
            container.addQueues(new Queue[]{subQueue});
        } else {
            container.addQueues(new Queue[]{queue});
        }
        final boolean needAck = ackModel == AcknowledgeMode.MANUAL;
        final int requeueCnt = listener.getRequeueCount();
        container.setMessageListener((MessageListener)new AbstractAdaptableMessageListener(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void onMessage(Message message, Channel channel) throws Exception {
                boolean requeue = false;
                String msgContent = null;
                try {
                    MessageProperties mp;
                    Address replyToAddr;
                    ReplyTo replyTo = null;
                    Object obj = coverter.fromMessage(message);
                    msgContent = obj instanceof String ? (String)obj : JSONUtil.toJSONString((Object)obj);
                    replyTo = listener.onMessage(msgContent);
                    if (replyTo == null) {
                        replyTo = new ReplyTo(ReplyStatus.SUCESS);
                    }
                    if ((replyToAddr = (mp = message.getMessageProperties()).getReplyToAddress()) != null) {
                        this.doPublish(channel, replyToAddr, coverter.toMessage((Object)JSONUtil.toJSONString((Object)replyTo), mp));
                    }
                    requeue = replyTo.getReplyStatus() == ReplyStatus.FAIL_REQUEUE;
                }
                catch (Throwable e) {
                    this.logger.error(joinName + "\u76d1\u542c\u5f02\u5e38:" + msgContent, e);
                    requeue = listener.onException().getReplyStatus() == ReplyStatus.FAIL_REQUEUE;
                }
                finally {
                    try {
                        if (needAck) {
                            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                        }
                    }
                    catch (Throwable e2) {
                        this.logger.error(joinName + "ACK\u5f02\u5e38" + msgContent, e2);
                    }
                    try {
                        if (requeue) {
                            int cntNext = 1;
                            Object cnt = message.getMessageProperties().getHeader("JOIN_REQUEUE_COUNT");
                            if (cnt != null) {
                                cntNext = cnt instanceof Number ? ((Number)cnt).intValue() + 1 : Integer.parseInt(cnt.toString()) + 1;
                            }
                            if (cntNext <= requeueCnt) {
                                message.getMessageProperties().setHeader("JOIN_REQUEUE_COUNT", (Object)cntNext);
                                VaJoinRabbitTemplate.this.rabbitTemplate.send(message.getMessageProperties().getConsumerQueue(), message);
                            } else {
                                this.logger.error(joinName + "\u91cd\u65b0\u5165\u961f\u6b21\u6570\u8fbe\u5230\u4e0a\u9650" + requeueCnt + "\uff0c\u4e22\u5f03\uff1a" + msgContent);
                            }
                        }
                    }
                    catch (Throwable e3) {
                        this.logger.error(joinName + "\u91cd\u65b0\u5165\u961f\u5f02\u5e38" + msgContent, e3);
                    }
                }
            }
        });
        containerCache.putIfAbsent(joinName, container);
        if (listener.isAutoStart()) {
            container.start();
        }
        return R.ok();
    }

    public R startListener(String joinName) {
        JoinDeclare declare = JoinCacheUtil.getDelcare((String)joinName);
        if (declare == null) {
            return R.error((String)("\u672a\u58f0\u660e\u7684\u63a5\u5165\u70b9\uff1a" + joinName));
        }
        this.reAddDeclare(declare);
        if (declare.getJoinMode() != JoinMode.QUEUE) {
            String subName = declare.getJoinMode() == JoinMode.TOPIC_SERVER ? JoinApiConfig.getAppName() : JoinApiConfig.getAppInstanceId();
            JoinDeclare subDeclare = JoinCacheUtil.getDelcare((String)(joinName + "_" + subName));
            this.reAddDeclare(subDeclare);
            Queue subQueue = queueCache.get(subDeclare.getName());
            Binding binding = BindingBuilder.bind((Queue)subQueue).to(this.topicExchange).with(joinName);
            this.amqpAdmin.declareBinding(binding);
        }
        manualStopFlag.remove(joinName);
        return this.changeListener(joinName, true);
    }

    public R stopListener(String joinName) {
        manualStopFlag.put(joinName, Boolean.TRUE);
        return this.changeListener(joinName, false);
    }

    private R changeListener(String joinName, boolean isUp) {
        SimpleMessageListenerContainer container = containerCache.get(joinName);
        if (container == null) {
            return R.error((String)"\u4e0d\u5b58\u5728\u7684\u76d1\u542c\u5668");
        }
        try {
            if (isUp && !container.isRunning()) {
                container.start();
            }
            if (!isUp && container.isRunning()) {
                container.stop();
            }
        }
        catch (Throwable e) {
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    public void handleFormerNames(Collection<JoinDeclare> joinDeclares) {
        for (JoinDeclare joinDeclare : joinDeclares) {
            if (joinDeclare.getJoinMode() != JoinMode.QUEUE || joinDeclare.getFormerNames() == null) continue;
            for (String formerName : joinDeclare.getFormerNames()) {
                formerNameMapping.put(formerName, joinDeclare.getName());
            }
        }
        if (formerNameMapping.isEmpty()) {
            return;
        }
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(this.connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setPrefetchCount(300);
        container.setAutoDeclare(false);
        for (String name : formerNameMapping.keySet()) {
            if (this.amqpAdmin.getQueueInfo(name) == null) continue;
            container.addQueueNames(new String[]{name});
        }
        container.setMessageListener((MessageListener)new AbstractAdaptableMessageListener(){

            public void onMessage(Message message, Channel channel) throws Exception {
                String formerName = message.getMessageProperties().getConsumerQueue();
                String queueName = (String)formerNameMapping.get(formerName);
                try {
                    Object obj = coverter.fromMessage(message);
                    if (obj instanceof String) {
                        VaJoinRabbitTemplate.this.rabbitTemplate.convertAndSend(queueName, (Object)message);
                    } else {
                        VaJoinRabbitTemplate.this.rabbitTemplate.convertAndSend(queueName, (Object)JSONUtil.toJSONString((Object)obj));
                    }
                }
                catch (Throwable e) {
                    this.logger.error("\u66fe\u7528\u540d\u961f\u5217\u6d88\u606f\u8f6c\u6362\u5f02\u5e38\uff1a" + queueName, e);
                }
            }
        });
        container.start();
    }

    @Scheduled(cron="0 0/3 * * * ?")
    public void checkListener() {
        Map.Entry<String, SimpleMessageListenerContainer> tmp = null;
        Iterator<Map.Entry<String, SimpleMessageListenerContainer>> iter = containerCache.entrySet().iterator();
        String joinName = null;
        while (iter.hasNext()) {
            tmp = iter.next();
            joinName = tmp.getKey();
            if (tmp.getValue().isActive() || manualStopFlag.containsKey(joinName)) continue;
            logger.error("\u68c0\u6d4b\u5230\u5931\u8d25\u7684\u76d1\u542c\uff0c\u5c1d\u8bd5\u91cd\u542f - " + tmp.getKey());
            this.stopListener(joinName);
            this.startListener(joinName);
        }
    }
}

