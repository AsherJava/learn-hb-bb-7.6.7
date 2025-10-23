/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.param;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.SubmitNodeConfig;

public interface SubmitFlowParam {
    public SubmitNodeConfig getSubmitNodeConfig();

    public ReportNodeConfig getReportNodeConfig();

    public AuditNodeConfig getAuditNodeConfig();
}

