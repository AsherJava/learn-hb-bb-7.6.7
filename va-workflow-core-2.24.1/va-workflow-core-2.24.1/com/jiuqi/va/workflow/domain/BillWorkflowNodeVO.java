/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.domain;

import com.jiuqi.va.mapper.domain.TenantDO;

public class BillWorkflowNodeVO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String groupName;
    private String groupTitle;
    private String bizName;
    private String bizTitle;
    private String workflowName;
    private String workflowTitle;
    private String nodeName;
    private String nodeId;
    private String type;

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupTitle() {
        return this.groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getBizName() {
        return this.bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getBizTitle() {
        return this.bizTitle;
    }

    public void setBizTitle(String bizTitle) {
        this.bizTitle = bizTitle;
    }

    public String getWorkflowName() {
        return this.workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getWorkflowTitle() {
        return this.workflowTitle;
    }

    public void setWorkflowTitle(String workflowTitle) {
        this.workflowTitle = workflowTitle;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return "BillWorkflowNodeVO{groupName='" + this.groupName + '\'' + ", groupTitle='" + this.groupTitle + '\'' + ", bizName='" + this.bizName + '\'' + ", bizTitle='" + this.bizTitle + '\'' + ", workflowName='" + this.workflowName + '\'' + ", workflowTitle='" + this.workflowTitle + '\'' + ", nodeName='" + this.nodeName + '\'' + ", nodeId='" + this.nodeId + '\'' + ", type='" + this.type + '\'' + '}';
    }
}

