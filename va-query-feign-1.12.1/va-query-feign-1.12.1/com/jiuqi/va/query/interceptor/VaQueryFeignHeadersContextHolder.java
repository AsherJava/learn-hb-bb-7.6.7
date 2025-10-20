/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.ttl.TransmittableThreadLocal
 */
package com.jiuqi.va.query.interceptor;

import com.alibaba.ttl.TransmittableThreadLocal;
import java.util.HashMap;
import java.util.Map;

public class VaQueryFeignHeadersContextHolder {
    private static TransmittableThreadLocal<Map<String, String>> feignHeadersContextThreadLocal = new TransmittableThreadLocal<Map<String, String>>(){

        protected Map<String, String> initialValue() {
            return new HashMap<String, String>();
        }
    };

    public static Map<String, String> getFeignHeadersContext() {
        return (Map)feignHeadersContextThreadLocal.get();
    }

    public static void setFeignHeadersContext(Map<String, String> feignHeadersContext) {
        feignHeadersContextThreadLocal.set(feignHeadersContext);
    }

    public static void releaseFeignHeadersContext() {
        feignHeadersContextThreadLocal.remove();
    }

    public static void putHeader(String headerName, String headerValue) {
        VaQueryFeignHeadersContextHolder.getFeignHeadersContext().put(headerName, headerValue);
    }

    public static void removeHeader(String headerName) {
        VaQueryFeignHeadersContextHolder.getFeignHeadersContext().remove(headerName);
    }

    public static String getHeaderValues(String headerName) {
        return VaQueryFeignHeadersContextHolder.getFeignHeadersContext().get(headerName);
    }

    public static void setUserToken(String token) {
        VaQueryFeignHeadersContextHolder.putHeader("VA_QUERY_TOKEN_KEY", token);
    }

    public static String getUserToken() {
        return VaQueryFeignHeadersContextHolder.getHeaderValues("VA_QUERY_TOKEN_KEY");
    }
}

