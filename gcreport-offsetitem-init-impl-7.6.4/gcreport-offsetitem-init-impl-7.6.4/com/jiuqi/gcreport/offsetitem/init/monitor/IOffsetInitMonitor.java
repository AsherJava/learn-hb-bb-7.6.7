/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.init.monitor;

import com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO;
import java.util.List;

public interface IOffsetInitMonitor {
    public String monitorName();

    default public void beforeSave(List<GcOffSetVchrItemInitEO> eoList) {
    }

    default public void afterSave(List<GcOffSetVchrItemInitEO> eoList) {
    }
}

