/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine;

import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.model.QuickReport;

public interface IPropertyProvider {
    public Object getProperty(QuickReport var1, String var2) throws ReportEngineException;
}

