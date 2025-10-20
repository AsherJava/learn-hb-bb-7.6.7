/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.RedisUtils
 */
package com.jiuqi.common.expimp.progress.executor.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.RedisUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.expimp.progress.executor.AbstractProgressExecutor;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProgressRedisExecutorImpl<E>
extends AbstractProgressExecutor<ProgressDataImpl<E>, E> {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private Environment env;
    private static final String BASE_KEY = "progress:";

    @Override
    public void create(ProgressDataImpl<E> progressData) {
        this.checkRedisEnabled();
        Objects.requireNonNull(progressData.getSn(), "\u8fdb\u5ea6\u4efb\u52a1sn\u5c5e\u6027\u4e0d\u9700\u8981\u4e3a\u7a7a\uff01");
        this.setRedisData(progressData);
    }

    @Override
    public void refresh(ProgressDataImpl<E> progressData) {
        this.setRedisData(progressData);
    }

    private void setRedisData(ProgressDataImpl<E> progressData) {
        String progressDataJson = JsonUtils.writeValueAsString(progressData);
        this.redisUtils.set(this.getRedisKey(progressData.getSn()), (Object)progressDataJson, progressData.getExpireTime());
    }

    @Override
    public ProgressDataImpl<E> query(String sn, boolean isAutoClearProgressDataWhenFinished) {
        if (sn == null) {
            return null;
        }
        Object progressDataJson = this.redisUtils.get(this.getRedisKey(sn));
        if (progressDataJson == null) {
            return null;
        }
        ProgressDataImpl progressData = (ProgressDataImpl)JsonUtils.readValue((String)progressDataJson.toString(), (TypeReference)new TypeReference<ProgressDataImpl<E>>(){});
        if (progressData == null) {
            return null;
        }
        if (isAutoClearProgressDataWhenFinished && progressData.getProgressValue() >= 1.0) {
            this.removeProgressData(sn);
        }
        return progressData;
    }

    @Override
    public void remove(String sn) {
        this.redisUtils.del(new String[]{this.getRedisKey(sn)});
    }

    private String getRedisKey(String sn) {
        return BASE_KEY + sn;
    }

    @Override
    public String getExecutorName() {
        return "redis";
    }

    private void checkRedisEnabled() {
        Boolean redisEnabled = this.env.getProperty("spring.redis.enabled", Boolean.class, true);
        if (!redisEnabled.booleanValue()) {
            throw new BusinessRuntimeException("\u5f53\u524dRedis\u672a\u542f\u7528\uff0c\u8bf7\u68c0\u67e5custom.progress.executor.name\u914d\u7f6e\u503c\u662f\u5426\u4e3amap \u6216 \u542f\u7528Redis");
        }
    }
}

