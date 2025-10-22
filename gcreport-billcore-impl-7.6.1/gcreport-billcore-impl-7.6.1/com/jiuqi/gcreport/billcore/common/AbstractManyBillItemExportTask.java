/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 */
package com.jiuqi.gcreport.billcore.common;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.billcore.common.AbstractOneBillItemExportTask;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.CellStyle;

public abstract class AbstractManyBillItemExportTask
extends AbstractOneBillItemExportTask {
    @Override
    final ExportExcelSheet exportHead(List<String> columnCodes, ExportContext context) {
        List<String> subTableNames = this.getSubTableNames();
        if (null == subTableNames) {
            return new ExportExcelSheet(Integer.valueOf(this.getSheetNo()), this.getSheetName());
        }
        if (subTableNames.size() == 1) {
            return super.exportHead(columnCodes, context);
        }
        return this.exportCombinedHead(context);
    }

    @Override
    final void exportContent(Map<String, Object> params, List<String> columnCodes, ExportExcelSheet sheet, List<Map<String, Object>> masters) {
        List<String> subTableNames = this.getSubTableNames();
        if (null == subTableNames) {
            return;
        }
        if (subTableNames.size() == 1) {
            super.exportContent(params, columnCodes, sheet, masters);
        }
        this.exportCombinedContent(params, sheet, masters);
    }

    private ExportExcelSheet exportCombinedHead(ExportContext context) {
        int colIndex;
        List<LinkedHashSet<String>> allBillItemSelectedColumnCodes = this.getAllBillItemSelectedColumnCodes();
        String[] materKeyColumnTitles = this.getMaterKeyColumnTitles();
        ExportExcelSheet sheet = new ExportExcelSheet(Integer.valueOf(this.getSheetNo()), this.getSheetName(), Integer.valueOf(2));
        List<String> subTableNames = this.getSubTableNames();
        int startCol = materKeyColumnTitles.length;
        String[] rowOneTitles = new String[this.getBillItemColumnCodeNum() + startCol];
        String[] rowTwoTitles = new String[rowOneTitles.length];
        for (colIndex = 0; colIndex < startCol; ++colIndex) {
            rowOneTitles[colIndex] = materKeyColumnTitles[colIndex];
            rowTwoTitles[colIndex] = materKeyColumnTitles[colIndex];
        }
        for (int i = 0; i < subTableNames.size(); ++i) {
            String subTableName = subTableNames.get(i);
            Map<String, ColumnModelDefine> columnCode2FieldDefineMap = NrTool.queryAllColumnsInTable((String)subTableName).stream().collect(Collectors.toMap(IModelDefineItem::getCode, item -> item, (k1, k2) -> k1));
            CellStyle contentAmt = (CellStyle)context.getVarMap().get("contentAmt");
            CellStyle headAmt = (CellStyle)context.getVarMap().get("headAmt");
            LinkedHashSet<String> columnCodes = allBillItemSelectedColumnCodes.get(i);
            for (String columnCode : columnCodes) {
                ColumnModelDefine define = columnCode2FieldDefineMap.get(columnCode);
                if (define.getColumnType() == ColumnModelType.DOUBLE || define.getColumnType() == ColumnModelType.INTEGER || define.getColumnType() == ColumnModelType.BIGDECIMAL) {
                    sheet.getContentCellStyleCache().put(colIndex, contentAmt);
                    sheet.getHeadCellStyleCache().put(colIndex, headAmt);
                }
                rowOneTitles[colIndex] = this.getSubPanelTitles().get(i);
                rowTwoTitles[colIndex++] = define.getTitle();
            }
        }
        sheet.getRowDatas().add(rowOneTitles);
        sheet.getRowDatas().add(rowTwoTitles);
        return sheet;
    }

    protected void exportCombinedContent(Map<String, Object> params, ExportExcelSheet sheet, List<Map<String, Object>> masters) {
        String[] materKeyColumnCodes = this.getMaterKeyColumnCodes();
        int startCol = materKeyColumnCodes.length;
        List<LinkedHashSet<String>> allBillItemSelectedColumnCodes = this.getAllBillItemSelectedColumnCodes();
    }

    private int getBillItemColumnCodeNum() {
        List<LinkedHashSet<String>> allBillItemSelectedColumnCodes = this.getAllBillItemSelectedColumnCodes();
        int size = 0;
        for (LinkedHashSet<String> allBillItemSelectedColumnCode : allBillItemSelectedColumnCodes) {
            size += allBillItemSelectedColumnCode.size();
        }
        return size;
    }
}

