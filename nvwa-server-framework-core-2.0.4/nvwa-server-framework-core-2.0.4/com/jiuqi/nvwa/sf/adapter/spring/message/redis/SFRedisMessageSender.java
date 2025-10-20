/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.IMessageSender
 *  com.jiuqi.nvwa.springadapter.redis.NvwaSpringRedisTemplate
 */
package com.jiuqi.nvwa.sf.adapter.spring.message.redis;

import com.jiuqi.bi.core.messagequeue.IMessageSender;
import com.jiuqi.nvwa.sf.adapter.spring.message.redis.SFRedisMessageListener;
import com.jiuqi.nvwa.springadapter.redis.NvwaSpringRedisTemplate;

public class SFRedisMessageSender
implements IMessageSender {
    private final String groupId;
    private final NvwaSpringRedisTemplate nvwaSpringRedisTemplate;
    private final SFRedisMessageListener sfRedisMessageListener;

    public SFRedisMessageSender(String groupId, NvwaSpringRedisTemplate stringRedisTemplate, SFRedisMessageListener sfRedisMessageListener) {
        this.groupId = groupId;
        this.nvwaSpringRedisTemplate = stringRedisTemplate;
        this.sfRedisMessageListener = sfRedisMessageListener;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void send(String msg) throws Exception {
        this.nvwaSpringRedisTemplate.convertAndSend(this.groupId, (Object)msg);
    }
}

