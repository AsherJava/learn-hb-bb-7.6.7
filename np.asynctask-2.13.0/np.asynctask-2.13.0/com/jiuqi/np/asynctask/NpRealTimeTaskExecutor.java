/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.log.comm.LogTraceIDUtil
 *  com.jiuqi.nr.common.util.JsonUtil
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskStepMonitor;
import com.jiuqi.np.asynctask.AsyncTaskStepMonitorImpl;
import com.jiuqi.np.asynctask.context.AsyncTaskContextOrg;
import com.jiuqi.np.asynctask.context.AsyncTaskContextUser;
import com.jiuqi.np.asynctask.util.AsyncTaskConsts;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.log.comm.LogTraceIDUtil;
import com.jiuqi.nr.common.util.JsonUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

public abstract class NpRealTimeTaskExecutor
extends AbstractRealTimeJob {
    private static final long serialVersionUID = 1163076382226779059L;
    private static final Logger logger = LoggerFactory.getLogger(NpRealTimeTaskExecutor.class);
    protected AsyncTaskStepMonitor asyncTaskStepMonitor;

    @Deprecated
    public String getTaskPoolType() {
        return "\u8be5\u65b9\u6cd5\u5df2\u5e9f\u5f03!";
    }

    public void execute(JobContext jobContext) throws JobExecutionException {
        try {
            this.asyncTaskStepMonitor = new AsyncTaskStepMonitorImpl(jobContext);
            this.setNpContext(jobContext.getRealTimeJob().getParams());
            this.executeWithNpContext(jobContext);
        }
        finally {
            this.clearNpContext();
        }
    }

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
    }

    public void setNpContext(Map<String, String> params) throws JobExecutionException {
        NpContextImpl context = (NpContextImpl)NpContextHolder.createEmptyContext();
        try {
            context.setTenant(params.get("NR_CONTEXT_TENANT"));
            String user = params.get("NR_CONTEXT_USER");
            context.setUser(user != null ? (ContextUser)JsonUtil.toObject((String)user, AsyncTaskContextUser.class) : null);
            String identity = params.get("NR_CONTEXT_IDENTITY");
            context.setIdentity(identity != null ? (ContextIdentity)JsonUtil.toObject((String)identity, NpContextIdentity.class) : null);
            String string = params.get("NR_CONTEXT_ORGANIZATION");
            context.setOrganization(string != null ? (ContextOrganization)JsonUtil.toObject((String)string, AsyncTaskContextOrg.class) : null);
            String locale = params.get("NR_CONTEXT_LOCALE");
            context.setLocale(locale != null ? (Locale)JsonUtil.toObject((String)params.get("NR_CONTEXT_LOCALE"), Locale.class) : LocaleContextHolder.getLocale());
            String date = params.get("NR_CONTEXT_LOGINDATE");
            context.setLoginDate(date != null ? (Date)JsonUtil.toObject((String)date, Date.class) : null);
        }
        catch (Exception e) {
            logger.error("\u4efb\u52a1\u6267\u884c\u524d\u6784\u5efaNpContext\u51fa\u9519\uff01{}", (Object)e.getMessage());
            throw new JobExecutionException((Throwable)e);
        }
        context.setIp(params.get("NR_CONTEXT_IP"));
        LogTraceIDUtil.setLogTraceId((String)params.get("NR_CONTEXT_TRACEID"));
        HashMap<String, ContextExtension> extensionMap = new HashMap<String, ContextExtension>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            if (!key.startsWith("NR_CONTEXT_EXTENSION_")) continue;
            extensionMap.put(key.substring(AsyncTaskConsts.NR_CONTEXT_EXTENSION_PRE_LENGTH), (ContextExtension)SimpleParamConverter.SerializationUtils.deserialize(entry.getValue()));
        }
        if (!extensionMap.isEmpty()) {
            for (Map.Entry<String, String> entry : extensionMap.entrySet()) {
                ContextExtension contextExtension = context.getExtension(entry.getKey());
                Consumer<Map.Entry> consumer = item -> {
                    Serializable seriaValue = (Serializable)item.getValue();
                    contextExtension.put((String)item.getKey(), seriaValue);
                };
                ((ContextExtension)entry.getValue()).apply(consumer);
            }
        }
        NpContextHolder.setContext((NpContext)context);
    }

    public void clearNpContext() {
        NpContextHolder.clearContext();
    }

    public String getTaskKey() {
        return super.getQueryField1();
    }

    public String getFormSchemeKey() {
        return super.getQueryField2();
    }

    public String getArgs() {
        return (String)super.getParams().get("NR_ARGS");
    }
}

