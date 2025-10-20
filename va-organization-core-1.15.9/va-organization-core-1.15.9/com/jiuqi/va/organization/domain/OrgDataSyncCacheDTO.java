/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Set;
import java.util.UUID;

public class OrgDataSyncCacheDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String currNodeId;
    private OrgDTO orgDTO;
    private boolean forceUpdate = false;
    private boolean remove = false;
    private Set<UUID> removeIds;
    private boolean clean = false;
    private int retry = 0;
    private String checkKey;

    public String getCurrNodeId() {
        return this.currNodeId;
    }

    public void setCurrNodeId(String currNodeId) {
        this.currNodeId = currNodeId;
    }

    public OrgDTO getOrgDTO() {
        return this.orgDTO;
    }

    public void setOrgDTO(OrgDTO orgDTO) {
        OrgDTO org = new OrgDTO();
        org.setTenantName(orgDTO.getTenantName());
        org.setCategoryname(orgDTO.getCategoryname());
        if (orgDTO.getId() != null) {
            org.setId(orgDTO.getId());
        }
        if (orgDTO.getQueryStartVer() != null) {
            org.setQueryStartVer(orgDTO.getQueryStartVer());
        } else if (orgDTO.getVer() != null) {
            org.setQueryStartVer(orgDTO.getVer());
        } else {
            org.setQueryStartVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        }
        if (orgDTO.getVersionDate() != null) {
            org.setVersionDate(orgDTO.getVersionDate());
        }
        this.orgDTO = org;
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

    public Set<UUID> getRemoveIds() {
        return this.removeIds;
    }

    public void setRemoveIds(Set<UUID> removeIds) {
        this.removeIds = removeIds;
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
        this.checkKey = this.orgDTO.getQueryStartVer() != null ? this.getTenantName() + "#" + this.orgDTO.getCategoryname() + "#" + this.orgDTO.getQueryStartVer().toString() : this.getTenantName() + "#" + this.orgDTO.getCategoryname();
        return this.checkKey;
    }
}

