/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;

public class OrgVersionSyncCacheDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String currNodeId;
    private BigDecimal ver;
    private OrgVersionDO orgVersionDO;
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

    public BigDecimal getVer() {
        return this.ver;
    }

    public void setVer(BigDecimal ver) {
        this.ver = ver;
    }

    public OrgVersionDO getOrgVersionDO() {
        return this.orgVersionDO;
    }

    public void setOrgVersionDO(OrgVersionDO orgVersionDO) {
        this.orgVersionDO = orgVersionDO;
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
        this.checkKey = this.orgVersionDO.getId() != null ? this.getTenantName() + "#" + this.orgVersionDO.getCategoryname() + "#" + this.orgVersionDO.getId() + "#" + this.ver.toString() : this.getTenantName() + "#" + this.orgVersionDO.getCategoryname() + "#" + this.ver.toString();
        return this.checkKey;
    }
}

