/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;

public class SubmitAfterCaculateEvent {
    BatchCalculateInfo batchCalculateInfo;
    NpContext npContext;

    public BatchCalculateInfo getBatchCalculateInfo() {
        return this.batchCalculateInfo;
    }

    public void setBatchCalculateInfo(BatchCalculateInfo batchCalculateInfo) {
        this.batchCalculateInfo = batchCalculateInfo;
    }

    public NpContext getNpContext() {
        return this.npContext;
    }

    public void setNpContext(NpContext npContext) {
        this.npContext = npContext;
    }
}

