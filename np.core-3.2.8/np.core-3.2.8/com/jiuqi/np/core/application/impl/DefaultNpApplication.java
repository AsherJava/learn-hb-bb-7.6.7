/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.comm.LogTraceIDUtil
 *  org.springframework.web.context.request.RequestAttributes
 *  org.springframework.web.context.request.RequestContextHolder
 */
package com.jiuqi.np.core.application.impl;

import com.jiuqi.np.common.exception.NvwaUserNotFoundException;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.application.NpApplicationProperties;
import com.jiuqi.np.core.application.NpContextForUserProvider;
import com.jiuqi.np.core.application.impl.NpTaskExecutor;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextBuilder;
import com.jiuqi.np.log.comm.LogTraceIDUtil;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class DefaultNpApplication
implements NpApplication {
    @Autowired
    private NpApplicationProperties applicationProperties;
    @Autowired
    private NpTaskExecutor taskExecutor;
    @Autowired(required=false)
    private NpContextForUserProvider contextForUserProvider;

    @Override
    public NpContext getTempContext(String userName) throws NvwaUserNotFoundException {
        if (null != this.contextForUserProvider) {
            return this.contextForUserProvider.getTempContext(userName);
        }
        throw new NvwaUserNotFoundException("\u8bf7\u5b9e\u73b0 NpContextForUserProvider");
    }

    @Override
    public String getApplicationName() {
        return this.applicationProperties.getName();
    }

    @Override
    public void runAsContext(NpContext npContext, Runnable runnable) {
        this.taskExecutor.run(npContext, runnable);
    }

    @Override
    public <R> R runAsContext(NpContext npContext, Callable<R> callable) throws Exception {
        return this.taskExecutor.run(npContext, callable);
    }

    @Override
    public void asyncRunAsContext(NpContext npContext, Runnable runnable) {
        TimeZone timeZone;
        Locale locale;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            try {
                requestAttributes.getSessionId();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (null == (locale = npContext.getLocale())) {
            locale = LocaleContextHolder.getLocale();
        }
        if (null == (timeZone = npContext.getTimeZone())) {
            timeZone = LocaleContextHolder.getTimeZone();
        }
        Map map = LogTraceIDUtil.getLogTraceIdContextMap();
        this.taskExecutor.asyncRun(npContext, locale, timeZone, (Map<String, String>)map, requestAttributes, runnable);
    }

    @Override
    public <T> Future<T> asyncRunAsContext(NpContext npContext, Callable<T> callable) throws Exception {
        TimeZone timeZone;
        Locale locale;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            try {
                requestAttributes.getSessionId();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (null == (locale = npContext.getLocale())) {
            locale = LocaleContextHolder.getLocale();
        }
        if (null == (timeZone = npContext.getTimeZone())) {
            timeZone = LocaleContextHolder.getTimeZone();
        }
        Map map = LogTraceIDUtil.getLogTraceIdContextMap();
        return this.taskExecutor.asyncRun(npContext, locale, timeZone, (Map<String, String>)map, requestAttributes, callable);
    }

    @Override
    public <R> R runAsTenant(String tenantId, Callable<R> callable) throws Exception {
        NpContext context = new NpContextBuilder().tenant(tenantId).build();
        return this.runAsContext(context, callable);
    }

    @Override
    public void runAsTenant(String tenantId, Runnable runnable) {
        NpContext context = new NpContextBuilder().tenant(tenantId).build();
        this.runAsContext(context, runnable);
    }

    @Override
    public <T> Future<T> asyncRunAsTenant(String tenantId, Callable<T> callable) throws Exception {
        NpContext context = new NpContextBuilder().tenant(tenantId).build();
        return this.asyncRunAsContext(context, callable);
    }
}

