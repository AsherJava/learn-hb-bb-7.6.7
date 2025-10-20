/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.session.Session
 */
package com.jiuqi.va.shiro.config.subject;

import org.apache.shiro.session.Session;

public interface MySubjectExtProvider {
    public void setThreadSesstion(Session var1);

    public Session getThreadSSesstion();
}

