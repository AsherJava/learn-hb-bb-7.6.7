/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.readwrite.bean;

import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.readwrite.bean.EntityBatchAuthCache;
import java.util.Map;
import java.util.Set;

public class WorkflowBatchAuthCache {
    private EntityBatchAuthCache entityBatchAuthCache;
    private boolean allowFormRejecct;
    private Map<DimensionCacheKey, Set<String>> rejectEntityForms;

    public EntityBatchAuthCache getEntityBatchAuthCache() {
        return this.entityBatchAuthCache;
    }

    public void setEntityBatchAuthCache(EntityBatchAuthCache entityBatchAuthCache) {
        this.entityBatchAuthCache = entityBatchAuthCache;
    }

    public boolean isAllowFormRejecct() {
        return this.allowFormRejecct;
    }

    public void setAllowFormRejecct(boolean allowFormRejecct) {
        this.allowFormRejecct = allowFormRejecct;
    }

    public Map<DimensionCacheKey, Set<String>> getRejectEntityForms() {
        return this.rejectEntityForms;
    }

    public void setRejectEntityForms(Map<DimensionCacheKey, Set<String>> rejectEntityForms) {
        this.rejectEntityForms = rejectEntityForms;
    }
}

