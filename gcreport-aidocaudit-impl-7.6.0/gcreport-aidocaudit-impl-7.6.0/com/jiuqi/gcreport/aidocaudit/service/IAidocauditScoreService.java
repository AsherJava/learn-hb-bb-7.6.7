/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 */
package com.jiuqi.gcreport.aidocaudit.service;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.aidocaudit.dto.AuditParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.CheckScoredResultDTO;

public interface IAidocauditScoreService {
    public BusinessResponseEntity<Boolean> audit(AuditParamDTO var1);

    public BusinessResponseEntity<CheckScoredResultDTO> checkScoredResult(AuditParamDTO var1);
}

