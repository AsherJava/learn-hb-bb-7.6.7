/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.reportTag.InjectContext
 */
package com.jiuqi.nr.datareport.obj;

import com.jiuqi.nr.datareport.obj.ReportEnv;
import com.jiuqi.nr.datareport.obj.ReportObj;
import com.jiuqi.nr.definition.reportTag.InjectContext;

public class ReportDataParam {
    private ReportObj reportObj;
    private ReportEnv reportEnv;
    private InjectContext injectContext;

    public InjectContext getInjectContext() {
        return this.injectContext;
    }

    public void setInjectContext(InjectContext injectContext) {
        this.injectContext = injectContext;
    }

    public ReportObj getReportObj() {
        return this.reportObj;
    }

    public void setReportObj(ReportObj reportObj) {
        this.reportObj = reportObj;
    }

    public ReportEnv getReportEnv() {
        return this.reportEnv;
    }

    public void setReportEnv(ReportEnv reportEnv) {
        this.reportEnv = reportEnv;
    }
}

