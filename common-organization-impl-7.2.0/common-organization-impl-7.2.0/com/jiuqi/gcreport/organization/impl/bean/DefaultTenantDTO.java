/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.mapper.domain.TenantDTO
 */
package com.jiuqi.gcreport.organization.impl.bean;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.mapper.domain.TenantDTO;

public interface DefaultTenantDTO
extends TenantDTO {
    default public String getTenantName() {
        return NpContextHolder.getContext().getTenant();
    }
}

