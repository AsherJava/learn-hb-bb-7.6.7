/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.context;

import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;

public class DataModelSyncCacheDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String currNodeId;
    private BigDecimal ver;
    private DataModelDO dataModel;
    private boolean forceUpdate = false;
    private boolean remove = false;
    private int retry = 0;

    public String getCurrNodeId() {
        return this.currNodeId;
    }

    public void setCurrNodeId(String currNodeId) {
        this.currNodeId = currNodeId;
    }

    public BigDecimal getVer() {
        return this.ver;
    }

    public void setVer(BigDecimal ver) {
        this.ver = ver;
    }

    public DataModelDO getDataModel() {
        return this.dataModel;
    }

    public void setDataModel(DataModelDO dataModel) {
        this.dataModel = dataModel;
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
        if (this.dataModel != null) {
            return this.getTenantName() + "#" + this.dataModel.getName() + "#" + this.ver.toString();
        }
        return this.getTenantName() + "#" + this.ver.toString();
    }
}

