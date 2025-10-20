/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.paramsync.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncModuleEnum;

public class VaParamSyncImpGroupsDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String id;
    private String metaType;
    private VaParamSyncModuleEnum paramType;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMetaType() {
        return this.metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public VaParamSyncModuleEnum getParamType() {
        return this.paramType;
    }

    public void setParamType(VaParamSyncModuleEnum paramType) {
        this.paramType = paramType;
    }
}

