/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  org.apache.shiro.mgt.SecurityManager
 *  org.apache.shiro.session.Session
 *  org.apache.shiro.subject.PrincipalCollection
 *  org.apache.shiro.subject.Subject
 *  org.apache.shiro.subject.SubjectContext
 *  org.apache.shiro.web.mgt.DefaultWebSubjectFactory
 *  org.apache.shiro.web.subject.WebSubject
 *  org.apache.shiro.web.subject.WebSubjectContext
 */
package com.jiuqi.va.shiro.config.subject;

import com.jiuqi.va.shiro.config.subject.MyDelegatingSubject;
import com.jiuqi.va.shiro.config.subject.MyWebDelegatingSubject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubjectContext;

public class MyWebSubjectFactory
extends DefaultWebSubjectFactory {
    public Subject createSubject(SubjectContext context) {
        boolean isNotBasedOnWebSubject;
        Subject subject = context.getSubject();
        boolean bl = isNotBasedOnWebSubject = subject != null && !(subject instanceof WebSubject);
        if (!(context instanceof WebSubjectContext) || isNotBasedOnWebSubject) {
            SecurityManager securityManager = context.resolveSecurityManager();
            Session session = context.resolveSession();
            boolean sessionCreationEnabled = context.isSessionCreationEnabled();
            PrincipalCollection principals = context.resolvePrincipals();
            boolean authenticated = context.resolveAuthenticated();
            String host = context.resolveHost();
            return new MyDelegatingSubject(principals, authenticated, host, session, sessionCreationEnabled, securityManager);
        }
        WebSubjectContext wsc = (WebSubjectContext)context;
        SecurityManager securityManager = wsc.resolveSecurityManager();
        Session session = wsc.resolveSession();
        boolean sessionEnabled = wsc.isSessionCreationEnabled();
        PrincipalCollection principals = wsc.resolvePrincipals();
        boolean authenticated = wsc.resolveAuthenticated();
        String host = wsc.resolveHost();
        ServletRequest request = wsc.resolveServletRequest();
        ServletResponse response = wsc.resolveServletResponse();
        return new MyWebDelegatingSubject(principals, authenticated, host, session, sessionEnabled, request, response, securityManager);
    }
}

