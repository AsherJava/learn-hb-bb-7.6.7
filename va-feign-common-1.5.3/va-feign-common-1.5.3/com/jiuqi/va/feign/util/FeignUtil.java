/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  feign.Response
 *  feign.RetryableException
 *  org.springframework.cloud.openfeign.FeignClientBuilder
 *  org.springframework.cloud.openfeign.FeignClientBuilder$Builder
 */
package com.jiuqi.va.feign.util;

import com.jiuqi.va.feign.config.VaFeignCommonConfig;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import feign.Response;
import feign.RetryableException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

public class FeignUtil {
    private static ConcurrentHashMap<String, Object> cacheMap = new ConcurrentHashMap();

    public static <T> T getDynamicClient(Class<T> feignClient, String appName, String appContextPath) {
        if (!StringUtils.hasText(appContextPath) || "/".equals(appContextPath)) {
            appContextPath = "";
        }
        if (appContextPath.endsWith("/")) {
            appContextPath = appContextPath.substring(0, appContextPath.length() - 1);
        }
        StringBuilder url = new StringBuilder();
        if (appName.endsWith("/") && !StringUtils.hasText(appContextPath)) {
            url.append(appName.substring(0, appName.length() - 1));
        } else {
            url.append(appName);
        }
        if (StringUtils.hasText(appContextPath)) {
            if (!appName.endsWith("/") && !appContextPath.startsWith("/")) {
                url.append("/");
            }
            url.append(appContextPath);
        }
        return FeignUtil.getDynamicClient(feignClient, url.toString());
    }

    public static <T> T getDynamicClient(Class<T> feignClient, String url) {
        T t;
        String cacheKey;
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        if (cacheMap.containsKey(cacheKey = url + feignClient.getName())) {
            return (T)cacheMap.get(cacheKey);
        }
        int nameStartPoint = url.indexOf("/") + 2;
        int nameEndPoint = url.indexOf("/", nameStartPoint);
        String name = "";
        name = nameEndPoint < 0 ? url.substring(nameStartPoint) : url.substring(nameStartPoint, nameEndPoint);
        if (name.contains(":")) {
            name = name.split("\\:")[0];
        }
        if (("localhost".equalsIgnoreCase(name) || "127.0.0.1".equals(name)) && (t = FeignUtil.getPrimaryImpl(feignClient)) != null) {
            cacheMap.put(cacheKey, t);
            return t;
        }
        FeignClientBuilder fcb = new FeignClientBuilder(ApplicationContextRegister.getApplicationContext());
        FeignClientBuilder.Builder builder = fcb.forType(feignClient, url);
        if ("localhost".equalsIgnoreCase(name) || FeignUtil.isIpV4Addr(name)) {
            builder.url(url);
        }
        Object t2 = builder.build();
        cacheMap.put(cacheKey, t2);
        return (T)t2;
    }

    public static <T> T getPrimaryImpl(Class<T> feignClient) {
        Map beans = ApplicationContextRegister.getBeansOfType(feignClient);
        if (!beans.isEmpty()) {
            for (Object t : beans.values()) {
                if (!t.getClass().isAnnotationPresent(Primary.class)) continue;
                return (T)t;
            }
        }
        return null;
    }

    public static <T> T getHttpImpl(Class<T> feignClient) {
        Map beans = ApplicationContextRegister.getBeansOfType(feignClient);
        if (!beans.isEmpty()) {
            for (Object t : beans.values()) {
                if (!t.toString().startsWith("HardCodedTarget")) continue;
                return (T)t;
            }
        }
        return null;
    }

    private static boolean isIpV4Addr(String str) {
        String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        if (str.matches(regex)) {
            String[] arr = str.split("\\.");
            for (int i = 0; i < 4; ++i) {
                int temp = Integer.parseInt(arr[i]);
                if (temp >= 0 && temp <= 255) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static Exception errorDecoder(Response response) {
        if ("INFO".equalsIgnoreCase(VaFeignCommonConfig.getExceptionLevel())) {
            return new RuntimeException("Feign - Request Exception");
        }
        if ("WARN".equalsIgnoreCase(VaFeignCommonConfig.getExceptionLevel())) {
            StringBuilder result = new StringBuilder();
            if (response.reason() != null) {
                result.append(String.format("Feign - %d %s", response.status(), response.reason()));
            } else {
                result.append(String.format("Feign - %d", response.status()));
            }
            String url = response.request().url();
            url = url.substring(url.indexOf("/", 9));
            result.append(String.format(" during [%s] to [%s]", response.request().httpMethod(), url));
            return new RuntimeException(result.toString());
        }
        return null;
    }

    public static void retryableException(boolean retryable, RetryableException e) {
        if ("INFO".equalsIgnoreCase(VaFeignCommonConfig.getExceptionLevel())) {
            throw new RuntimeException("Feign - Request Exception");
        }
        if ("WARN".equalsIgnoreCase(VaFeignCommonConfig.getExceptionLevel())) {
            String url = e.request().url();
            url = url.substring(url.indexOf("/", 9));
            String msg = String.format("during [%s] to [%s]", e.request().httpMethod(), url);
            if (retryable) {
                throw new RuntimeException("Feign - 500 Retryable Exception " + msg);
            }
            throw new RuntimeException("Feign - 500 Request Exception " + msg);
        }
    }
}

