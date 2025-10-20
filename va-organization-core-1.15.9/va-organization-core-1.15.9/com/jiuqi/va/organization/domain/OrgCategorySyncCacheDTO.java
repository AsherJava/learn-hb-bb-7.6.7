/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;

public class OrgCategorySyncCacheDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String currNodeId;
    private BigDecimal ver;
    private String[] categoryNames;
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

    public String[] getCategoryNames() {
        return this.categoryNames;
    }

    public void setCategoryNames(String ... categoryNames) {
        this.categoryNames = categoryNames;
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
        this.checkKey = this.getTenantName() + "#" + this.ver.toString();
        return this.checkKey;
    }
}

