/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.filter.GenericFilterBean
 */
package com.jiuqi.nr.common.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@ConditionalOnProperty(prefix="jiuqi.nr.ie", name={"enabled"}, havingValue="true", matchIfMissing=false)
@Component
public class IEGetRequestFilter
extends GenericFilterBean {
    private static Set<String> releaseSet = new HashSet<String>();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI;
        String method;
        String agent;
        HttpServletResponse rep = (HttpServletResponse)response;
        HttpServletRequest req = (HttpServletRequest)request;
        String userAgent = req.getHeader("User-Agent");
        if (null != userAgent && ((agent = userAgent.toLowerCase()).indexOf("msie") >= 0 || agent.indexOf("gecko") >= 0 && agent.indexOf("rv:11") >= 0) && null != (method = req.getMethod()) && "get".equals(method = method.toLowerCase()) && null != (requestURI = req.getRequestURI())) {
            int indexOf = (requestURI = requestURI.toLowerCase()).indexOf(".");
            if (-1 != indexOf) {
                String suffix = requestURI.substring(indexOf + 1, requestURI.length());
                if (!releaseSet.contains(suffix)) {
                    rep.setHeader("Cache-Control", "no-cache;no-store;must-revalidate");
                    rep.setHeader("Pragma", "no-cache");
                    rep.setHeader("Expires", "0");
                }
            } else {
                rep.setHeader("Cache-Control", "no-cache;no-store;must-revalidate");
                rep.setHeader("Pragma", "no-cache");
                rep.setHeader("Expires", "0");
            }
        }
        chain.doFilter((ServletRequest)req, (ServletResponse)rep);
    }

    static {
        releaseSet.add("css");
        releaseSet.add("js");
        releaseSet.add("png");
        releaseSet.add("woff");
        releaseSet.add("jpg");
        releaseSet.add("bmp");
        releaseSet.add("dib");
        releaseSet.add("rle");
        releaseSet.add("gif");
        releaseSet.add("jpeg");
        releaseSet.add("tif");
    }
}

