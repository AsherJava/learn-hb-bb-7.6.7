/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bizmeta.domain.metamodel;

import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.mapper.domain.TenantDO;

public class MetaModelDTO
extends TenantDO {
    private String moduleName;
    private String metaType;
    private OperateType operateType;

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getMetaType() {
        return this.metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public OperateType getOperateType() {
        return this.operateType;
    }

    public void setOperateType(OperateType operateType) {
        this.operateType = operateType;
    }
}

