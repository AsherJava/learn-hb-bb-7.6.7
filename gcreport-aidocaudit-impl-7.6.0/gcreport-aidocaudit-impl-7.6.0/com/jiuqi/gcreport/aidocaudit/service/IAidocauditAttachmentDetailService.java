/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.service;

import com.jiuqi.gcreport.aidocaudit.dto.ResultitemOrderDTO;
import java.util.List;
import java.util.Map;

public interface IAidocauditAttachmentDetailService {
    public List<ResultitemOrderDTO> auditDetails(String var1, int var2);

    public Map<String, Object> resultScore(String var1, String var2);
}

