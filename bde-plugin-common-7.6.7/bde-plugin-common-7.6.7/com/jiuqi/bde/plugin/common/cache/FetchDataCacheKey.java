/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 */
package com.jiuqi.bde.plugin.common.cache;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;

public class FetchDataCacheKey {
    private String requestTaskId;
    private String bizCombId;

    public FetchDataCacheKey() {
    }

    public FetchDataCacheKey(BalanceCondition condi) {
        this.requestTaskId = condi.getRequestTaskId();
        this.bizCombId = condi.getBizCombId();
    }

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public String getBizCombId() {
        return this.bizCombId;
    }

    public void setBizCombId(String bizCombId) {
        this.bizCombId = bizCombId;
    }
}

