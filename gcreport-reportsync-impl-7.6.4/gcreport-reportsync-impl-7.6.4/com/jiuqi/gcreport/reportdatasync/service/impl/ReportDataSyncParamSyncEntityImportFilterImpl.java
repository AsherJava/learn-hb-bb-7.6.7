/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.ext.filter.IEntityImportFilter
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import com.jiuqi.nr.entity.ext.filter.IEntityImportFilter;
import org.springframework.stereotype.Component;

@Component
public class ReportDataSyncParamSyncEntityImportFilterImpl
implements IEntityImportFilter {
    public boolean importEntityDefine(String entityId) {
        if ("MD_ORG".equals(entityId)) {
            return false;
        }
        if ("MD_CURRENCY".equals(entityId)) {
            return false;
        }
        return !"MD_GCORGTYPE".equals(entityId);
    }

    public boolean importEntityData(String entityId) {
        if ("MD_ORG".equals(entityId)) {
            return false;
        }
        if ("MD_CURRENCY".equals(entityId)) {
            return false;
        }
        return !"MD_GCORGTYPE".equals(entityId);
    }
}

