/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.web.servlet.ShiroHttpServletRequest
 *  org.apache.shiro.web.servlet.ShiroHttpServletResponse
 */
package com.jiuqi.va.shiro.config.optimize;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;

public class MyShiroHttpServletResponse
extends ShiroHttpServletResponse {
    public MyShiroHttpServletResponse(HttpServletResponse wrapped, ServletContext context, ShiroHttpServletRequest request) {
        super(wrapped, context, request);
    }

    protected String toEncoded(String url, String sessionId) {
        int pound;
        if (url == null || sessionId == null) {
            return url;
        }
        String path = url;
        String query = "";
        String anchor = "";
        int question = url.indexOf(63);
        if (question >= 0) {
            path = url.substring(0, question);
            query = url.substring(question);
        }
        if ((pound = path.indexOf(35)) >= 0) {
            anchor = path.substring(pound);
            path = path.substring(0, pound);
        }
        StringBuilder sb = new StringBuilder(path);
        sb.append(anchor);
        sb.append(query);
        return sb.toString();
    }
}

