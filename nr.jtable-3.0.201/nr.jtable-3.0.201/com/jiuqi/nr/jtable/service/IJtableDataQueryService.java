/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.CardInputInit;
import com.jiuqi.nr.jtable.params.input.CellValueQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.ReportDataQueryInfo;
import com.jiuqi.nr.jtable.params.input.SingleCellValueQueryInfo;
import com.jiuqi.nr.jtable.params.output.CardInputInfo;
import com.jiuqi.nr.jtable.params.output.CellDataSet;
import com.jiuqi.nr.jtable.params.output.CellValueInfo;
import com.jiuqi.nr.jtable.params.output.MultiPeriodDataSet;
import com.jiuqi.nr.jtable.params.output.RegionDataCount;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.params.output.RegionSingleDataSet;
import com.jiuqi.nr.jtable.params.output.ReportDataSet;

public interface IJtableDataQueryService {
    public ReportDataSet queryReportFormDatas(ReportDataQueryInfo var1);

    public RegionDataSet queryRegionDatas(RegionQueryInfo var1);

    public ReportDataSet queryRegionsDatas(ReportDataQueryInfo var1);

    public RegionDataCount queryRegionDatasCount(RegionQueryInfo var1);

    public PagerInfo queryFloatRowIndex(RegionQueryInfo var1);

    public RegionSingleDataSet querySingleFloatRowData(RegionQueryInfo var1);

    public CellDataSet queryCellDataSet(CellValueQueryInfo var1);

    public CardInputInfo cardInputInit(CardInputInit var1);

    public MultiPeriodDataSet queryMultiPeriodData(JtableContext var1);

    public CellValueInfo querySingleCellValue(SingleCellValueQueryInfo var1);
}

