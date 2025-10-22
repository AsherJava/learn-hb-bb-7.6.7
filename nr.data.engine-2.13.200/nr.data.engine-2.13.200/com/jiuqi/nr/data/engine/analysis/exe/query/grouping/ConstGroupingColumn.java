/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.analysis.exe.query.grouping;

import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import com.jiuqi.nr.data.engine.analysis.exe.query.grouping.IAnalysisGroupingColumn;

public class ConstGroupingColumn
implements IAnalysisGroupingColumn {
    private final Object value;

    @Override
    public Object readValue(AnalysisContext context) {
        return this.value;
    }

    public ConstGroupingColumn(Object value) {
        this.value = value;
    }

    @Override
    public void writeValue(AnalysisContext context, Object value) {
    }
}

