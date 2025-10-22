/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.ReportInfo;

public class FormulaDataLinkPosition {
    private int beginIndex;
    private int endIndex;
    private DataModelLinkColumn dataModelLinkColumn;

    public FormulaDataLinkPosition(int beginIndex, int endIndex, DataModelLinkColumn dataModelLinkColumn) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.dataModelLinkColumn = dataModelLinkColumn;
    }

    public int getBeginIndex() {
        return this.beginIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    public String getDataLinkCode() {
        return this.dataModelLinkColumn.getDataLinkCode();
    }

    public String getReportName() {
        ReportInfo reportInfo = this.getReportInfo();
        if (reportInfo != null) {
            return reportInfo.getReportName();
        }
        return null;
    }

    public ReportInfo getReportInfo() {
        return this.dataModelLinkColumn.getReportInfo();
    }

    public String getReportKey() {
        ReportInfo reportInfo = this.getReportInfo();
        if (reportInfo != null) {
            return this.getReportInfo().getReportKey();
        }
        return null;
    }

    @Deprecated
    public DataLinkColumn getDataLinkColumn() {
        return new DataLinkColumn(this.dataModelLinkColumn);
    }

    public DataModelLinkColumn getDataModelLinkColumn() {
        return this.dataModelLinkColumn;
    }
}

