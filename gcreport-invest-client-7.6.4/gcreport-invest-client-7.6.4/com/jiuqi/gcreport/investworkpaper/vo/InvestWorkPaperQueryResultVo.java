/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.investworkpaper.vo;

import com.jiuqi.gcreport.investworkpaper.vo.Column;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperRowData;
import java.util.List;

public class InvestWorkPaperQueryResultVo {
    private List<Column> tableColumns;
    private List<InvestWorkPaperRowData> tableRowDatas;

    public List<Column> getTableColumns() {
        return this.tableColumns;
    }

    public void setTableColumns(List<Column> tableColumns) {
        this.tableColumns = tableColumns;
    }

    public List<InvestWorkPaperRowData> getTableRowDatas() {
        return this.tableRowDatas;
    }

    public void setTableRowDatas(List<InvestWorkPaperRowData> tableRowDatas) {
        this.tableRowDatas = tableRowDatas;
    }
}

