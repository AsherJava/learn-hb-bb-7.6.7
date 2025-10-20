/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO
 */
package com.jiuqi.dc.adjustvchr.impl.monitor;

import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO;
import java.util.Collection;
import java.util.List;

public interface IAdjustVoucherItemMonitor {
    public String monitorName();

    default public void beforeDelete(Collection<String> vchrIdList) {
    }

    default public void beforeSave(List<AdjustVchrItemEO> items) {
    }

    default public void afterDelete(Collection<String> vchrIdList) {
    }

    default public void afterSave(List<AdjustVchrItemEO> items) {
    }
}

