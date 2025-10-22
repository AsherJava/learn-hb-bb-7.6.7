/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReportDataSyncProperties {
    @Value(value="${jiuqi.gc.dataSync.corePoolSize:5}")
    private int corePoolSize;
    @Value(value="${jiuqi.gc.dataSync.maximumPoolSize:5}")
    private int maximumPoolSize;

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return this.maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }
}

