/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.domain;

import com.jiuqi.va.paramsync.domain.VaParamSyncModuleEnum;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VaParamSyncResultDO
implements Serializable {
    private static final long serialVersionUID = 2405172041960251807L;
    private Set<VaParamSyncModuleEnum> resultRange;
    private Map<VaParamSyncModuleEnum, Map<String, List<Map<String, Object>>>> resultSet;

    public Set<VaParamSyncModuleEnum> getResultRange() {
        return this.resultRange;
    }

    public void setResultRange(Set<VaParamSyncModuleEnum> resultRange) {
        this.resultRange = resultRange;
    }

    public Map<VaParamSyncModuleEnum, Map<String, List<Map<String, Object>>>> getResultSet() {
        return this.resultSet;
    }

    public void setResultSet(Map<VaParamSyncModuleEnum, Map<String, List<Map<String, Object>>>> resultSet) {
        this.resultSet = resultSet;
    }

    public boolean addResultRange(Set<VaParamSyncModuleEnum> resultRange) {
        if (resultRange == null || resultRange.isEmpty()) {
            return false;
        }
        if (this.resultRange == null || this.resultRange.isEmpty()) {
            this.resultRange = new HashSet<VaParamSyncModuleEnum>();
        }
        return this.resultRange.addAll(resultRange);
    }
}

