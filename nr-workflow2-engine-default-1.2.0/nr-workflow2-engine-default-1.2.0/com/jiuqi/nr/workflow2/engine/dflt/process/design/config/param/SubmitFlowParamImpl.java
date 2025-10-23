/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.param;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.SubmitNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.param.SubmitFlowParam;

public class SubmitFlowParamImpl
implements SubmitFlowParam {
    private SubmitNodeConfig submitNodeConfig;
    private ReportNodeConfig reportNodeConfig;
    private AuditNodeConfig auditNodeConfig;

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

