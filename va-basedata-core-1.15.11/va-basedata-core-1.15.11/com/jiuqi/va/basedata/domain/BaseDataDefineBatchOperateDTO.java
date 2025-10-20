/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.basedata.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.UUID;

public class BaseDataDefineBatchOperateDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private List<UUID> addAuthDataIdList;
    private List<UUID> reduceAuthDataIdList;
    private List<UUID> addDisableCacheIdList;
    private List<UUID> reduceDisableCacheIdList;

    public List<UUID> getAddAuthDataIdList() {
        return this.addAuthDataIdList;
    }

    public void setAddAuthDataIdList(List<UUID> addAuthDataIdList) {
        this.addAuthDataIdList = addAuthDataIdList;
    }

    public List<UUID> getReduceAuthDataIdList() {
        return this.reduceAuthDataIdList;
    }

    public void setReduceAuthDataIdList(List<UUID> reduceAuthDataIdList) {
        this.reduceAuthDataIdList = reduceAuthDataIdList;
    }

    public List<UUID> getAddDisableCacheIdList() {
        return this.addDisableCacheIdList;
    }

    public void setAddDisableCacheIdList(List<UUID> addDisableCacheIdList) {
        this.addDisableCacheIdList = addDisableCacheIdList;
    }

    public List<UUID> getReduceDisableCacheIdList() {
        return this.reduceDisableCacheIdList;
    }

    public void setReduceDisableCacheIdList(List<UUID> reduceDisableCacheIdList) {
        this.reduceDisableCacheIdList = reduceDisableCacheIdList;
    }
}

