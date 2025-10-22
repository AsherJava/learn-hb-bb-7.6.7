/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.samecontrol.vo;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;

public class SameChgOrgVersionVO {
    private GcOrgCacheVO changeBeforeOrg;
    private GcOrgCacheVO changeCurrentOrg;

    public GcOrgCacheVO getChangeBeforeOrg() {
        return this.changeBeforeOrg;
    }

    public void setChangeBeforeOrg(GcOrgCacheVO changeBeforeOrg) {
        this.changeBeforeOrg = changeBeforeOrg;
    }

    public GcOrgCacheVO getChangeCurrentOrg() {
        return this.changeCurrentOrg;
    }

    public void setChangeCurrentOrg(GcOrgCacheVO changeCurrentOrg) {
        this.changeCurrentOrg = changeCurrentOrg;
    }
}

