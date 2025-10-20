/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.vo.tool;

import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;

public class OrgCenterParam {
    private String orgType;
    private YearPeriodDO yp;
    private GcAuthorityType authType;

    public OrgCenterParam(String orgType, YearPeriodObject yp, GcAuthorityType authType) {
        this(orgType, yp.formatYP(), authType);
    }

    public OrgCenterParam(String orgType, YearPeriodDO yp, GcAuthorityType authType) {
        this.authType = authType;
        this.yp = yp;
        this.orgType = orgType;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public YearPeriodDO getYp() {
        return this.yp;
    }

    public void setYp(YearPeriodDO yp) {
        this.yp = yp;
    }

    public GcAuthorityType getAuthType() {
        return this.authType;
    }

    public void setAuthType(GcAuthorityType authType) {
        this.authType = authType;
    }
}

