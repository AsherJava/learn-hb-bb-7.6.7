/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.openapi.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.openapi.domain.OpenApiAuthDO;

public class OpenApiSyncCacheDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String currtNodeId;
    private OpenApiAuthDO openApiAuthDO;
    private boolean update;
    private boolean remove;
    private boolean stop;

    public String getCurrtNodeId() {
        return this.currtNodeId;
    }

    public void setCurrtNodeId(String currtNodeId) {
        this.currtNodeId = currtNodeId;
    }

    public OpenApiAuthDO getOpenApiAuthDO() {
        return this.openApiAuthDO;
    }

    public void setOpenApiAuthDO(OpenApiAuthDO openApiAuthDO) {
        this.openApiAuthDO = openApiAuthDO;
    }

    public boolean isUpdate() {
        return this.update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isRemove() {
        return this.remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isStop() {
        return this.stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}

