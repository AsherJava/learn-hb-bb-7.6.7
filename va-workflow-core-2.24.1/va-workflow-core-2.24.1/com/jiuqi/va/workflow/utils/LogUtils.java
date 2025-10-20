/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.log.LogDO
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.workflow.utils;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.log.LogDO;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import org.springframework.data.redis.core.StringRedisTemplate;

public class LogUtils {
    private LogUtils() {
    }

    public static void addLog(String function, String action, String biztype, String bizid, String msg, String tenantName) {
        LogDO log = new LogDO();
        log.setFuncname(function);
        log.setActname(action);
        log.setOptuser(ShiroUtil.getUser().getUsername());
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
        String userName = ShiroUtil.getUser().getUsername();
        String key = "tenantName:" + tenantName + ":userName:" + userName + ":clientIp";
        String iptemp = (String)stringRedisTemplate.opsForValue().get((Object)key);
        if (iptemp != null) {
            log.setOptip(iptemp);
        }
        log.setBiztype(biztype);
        log.setBizid(bizid);
        log.setProperties(msg);
        LogUtil.add((LogDO)log);
    }
}

