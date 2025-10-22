/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.nr.context.cxt.DsContext
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.nr.context.cxt.DsContext;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public interface NpContextParam
extends Serializable {
    public String getTenant();

    public ContextUser getContextUser();

    public ContextIdentity getContextIdentity();

    public Map<String, ContextExtension> getExtensionMap();

    public Locale getLocal();

    public String getIp();

    public ContextOrganization getOrganization();

    public DsContext getDsContext();

    public String getTraceId();

    public Date getLoginDate();
}

