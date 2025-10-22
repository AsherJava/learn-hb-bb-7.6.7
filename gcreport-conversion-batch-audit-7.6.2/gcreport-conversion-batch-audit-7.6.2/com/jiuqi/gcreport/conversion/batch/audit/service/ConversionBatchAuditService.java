/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.conversion.batch.audit.service;

import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditFileEntity;
import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditRunnerEntity;
import com.jiuqi.np.period.PeriodWrapper;
import java.sql.Blob;
import java.util.List;
import java.util.Map;

public interface ConversionBatchAuditService {
    public void addAudit(ConversionBatchAuditRunnerEntity var1);

    public Map<String, Object> getAllBatchAudit(ConversionBatchAuditRunnerEntity var1);

    public List<ConversionBatchAuditFileEntity> getFileListForId(ConversionBatchAuditFileEntity var1);

    public void deleteSelectBatchAudit(List<String> var1);

    public String getFileNameForEntity();

    public PeriodWrapper getParamPeriod(ConversionBatchAuditRunnerEntity var1);

    public Blob getFileBlob(ConversionBatchAuditRunnerEntity var1, PeriodWrapper var2);
}

