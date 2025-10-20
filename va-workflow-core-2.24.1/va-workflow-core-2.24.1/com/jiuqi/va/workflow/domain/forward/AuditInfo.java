/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.domain.forward;

import java.util.Date;

public class AuditInfo {
    private String auditUser;
    private String auditResult;
    private Date completeTime;
    private String comment;

    public String getAuditUser() {
        return this.auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public String getAuditResult() {
        return this.auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public Date getCompleteTime() {
        return this.completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

