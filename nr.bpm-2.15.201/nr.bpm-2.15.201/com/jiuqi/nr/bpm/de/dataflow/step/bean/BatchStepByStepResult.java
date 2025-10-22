/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.step.bean;

import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchNoOperate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BatchStepByStepResult {
    private Set<String> operateUnits;
    private Map<BatchNoOperate, List<BatchNoOperate>> noOperateUnitMap;
    private Map<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>> noOperateGroupOrFormMap;
    private Map<String, LinkedHashSet<String>> operateUnitAndForms;
    private Map<String, LinkedHashSet<String>> operateUnitAndGroups;
    private CompleteMsg completeMsg;

    public Set<String> getOperateUnits() {
        return this.operateUnits;
    }

    public void setOperateUnits(Set<String> operateUnits) {
        this.operateUnits = operateUnits;
    }

    public Map<String, LinkedHashSet<String>> getOperateUnitAndForms() {
        return this.operateUnitAndForms;
    }

    public void setOperateUnitAndForms(Map<String, LinkedHashSet<String>> operateUnitAndForms) {
        this.operateUnitAndForms = operateUnitAndForms;
    }

    public Map<String, LinkedHashSet<String>> getOperateUnitAndGroups() {
        return this.operateUnitAndGroups;
    }

    public void setOperateUnitAndGroups(Map<String, LinkedHashSet<String>> operateUnitAndGroups) {
        this.operateUnitAndGroups = operateUnitAndGroups;
    }

    public Map<BatchNoOperate, List<BatchNoOperate>> getNoOperateUnitMap() {
        return this.noOperateUnitMap;
    }

    public void setNoOperateUnitMap(Map<BatchNoOperate, List<BatchNoOperate>> noOperateUnitMap) {
        this.noOperateUnitMap = noOperateUnitMap;
    }

    public CompleteMsg getCompleteMsg() {
        return this.completeMsg;
    }

    public void setCompleteMsg(CompleteMsg completeMsg) {
        this.completeMsg = completeMsg;
    }

    public Map<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>> getNoOperateGroupOrFormMap() {
        return this.noOperateGroupOrFormMap;
    }

    public void setNoOperateGroupOrFormMap(Map<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>> noOperateGroupOrFormMap) {
        this.noOperateGroupOrFormMap = noOperateGroupOrFormMap;
    }
}

