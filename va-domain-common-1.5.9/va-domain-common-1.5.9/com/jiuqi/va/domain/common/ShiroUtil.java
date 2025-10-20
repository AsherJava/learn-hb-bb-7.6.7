/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.TenantUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.apache.shiro.subject.Subject
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.va.domain.common;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.mapper.common.TenantUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

public class ShiroUtil {
    public static final String SHIRO_TOKEN_KEY = "SHIRO_TOKEN_KEY";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String URL_JTOKENID = "JTOKENID";

    public static Subject getSubjct() {
        try {
            Subject subject = ThreadContext.getSubject();
            if (subject == null) break block4;
            if (subject.getPrincipal() != null) {
                return subject;
            }
        }
        finally {
            return null;
        }
        {
            block4: {
            }
        }
    }

    public static String getToken() {
        Object token = ThreadContext.get((Object)SHIRO_TOKEN_KEY);
        if (token != null) {
            return token.toString();
        }
        UserLoginDTO user = (UserLoginDTO)((Object)ThreadContext.get((Object)"LOGIN_USER_KEY"));
        if (user != null && (token = user.getExtInfo(URL_JTOKENID)) != null) {
            return token.toString();
        }
        try {
            Subject subjct = ShiroUtil.getSubjct();
            if (subjct != null) {
                return subjct.getSession(false).getId().toString();
            }
        }
        finally {
            return null;
        }
        {
        }
    }

    public static UserLoginDTO getUser() {
        UserLoginDTO user = (UserLoginDTO)((Object)ThreadContext.get((Object)"LOGIN_USER_KEY"));
        if (user != null) {
            return user;
        }
        TenantDO tenantDO = TenantUtil.getContextByProvider();
        if (tenantDO instanceof UserLoginDTO) {
            return (UserLoginDTO)tenantDO;
        }
        String vaCtxJson = TenantUtil.getContextFromShiro();
        if (vaCtxJson != null) {
            return JSONUtil.parseObject(vaCtxJson, UserLoginDTO.class);
        }
        return null;
    }

    public static String getTenantName() {
        return TenantUtil.getTenantName();
    }

    public static void logout() {
        try {
            Subject subject = ThreadContext.getSubject();
            if (subject != null) {
                subject.logout();
            }
        }
        finally {
            ShiroUtil.cleanContext();
        }
    }

    public static void bindTenantName(String tenantName) {
        ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)tenantName);
    }

    public static void unbindTenantName() {
        ThreadContext.remove((Object)"SECURITY_TENANT_KEY");
    }

    public static void ignoreApiAuth() {
        ThreadContext.put((Object)"NONE_AUTH_KEY", (Object)"true");
    }

    public static void resetApiAuth() {
        ThreadContext.remove((Object)"NONE_AUTH_KEY");
    }

    public static void bindSubject(Subject subject) {
        ThreadContext.bind((Subject)subject);
    }

    public static void unbindSubject() {
        ThreadContext.unbindSubject();
    }

    public static void bindToken(String token) {
        String currToken = (String)ThreadContext.get((Object)SHIRO_TOKEN_KEY);
        if (currToken != null && !currToken.equals(token)) {
            throw new RuntimeException("\u5f53\u524d\u4e0a\u4e0b\u6587\u4e2d\u5df2\u5b58\u5728token");
        }
        ThreadContext.put((Object)SHIRO_TOKEN_KEY, (Object)token);
    }

    public static void unbindToken(String token) {
        String currToken = (String)ThreadContext.get((Object)SHIRO_TOKEN_KEY);
        if (currToken != null && !currToken.equals(token)) {
            throw new RuntimeException("\u4e0e\u5f53\u524d\u4e0a\u4e0b\u6587\u4e2dtoken\u4e0d\u5339\u914d");
        }
        ThreadContext.remove((Object)SHIRO_TOKEN_KEY);
    }

    public static void bindUser(UserLoginDTO user) {
        UserLoginDTO currUser = (UserLoginDTO)((Object)ThreadContext.get((Object)"LOGIN_USER_KEY"));
        if (currUser != null && (user.getId() != null && !user.getId().equals(currUser.getId()) || user.getUsername() != null && !user.getUsername().equals(currUser.getUsername()))) {
            throw new RuntimeException("\u5f53\u524d\u4e0a\u4e0b\u6587\u4e2d\u5df2\u5b58\u5728\u5176\u4ed6\u7528\u6237");
        }
        ThreadContext.put((Object)"LOGIN_USER_KEY", (Object)((Object)user));
    }

    public static void unbindUser() {
        ThreadContext.remove((Object)"LOGIN_USER_KEY");
    }

    public static void removeAll() {
        ThreadContext.remove();
        ShiroUtil.cleanContext();
    }

    public static void cleanContext() {
        TenantUtil.cleanContextFormProvider();
    }
}

