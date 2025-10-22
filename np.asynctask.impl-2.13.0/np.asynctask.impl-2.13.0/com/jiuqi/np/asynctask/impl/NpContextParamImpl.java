/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.NpContextParam
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.nr.context.cxt.DsContext
 */
package com.jiuqi.np.asynctask.impl;

import com.jiuqi.np.asynctask.NpContextParam;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.nr.context.cxt.DsContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NpContextParamImpl
implements NpContextParam {
    private static final long serialVersionUID = -7543774477909108644L;
    private String tenant;
    private ContextUser contextUser;
    private ContextIdentity contextIdentity;
    private Map<String, ContextExtension> extensionMap;
    private Locale locale;
    private String ip;
    private ContextOrganization organization;
    private DsContext dsContext;
    private Date loginDate;
    private String traceId;

    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTenant() {
        return this.tenant;
    }

    public ContextUser getContextUser() {
        return this.contextUser;
    }

    public ContextIdentity getContextIdentity() {
        return this.contextIdentity;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public void setContextUser(ContextUser contextUser) {
        this.contextUser = contextUser;
    }

    public void setContextIdentity(ContextIdentity contextIdentity) {
        this.contextIdentity = contextIdentity;
    }

    public void setExtensionMap(HashMap<String, ContextExtension> extensionMap) {
        this.extensionMap = extensionMap;
    }

    public Map<String, ContextExtension> getExtensionMap() {
        return this.extensionMap;
    }

    public Locale getLocal() {
        return this.locale;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public ContextOrganization getOrganization() {
        return this.organization;
    }

    public void setOrganization(ContextOrganization organization) {
        this.organization = organization;
    }

    public void setDsContext(DsContext dsContext) {
        this.dsContext = dsContext;
    }

    public DsContext getDsContext() {
        return this.dsContext;
    }

    public Date getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}

