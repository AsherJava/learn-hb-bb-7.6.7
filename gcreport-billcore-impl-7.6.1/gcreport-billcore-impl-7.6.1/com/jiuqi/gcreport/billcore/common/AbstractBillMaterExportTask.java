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
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.billcore.common;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.billcore.common.AbstractBillParseExportTask;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBillMaterExportTask
extends AbstractBillParseExportTask {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected ExportExcelSheet doExecuteExport(ExportContext context) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        List<String> columnCodes = (List<String>)params.get("columnCodes");
        if (context.isTemplateExportFlag()) {
            columnCodes = this.getBillMasterSelectedColumnCodes();
        }
        if (!columnCodes.contains("UNITCODE")) {
            columnCodes.add(0, "UNITCODE");
        }
        context.getProgressData().setProgressValueAndRefresh(0.1);
        ExportExcelSheet sheet = this.exportHead(columnCodes, context);
        context.getProgressData().setProgressValueAndRefresh(0.4);
        if (context.isTemplateExportFlag()) {
            return sheet;
        }
        List<Map<String, Object>> records = this.getRecords(params);
        for (Map<String, Object> record : records) {
            Object[] values = new Object[columnCodes.size()];
            for (int i = 0; i < columnCodes.size(); ++i) {
                values[i] = record.get(columnCodes.get(i));
            }
            sheet.getRowDatas().add(values);
        }
        return sheet;
    }

    private ExportExcelSheet exportHead(List<String> columnCodes, ExportContext context) {
        ExportExcelSheet sheet = new ExportExcelSheet(Integer.valueOf(0), this.getSheetName());
        String[] titles = new String[columnCodes.size()];
        Map<String, ColumnModelDefine> columnCode2FieldDefineMap = NrTool.queryAllColumnsInTable((String)this.getMasterTableName()).stream().collect(Collectors.toMap(IModelDefineItem::getCode, item -> item, (k1, k2) -> k1));
        CellStyle contentAmt = (CellStyle)context.getVarMap().get("contentAmt");
        CellStyle contentInt = (CellStyle)context.getVarMap().get("contentInt");
        CellStyle headAmt = (CellStyle)context.getVarMap().get("headAmt");
        Workbook workbook = (Workbook)context.getVarMap().get("workbook");
        for (int i = 0; i < columnCodes.size(); ++i) {
            ColumnModelDefine define = columnCode2FieldDefineMap.get(columnCodes.get(i));
            if (null == define) {
                this.logger.error(String.format("%1s\u9875\u7b7e\u6267\u884c\u5bfc\u51fa\u65f6,\u627e\u4e0d\u5230\u5b57\u6bb5%s", sheet.getSheetName(), columnCodes.get(i)));
                continue;
            }
            titles[i] = define.getTitle();
            if (define.getColumnType() == ColumnModelType.DOUBLE || define.getColumnType() == ColumnModelType.BIGDECIMAL) {
                CellStyle newContentStyle = this.getNewContentStyle(contentAmt, workbook, define);
                sheet.getContentCellStyleCache().put(i, newContentStyle);
                sheet.getHeadCellStyleCache().put(i, headAmt);
                sheet.getContentCellTypeCache().put(i, CellType.NUMERIC);
                continue;
            }
            if (define.getColumnType() != ColumnModelType.INTEGER || "SRCTYPE".equals(define.getName())) continue;
            sheet.getContentCellStyleCache().put(i, contentInt);
            sheet.getHeadCellStyleCache().put(i, headAmt);
            sheet.getContentCellTypeCache().put(i, CellType.NUMERIC);
        }
        sheet.getRowDatas().add(titles);
        return sheet;
    }

    protected abstract List<Map<String, Object>> getRecords(Map<String, Object> var1);

    private CellStyle getNewContentStyle(CellStyle contentAmt, Workbook workbook, ColumnModelDefine define) {
        if (workbook == null) {
            return contentAmt;
        }
        StringBuilder decimal = new StringBuilder();
        if (define.getDecimal() > 0) {
            decimal.append(".");
            for (int j = 0; j < define.getDecimal(); ++j) {
                decimal.append("0");
            }
        }
        CellStyle newStyle = workbook.createCellStyle();
        newStyle.cloneStyleFrom(contentAmt);
        newStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0" + decimal));
        return newStyle;
    }
}

