/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.task.IGcEndCarryForwardExportTask
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryTableVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.unionrule.util.ExcelUtils$ExportColumnTypeEnum
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.invest.offsetitem.task;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.task.IGcEndCarryForwardExportTask;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryTableVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.unionrule.util.ExcelUtils;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MinorityRecoveryWorkPaperExportTask {
    @Autowired
    private IGcEndCarryForwardExportTask gcEndCarryForwardExportTask;

    protected ExportExcelSheet exportExcelSheet(QueryParamsVO queryParamsVO, MinorityRecoveryTableVO minorityRecoveryTableVO) {
        ExportExcelSheet exportExcelSheet = this.gcEndCarryForwardExportTask.exportMinorityRecoveryWorkPaper(queryParamsVO, minorityRecoveryTableVO);
        this.setColumnWidth(exportExcelSheet);
        return exportExcelSheet;
    }

    private void setColumnWidth(ExportExcelSheet exportExcelSheet) {
        Map columnWidthCache = exportExcelSheet.getColumnWidthCache();
        for (int i = 0; i < 13; ++i) {
            if (i == 0) {
                columnWidthCache.put(i, 2560);
                continue;
            }
            if (i < 6) {
                columnWidthCache.put(i, 7680);
                continue;
            }
            columnWidthCache.put(i, 5120);
        }
    }

    protected void drawStyle(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        String cellStyleKey;
        QueryParamsVO queryParamsVO = (QueryParamsVO)JsonUtils.readValue((String)context.getParam(), QueryParamsVO.class);
        int rowNum = row.getRowNum();
        List rowDatas = excelSheet.getRowDatas();
        String oppUnitTitle = (String)((Object[])rowDatas.get(rowNum))[1];
        boolean isTotalRow = false;
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            isTotalRow = GcI18nUtil.getMessage((String)"gc.calculate.lossGain.sum").equals(oppUnitTitle) || GcI18nUtil.getMessage((String)"gc.calculate.lossGain.deferredIncomeTaxSum").equals(oppUnitTitle) || GcI18nUtil.getMessage((String)"gc.calculate.lossGain.workPaperSum").equals(oppUnitTitle);
        } else {
            boolean bl = isTotalRow = GcI18nUtil.getMessage((String)"gc.calculate.lossGain.sum").equals(oppUnitTitle) || GcI18nUtil.getMessage((String)"gc.calculate.lossGain.unrealized.gain.or.loss").equals(oppUnitTitle) || GcI18nUtil.getMessage((String)"gc.calculate.lossGain.downStreamTotal").equals(oppUnitTitle) || GcI18nUtil.getMessage((String)"gc.calculate.lossGain.againstStreamTotal").equals(oppUnitTitle) || GcI18nUtil.getMessage((String)"gc.calculate.lossGain.horizStreamTotal").equals(oppUnitTitle) || GcI18nUtil.getMessage((String)"gc.calculate.lossGain.deferredIncomeTaxTotal").equals(oppUnitTitle) || GcI18nUtil.getMessage((String)"gc.calculate.lossGain.lossGainTotal").equals(oppUnitTitle);
        }
        if (cell.getColumnIndex() < 4 || 5 < cell.getColumnIndex() && cell.getColumnIndex() < ((Object[])rowDatas.get(0)).length - 7) {
            cellStyleKey = rowNum < 2 ? "headText" : (isTotalRow ? "intervalColorText" : "contentText");
        } else {
            if (!(rowNum < 2 || cellValue == null || StringUtils.isEmpty((String)cellValue.toString()) || "--".equals(cellValue.toString()) || cellValue.toString().contains("%"))) {
                cell.setCellType(ExcelUtils.ExportColumnTypeEnum.NUMERIC.getCode());
                cell.setCellValue(Double.valueOf(String.valueOf(cellValue).replace(",", "")).doubleValue());
            }
            String string = rowNum < 2 ? "headAmt" : (cellStyleKey = isTotalRow ? "intervalColorAmt" : "contentAmt");
            if ("contentAmt".equals(cellStyleKey) && cell.getColumnIndex() == 7) {
                cellStyleKey = "specialContentAmt";
            }
        }
        CellStyle cellStyle = (CellStyle)context.getVarMap().get(cellStyleKey);
        if (rowNum == 0) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        }
        cell.setCellStyle(cellStyle);
    }
}

