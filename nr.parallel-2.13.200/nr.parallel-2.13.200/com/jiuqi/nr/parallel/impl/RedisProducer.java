/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 *  org.springframework.data.redis.core.RedisTemplate
 */
package com.jiuqi.nr.parallel.impl;

import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.IParallelTaskProducer;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Deprecated
public class RedisProducer
implements IParallelTaskProducer {
    private static final Logger logger = LoggerFactory.getLogger(RedisProducer.class);
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private DataSource dataSource;
    private String dbuuid;

    @Override
    public boolean produce(BatchParallelExeTask task) {
        logger.info(" produce task " + task.getKey());
        return true;
    }
}

