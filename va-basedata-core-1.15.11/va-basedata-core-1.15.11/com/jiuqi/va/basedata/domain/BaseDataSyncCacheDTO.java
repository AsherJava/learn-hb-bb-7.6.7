/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.basedata.domain;

import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.mapper.domain.TenantDO;

public class BaseDataSyncCacheDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String currNodeId;
    private BaseDataDTO baseDataDTO;
    private boolean forceUpdate = false;
    private boolean remove = false;
    private boolean clean = false;
    private int retry = 0;
    private String checkKey;

    public String getCurrNodeId() {
        return this.currNodeId;
    }

    public void setCurrNodeId(String currNodeId) {
        this.currNodeId = currNodeId;
    }

    public BaseDataDTO getBaseDataDTO() {
        return this.baseDataDTO;
    }

    public void setBaseDataDTO(BaseDataDTO baseDataDTO) {
        BaseDataDTO bdto = new BaseDataDTO();
        bdto.setTenantName(baseDataDTO.getTenantName());
        bdto.setTableName(baseDataDTO.getTableName());
        bdto.setVer(baseDataDTO.getVer());
        if (baseDataDTO.getId() != null) {
            bdto.setId(baseDataDTO.getId());
        }
        if (baseDataDTO.getQueryStartVer() != null) {
            bdto.setQueryStartVer(baseDataDTO.getQueryStartVer());
        } else if (baseDataDTO.getVer() != null) {
            bdto.setQueryStartVer(baseDataDTO.getVer());
        } else {
            bdto.setQueryStartVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        }
        if (baseDataDTO.getVersionDate() != null) {
            bdto.setVersionDate(baseDataDTO.getVersionDate());
        }
        this.baseDataDTO = bdto;
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

    public boolean isClean() {
        return this.clean;
    }

    public void setClean(boolean clean) {
        this.clean = clean;
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
        this.checkKey = this.baseDataDTO.getQueryStartVer() != null ? this.getTenantName() + "#" + this.baseDataDTO.getTableName() + "#" + this.baseDataDTO.getQueryStartVer().toString() : this.getTenantName() + "#" + this.baseDataDTO.getTableName();
        return this.checkKey;
    }
}

