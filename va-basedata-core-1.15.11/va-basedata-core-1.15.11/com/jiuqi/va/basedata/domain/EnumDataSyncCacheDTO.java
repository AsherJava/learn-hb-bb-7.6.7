/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.basedata.domain;

import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.mapper.domain.TenantDO;

public class EnumDataSyncCacheDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String currNodeId;
    private EnumDataDO enumDataDO;
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

    public EnumDataDO getEnumDataDO() {
        return this.enumDataDO;
    }

    public void setEnumDataDO(EnumDataDO enumDataDO) {
        this.enumDataDO = enumDataDO;
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
        this.checkKey = this.enumDataDO == null ? this.getTenantName() : (this.enumDataDO.getVer() != null ? this.getTenantName() + "#" + this.enumDataDO.getBiztype() + "#" + this.enumDataDO.getVer().toString() : this.getTenantName() + "#" + this.enumDataDO.getBiztype());
        return this.checkKey;
    }
}

