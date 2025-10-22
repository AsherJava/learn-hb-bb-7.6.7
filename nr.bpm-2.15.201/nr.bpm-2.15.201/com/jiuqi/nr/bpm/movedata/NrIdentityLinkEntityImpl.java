/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata;

import java.math.BigDecimal;
import java.util.Objects;

public class NrIdentityLinkEntityImpl {
    private String id;
    private BigDecimal rev;
    private String groupId;
    private String type;
    private String userId;
    private String taskId;
    private String procInstId;
    private String procDefId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getRev() {
        return this.rev;
    }

    public void setRev(BigDecimal rev) {
        this.rev = rev;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcInstId() {
        return this.procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getProcDefId() {
        return this.procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String toString() {
        return "NrIdentityLinkEntityImpl{id='" + this.id + '\'' + ", rev=" + this.rev + ", groupId='" + this.groupId + '\'' + ", type='" + this.type + '\'' + ", userId='" + this.userId + '\'' + ", taskId='" + this.taskId + '\'' + ", procInstId='" + this.procInstId + '\'' + ", procDefId='" + this.procDefId + '\'' + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        NrIdentityLinkEntityImpl that = (NrIdentityLinkEntityImpl)o;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}

