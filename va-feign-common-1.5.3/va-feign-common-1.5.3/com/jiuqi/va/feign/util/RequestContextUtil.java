/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.va.feign.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class RequestContextUtil {
    private static Logger logger = LoggerFactory.getLogger(RequestContextUtil.class);
    private static final String IP_UNKNOWN = "unknown";

    public static boolean isHttpRequest() {
        return RequestContextUtil.getRequest() != null;
    }

    public static String getHeader(String name) {
        HttpServletRequest request = RequestContextUtil.getRequest();
        if (request != null) {
            return request.getHeader(name);
        }
        return null;
    }

    public static String getParameter(String name) {
        HttpServletRequest request = RequestContextUtil.getRequest();
        if (request != null) {
            return request.getParameter(name);
        }
        return null;
    }

    public static void setAttribute(String name, Object obj) {
        HttpServletRequest request = RequestContextUtil.getRequest();
        if (request != null) {
            request.setAttribute(name, obj);
        }
    }

    public static Object getAttribute(String name) {
        HttpServletRequest request = RequestContextUtil.getRequest();
        if (request != null) {
            return request.getAttribute(name);
        }
        return null;
    }

    public static String getMethod() {
        HttpServletRequest request = RequestContextUtil.getRequest();
        if (request != null) {
            return request.getMethod();
        }
        return null;
    }

    public static String getRequestURI() {
        HttpServletRequest request = RequestContextUtil.getRequest();
        if (request != null) {
            return request.getRequestURI();
        }
        return null;
    }

    public static String getContextPath() {
        HttpServletRequest request = RequestContextUtil.getRequest();
        if (request != null) {
            return request.getContextPath();
        }
        return null;
    }

    public static String getQueryString() {
        HttpServletRequest request = RequestContextUtil.getRequest();
        if (request != null) {
            return request.getQueryString();
        }
        return null;
    }

    public static InputStream getInputStream() {
        HttpServletRequest request = RequestContextUtil.getRequest();
        if (request != null) {
            try {
                return request.getInputStream();
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static void setResponseContentType(String contentType) {
        HttpServletResponse res = RequestContextUtil.getResponse();
        if (res != null) {
            res.setContentType(contentType);
        }
    }

    public static void setResponseContentLengthLong(long contentLength) {
        HttpServletResponse res = RequestContextUtil.getResponse();
        if (res != null) {
            res.setContentLengthLong(contentLength);
        }
    }

    public static void setResponseCharacterEncoding(String characterEncoding) {
        HttpServletResponse res = RequestContextUtil.getResponse();
        if (res != null) {
            res.setCharacterEncoding(characterEncoding);
        }
    }

    public static void setResponseHeader(String name, String value) {
        HttpServletResponse res = RequestContextUtil.getResponse();
        if (res != null) {
            res.setHeader(name, value);
        }
    }

    public static void setResponseStatus(int status) {
        HttpServletResponse res = RequestContextUtil.getResponse();
        if (res != null) {
            res.setStatus(status);
        }
    }

    public static void sendResponseError(int status, String msg) throws IOException {
        HttpServletResponse res = RequestContextUtil.getResponse();
        if (res != null) {
            if (msg != null) {
                res.sendError(status, msg);
            } else {
                res.sendError(status);
            }
        }
    }

    public static PrintWriter getWriter() throws IOException {
        HttpServletResponse res = RequestContextUtil.getResponse();
        if (res != null) {
            return res.getWriter();
        }
        return null;
    }

    public static OutputStream getOutputStream() {
        HttpServletResponse res = RequestContextUtil.getResponse();
        if (res != null) {
            try {
                return res.getOutputStream();
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static void sendRedirect(String location) throws IOException {
        HttpServletResponse res = RequestContextUtil.getResponse();
        if (res != null) {
            res.sendRedirect(location);
        }
    }

    public static String getIpAddr() {
        return RequestContextUtil.getIpAddr(null, null);
    }

    public static String getIpAddr(String ipHeaderName) {
        return RequestContextUtil.getIpAddr(null, ipHeaderName);
    }

    static String getIpAddr(HttpServletRequest request, String ipHeaderName) {
        if (request == null) {
            request = RequestContextUtil.getRequest();
        }
        if (request == null) {
            return "-";
        }
        String ip = null;
        if (StringUtils.hasLength(ipHeaderName)) {
            ip = request.getHeader(ipHeaderName);
        }
        if (!StringUtils.hasLength(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (!StringUtils.hasLength(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.hasLength(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.hasLength(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (!StringUtils.hasLength(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && !ip.isEmpty()) {
            ip = ip.split("\\,")[0];
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    public static String getRemoteAddr() {
        HttpServletRequest request = RequestContextUtil.getRequest();
        if (request != null) {
            return request.getRemoteAddr();
        }
        return null;
    }

    public static String getLocalAddr() {
        HttpServletRequest request = RequestContextUtil.getRequest();
        if (request != null) {
            return request.getLocalAddr();
        }
        return null;
    }

    public static void setCookie(String name, String value, String domain, String path, int maxAge, boolean isSecure) {
        RequestContextUtil.setCookie(name, value, domain, path, null, true, maxAge, isSecure, 0);
    }

    public static void setCookie(String name, String value, String domain, String path, String comment, boolean isHttpOnly, int maxAge, boolean isSecure, int version) {
        HttpServletResponse res = RequestContextUtil.getResponse();
        if (res == null) {
            return;
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setVersion(version);
        cookie.setComment(comment);
        cookie.setHttpOnly(isHttpOnly);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(isSecure);
        res.addCookie(cookie);
    }

    static HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                request = attributes.getRequest();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return request;
    }

    static HttpServletResponse getResponse() {
        HttpServletResponse res = null;
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                res = attributes.getResponse();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return res;
    }
}

