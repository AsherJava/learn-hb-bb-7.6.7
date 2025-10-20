/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextConsumer;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class NpContextBuilder {
    private final NpContextImpl context = new NpContextImpl();

    public NpContextBuilder from(NpContext npContext) {
        if (npContext != null) {
            this.tenant(npContext.getTenant());
            this.user(npContext.getUser());
            this.identity(npContext.getIdentity());
            this.organization(npContext.getOrganization());
            this.local(npContext.getLocale());
            this.date(npContext.getLoginDate());
            this.ip(npContext.getDeviceMsg());
            this.deviceMsg(npContext.getDeviceMsg());
            this.extension(npContext.getDefaultExtension());
        }
        return this;
    }

    public NpContextBuilder extension(ContextExtension ctxExt) {
        HashMap<String, Object> contextextInfo = new HashMap<String, Object>();
        NpContextConsumer consumer = new NpContextConsumer(contextextInfo);
        ctxExt.apply(consumer);
        ContextExtension newCtxExt = this.context.getDefaultExtension();
        contextextInfo.entrySet().stream().forEach(o -> {
            Object value = o.getValue();
            if (null != value) {
                if (value instanceof Serializable) {
                    newCtxExt.put((String)o.getKey(), (Serializable)value);
                }
            } else {
                newCtxExt.put((String)o.getKey(), null);
            }
        });
        return this;
    }

    public NpContextBuilder fromCurrent() {
        return this.from(NpContextHolder.getContext());
    }

    public NpContextBuilder date(Date loginDate) {
        this.context.setLoginDate(loginDate);
        return this;
    }

    public NpContextBuilder ip(String ip) {
        this.context.setIp(ip);
        return this;
    }

    public NpContextBuilder deviceMsg(String deviceMsg) {
        this.context.setDeviceMsg(deviceMsg);
        return this;
    }

    public NpContextBuilder tenant(String tenant) {
        this.context.setTenant(tenant);
        return this;
    }

    public NpContextBuilder user(ContextUser user) {
        this.context.setUser(user);
        return this;
    }

    public NpContextBuilder identity(ContextIdentity identity) {
        this.context.setIdentity(identity);
        return this;
    }

    public NpContextBuilder organization(ContextOrganization organization) {
        this.context.setOrganization(organization);
        return this;
    }

    public NpContextBuilder local(Locale locale) {
        this.context.setLocale(locale);
        return this;
    }

    public NpContext build() {
        return this.context;
    }
}

