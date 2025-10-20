/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 *  org.apache.shiro.web.filter.InvalidRequestFilter
 *  org.apache.shiro.web.util.WebUtils
 */
package com.jiuqi.va.shiro.config.optimize;

import com.jiuqi.va.shiro.config.optimize.MyWebUtil;
import javax.servlet.ServletRequest;
import org.apache.shiro.web.filter.InvalidRequestFilter;
import org.apache.shiro.web.util.WebUtils;

public class MyInvalidRequestFilter
extends InvalidRequestFilter {
    protected String getPathWithinApplication(ServletRequest request) {
        return MyWebUtil.getPathWithinApplication(WebUtils.toHttp((ServletRequest)request));
    }
}

