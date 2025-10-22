/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.datacheck.attachment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.datacheck.attachment.AttachmentAuditScope;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AttachmentConfig {
    private List<AttachmentAuditScope> auditScope;

    public List<AttachmentAuditScope> getAuditScope() {
        return this.auditScope;
    }

    public void setAuditScope(List<AttachmentAuditScope> auditScope) {
        this.auditScope = auditScope;
    }
}

