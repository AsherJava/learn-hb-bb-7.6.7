/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.datacheckcommon.param;

import com.jiuqi.nr.context.infc.INRContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class QueryDimParam
implements INRContext,
Serializable {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String formSchemeKey;
    private List<String> orgCode;
    private String period;
    private Map<String, String> dims;
    private String contextEntityId;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(List<String> orgCode) {
        this.orgCode = orgCode;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Map<String, String> getDims() {
        return this.dims;
    }

    public void setDims(Map<String, String> dims) {
        this.dims = dims;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return null;
    }
}

