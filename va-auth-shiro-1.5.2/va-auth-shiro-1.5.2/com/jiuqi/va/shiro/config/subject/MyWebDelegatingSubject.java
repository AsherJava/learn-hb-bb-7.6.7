/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.shiro.authz.AuthorizationException
 *  org.apache.shiro.mgt.SecurityManager
 *  org.apache.shiro.session.Session
 *  org.apache.shiro.subject.PrincipalCollection
 *  org.apache.shiro.util.ThreadContext
 *  org.apache.shiro.web.subject.support.WebDelegatingSubject
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.va.shiro.config.subject;

import com.jiuqi.va.shiro.config.subject.MySubjectExtProvider;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class MyWebDelegatingSubject
extends WebDelegatingSubject
implements MySubjectExtProvider {
    private Session threadSesstion;

    public MyWebDelegatingSubject(PrincipalCollection principals, boolean authenticated, String host, Session session, boolean sessionEnabled, ServletRequest request, ServletResponse response, SecurityManager securityManager) {
        super(principals, authenticated, host, session, sessionEnabled, request, response, securityManager);
    }

    public void checkPermission(String permission) throws AuthorizationException {
        if (this.noneAuthRequest()) {
            return;
        }
        super.checkPermission(permission);
    }

    private boolean noneAuthRequest() {
        String noneAuthKey = (String)ThreadContext.get((Object)"NONE_AUTH_KEY");
        if ("true".equalsIgnoreCase(noneAuthKey)) {
            return true;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return false;
        }
        HttpServletRequest request = attributes.getRequest();
        return "true".equalsIgnoreCase(request.getHeader("FeignClient"));
    }

    @Override
    public void setThreadSesstion(Session s) {
        this.threadSesstion = s;
    }

    @Override
    public Session getThreadSSesstion() {
        return this.threadSesstion;
    }
}

