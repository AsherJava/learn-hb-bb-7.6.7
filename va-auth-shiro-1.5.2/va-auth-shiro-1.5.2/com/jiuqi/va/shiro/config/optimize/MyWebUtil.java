/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.shiro.web.util.WebUtils
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.va.shiro.config.optimize;

import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class MyWebUtil
extends WebUtils {
    private static boolean allowBackslash = Boolean.getBoolean("org.apache.shiro.web.ALLOW_BACKSLASH");

    public static String getPathWithinApplication(HttpServletRequest request) {
        return MyWebUtil.normalize(MyWebUtil.removeSemicolon(MyWebUtil.getServletPath(request) + MyWebUtil.getPathInfo(request)));
    }

    private static String removeSemicolon(String uri) {
        int semicolonIndex = uri.indexOf(59);
        return semicolonIndex != -1 ? uri.substring(0, semicolonIndex) : uri;
    }

    private static String getServletPath(HttpServletRequest request) {
        String servletPath = (String)request.getAttribute("javax.servlet.include.servlet_path");
        return servletPath != null ? servletPath : MyWebUtil.valueOrEmpty(request.getServletPath());
    }

    private static String getPathInfo(HttpServletRequest request) {
        String pathInfo = (String)request.getAttribute("javax.servlet.include.path_info");
        return pathInfo != null ? pathInfo : MyWebUtil.valueOrEmpty(request.getPathInfo());
    }

    private static String valueOrEmpty(String input) {
        if (input == null) {
            return "";
        }
        return input;
    }

    public static String normalize(String path) {
        return MyWebUtil.normalize(path, allowBackslash);
    }

    private static String normalize(String path, boolean replaceBackSlash) {
        int index;
        if (path == null) {
            return null;
        }
        String normalized = path;
        if (replaceBackSlash && normalized.indexOf(92) >= 0) {
            normalized = normalized.replace('\\', '/');
        }
        if (normalized.equals("/.")) {
            return "/";
        }
        if (!normalized.startsWith("/")) {
            normalized = "/" + normalized;
        }
        while ((index = normalized.indexOf("//")) >= 0) {
            normalized = normalized.substring(0, index) + normalized.substring(index + 1);
        }
        while ((index = normalized.indexOf("/./")) >= 0) {
            normalized = normalized.substring(0, index) + normalized.substring(index + 2);
        }
        while ((index = normalized.indexOf("/../")) >= 0) {
            if (index == 0) {
                return null;
            }
            int index2 = normalized.lastIndexOf(47, index - 1);
            normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
        }
        return normalized;
    }

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                request = attributes.getRequest();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return request;
    }
}

