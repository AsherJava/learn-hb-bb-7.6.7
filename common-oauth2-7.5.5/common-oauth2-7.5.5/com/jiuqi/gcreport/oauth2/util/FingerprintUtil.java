/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.gcreport.oauth2.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class FingerprintUtil {
    public static String genFingerprint(String username, String contextPath) {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String host = request.getHeader("Host");
        String userAgent = request.getHeader("User-Agent");
        String temp = username + "|" + host + "|" + contextPath + "|" + userAgent + "|" + request.getRemoteAddr();
        return DigestUtils.md5DigestAsHex(temp.getBytes());
    }

    public static String genFp4State(String clientIpHeader) {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = FingerprintUtil.getIpAddr(request, clientIpHeader);
        String ua = request.getHeader("User-Agent");
        String temp = ip + "|" + ua;
        return DigestUtils.md5DigestAsHex(temp.getBytes());
    }

    public static boolean validateFp4State(String inputFingerprint, String clientIpHeader) {
        String genFp4State = FingerprintUtil.genFp4State(clientIpHeader);
        return genFp4State.equals(inputFingerprint);
    }

    public static String getIpAddr(HttpServletRequest request, String ipHeaderName) {
        String ip = null;
        if (StringUtils.hasLength(ipHeaderName)) {
            ip = request.getHeader(ipHeaderName);
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (!StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.length() > 0) {
            String[] ips = ip.split("\\,");
            ip = ips[ips.length - 1];
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}

