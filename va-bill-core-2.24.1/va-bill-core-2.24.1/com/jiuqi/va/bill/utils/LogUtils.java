/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.log.LogDO
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.bill.utils;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.log.LogDO;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;

public class LogUtils {
    private static final Logger log = LogManager.getLogger(LogUtils.class);

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

    public static void logUserLocalIp(StringRedisTemplate stringRedisTemplate) {
        String tenantName = ShiroUtil.getTenantName();
        String userName = ShiroUtil.getUser().getUsername();
        try {
            if (stringRedisTemplate != null && tenantName != null && userName != null) {
                String key = "tenantName:" + tenantName + ":userName:" + userName + ":clientIp";
                String value = RequestContextUtil.getIpAddr();
                stringRedisTemplate.opsForValue().set((Object)key, (Object)value);
            }
        }
        catch (Exception e) {
            log.error("\u8bb0\u5f55\u7528\u6237\u767b\u5f55ip\u5730\u5740\u5230\u7f13\u5b58\u4e2d\u5931\u8d25", (Throwable)e);
        }
    }
}

