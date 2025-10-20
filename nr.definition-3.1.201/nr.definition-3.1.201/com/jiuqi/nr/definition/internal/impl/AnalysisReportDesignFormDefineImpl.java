/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.nr.definition.facade.AnalysisReportDesignFormDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl;

public class AnalysisReportDesignFormDefineImpl
extends DesignFormDefineImpl
implements AnalysisReportDesignFormDefine {
    private static final long serialVersionUID = 1L;

    @Override
    public String getAnalysisReportKey() {
        return (String)super.getExtensionProp("reportKey");
    }

    @Override
    public void setAnalysisReportKey(String reportKey) {
        super.addExtensions("reportKey", reportKey);
    }
}

