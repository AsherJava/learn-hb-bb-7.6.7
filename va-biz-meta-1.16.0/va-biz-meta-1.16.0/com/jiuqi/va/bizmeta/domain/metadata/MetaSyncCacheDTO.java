/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bizmeta.domain.metadata;

import com.jiuqi.va.mapper.domain.TenantDO;

public class MetaSyncCacheDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String uuid;

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}

