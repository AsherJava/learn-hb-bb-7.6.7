/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.dataentry.readwrite.bean;

import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.readwrite.bean.EntityBatchAuthCache;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.HashSet;
import java.util.Map;

public class FlowObjectBatchAuthCache
extends EntityBatchAuthCache {
    private WorkFlowType workFlowType;
    private Map<DimensionCacheKey, HashSet<String>> notCanWriteFormKeys;

    public WorkFlowType getWorkFlowType() {
        return this.workFlowType;
    }

    public void setWorkFlowType(WorkFlowType workFlowType) {
        this.workFlowType = workFlowType;
    }

    public Map<DimensionCacheKey, HashSet<String>> getNotCanWriteFormKeys() {
        return this.notCanWriteFormKeys;
    }

    public void setNotCanWriteFormKeys(Map<DimensionCacheKey, HashSet<String>> notCanWriteFormKeys) {
        this.notCanWriteFormKeys = notCanWriteFormKeys;
    }
}

