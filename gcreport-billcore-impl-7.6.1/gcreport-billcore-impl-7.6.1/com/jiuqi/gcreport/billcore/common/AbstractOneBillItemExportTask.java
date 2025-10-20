/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.util.CellRangeAddress
 */
package com.jiuqi.gcreport.billcore.common;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.billcore.common.AbstractBillParseExportTask;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;

public abstract class AbstractOneBillItemExportTask
extends AbstractBillParseExportTask {
    @Override
    ExportExcelSheet doExecuteExport(ExportContext context) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        List<String> columnCodes = this.getFirstBillItemSelectedColumnCodes();
        ExportExcelSheet sheet = this.exportHead(columnCodes, context);
        if (context.isTemplateExportFlag()) {
            return sheet;
        }
        List<Map<String, Object>> masters = this.getMasterRecords(params);
        this.exportContent(params, columnCodes, sheet, masters);
        return sheet;
    }

    ExportExcelSheet exportHead(List<String> columnCodes, ExportContext context) {
        String[] materKeyColumnTitles = this.getMaterKeyColumnTitles();
        int startCol = materKeyColumnTitles.length;
        ExportExcelSheet sheet = new ExportExcelSheet(Integer.valueOf(1), this.getSheetName());
        Map<String, ColumnModelDefine> columnCode2FieldDefineMap = NrTool.queryAllColumnsInTable((String)this.getSubTableName()).stream().collect(Collectors.toMap(IModelDefineItem::getCode, item -> item, (k1, k2) -> k1));
        String[] titles = new String[columnCodes.size() + startCol];
        for (int i = 0; i < startCol; ++i) {
            titles[i] = materKeyColumnTitles[i];
        }
        CellStyle contentAmt = (CellStyle)context.getVarMap().get("contentAmt");
        CellStyle contentInt = (CellStyle)context.getVarMap().get("contentInt");
        CellStyle headAmt = (CellStyle)context.getVarMap().get("headAmt");
        for (int i = 0; i < columnCodes.size(); ++i) {
            ColumnModelDefine define = columnCode2FieldDefineMap.get(columnCodes.get(i));
            titles[i + startCol] = define.getTitle();
            if (define.getColumnType() == ColumnModelType.DOUBLE || define.getColumnType() == ColumnModelType.BIGDECIMAL) {
                sheet.getContentCellStyleCache().put(i + startCol, contentAmt);
                sheet.getHeadCellStyleCache().put(i + startCol, headAmt);
                sheet.getContentCellTypeCache().put(i + startCol, CellType.NUMERIC);
                continue;
            }
            if (define.getColumnType() != ColumnModelType.INTEGER) continue;
            sheet.getContentCellStyleCache().put(i + startCol, contentInt);
            sheet.getHeadCellStyleCache().put(i + startCol, headAmt);
            sheet.getContentCellTypeCache().put(i + startCol, CellType.NUMERIC);
        }
        sheet.getRowDatas().add(titles);
        return sheet;
    }

    void exportContent(Map<String, Object> params, List<String> columnCodes, ExportExcelSheet sheet, List<Map<String, Object>> masters) {
        String[] materKeyColumnCodes = this.getMaterKeyColumnCodes();
        int startCol = materKeyColumnCodes.length;
        int rowStart = 1;
        for (Map<String, Object> master : masters) {
            String billCode = (String)master.get("BILLCODE");
            List<Map<String, Object>> subItems = InvestBillTool.getBillItemByBillCode(billCode, this.getSubTableName());
            InvestBillTool.formatBillContent(subItems, params, this.getSubTableName(), false);
            for (Map<String, Object> subItem : subItems) {
                int i;
                Object[] values = new Object[columnCodes.size() + startCol];
                for (i = 0; i < startCol; ++i) {
                    values[i] = master.get(materKeyColumnCodes[i]);
                }
                for (i = 0; i < columnCodes.size(); ++i) {
                    values[i + startCol] = subItem.get(columnCodes.get(i));
                }
                sheet.getRowDatas().add(values);
            }
            if (subItems.size() > 1) {
                for (int i = 0; i < startCol; ++i) {
                    sheet.getCellRangeAddresses().add(new CellRangeAddress(rowStart, rowStart + subItems.size() - 1, i, i));
                }
            }
            rowStart += subItems.size();
        }
    }

    protected String[] getMaterKeyColumnTitles() {
        String[] materKeyColumnCodes = this.getMaterKeyColumnCodes();
        String[] result = new String[materKeyColumnCodes.length];
        Map<String, String> columnCode2FieldDefineMap = NrTool.queryAllColumnsInTable((String)this.getMasterTableName()).stream().collect(Collectors.toMap(IModelDefineItem::getCode, ColumnModelDefine::getTitle, (k1, k2) -> k1));
        for (int i = 0; i < materKeyColumnCodes.length; ++i) {
            result[i] = columnCode2FieldDefineMap.get(materKeyColumnCodes[i]);
        }
        return result;
    }

    protected String[] getMaterKeyColumnCodes() {
        return new String[0];
    }

    protected abstract List<Map<String, Object>> getMasterRecords(Map<String, Object> var1);
}

