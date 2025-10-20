/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.biz.domain.workflow.detection;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.Map;

public class WorkflowDetectionFormula
extends TenantDO {
    String bizType;
    String bizDefine;
    String workflowDefineKey;
    String unitCode;
    List<Map<String, Object>> workflowVariables;

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizDefine() {
        return this.bizDefine;
    }

    public void setBizDefine(String bizDefine) {
        this.bizDefine = bizDefine;
    }

    public String getWorkflowDefineKey() {
        return this.workflowDefineKey;
    }

    public void setWorkflowDefineKey(String workflowDefineKey) {
        this.workflowDefineKey = workflowDefineKey;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public List<Map<String, Object>> getWorkflowVariables() {
        return this.workflowVariables;
    }

    public void setWorkflowVariables(List<Map<String, Object>> workflowVariables) {
        this.workflowVariables = workflowVariables;
    }
}

