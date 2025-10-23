/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository;

import java.util.Calendar;

public class ProcessOperationDO {
    private String id;
    private String instanceId;
    private String fromNode;
    private String action;
    private String toNode;
    private String newStatus;
    private Calendar operateTime;
    private String operate_user;
    private String operate_identity;
    private String comment;
    private String operate_type;
    private boolean forceReport;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getFromNode() {
        return this.fromNode;
    }

    public void setFromNode(String fromNode) {
        this.fromNode = fromNode;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getToNode() {
        return this.toNode;
    }

    public void setToNode(String toNode) {
        this.toNode = toNode;
    }

    public String getNewStatus() {
        return this.newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public Calendar getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(Calendar operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperate_user() {
        return this.operate_user;
    }

    public void setOperate_user(String operate_user) {
        this.operate_user = operate_user;
    }

    public String getOperate_identity() {
        return this.operate_identity;
    }

    public void setOperate_identity(String operate_identity) {
        this.operate_identity = operate_identity;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOperate_type() {
        return this.operate_type;
    }

    public void setOperate_type(String operate_type) {
        this.operate_type = operate_type;
    }

    public boolean isForceReport() {
        return this.forceReport;
    }

    public void setForceReport(boolean forceReport) {
        this.forceReport = forceReport;
    }
}

