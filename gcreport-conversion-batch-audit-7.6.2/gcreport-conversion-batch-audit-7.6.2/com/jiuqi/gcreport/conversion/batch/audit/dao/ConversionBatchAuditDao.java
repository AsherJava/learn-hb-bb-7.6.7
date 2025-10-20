/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.batch.audit.dao;

import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditFileEntity;
import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditRunnerEntity;
import java.util.List;

public interface ConversionBatchAuditDao {
    public String addBatchAudit(ConversionBatchAuditRunnerEntity var1);

    public void deleteSelectBatchAudit(List<String> var1);

    public List<ConversionBatchAuditRunnerEntity> getAllBatchAudit(ConversionBatchAuditRunnerEntity var1);

    public List<ConversionBatchAuditFileEntity> getFileListForId(ConversionBatchAuditFileEntity var1);

    public int getAllBatchAuditCount(ConversionBatchAuditRunnerEntity var1);
}

