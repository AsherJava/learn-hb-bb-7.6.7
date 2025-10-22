/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 */
package com.jiuqi.nr.data.logic.internal.cache;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.nr.data.logic.internal.cache.MemoryDataNodeFinder;

public class MemoryDataContext
implements IContext {
    private DataRow row;
    private Metadata<ColumnInfo> metadata;
    private String defaultTableName;
    private String dwTableName;
    private ReportFormulaParser parser;

    public MemoryDataContext(Metadata<ColumnInfo> metadata, String defaultTableName, String dwTableName) {
        this.metadata = metadata;
        this.defaultTableName = defaultTableName;
        this.dwTableName = dwTableName;
    }

    public Object getValue(String columnName) {
        int index;
        Object value = null;
        if (this.row != null && this.metadata != null && (index = this.metadata.indexOf(columnName)) >= 0) {
            value = this.row.getValue(index);
        }
        return value;
    }

    public int getIndex(String columnName) {
        return this.metadata.indexOf(columnName);
    }

    public ReportFormulaParser getParser() {
        if (this.parser == null) {
            this.parser = ReportFormulaParser.getInstance();
            this.parser.setJQReportMode(true);
            this.parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new MemoryDataNodeFinder());
        }
        return this.parser;
    }

    public DataRow getRow() {
        return this.row;
    }

    public void setRow(DataRow row) {
        this.row = row;
    }

    public Metadata<ColumnInfo> getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Metadata<ColumnInfo> metadata) {
        this.metadata = metadata;
    }

    public String getDefaultTableName() {
        return this.defaultTableName;
    }

    public void setDefaultTableName(String defaultTableName) {
        this.defaultTableName = defaultTableName;
    }

    public String getDwTableName() {
        return this.dwTableName;
    }

    public void setDwTableName(String dwTableName) {
        this.dwTableName = dwTableName;
    }
}

