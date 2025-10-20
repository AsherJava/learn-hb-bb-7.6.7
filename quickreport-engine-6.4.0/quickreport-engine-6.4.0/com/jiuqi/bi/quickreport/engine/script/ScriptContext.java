/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.script;

import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.script.IScriptObj;

public class ScriptContext
implements IScriptObj {
    public static final String ID = "context";
    private ReportContext context;

    public ScriptContext(ReportContext context) {
        this.context = context;
    }

    public void resetDataSource(String dsName, String dataSrcName) {
        this.context.resetDataSource(dsName, dataSrcName);
    }

    @Override
    public Object getObj() {
        return null;
    }
}

