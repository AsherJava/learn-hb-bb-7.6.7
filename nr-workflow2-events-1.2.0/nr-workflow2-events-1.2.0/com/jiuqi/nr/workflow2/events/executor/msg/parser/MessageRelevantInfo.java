/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.executor.msg.parser;

import java.util.Map;
import java.util.Set;

public class MessageRelevantInfo {
    private Map<String, String> varibleReplaceMap;
    private Set<String> relevantUnitKey;
    private Set<String> relevantUnitTitle;

    public Map<String, String> getVaribleReplaceMap() {
        return this.varibleReplaceMap;
    }

    public void setVaribleReplaceMap(Map<String, String> varibleReplaceMap) {
        this.varibleReplaceMap = varibleReplaceMap;
    }

    public Set<String> getRelevantUnitKey() {
        return this.relevantUnitKey;
    }

    public void setRelevantUnitKey(Set<String> relevantUnitKey) {
        this.relevantUnitKey = relevantUnitKey;
    }

    public Set<String> getRelevantUnitTitle() {
        return this.relevantUnitTitle;
    }

    public void setRelevantUnitTitle(Set<String> relevantUnitTitle) {
        this.relevantUnitTitle = relevantUnitTitle;
    }
}

