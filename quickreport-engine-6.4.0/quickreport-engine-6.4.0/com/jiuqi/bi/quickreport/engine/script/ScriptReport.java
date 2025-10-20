/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.script;

import com.jiuqi.bi.quickreport.engine.script.IScriptObj;
import com.jiuqi.bi.quickreport.model.QuickReport;

public final class ScriptReport
implements IScriptObj {
    public static final String ID = "report";
    private QuickReport report;

    public ScriptReport(QuickReport report) {
        this.report = report;
    }

    public String getName() {
        return this.report.getName();
    }

    public String getTitle() {
        return this.report.getTitle();
    }

    public void setProperty(String propName, String propValue) {
        this.report.getProperties().setProperty(propName, propValue);
    }

    @Override
    public Object getObj() {
        return this.report;
    }
}

