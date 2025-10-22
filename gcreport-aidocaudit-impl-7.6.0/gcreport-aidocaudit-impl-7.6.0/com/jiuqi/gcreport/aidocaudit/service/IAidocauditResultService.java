/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.service;

import com.jiuqi.gcreport.aidocaudit.dto.MQScoreResultDTO;
import java.util.Map;

public interface IAidocauditResultService {
    public void handerMessage(MQScoreResultDTO var1);

    public void handerErrorMessage(Map<String, String> var1);
}

