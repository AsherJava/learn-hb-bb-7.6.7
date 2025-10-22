/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.analysis.exe.query.grouping;

import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;

public interface IAnalysisGroupingColumn {
    public Object readValue(AnalysisContext var1);

    public void writeValue(AnalysisContext var1, Object var2);
}

