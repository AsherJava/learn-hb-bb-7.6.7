/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 *  org.apache.shiro.web.filter.authc.AnonymousFilter
 *  org.apache.shiro.web.util.WebUtils
 */
package com.jiuqi.va.shiro.config.optimize;

import com.jiuqi.va.shiro.config.optimize.MyWebUtil;
import javax.servlet.ServletRequest;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.util.WebUtils;

public class MyAnonymousFilter
extends AnonymousFilter {
    protected String getPathWithinApplication(ServletRequest request) {
        return MyWebUtil.getPathWithinApplication(WebUtils.toHttp((ServletRequest)request));
    }
}

