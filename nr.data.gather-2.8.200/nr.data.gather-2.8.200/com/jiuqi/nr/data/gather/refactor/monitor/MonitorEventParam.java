/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.engine.gather.GatherEntityMap
 */
package com.jiuqi.nr.data.gather.refactor.monitor;

import com.jiuqi.nr.data.engine.gather.GatherEntityMap;
import com.jiuqi.nr.data.gather.bean.GatherParam;
import java.util.HashSet;
import java.util.Set;

public class MonitorEventParam {
    private String targetKey;
    private GatherParam dataGatherParam;
    private GatherEntityMap gatherEntityMap;
    private Set<String> formKeys = new HashSet<String>();

    public MonitorEventParam() {
    }

    public MonitorEventParam(String targetKey, GatherParam dataGatherParam) {
        this.targetKey = targetKey;
        this.dataGatherParam = dataGatherParam;
    }

    public String getTargetKey() {
        return this.targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public GatherParam getDataGatherParam() {
        return this.dataGatherParam;
    }

    public void setDataGatherParam(GatherParam dataGatherParam) {
        this.dataGatherParam = dataGatherParam;
    }

    public GatherEntityMap getGatherEntityMap() {
        return this.gatherEntityMap;
    }

    public void setGatherEntityMap(GatherEntityMap gatherEntityMap) {
        this.gatherEntityMap = gatherEntityMap;
    }

    public Set<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(Set<String> formKeys) {
        this.formKeys = formKeys;
    }
}

