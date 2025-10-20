/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.utils;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.efdcdatacheck.impl.pool.EFDCDataCheckThreadPoolProperties;
import java.util.concurrent.Semaphore;

public class GcEfdcDataCheckSemaphoreUtil {
    private static volatile Semaphore semaphore;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Semaphore getSemaphore() {
        if (semaphore != null) return semaphore;
        Class<GcEfdcDataCheckSemaphoreUtil> clazz = GcEfdcDataCheckSemaphoreUtil.class;
        synchronized (GcEfdcDataCheckSemaphoreUtil.class) {
            if (semaphore != null) return semaphore;
            EFDCDataCheckThreadPoolProperties threadPoolProperties = (EFDCDataCheckThreadPoolProperties)SpringContextUtils.getBean(EFDCDataCheckThreadPoolProperties.class);
            semaphore = new Semaphore(threadPoolProperties.getQueueCapacity());
            // ** MonitorExit[var0] (shouldn't be in output)
            return semaphore;
        }
    }
}

