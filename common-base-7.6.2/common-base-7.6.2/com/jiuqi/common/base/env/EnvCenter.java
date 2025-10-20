/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.common.base.env;

import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Date;

public class EnvCenter {
    public static NpContext getContext() {
        return NpContextHolder.getContext();
    }

    public static ContextUser getCurrentUser() {
        return EnvCenter.getContext().getUser();
    }

    public static ContextOrganization getCurrentOrg() {
        return EnvCenter.getContext().getOrganization();
    }

    public static ContextIdentity getCurrentIdentity() {
        return EnvCenter.getContext().getIdentity();
    }

    public static String getCurrentTenant() {
        return EnvCenter.getContext().getTenant();
    }

    public static Date getLoginTime() {
        return null;
    }

    public static String getCurrentUserID() {
        return EnvCenter.getContext().getUserId();
    }

    public static String getOrgID() {
        return EnvCenter.getContext().getOrgId().toString();
    }

    public static String getIdentityId() {
        return EnvCenter.getContext().getIdentityId();
    }
}

