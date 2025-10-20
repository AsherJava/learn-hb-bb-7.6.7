/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.MD5Util
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.log.LogDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.servlet.http.HttpServletRequest
 */
package com.jiuqi.va.feign.util;

import com.jiuqi.va.domain.common.MD5Util;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.log.LogDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.VaLogClient;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class LogUtil {
    private static Logger logger = LoggerFactory.getLogger(LogUtil.class);
    private static VaLogClient logClient;

    public static R add(String function, String action) {
        return LogUtil.add(function, action, null, null, null);
    }

    public static R add(String function, String action, String biztype, String bizid, String properties) {
        LogDO log = new LogDO();
        log.setFuncname(function);
        log.setActname(action);
        log.setBiztype(biztype);
        log.setBizid(bizid);
        log.setProperties(properties);
        return LogUtil.add(log);
    }

    public static R add(LogDO log) {
        if (log.getId() == null) {
            log.setId(UUID.randomUUID());
        }
        if (!StringUtils.hasText(log.getSessionid())) {
            String token = ShiroUtil.getToken();
            if (token == null) {
                token = UUID.randomUUID().toString();
            }
            log.setSessionid(MD5Util.encrypt((String)token));
        }
        if (!StringUtils.hasText(log.tenantName)) {
            String tenantName = ShiroUtil.getTenantName();
            if (tenantName == null) {
                return R.error((String)"\u6dfb\u52a0\u65e5\u5fd7\u5931\u8d25\uff0c\u4e0a\u4e0b\u6587\u4e2d\u7f3a\u5c11\u79df\u6237\u4fe1\u606f");
            }
            log.setTenantName(tenantName);
        }
        UserLoginDTO user = null;
        if (!StringUtils.hasText(log.getOptuser()) || !StringUtils.hasText(log.getOrgcode())) {
            try {
                user = ShiroUtil.getUser();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (!StringUtils.hasText(log.getOptuser())) {
            if (user == null) {
                log.setOptuser("-");
            } else {
                log.setOptuser(user.getUsername());
            }
        }
        if (!StringUtils.hasText(log.getOrgcode()) && user != null) {
            log.setOrgcode(user.getLoginUnit());
        }
        if (!StringUtils.hasText(log.getOptip())) {
            try {
                log.setOptip(RequestContextUtil.getIpAddr());
            }
            catch (Exception e) {
                log.setOptip("-");
            }
        }
        if (log.getOpttime() == null) {
            log.setOpttime(new Date());
        }
        if (logClient == null) {
            logClient = (VaLogClient)ApplicationContextRegister.getBean(VaLogClient.class);
        }
        try {
            return logClient.add(log);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("\u9700\u8981\u58f0\u660e\u65e5\u5fd7\u670d\u52a1\uff0c\u6216\u901a\u8fc7\u589e\u52a0VaLogClient\u7684\u5b9e\u73b0\u7c7b\u63a5\u7ba1\u65e5\u5fd7\uff0c\u6ce8\u610f\u589e\u52a0 @Primary\u3002");
            return R.error((String)e.getMessage());
        }
    }

    @Deprecated
    public static String getIpAddr(HttpServletRequest request) {
        return RequestContextUtil.getIpAddr(request, null);
    }
}

