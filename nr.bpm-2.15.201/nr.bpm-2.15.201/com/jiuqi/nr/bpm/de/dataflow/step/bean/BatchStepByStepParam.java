/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.de.dataflow.step.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BaseStepParam;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class BatchStepByStepParam
extends BaseStepParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> stepUnit;
    private Map<String, LinkedHashSet<String>> formKeys = new HashMap<String, LinkedHashSet<String>>();
    private Map<String, LinkedHashSet<String>> GroupKeys = new HashMap<String, LinkedHashSet<String>>();
    private Map<String, LinkedHashSet<String>> stepFormKeyMap = new HashMap<String, LinkedHashSet<String>>();
    private Map<String, LinkedHashSet<String>> stepGroupKeyMap = new HashMap<String, LinkedHashSet<String>>();
    private String taskCode;

    public List<DimensionValueSet> getStepUnit() {
        ArrayList<DimensionValueSet> temp = new ArrayList<DimensionValueSet>();
        if (this.stepUnit != null && this.stepUnit.size() > 0) {
            for (String dimensionValueStr : this.stepUnit) {
                DimensionValueSet dimension = new DimensionValueSet();
                dimension.parseString(dimensionValueStr);
                temp.add(dimension);
            }
        }
        return temp;
    }

    public void setStepUnit(List<DimensionValueSet> stepUnit) {
        ArrayList<String> temp = new ArrayList<String>();
        if (stepUnit != null && stepUnit.size() > 0) {
            for (DimensionValueSet dimension : stepUnit) {
                temp.add(dimension.toString());
            }
        }
        this.stepUnit = temp;
    }

    public Map<String, LinkedHashSet<String>> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(Map<String, LinkedHashSet<String>> formKeys) {
        this.formKeys = formKeys;
    }

    public Map<String, LinkedHashSet<String>> getGroupKeys() {
        return this.GroupKeys;
    }

    public void setGroupKeys(Map<String, LinkedHashSet<String>> groupKeys) {
        this.GroupKeys = groupKeys;
    }

    public Map<String, LinkedHashSet<String>> getStepFormKeyMap() {
        return this.stepFormKeyMap;
    }

    public void setStepFormKeyMap(Map<String, LinkedHashSet<String>> stepFormKeyMap) {
        this.stepFormKeyMap = stepFormKeyMap;
    }

    public Map<String, LinkedHashSet<String>> getStepGroupKeyMap() {
        return this.stepGroupKeyMap;
    }

    public void setStepGroupKeyMap(Map<String, LinkedHashSet<String>> stepGroupKeyMap) {
        this.stepGroupKeyMap = stepGroupKeyMap;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }
}

