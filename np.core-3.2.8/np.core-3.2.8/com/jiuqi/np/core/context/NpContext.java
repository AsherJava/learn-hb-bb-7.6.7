/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Consumer;

public interface NpContext
extends Serializable {
    public static final String DEFAULT_TENANT = "__default_tenant__";

    public String getTenant();

    public Date getLoginDate();

    public ContextUser getUser();

    public ContextIdentity getIdentity();

    public ContextOrganization getOrganization();

    public ContextExtension getExtension(String var1);

    public void applyExtensions(Consumer<Map.Entry<String, ContextExtension>> var1);

    default public String getUserId() {
        ContextUser user = this.getUser();
        return user == null ? null : user.getId();
    }

    default public String getUserName() {
        ContextUser user = this.getUser();
        return user == null ? null : user.getName();
    }

    default public String getIdentityId() {
        ContextIdentity identity = this.getIdentity();
        return identity == null ? null : identity.getId();
    }

    default public String getOrgId() {
        ContextOrganization organization = this.getOrganization();
        return organization == null ? null : organization.getId();
    }

    default public ContextExtension getDefaultExtension() {
        String DEFAULT_APPLICATION_NAME = "com.jiuqi.np";
        return this.getExtension("com.jiuqi.np");
    }

    public Locale getLocale();

    public TimeZone getTimeZone();

    public TimeZone getServerTimeZone();

    public String getIp();

    public String getDeviceMsg();
}

