/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.springframework.cloud.stream.binder.Binder
 *  org.springframework.cloud.stream.binder.Binding
 *  org.springframework.cloud.stream.binder.ConsumerProperties
 *  org.springframework.cloud.stream.binder.ProducerProperties
 *  org.springframework.cloud.stream.binder.rabbit.properties.RabbitConsumerProperties
 *  org.springframework.cloud.stream.binder.rabbit.properties.RabbitExtendedBindingProperties
 *  org.springframework.cloud.stream.messaging.DirectWithAttributesChannel
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.SubscribableChannel
 *  org.springframework.messaging.support.ChannelInterceptor
 *  org.springframework.messaging.support.GenericMessage
 */
package com.jiuqi.common.taskschedule.binder;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.taskschedule.streamdb.db.eo.EntTaskInfoEo;
import com.jiuqi.common.taskschedule.streamdb.db.init.EntDbTaskExecuteCollect;
import com.jiuqi.common.taskschedule.streamdb.db.init.pool.EntTaskDbInitPoolHandle;
import com.jiuqi.common.taskschedule.streamdb.db.service.EntDbTaskScheduleService;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.binder.rabbit.properties.RabbitConsumerProperties;
import org.springframework.cloud.stream.binder.rabbit.properties.RabbitExtendedBindingProperties;
import org.springframework.cloud.stream.messaging.DirectWithAttributesChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.Assert;

public class DbMessageChannelBinder
implements Binder<MessageChannel, ConsumerProperties, ProducerProperties> {
    private static final Logger logger = LoggerFactory.getLogger(DbMessageChannelBinder.class);
    private EntDbTaskScheduleService dbTaskService;
    private RabbitExtendedBindingProperties extendedBindingProperties;

    public DbMessageChannelBinder(EntDbTaskScheduleService service, RabbitExtendedBindingProperties extendedBindingProperties) {
        this.dbTaskService = service;
        this.extendedBindingProperties = extendedBindingProperties;
    }

    public Binding<MessageChannel> bindConsumer(String name, String group, MessageChannel inputChannel, ConsumerProperties properties) {
        RabbitConsumerProperties extendedProperties = this.getExtendedConsumerProperties(properties.getBindingName());
        int maxConcurrency = extendedProperties.getMaxConcurrency() > properties.getConcurrency() ? extendedProperties.getMaxConcurrency() : properties.getConcurrency();
        EntTaskDbInitPoolHandle.addQueueToPool(name, properties.getConcurrency(), maxConcurrency);
        EntDbTaskExecuteCollect.addListenerNameToFunctionMap(name, message -> {
            if (message == null) {
                message = "";
            }
            return inputChannel.send((Message)new GenericMessage(message));
        });
        return () -> {};
    }

    public Binding<MessageChannel> bindProducer(String name, MessageChannel outputChannel, ProducerProperties producerProperties) {
        Assert.isInstanceOf(SubscribableChannel.class, (Object)outputChannel, "Binding is supported only for SubscribableChannel instances");
        SubscribableChannel subscribableChannel = (SubscribableChannel)outputChannel;
        DirectWithAttributesChannel aaa = (DirectWithAttributesChannel)outputChannel;
        aaa.addInterceptor(new ChannelInterceptor(){

            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                return super.preSend(message, channel);
            }

            public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
                super.afterSendCompletion(message, channel, sent, ex);
            }
        });
        subscribableChannel.subscribe(message -> {
            Object payload = message.getPayload();
            if (payload instanceof byte[]) {
                payload = new String((byte[])payload);
            }
            this.dbTaskService.publish(this.getEntTaskInfoEo(name, payload));
        });
        return () -> logger.info("Unbinding");
    }

    private EntTaskInfoEo getEntTaskInfoEo(String queueName, Object messageBody) {
        EntTaskInfoEo eo = new EntTaskInfoEo();
        eo.setId(UUIDUtils.newUUIDStr());
        eo.setQueueName(queueName);
        eo.setMessageBody(messageBody instanceof String ? (String)messageBody : JsonUtils.writeValueAsString((Object)messageBody));
        eo.setStatus(0);
        Date now = DateUtils.now();
        eo.setCreateTime(now);
        eo.setStartTime(now);
        eo.setVer(System.currentTimeMillis());
        return eo;
    }

    public RabbitConsumerProperties getExtendedConsumerProperties(String channelName) {
        return (RabbitConsumerProperties)this.extendedBindingProperties.getExtendedConsumerProperties(channelName);
    }
}

