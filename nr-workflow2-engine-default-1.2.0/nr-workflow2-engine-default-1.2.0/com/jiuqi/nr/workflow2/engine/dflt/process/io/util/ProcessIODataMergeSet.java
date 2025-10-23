/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io.util;

import java.util.HashSet;
import java.util.Set;

public class ProcessIODataMergeSet {
    private final Set<String> mergedInstanceIdSet = new HashSet<String>();

    public void addMergedInstanceId(String mergeInstanceId) {
        this.mergedInstanceIdSet.add(mergeInstanceId);
    }

    public boolean isMergedInstanceId(String instanceId) {
        return this.mergedInstanceIdSet.contains(instanceId);
    }

    public void clear() {
        this.mergedInstanceIdSet.clear();
    }
}

