/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.monitor;

import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import java.util.Collection;
import java.util.List;

public interface IOffsetCoreMonitor {
    public String monitorName();

    default public void beforeDelete(Collection<String> idList) {
    }

    default public void beforeSave(List<GcOffSetVchrItemAdjustEO> eoList) {
    }

    default public void afterDelete(Collection<String> idList) {
    }

    default public void afterSave(List<GcOffSetVchrItemAdjustEO> eoList) {
    }
}

