/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.mgt.SecurityManager
 *  org.apache.shiro.session.Session
 *  org.apache.shiro.subject.PrincipalCollection
 *  org.apache.shiro.subject.support.DelegatingSubject
 */
package com.jiuqi.va.shiro.config.subject;

import com.jiuqi.va.shiro.config.subject.MySubjectExtProvider;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DelegatingSubject;

public class MyDelegatingSubject
extends DelegatingSubject
implements MySubjectExtProvider {
    private Session threadSesstion;

    public MyDelegatingSubject(PrincipalCollection principals, boolean authenticated, String host, Session session, boolean sessionCreationEnabled, SecurityManager securityManager) {
        super(principals, authenticated, host, session, sessionCreationEnabled, securityManager);
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

