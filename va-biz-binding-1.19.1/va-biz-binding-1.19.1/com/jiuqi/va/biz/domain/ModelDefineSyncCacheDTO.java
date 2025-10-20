/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.biz.domain;

import com.jiuqi.va.mapper.domain.TenantDO;

public class ModelDefineSyncCacheDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String defineName;
    private String currNodeId;

    public String getDefineName() {
        return this.defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    public String getCurrNodeId() {
        return this.currNodeId;
    }

    public void setCurrNodeId(String currNodeId) {
        this.currNodeId = currNodeId;
    }
}

