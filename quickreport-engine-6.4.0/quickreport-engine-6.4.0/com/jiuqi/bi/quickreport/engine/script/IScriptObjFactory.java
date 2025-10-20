/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.script;

import com.jiuqi.bi.quickreport.engine.script.IScriptObj;
import com.jiuqi.bi.quickreport.model.QuickReport;

public interface IScriptObjFactory {
    public String getId();

    public IScriptObj newScriptObj(QuickReport var1);
}

