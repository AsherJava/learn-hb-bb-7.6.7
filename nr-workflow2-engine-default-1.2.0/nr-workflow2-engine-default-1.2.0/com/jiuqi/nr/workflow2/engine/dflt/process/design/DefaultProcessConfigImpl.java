/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.SubmitNodeConfig;

public class DefaultProcessConfigImpl
implements DefaultProcessConfig {
    private WorkflowDefineTemplate workflowDefineTemplate;
    private SubmitNodeConfig submitNodeConfig;
    private ReportNodeConfig reportNodeConfig;
    private AuditNodeConfig auditNodeConfig;

    @Override
    public WorkflowDefineTemplate getWorkflowDefineTemplate() {
        return this.workflowDefineTemplate;
    }

    public void setWorkflowDefineTemplate(WorkflowDefineTemplate workflowDefineTemplate) {
        this.workflowDefineTemplate = workflowDefineTemplate;
    }

    @Override
    public SubmitNodeConfig getSubmitNodeConfig() {
        return this.submitNodeConfig;
    }

    public void setSubmitNodeConfig(SubmitNodeConfig submitNodeConfig) {
        this.submitNodeConfig = submitNodeConfig;
    }

    @Override
    public ReportNodeConfig getReportNodeConfig() {
        return this.reportNodeConfig;
    }

    public void setReportNodeConfig(ReportNodeConfig reportNodeConfig) {
        this.reportNodeConfig = reportNodeConfig;
    }

    @Override
    public AuditNodeConfig getAuditNodeConfig() {
        return this.auditNodeConfig;
    }

    public void setAuditNodeConfig(AuditNodeConfig auditNodeConfig) {
        this.auditNodeConfig = auditNodeConfig;
    }
}

