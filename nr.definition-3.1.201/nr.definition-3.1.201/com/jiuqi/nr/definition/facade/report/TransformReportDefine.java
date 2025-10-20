/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.report;

import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.facade.report.TransportReportTemplateDefineImpl;
import java.util.List;

public class TransformReportDefine {
    private List<TransportReportTemplateDefineImpl> designReportTemplateDefines;
    private List<DesignReportTagDefine> designReportTagDefines;

    public List<TransportReportTemplateDefineImpl> getDesignReportTemplateDefines() {
        return this.designReportTemplateDefines;
    }

    public void setDesignReportTemplateDefines(List<TransportReportTemplateDefineImpl> designReportTemplateDefines) {
        this.designReportTemplateDefines = designReportTemplateDefines;
    }

    public List<DesignReportTagDefine> getDesignReportTagDefines() {
        return this.designReportTagDefines;
    }

    public void setDesignReportTagDefines(List<DesignReportTagDefine> designReportTagDefines) {
        this.designReportTagDefines = designReportTagDefines;
    }
}

