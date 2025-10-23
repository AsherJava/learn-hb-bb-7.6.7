/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.param;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.param.StandardFlowParam;

public class StandardFlowParamImpl
implements StandardFlowParam {
    private ReportNodeConfig reportNodeConfig;
    private AuditNodeConfig auditNodeConfig;

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

