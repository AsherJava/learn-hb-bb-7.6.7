/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.analysis.exe.query.grouping;

import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import com.jiuqi.nr.data.engine.analysis.exe.query.grouping.IAnalysisGroupingColumn;
import java.util.List;

public class AnalysisGroupingRow {
    private List<Object> groupFieldValues;
    private IAnalysisGroupingColumn[] columns;
    private String groupFieldValueStr;

    public AnalysisGroupingRow(IAnalysisGroupingColumn[] columns) {
        this.columns = columns;
    }

    public Object readValue(AnalysisContext context, int index) {
        return this.columns[index].readValue(context);
    }

    public void writeValue(AnalysisContext context, int index, Object value) {
        this.columns[index].writeValue(context, value);
    }

    public void setGroupFieldValues(List<Object> groupFieldValues) {
        this.groupFieldValues = groupFieldValues;
    }

    public List<Object> getGroupFieldValues() {
        return this.groupFieldValues;
    }

    public String getGroupFieldValueStr() {
        return this.groupFieldValueStr;
    }

    public void setGroupFieldValueStr(String groupFieldValueStr) {
        this.groupFieldValueStr = groupFieldValueStr;
    }
}

