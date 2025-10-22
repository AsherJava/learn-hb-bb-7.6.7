/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 */
package com.jiuqi.gcreport.bde.fetch.impl.intf.impl;

import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import java.util.List;

public class BatchFetchHandleResult {
    private boolean success;
    private List<EfdcInfo> efdcInfoList;

    public BatchFetchHandleResult() {
    }

    public BatchFetchHandleResult(boolean success, List<EfdcInfo> efdcInfoList) {
        this.success = success;
        this.efdcInfoList = efdcInfoList;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<EfdcInfo> getEfdcInfoList() {
        return this.efdcInfoList;
    }

    public void setEfdcInfoList(List<EfdcInfo> efdcInfoList) {
        this.efdcInfoList = efdcInfoList;
    }

    public String toString() {
        return "BatchFetchHandleResult [success=" + this.success + ", efdcInfoList=" + this.efdcInfoList + "]";
    }
}

