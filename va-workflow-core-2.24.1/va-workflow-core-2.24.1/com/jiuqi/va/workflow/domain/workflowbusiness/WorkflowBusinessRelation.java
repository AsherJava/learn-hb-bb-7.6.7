/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.domain.workflowbusiness;

import com.jiuqi.va.mapper.domain.TenantDO;

public class WorkflowBusinessRelation
extends TenantDO {
    private String workflowDefineKey;
    private String workflowDefineTitle;
    private Long workflowDefineVersion;
    private String workflowDefineFullPath;
    private String workflowDefineFullPathTitle;
    private String bizDefine;
    private String bizDefineTitle;
    private String bizDefineFullPath;
    private String bizDefineFullPathTitle;
    private String bizType;

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getWorkflowDefineKey() {
        return this.workflowDefineKey;
    }

    public void setWorkflowDefineKey(String workflowDefineKey) {
        this.workflowDefineKey = workflowDefineKey;
    }

    public Long getWorkflowDefineVersion() {
        return this.workflowDefineVersion;
    }

    public void setWorkflowDefineVersion(Long workflowDefineVersion) {
        this.workflowDefineVersion = workflowDefineVersion;
    }

    public String getWorkflowDefineTitle() {
        return this.workflowDefineTitle;
    }

    public void setWorkflowDefineTitle(String workflowDefineTitle) {
        this.workflowDefineTitle = workflowDefineTitle;
    }

    public String getWorkflowDefineFullPath() {
        return this.workflowDefineFullPath;
    }

    public void setWorkflowDefineFullPath(String workflowDefineFullPath) {
        this.workflowDefineFullPath = workflowDefineFullPath;
    }

    public String getWorkflowDefineFullPathTitle() {
        return this.workflowDefineFullPathTitle;
    }

    public void setWorkflowDefineFullPathTitle(String workflowDefineFullPathTitle) {
        this.workflowDefineFullPathTitle = workflowDefineFullPathTitle;
    }

    public String getBizDefine() {
        return this.bizDefine;
    }

    public void setBizDefine(String bizDefine) {
        this.bizDefine = bizDefine;
    }

    public String getBizDefineTitle() {
        return this.bizDefineTitle;
    }

    public void setBizDefineTitle(String bizDefineTitle) {
        this.bizDefineTitle = bizDefineTitle;
    }

    public String getBizDefineFullPath() {
        return this.bizDefineFullPath;
    }

    public void setBizDefineFullPath(String bizDefineFullPath) {
        this.bizDefineFullPath = bizDefineFullPath;
    }

    public String getBizDefineFullPathTitle() {
        return this.bizDefineFullPathTitle;
    }

    public void setBizDefineFullPathTitle(String bizDefineFullPathTitle) {
        this.bizDefineFullPathTitle = bizDefineFullPathTitle;
    }
}

