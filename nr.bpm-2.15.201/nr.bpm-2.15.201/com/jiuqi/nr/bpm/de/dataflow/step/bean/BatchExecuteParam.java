/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 */
package com.jiuqi.nr.bpm.de.dataflow.step.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import java.util.List;

public class BatchExecuteParam
extends ExecuteParam {
    private BusinessKeySet businessKeySet;
    private List<DimensionValueSet> canUploadDimensionSets;
    private List<String> units;
    private List<String> formKeys;
    private List<String> groupKeys;
    private IConditionCache conditionCache;
    private List<String> messageIds;

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public List<String> getGroupKeys() {
        return this.groupKeys;
    }

    public void setGroupKeys(List<String> groupKeys) {
        this.groupKeys = groupKeys;
    }

    public BusinessKeySet getBusinessKeySet() {
        return this.businessKeySet;
    }

    public void setBusinessKeySet(BusinessKeySet businessKeySet) {
        this.businessKeySet = businessKeySet;
    }

    public List<DimensionValueSet> getCanUploadDimensionSets() {
        return this.canUploadDimensionSets;
    }

    public void setCanUploadDimensionSets(List<DimensionValueSet> canUploadDimensionSets) {
        this.canUploadDimensionSets = canUploadDimensionSets;
    }

    public List<String> getMessageIds() {
        return this.messageIds;
    }

    public void setMessageIds(List<String> messageIds) {
        this.messageIds = messageIds;
    }

    public List<String> getUnits() {
        return this.units;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }

    public IConditionCache getConditionCache() {
        return this.conditionCache;
    }

    public void setConditionCache(IConditionCache conditionCache) {
        this.conditionCache = conditionCache;
    }
}

