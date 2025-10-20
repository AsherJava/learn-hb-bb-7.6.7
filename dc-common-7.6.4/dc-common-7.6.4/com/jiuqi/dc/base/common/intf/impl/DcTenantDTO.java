/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.domain.TenantDTO
 *  javax.persistence.Transient
 */
package com.jiuqi.dc.base.common.intf.impl;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.domain.TenantDTO;
import java.io.Serializable;
import javax.persistence.Transient;

public class DcTenantDTO
implements TenantDTO,
Serializable {
    private static final long serialVersionUID = -5990464558687267584L;
    @Transient
    public String tenantName;
    @Transient
    public String dbType;

    public String getTenantName() {
        if (this.tenantName == null) {
            this.tenantName = ShiroUtil.getTenantName();
        }
        return this.tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getDbType() {
        return this.dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String toString() {
        return "AcctTenantDTO{tenantName='" + this.tenantName + '\'' + '}';
    }
}

