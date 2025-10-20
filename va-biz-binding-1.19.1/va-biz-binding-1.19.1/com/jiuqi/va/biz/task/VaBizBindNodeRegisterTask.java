/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.biz.task;

import com.jiuqi.va.biz.config.VaBizBindingConfig;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VaBizBindNodeRegisterTask {
    private static final String NODE_KEY = "#VaBizBindingNodes";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Scheduled(initialDelay=0L, fixedRate=90L, timeUnit=TimeUnit.SECONDS)
    public void bizNodeRegister() {
        if (VaBizBindingConfig.isRedisEnable()) {
            long sysTime = System.currentTimeMillis();
            this.stringRedisTemplate.opsForHash().put((Object)NODE_KEY, (Object)VaBizBindingConfig.getCurrNodeId(), (Object)String.valueOf(sysTime));
            Map infos = this.stringRedisTemplate.opsForHash().entries((Object)NODE_KEY);
            for (Map.Entry entry : infos.entrySet()) {
                long nodePeriod;
                long time = Long.parseLong(entry.getValue().toString());
                if (time + (nodePeriod = 90000L) >= sysTime) continue;
                this.stringRedisTemplate.opsForHash().delete((Object)NODE_KEY, new Object[]{entry.getKey()});
            }
        }
    }
}

