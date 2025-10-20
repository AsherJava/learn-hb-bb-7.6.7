/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.data.redis.support.atomic.RedisAtomicLong
 */
package com.jiuqi.va.domain.common;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

public class OrderNumUtil {
    private static Logger logger = LoggerFactory.getLogger(OrderNumUtil.class);
    private static StringRedisTemplate stringRedisTemplate;
    private static long lastTimestamp;
    private static final AtomicLong waitCount;
    private static BigDecimal lastOrderNum;

    @Deprecated
    public static BigDecimal getOrderNumByCurrentTimeMillis(Environment environment) {
        EnvConfig.setEnvironment(environment);
        return OrderNumUtil.getOrderNumByCurrentTimeMillis();
    }

    public static BigDecimal getOrderNumByCurrentTimeMillis() {
        if (!EnvConfig.getRedisEnable()) {
            return OrderNumUtil.getNextOrderNum();
        }
        long currTimestamp = System.currentTimeMillis();
        long orderNum = OrderNumUtil.getOrderNum("" + currTimestamp, TimeUnit.MILLISECONDS);
        BigDecimal bd1 = new BigDecimal(currTimestamp, new MathContext(19)).setScale(6, RoundingMode.HALF_UP);
        BigDecimal bd2 = new BigDecimal((double)orderNum / 1000000.0, new MathContext(19)).setScale(6, RoundingMode.HALF_UP);
        BigDecimal bd = bd1.add(bd2);
        if (lastOrderNum != null && lastOrderNum.compareTo(bd) == 0) {
            bd = OrderNumUtil.getNextOrderNum();
        }
        lastOrderNum = bd;
        return bd;
    }

    public static long getOrderNum(String key, TimeUnit expireTimeUnit) {
        if (!EnvConfig.getRedisEnable()) {
            throw new IllegalStateException("\u5c1a\u672a\u542f\u7528Redis");
        }
        try {
            if (stringRedisTemplate == null) {
                stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
            }
            if (stringRedisTemplate == null) {
                OrderNumUtil.getNextOrderNum();
                return waitCount.get();
            }
            RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
            long increment = entityIdCounter.getAndIncrement();
            if (increment == 0L) {
                entityIdCounter.expire(1L, expireTimeUnit);
            }
            return increment;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Redis\u4f7f\u7528\u5f02\u5e38");
        }
    }

    private static synchronized BigDecimal getNextOrderNum() {
        long currTimestamp = System.currentTimeMillis();
        if (currTimestamp < lastTimestamp) {
            try {
                Thread.sleep(200L);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return OrderNumUtil.getNextOrderNum();
        }
        if (currTimestamp > lastTimestamp) {
            lastTimestamp = currTimestamp;
            waitCount.set(0L);
            return new BigDecimal(currTimestamp);
        }
        BigDecimal bd1 = new BigDecimal(currTimestamp, new MathContext(19)).setScale(6, RoundingMode.HALF_UP);
        BigDecimal bd2 = new BigDecimal((double)waitCount.incrementAndGet() / 1000000.0, new MathContext(19)).setScale(6, RoundingMode.HALF_UP);
        return bd1.add(bd2);
    }

    static {
        lastTimestamp = -1L;
        waitCount = new AtomicLong(0L);
        lastOrderNum = null;
    }
}

