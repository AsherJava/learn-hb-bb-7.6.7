/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.comm.LogTraceIDUtil
 *  org.springframework.web.context.request.RequestAttributes
 *  org.springframework.web.context.request.RequestContextHolder
 */
package com.jiuqi.np.core.application.impl;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.comm.LogTraceIDUtil;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class NpTaskExecutor {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run(NpContext npContext, Runnable runnable) {
        if (npContext == null) {
            throw new IllegalArgumentException("'npContext' must not be null.");
        }
        NpContext currentContext = NpContextHolder.getContext();
        Locale oldLocale = LocaleContextHolder.getLocale();
        Locale locale = currentContext.getLocale();
        if (null != locale && !locale.equals(oldLocale)) {
            LocaleContextHolder.setLocale(locale);
        }
        TimeZone oldTimeZone = LocaleContextHolder.getTimeZone();
        TimeZone timeZone = currentContext.getTimeZone();
        if (null != timeZone && !timeZone.equals(oldTimeZone)) {
            LocaleContextHolder.setTimeZone(timeZone);
        }
        try {
            NpContextHolder.setContext(npContext);
            runnable.run();
        }
        finally {
            NpContextHolder.setContext(currentContext);
            LocaleContextHolder.setLocale(oldLocale);
            LocaleContextHolder.setTimeZone(oldTimeZone);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <R> R run(NpContext npContext, Callable<R> callable) throws Exception {
        if (npContext == null) {
            throw new IllegalArgumentException("'npContext' must not be null.");
        }
        NpContext currentContext = NpContextHolder.getContext();
        Locale oldLocale = LocaleContextHolder.getLocale();
        Locale locale = currentContext.getLocale();
        if (null != locale && !locale.equals(oldLocale)) {
            LocaleContextHolder.setLocale(locale);
        }
        TimeZone oldTimeZone = LocaleContextHolder.getTimeZone();
        TimeZone timeZone = currentContext.getTimeZone();
        if (null != timeZone && !timeZone.equals(oldTimeZone)) {
            LocaleContextHolder.setTimeZone(timeZone);
        }
        try {
            NpContextHolder.setContext(npContext);
            R r = callable.call();
            return r;
        }
        finally {
            NpContextHolder.setContext(currentContext);
            LocaleContextHolder.setLocale(oldLocale);
            LocaleContextHolder.setTimeZone(oldTimeZone);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Async
    public void asyncRun(NpContext npContext, Locale locale, TimeZone timeZone, Map<String, String> logMap, RequestAttributes requestAttributes, Runnable runnable) {
        if (npContext == null) {
            throw new IllegalArgumentException("'npContext' must not be null.");
        }
        if (requestAttributes != null) {
            RequestContextHolder.setRequestAttributes((RequestAttributes)requestAttributes);
        }
        Locale oldLocale = LocaleContextHolder.getLocale();
        if (null != locale) {
            LocaleContextHolder.setLocale(locale);
        }
        TimeZone oldTimeZone = LocaleContextHolder.getTimeZone();
        if (null != timeZone) {
            LocaleContextHolder.setTimeZone(timeZone);
        }
        try {
            LogTraceIDUtil.setLogTraceIdContextMap(logMap);
            NpContextHolder.setContext(npContext);
            runnable.run();
        }
        finally {
            if (requestAttributes != null) {
                RequestContextHolder.resetRequestAttributes();
            }
            NpContextHolder.clearContext();
            LogTraceIDUtil.cleanLogTraceIdContextMap();
            LocaleContextHolder.setLocale(oldLocale);
            LocaleContextHolder.setTimeZone(oldTimeZone);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Async
    public <T> Future<T> asyncRun(NpContext npContext, Locale locale, TimeZone timeZone, Map<String, String> logMap, RequestAttributes requestAttributes, Callable<T> callable) throws Exception {
        if (npContext == null) {
            throw new IllegalArgumentException("'npContext' must not be null.");
        }
        if (requestAttributes != null) {
            RequestContextHolder.setRequestAttributes((RequestAttributes)requestAttributes);
        }
        Locale oldLocale = LocaleContextHolder.getLocale();
        if (null != locale) {
            LocaleContextHolder.setLocale(locale);
        }
        TimeZone oldTimeZone = LocaleContextHolder.getTimeZone();
        if (null != timeZone) {
            LocaleContextHolder.setTimeZone(timeZone);
        }
        try {
            LogTraceIDUtil.setLogTraceIdContextMap(logMap);
            NpContextHolder.setContext(npContext);
            T resultValue = callable.call();
            AsyncResult<T> asyncResult = new AsyncResult<T>(resultValue);
            return asyncResult;
        }
        finally {
            if (requestAttributes != null) {
                RequestContextHolder.resetRequestAttributes();
            }
            NpContextHolder.clearContext();
            LogTraceIDUtil.cleanLogTraceIdContextMap();
            LocaleContextHolder.setLocale(oldLocale);
            LocaleContextHolder.setTimeZone(oldTimeZone);
        }
    }
}

