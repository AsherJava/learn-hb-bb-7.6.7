/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context.impl;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.impl.NpContextExtension;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Consumer;
import org.springframework.context.i18n.LocaleContextHolder;

public class NpContextImpl
implements NpContext {
    private static final long serialVersionUID = -7302909942675060096L;
    private String tenant;
    private Date loginDate;
    private ContextUser user;
    private ContextIdentity identity;
    private ContextOrganization organization;
    private HashMap<String, ContextExtension> extensionMap;
    private Locale locale;
    private TimeZone timeZone;
    private String ip;
    private String deviceMsg;

    @Override
    public String getTenant() {
        if (this.tenant == null || this.tenant.isEmpty()) {
            return "__default_tenant__";
        }
        return this.tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    @Override
    public Date getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    @Override
    public ContextUser getUser() {
        return this.user;
    }

    public void setUser(ContextUser user) {
        this.user = user;
    }

    public void setOrganization(ContextOrganization organization) {
        this.organization = organization;
    }

    @Override
    public ContextIdentity getIdentity() {
        return this.identity;
    }

    public void setIdentity(ContextIdentity identity) {
        this.identity = identity;
    }

    @Override
    public ContextOrganization getOrganization() {
        return this.organization;
    }

    @Override
    public ContextExtension getExtension(String applicationName) {
        ContextExtension extension;
        if (null == this.extensionMap) {
            this.extensionMap = new HashMap();
        }
        if (null == (extension = this.extensionMap.get(applicationName))) {
            extension = new NpContextExtension();
            this.extensionMap.put(applicationName, extension);
        }
        return extension;
    }

    @Override
    public void applyExtensions(Consumer<Map.Entry<String, ContextExtension>> consumer) {
        if (this.extensionMap == null || this.extensionMap.size() <= 0) {
            return;
        }
        for (Map.Entry<String, ContextExtension> entry : this.extensionMap.entrySet()) {
            consumer.accept(entry);
        }
    }

    @Override
    public Locale getLocale() {
        if (this.locale == null) {
            return LocaleContextHolder.getLocale();
        }
        return this.locale;
    }

    @Override
    public String getIp() {
        return this.ip;
    }

    public void setDeviceMsg(String deviceMsg) {
        this.deviceMsg = deviceMsg;
    }

    @Override
    public String getDeviceMsg() {
        return this.deviceMsg;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        LocaleContextHolder.setLocale(locale);
    }

    @Override
    public TimeZone getTimeZone() {
        if (null == this.timeZone) {
            return LocaleContextHolder.getTimeZone();
        }
        return this.timeZone;
    }

    @Override
    public TimeZone getServerTimeZone() {
        return TimeZone.getDefault();
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
        LocaleContextHolder.setTimeZone(timeZone);
    }
}

