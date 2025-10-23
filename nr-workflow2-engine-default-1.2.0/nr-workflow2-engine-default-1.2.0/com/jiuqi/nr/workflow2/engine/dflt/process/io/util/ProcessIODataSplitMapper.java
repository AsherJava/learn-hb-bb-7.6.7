/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessIODataSplitMapper {
    private final Map<String, List<String>> splitMap = new HashMap<String, List<String>>();

    public void putSplitRelation(String originalInstanceId, List<String> splitInstanceIds) {
        this.splitMap.put(originalInstanceId, splitInstanceIds);
    }

    public List<String> getSplitInstanceIds(String originalInstanceId) {
        return this.splitMap.get(originalInstanceId);
    }

    public void clear() {
        this.splitMap.clear();
    }
}

