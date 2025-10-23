/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.ReportInfo
 */
package com.jiuqi.nr.summary.executor.sum.parse;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.ReportInfo;
import java.util.HashMap;
import java.util.Map;

public class SummaryReportInfo {
    private ReportInfo reportInfo;
    private Map<Position, DataModelLinkColumn> cells = new HashMap<Position, DataModelLinkColumn>();

    public SummaryReportInfo(ReportInfo reportInfo) {
        this.reportInfo = reportInfo;
    }

    public Map<Position, DataModelLinkColumn> getCells() {
        return this.cells;
    }

    public ReportInfo getReportInfo() {
        return this.reportInfo;
    }
}

