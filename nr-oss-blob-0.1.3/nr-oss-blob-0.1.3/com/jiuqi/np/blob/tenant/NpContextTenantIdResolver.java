/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.np.blob.tenant;

import com.jiuqi.np.blob.tenant.TenantIdResolver;
import com.jiuqi.np.core.context.NpContextHolder;

public class NpContextTenantIdResolver
implements TenantIdResolver {
    @Override
    public String getCurrentTenantId() {
        return NpContextHolder.getContext().getTenant();
    }
}

