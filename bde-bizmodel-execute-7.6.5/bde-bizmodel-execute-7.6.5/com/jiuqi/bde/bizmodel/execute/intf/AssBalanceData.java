/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.intf;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceData;
import java.util.Map;

public class AssBalanceData
extends BalanceData {
    private String optimAssistKey;
    private String assistKey;
    private Map<String, String> assistMap;

    public String getOptimAssistKey() {
        return this.optimAssistKey;
    }

    public void setOptimAssistKey(String optimAssistKey) {
        this.optimAssistKey = optimAssistKey;
    }

    public String getAssistKey() {
        return this.assistKey;
    }

    public void setAssistKey(String assistKey) {
        this.assistKey = assistKey;
    }

    public Map<String, String> getAssistMap() {
        return this.assistMap;
    }

    public void setAssistMap(Map<String, String> assistMap) {
        this.assistMap = assistMap;
    }
}

