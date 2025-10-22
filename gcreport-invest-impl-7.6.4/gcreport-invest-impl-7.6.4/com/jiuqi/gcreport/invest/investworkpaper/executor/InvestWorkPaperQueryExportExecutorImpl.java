/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelOneSheetExecutor
 *  com.jiuqi.gcreport.investworkpaper.vo.Column
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryResultVo
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperRowData
 */
package com.jiuqi.gcreport.invest.investworkpaper.executor;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelOneSheetExecutor;
import com.jiuqi.gcreport.invest.investworkpaper.enums.DataSourceEnum;
import com.jiuqi.gcreport.invest.investworkpaper.service.InvestWorkPaperQueryService;
import com.jiuqi.gcreport.investworkpaper.vo.Column;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryResultVo;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperRowData;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvestWorkPaperQueryExportExecutorImpl
extends AbstractExportExcelOneSheetExecutor {
    @Autowired
    private InvestWorkPaperQueryService investWorkPaperQueryService;

    public String getName() {
        return "InvestWorkPaperQueryExportExecutor";
    }

    protected ExportExcelSheet exportExcelSheet(ExportContext context, Workbook workbook) {
        InvestWorkPaperQueryCondition investWorkPaperQueryCondition = (InvestWorkPaperQueryCondition)JsonUtils.readValue((String)context.getParam(), InvestWorkPaperQueryCondition.class);
        InvestWorkPaperQueryResultVo investWorkPaperColumnsAndDatas = this.investWorkPaperQueryService.getInvestWorkPaperColumnsAndDatas(investWorkPaperQueryCondition);
        List tableColumns = investWorkPaperColumnsAndDatas.getTableColumns();
        List investWorkPaperRowDataList = investWorkPaperColumnsAndDatas.getTableRowDatas();
        List<InvestWorkPaperRowData> investWorkPaperHeaderRows = investWorkPaperRowDataList.subList(0, 4);
        List<InvestWorkPaperRowData> investWorkPaperDataRows = investWorkPaperRowDataList.subList(4, investWorkPaperRowDataList.size());
        ExportExcelSheet sheet = new ExportExcelSheet(Integer.valueOf(0), "\u6295\u8d44\u5de5\u4f5c\u5e95\u7a3f", Integer.valueOf(4));
        sheet.getRowDatas().addAll(this.getHeaderRow(tableColumns, investWorkPaperHeaderRows));
        sheet.getRowDatas().addAll(this.getDataRows(tableColumns, investWorkPaperDataRows, sheet));
        return sheet;
    }

    private List<Object[]> getDataRows(List<Column> tableColumns, List<InvestWorkPaperRowData> dataRows, ExportExcelSheet sheet) {
        ArrayList<Object[]> dataRowList = new ArrayList<Object[]>();
        this.appendRuleRow(dataRows);
        for (int i = 0; i < dataRows.size(); ++i) {
            InvestWorkPaperRowData rowData = dataRows.get(i);
            Object[] values = new Object[tableColumns.size()];
            values[0] = rowData.getDataSourceTitle();
            values[1] = rowData.getDataSourceTitle();
            if (rowData.getDataSource().equals(DataSourceEnum.OFFSETDATA.getCode())) {
                values[1] = rowData.getOrientTitle();
            } else {
                sheet.getCellRangeAddresses().add(new CellRangeAddress(i + 4, i + 4, 0, 1));
            }
            values[2] = rowData.getZbTitle();
            for (int j = 3; j < tableColumns.size(); ++j) {
                values[j] = rowData.getDynamicFields().get(tableColumns.get(j).getColumnKey());
            }
            dataRowList.add(values);
        }
        return dataRowList;
    }

    private void appendRuleRow(List<InvestWorkPaperRowData> dataRows) {
        int index = IntStream.range(0, dataRows.size()).filter(i -> DataSourceEnum.OFFSETDATA.getCode().equals(((InvestWorkPaperRowData)dataRows.get(i)).getDataSource())).findFirst().orElse(-1);
        InvestWorkPaperRowData investWorkPaperRowData = new InvestWorkPaperRowData();
        investWorkPaperRowData.setDataSourceTitle("\u5408\u5e76\u89c4\u5219");
        investWorkPaperRowData.setOrientTitle("\u65b9\u5411");
        investWorkPaperRowData.setZbTitle("\u79d1\u76ee");
        investWorkPaperRowData.setDataSource(DataSourceEnum.OFFSETDATA.getCode());
        if (index == -1) {
            dataRows.add(investWorkPaperRowData);
        } else {
            dataRows.add(index, investWorkPaperRowData);
        }
    }

    private List<Object[]> getHeaderRow(List<Column> tableColumns, List<InvestWorkPaperRowData> headerRows) {
        ArrayList<Object[]> headerRowList = new ArrayList<Object[]>();
        for (int i = 0; i < headerRows.size(); ++i) {
            Object[] values = new Object[tableColumns.size()];
            InvestWorkPaperRowData headerRow = headerRows.get(i);
            values[0] = headerRow.getZbTitle();
            values[1] = headerRow.getZbTitle();
            values[2] = headerRow.getZbTitle();
            for (int j = 3; j < tableColumns.size() - 1; ++j) {
                values[j] = headerRow.getDynamicFields().get(tableColumns.get(j).getColumnKey());
            }
            values[tableColumns.size() - 1] = "\u5f53\u524d\u5c42\u7ea7\u5f52\u5c5e\u6295\u8d44\u53f0\u8d26\u4ee5\u5916\u7684\u62b5\u9500\u5206\u5f55";
            headerRowList.add(values);
        }
        return headerRowList;
    }
}

