/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.storage.intf.impl;

import com.jiuqi.dc.base.common.storage.intf.SyncTableCtx;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SyncTableCtxImpl
implements SyncTableCtx {
    private String tenantName;
    private List<Integer> yearList;

    public SyncTableCtxImpl(String tenantName, List<Integer> yearList) {
        this.tenantName = tenantName;
        if (yearList != null && yearList.size() > 0) {
            this.yearList = yearList;
        } else {
            this.yearList = new ArrayList<Integer>();
            this.yearList.add(LocalDate.now().getYear());
        }
    }

    @Override
    public String getTenantName() {
        return this.tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    @Override
    public List<Integer> getYearList() {
        return this.yearList;
    }

    public void setYearList(List<Integer> yearList) {
        this.yearList = yearList;
    }
}

