/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.shiro.subject.Subject
 *  org.apache.shiro.util.ThreadContext
 *  org.apache.shiro.web.servlet.ShiroHttpServletRequest
 */
package com.jiuqi.va.shiro.config.optimize;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;

public class MyShiroHttpServletRequest
extends ShiroHttpServletRequest {
    public MyShiroHttpServletRequest(HttpServletRequest wrapped, ServletContext servletContext, boolean httpSessions) {
        super(wrapped, servletContext, httpSessions);
    }

    public Subject getSubject() {
        return ThreadContext.getSubject();
    }
}

