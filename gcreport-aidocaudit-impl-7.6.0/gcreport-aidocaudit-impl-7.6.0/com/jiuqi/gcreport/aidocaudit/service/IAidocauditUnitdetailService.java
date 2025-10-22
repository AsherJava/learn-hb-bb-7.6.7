/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.service;

import com.jiuqi.gcreport.aidocaudit.dto.AidocauditResultDTO;
import com.jiuqi.gcreport.aidocaudit.dto.AidocauditResultDetailParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.AuditParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.ResultDetailDTO;
import com.jiuqi.gcreport.aidocaudit.dto.RuleItemTreeDTO;
import java.util.List;

public interface IAidocauditUnitdetailService {
    public List<AidocauditResultDTO> orgAuditResults(AuditParamDTO var1);

    public List<RuleItemTreeDTO> ruleAuditResults(AuditParamDTO var1);

    public List<ResultDetailDTO> ruleAuditResultDetail(AidocauditResultDetailParamDTO var1);
}

