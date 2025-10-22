/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.service;

import com.jiuqi.gcreport.aidocaudit.dto.AuditParamDTO;
import java.util.List;
import java.util.Map;

public interface IAidocauditRuleViewService {
    public Map<String, Object> orguploadedstatus(AuditParamDTO var1);

    public Map<String, Object> orgauditstatus(AuditParamDTO var1);

    public List<Map<String, Object>> orgquestionstatus(AuditParamDTO var1);

    public List<Map<String, Object>> orglowestscorestatus(AuditParamDTO var1);
}

