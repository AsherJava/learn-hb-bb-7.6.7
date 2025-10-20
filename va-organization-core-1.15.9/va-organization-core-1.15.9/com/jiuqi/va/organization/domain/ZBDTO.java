/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.mapper.common.TenantUtil
 *  com.jiuqi.va.mapper.domain.TenantDTO
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.mapper.common.TenantUtil;
import com.jiuqi.va.mapper.domain.TenantDTO;

public class ZBDTO
extends ZB
implements TenantDTO {
    private String tenantName;
    private String orgcategoryname;
    private boolean checkval;
    private String relfieldname;

    public String getTenantName() {
        if (this.tenantName == null) {
            this.tenantName = TenantUtil.getTenantName();
        }
        return this.tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getOrgcategoryname() {
        return this.orgcategoryname;
    }

    public void setOrgcategoryname(String orgcategoryname) {
        this.orgcategoryname = orgcategoryname;
    }

    public boolean isCheckval() {
        return this.checkval;
    }

    public void setCheckval(boolean checkval) {
        this.checkval = checkval;
    }

    public String getRelfieldname() {
        return this.relfieldname;
    }

    public void setRelfieldname(String relfieldname) {
        this.relfieldname = relfieldname;
    }
}

