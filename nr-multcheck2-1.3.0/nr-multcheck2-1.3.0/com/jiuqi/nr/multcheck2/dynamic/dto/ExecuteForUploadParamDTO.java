/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.multcheck2.dynamic.dto;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.multcheck2.common.CheckSource;
import java.util.List;
import java.util.Map;

public class ExecuteForUploadParamDTO {
    private String task;
    private String period;
    private String org;
    private List<String> orgCodes;
    private Map<String, DimensionValue> dimSetMap;
    private CheckSource source = CheckSource.FLOW;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public Map<String, DimensionValue> getDimSetMap() {
        return this.dimSetMap;
    }

    public void setDimSetMap(Map<String, DimensionValue> dimSetMap) {
        this.dimSetMap = dimSetMap;
    }

    public CheckSource getSource() {
        return this.source;
    }

    public void setSource(CheckSource source) {
        this.source = source;
    }
}

