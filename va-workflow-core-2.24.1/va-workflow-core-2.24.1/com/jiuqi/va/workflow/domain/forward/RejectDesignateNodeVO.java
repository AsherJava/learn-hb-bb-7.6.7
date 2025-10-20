/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.workflow.domain.forward;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.workflow.domain.forward.AuditInfo;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class RejectDesignateNodeVO {
    private int canReject;
    private String stencilId;
    private String nodeName;
    private List<AuditInfo> auditInfo;
    private String auditStatus;
    private int auditState;
    private String subProcessBranch;
    private String pgwBranch;
    private List<List<RejectDesignateNodeVO>> children;

    public String getStencilId() {
        return this.stencilId;
    }

    public void setStencilId(String stencilId) {
        this.stencilId = stencilId;
    }

    public int getCanReject() {
        return this.canReject;
    }

    public void setCanReject(int canReject) {
        this.canReject = canReject;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<AuditInfo> getAuditInfo() {
        return this.auditInfo;
    }

    public void setAuditInfo(List<AuditInfo> auditInfo) {
        this.auditInfo = auditInfo;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public int getAuditState() {
        return this.auditState;
    }

    public void setAuditState(int auditState) {
        this.auditState = auditState;
    }

    public String getSubProcessBranch() {
        return this.subProcessBranch;
    }

    public void setSubProcessBranch(String subProcessBranch) {
        this.subProcessBranch = subProcessBranch;
    }

    public List<List<RejectDesignateNodeVO>> getChildren() {
        return this.children;
    }

    public void setChildren(List<List<RejectDesignateNodeVO>> children) {
        this.children = children;
    }

    public String getPgwBranch() {
        return this.pgwBranch;
    }

    public void setPgwBranch(String pgwBranch) {
        this.pgwBranch = pgwBranch;
    }
}

