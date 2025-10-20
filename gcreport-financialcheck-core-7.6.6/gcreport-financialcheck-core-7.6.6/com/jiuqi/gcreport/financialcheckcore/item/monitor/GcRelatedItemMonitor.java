/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.item.monitor;

import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.List;

public interface GcRelatedItemMonitor {
    default public List<GcRelatedItemEO> beforeSave(List<GcRelatedItemEO> eoList) {
        return null;
    }

    default public void afterSave(List<GcRelatedItemEO> eoList) {
    }

    default public void beforeCheckInfoUpdate(List<GcRelatedItemEO> eoList, String dbCheckState) {
    }

    default public void afterCheckInfoUpdate(List<GcRelatedItemEO> eoList, String dbCheckState) {
    }

    default public void beforeDelete(List<String> ids) {
    }
}

