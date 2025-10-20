/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.IMessageQueuePolicy
 *  com.jiuqi.bi.core.messagequeue.IMessageReceiver
 *  com.jiuqi.bi.core.messagequeue.IMessageSender
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.springadapter.redis.NvwaSpringRedisTemplate
 *  org.springframework.data.redis.connection.MessageListener
 *  org.springframework.data.redis.listener.ChannelTopic
 *  org.springframework.data.redis.listener.RedisMessageListenerContainer
 *  org.springframework.data.redis.listener.Topic
 *  org.springframework.data.redis.serializer.StringRedisSerializer
 */
package com.jiuqi.nvwa.sf.adapter.spring.message.redis;

import com.jiuqi.bi.core.messagequeue.IMessageQueuePolicy;
import com.jiuqi.bi.core.messagequeue.IMessageReceiver;
import com.jiuqi.bi.core.messagequeue.IMessageSender;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.adapter.spring.message.redis.SFRedisMessageListener;
import com.jiuqi.nvwa.sf.adapter.spring.message.redis.SFRedisMessageSender;
import com.jiuqi.nvwa.springadapter.redis.NvwaSpringRedisTemplate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class MessageQueueRedisPolicy
implements IMessageQueuePolicy {
    private final Map<String, SFRedisMessageListener> receivers = new HashMap<String, SFRedisMessageListener>();
    private boolean isRunning = false;
    @Autowired
    private NvwaSpringRedisTemplate nvwaSpringRedisTemplate;
    @Autowired(required=false)
    private StringRedisSerializer stringRedisSerializer;

    public String getPolicy() {
        return "REDIS";
    }

    public IMessageSender getMessageSender(String groupId) {
        SFRedisMessageListener sfRedisMessageListener = this.receivers.get(groupId);
        return new SFRedisMessageSender(groupId, this.nvwaSpringRedisTemplate, sfRedisMessageListener);
    }

    public void registReceiver(IMessageReceiver iMessageReceiver) {
        SFRedisMessageListener sfRedisMessageListener = new SFRedisMessageListener(iMessageReceiver, this.stringRedisSerializer);
        RedisMessageListenerContainer sfRedisMessageListenerContainer = (RedisMessageListenerContainer)SpringBeanUtils.getBean((String)"sfRedisMessageListenerContainer", RedisMessageListenerContainer.class);
        sfRedisMessageListenerContainer.addMessageListener((MessageListener)sfRedisMessageListener, (Topic)new ChannelTopic(iMessageReceiver.getGroupId()));
        this.receivers.put(iMessageReceiver.getGroupId(), sfRedisMessageListener);
    }

    public void start() {
        RedisMessageListenerContainer sfRedisMessageListenerContainer = (RedisMessageListenerContainer)SpringBeanUtils.getBean((String)"sfRedisMessageListenerContainer", RedisMessageListenerContainer.class);
        for (Map.Entry<String, SFRedisMessageListener> stringSFRedisMessageListenerEntry : this.receivers.entrySet()) {
            sfRedisMessageListenerContainer.addMessageListener((MessageListener)stringSFRedisMessageListenerEntry.getValue(), (Topic)new ChannelTopic(stringSFRedisMessageListenerEntry.getKey()));
        }
        this.isRunning = true;
    }

    public void stop() {
        RedisMessageListenerContainer sfRedisMessageListenerContainer = (RedisMessageListenerContainer)SpringBeanUtils.getBean((String)"sfRedisMessageListenerContainer", RedisMessageListenerContainer.class);
        for (Map.Entry<String, SFRedisMessageListener> stringSFRedisMessageListenerEntry : this.receivers.entrySet()) {
            sfRedisMessageListenerContainer.removeMessageListener((MessageListener)stringSFRedisMessageListenerEntry.getValue(), (Topic)new ChannelTopic(stringSFRedisMessageListenerEntry.getKey()));
        }
        this.isRunning = false;
    }

    public boolean hasStarted() {
        return this.isRunning;
    }
}

