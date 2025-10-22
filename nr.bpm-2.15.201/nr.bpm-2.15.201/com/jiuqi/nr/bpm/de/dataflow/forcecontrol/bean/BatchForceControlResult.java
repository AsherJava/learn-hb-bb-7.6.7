/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean;

import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean.ForceControlInfo;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchNoOperate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BatchForceControlResult
extends ForceControlInfo {
    private Set<String> canRejectUnitKeys;
    private Map<String, LinkedHashSet<String>> canRejectUnitToForms;
    private Map<String, LinkedHashSet<String>> canRejectUnitToGroups;
    private Map<BatchNoOperate, List<BatchNoOperate>> noOperateUnitMap;
    private Map<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>> noOperateGroupOrFormMap;

    public Set<String> getCanRejectUnitKeys() {
        return this.canRejectUnitKeys;
    }

    public void setCanRejectUnitKeys(Set<String> canRejectUnitKeys) {
        this.canRejectUnitKeys = canRejectUnitKeys;
    }

    public Map<String, LinkedHashSet<String>> getCanRejectUnitToForms() {
        return this.canRejectUnitToForms;
    }

    public void setCanRejectUnitToForms(Map<String, LinkedHashSet<String>> canRejectUnitToForms) {
        this.canRejectUnitToForms = canRejectUnitToForms;
    }

    public Map<String, LinkedHashSet<String>> getCanRejectUnitToGroups() {
        return this.canRejectUnitToGroups;
    }

    public void setCanRejectUnitToGroups(Map<String, LinkedHashSet<String>> canRejectUnitToGroups) {
        this.canRejectUnitToGroups = canRejectUnitToGroups;
    }

    public Map<BatchNoOperate, List<BatchNoOperate>> getNoOperateUnitMap() {
        return this.noOperateUnitMap;
    }

    public void setNoOperateUnitMap(Map<BatchNoOperate, List<BatchNoOperate>> noOperateUnitMap) {
        this.noOperateUnitMap = noOperateUnitMap;
    }

    public Map<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>> getNoOperateGroupOrFormMap() {
        return this.noOperateGroupOrFormMap;
    }

    public void setNoOperateGroupOrFormMap(Map<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>> noOperateGroupOrFormMap) {
        this.noOperateGroupOrFormMap = noOperateGroupOrFormMap;
    }
}

