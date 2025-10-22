/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.step.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BatchUploadBean
implements Serializable {
    private static final long serialVersionUID = 1L;
    private BatchStepByStepParam stepByOptParam;
    @JsonIgnore
    private List<String> canUploadUnit;
    @JsonIgnore
    private IConditionCache conditionCache;
    private WorkFlowType workFlowType;
    private String dimName;
    private String defaultFormKey;
    private TaskContext taskContext;

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public String getDefaultFormKey() {
        return this.defaultFormKey;
    }

    public void setDefaultFormKey(String defaultFormKey) {
        this.defaultFormKey = defaultFormKey;
    }

    public List<DimensionValueSet> getCanUploadUnit() {
        ArrayList<DimensionValueSet> temp = new ArrayList<DimensionValueSet>();
        if (this.canUploadUnit != null && this.canUploadUnit.size() > 0) {
            for (String dimensionValueStr : this.canUploadUnit) {
                DimensionValueSet dimension = new DimensionValueSet();
                dimension.parseString(dimensionValueStr);
                temp.add(dimension);
            }
        }
        return temp;
    }

    public void setCanUploadUnit(List<DimensionValueSet> canUploadUnit) {
        ArrayList<String> temp = new ArrayList<String>();
        if (canUploadUnit != null && canUploadUnit.size() > 0) {
            for (DimensionValueSet dimension : canUploadUnit) {
                temp.add(dimension.toString());
            }
        }
        this.canUploadUnit = temp;
    }

    public WorkFlowType getWorkFlowType() {
        return this.workFlowType;
    }

    public void setWorkFlowType(WorkFlowType workFlowType) {
        this.workFlowType = workFlowType;
    }

    public BatchStepByStepParam getStepByOptParam() {
        return this.stepByOptParam;
    }

    public void setStepByOptParam(BatchStepByStepParam stepByOptParam) {
        this.stepByOptParam = stepByOptParam;
    }

    public IConditionCache getConditionCache() {
        return this.conditionCache;
    }

    public void setConditionCache(IConditionCache conditionCache) {
        this.conditionCache = conditionCache;
    }

    public TaskContext getTaskContext() {
        return this.taskContext;
    }

    public void setTaskContext(TaskContext taskContext) {
        this.taskContext = taskContext;
    }
}

