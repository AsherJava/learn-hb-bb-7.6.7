/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.domain.common;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisLockUtil {
    private static Logger logger = LoggerFactory.getLogger(RedisLockUtil.class);
    private static Timer timer = new Timer();
    private static StringRedisTemplate stringRedisTemplate = null;

    private static void init() {
        if (!EnvConfig.getRedisEnable()) {
            throw new RuntimeException("Redis is disabled!");
        }
        if (stringRedisTemplate == null) {
            stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
        }
    }

    public static R execute(Runnable runnable, String lockKey, long lockTime, boolean autoRelock) {
        R rs = new R();
        RedisLockUtil.execute(runnable, lockKey, lockTime, autoRelock, rs);
        return rs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void execute(Runnable runnable, final String lockKey, final long lockTime, boolean autoRelock, R rs) {
        block22: {
            RedisLockUtil.init();
            if (rs == null) {
                throw new IllegalArgumentException("The 'rs' parameter is required!");
            }
            if (lockTime <= 0L) {
                throw new IllegalArgumentException("Non-positive lockTime");
            }
            boolean flag = true;
            TimerTask timerTask = null;
            try {
                Long hasKeyExpire = stringRedisTemplate.getExpire((Object)lockKey);
                if (hasKeyExpire != null && hasKeyExpire.intValue() == -1) {
                    stringRedisTemplate.delete((Object)lockKey);
                }
                if (stringRedisTemplate.opsForValue().setIfAbsent((Object)lockKey, (Object)"1", lockTime, TimeUnit.MILLISECONDS).booleanValue()) {
                    if (autoRelock) {
                        timerTask = new TimerTask(){

                            @Override
                            public void run() {
                                stringRedisTemplate.opsForValue().setIfPresent((Object)lockKey, (Object)"1", lockTime, TimeUnit.MILLISECONDS);
                            }
                        };
                        try {
                            timer.scheduleAtFixedRate(timerTask, lockTime / 2L, lockTime);
                        }
                        catch (Throwable e) {
                            logger.error("Exception occurred during '" + lockKey + "' task relock.", e);
                        }
                    }
                    rs.setMsg(0, "Successfully obtained lock");
                    try {
                        runnable.run();
                    }
                    catch (Throwable e) {
                        logger.error("Exception occurred during '" + lockKey + "' task execution.", e);
                        rs.setMsg(500, e.getMessage());
                    }
                    break block22;
                }
                flag = false;
                rs.setMsg(100, "Failed to obtain lock.");
            }
            catch (Throwable e) {
                logger.error("Exception occurred during '" + lockKey + "' task lock.", e);
                rs.setMsg(500, e.getMessage());
            }
            finally {
                try {
                    if (flag) {
                        stringRedisTemplate.delete((Object)lockKey);
                    }
                    if (timerTask != null) {
                        timerTask.cancel();
                        timer.purge();
                    }
                }
                catch (Throwable hasKeyExpire) {}
            }
        }
    }
}

