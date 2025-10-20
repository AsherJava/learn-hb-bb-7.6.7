/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.basedata.domain;

import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.mapper.domain.TenantDO;

public class BaseDataDefineSyncCacheDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String currNodeId;
    private BaseDataDefineDO baseDataDefine;
    private boolean forceUpdate = false;
    private boolean remove = false;
    private int retry = 0;
    private String checkKey;

    public String getCurrNodeId() {
        return this.currNodeId;
    }

    public void setCurrNodeId(String currNodeId) {
        this.currNodeId = currNodeId;
    }

    public BaseDataDefineDO getBaseDataDefine() {
        return this.baseDataDefine;
    }

    public void setBaseDataDefine(BaseDataDefineDO baseDataDefine) {
        BaseDataDefineDO define = new BaseDataDefineDO();
        define.setTenantName(baseDataDefine.getTenantName());
        define.setName(baseDataDefine.getName());
        define.setModifytime(baseDataDefine.getModifytime());
        this.baseDataDefine = define;
    }

    public boolean isForceUpdate() {
        return this.forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public boolean isRemove() {
        return this.remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public int getRetry() {
        return this.retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public String getCheckKey() {
        if (this.checkKey != null) {
            return this.checkKey;
        }
        this.checkKey = this.baseDataDefine == null ? this.getTenantName() : (this.baseDataDefine.getModifytime() != null ? this.getTenantName() + "#" + this.baseDataDefine.getName() + "#" + this.baseDataDefine.getModifytime().getTime() : this.getTenantName() + "#" + this.baseDataDefine.getName());
        return this.checkKey;
    }
}

