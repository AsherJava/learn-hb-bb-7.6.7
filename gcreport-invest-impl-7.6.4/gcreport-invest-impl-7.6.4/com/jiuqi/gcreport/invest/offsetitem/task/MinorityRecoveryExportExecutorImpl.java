/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.offsetitem.service.GcEndCarryForwardService
 *  com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardResultVO
 *  com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryTableVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.invest.offsetitem.task;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.invest.offsetitem.task.DeferredIncomeTaxExportTask;
import com.jiuqi.gcreport.invest.offsetitem.task.LossGainExportTask;
import com.jiuqi.gcreport.invest.offsetitem.task.MinorityRecoveryExportTask;
import com.jiuqi.gcreport.invest.offsetitem.task.MinorityRecoveryWorkPaperExportTask;
import com.jiuqi.gcreport.invest.offsetitem.task.ReclassifyExportTask;
import com.jiuqi.gcreport.invest.offsetitem.task.ReduceReclassifyExportTask;
import com.jiuqi.gcreport.offsetitem.service.GcEndCarryForwardService;
import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardResultVO;
import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryTableVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MinorityRecoveryExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MinorityRecoveryExportExecutorImpl.class);
    @Autowired
    private MinorityRecoveryWorkPaperExportTask minorityRecoveryWorkPaperExportTask;
    @Autowired
    private MinorityRecoveryExportTask minorityRecoveryExportTask;
    @Autowired
    private LossGainExportTask lossGainExportTask;
    @Autowired
    private DeferredIncomeTaxExportTask deferredIncomeTaxExportTask;
    @Autowired
    private ReclassifyExportTask reclassifyExportTask;
    @Autowired
    private ReduceReclassifyExportTask reduceReclassifyExportTask;
    @Autowired
    private GcEndCarryForwardService gcEndCarryForwardService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskCacheService;

    public String getName() {
        return "MinorityRecoveryExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        QueryParamsVO queryParamsVO = (QueryParamsVO)JsonUtils.readValue((String)context.getParam(), QueryParamsVO.class);
        this.loadCellStyleCache(context, workbook);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        int sheetNo = 0;
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskCacheService.getTaskByTaskKeyAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
        EndCarryForwardResultVO resultVO = this.gcEndCarryForwardService.queryEndCarryForward(queryParamsVO);
        LossGainOffsetVO lossGainOffsetVO = resultVO.getLossGainOffsetVO();
        MinorityRecoveryTableVO minorityRecoveryTableVO = resultVO.getMinorityRecoveryTableVO();
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge()) || consolidatedTaskVO.getEnableMinLossGainRecovery().booleanValue()) {
            exportExcelSheets.add(this.minorityRecoveryWorkPaperExportTask.exportExcelSheet(queryParamsVO, minorityRecoveryTableVO));
            ++sheetNo;
        }
        if (consolidatedTaskVO.getEnableDeferredIncomeTax().booleanValue()) {
            exportExcelSheets.add(this.deferredIncomeTaxExportTask.exportExcelSheet(context, lossGainOffsetVO, sheetNo++));
        }
        if (!Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge()) && consolidatedTaskVO.getEnableMinLossGainRecovery().booleanValue()) {
            exportExcelSheets.add(this.minorityRecoveryExportTask.exportExcelSheet(context, lossGainOffsetVO, sheetNo++));
        }
        if (consolidatedTaskVO.getEnableLossGain().booleanValue()) {
            exportExcelSheets.add(this.lossGainExportTask.exportExcelSheet(context, lossGainOffsetVO, sheetNo++));
        }
        if (consolidatedTaskVO.getEnableReclassify().booleanValue()) {
            exportExcelSheets.add(this.reclassifyExportTask.exportExcelSheet(context, lossGainOffsetVO, sheetNo++));
        }
        if (consolidatedTaskVO.getEnableReduceReclassify().booleanValue()) {
            exportExcelSheets.add(this.reduceReclassifyExportTask.exportExcelSheet(context, lossGainOffsetVO, sheetNo++));
        }
        context.getProgressData().setProgressValueAndRefresh(0.95);
        return exportExcelSheets;
    }

    private void loadCellStyleCache(ExportContext context, Workbook workbook) {
        Map cellStyleMap = context.getVarMap();
        CellStyle headTextStyle = this.buildDefaultHeadCellStyle(workbook);
        headTextStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyleMap.put("headText", headTextStyle);
        CellStyle headAmountStyle = this.buildDefaultHeadCellStyle(workbook);
        headAmountStyle.setAlignment(HorizontalAlignment.RIGHT);
        cellStyleMap.put("headAmt", headAmountStyle);
        CellStyle contentTextStyle = this.buildDefaultContentCellStyle(workbook);
        contentTextStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyleMap.put("contentText", contentTextStyle);
        CellStyle contentAmountStyle = this.buildDefaultContentCellStyle(workbook);
        contentAmountStyle.setAlignment(HorizontalAlignment.RIGHT);
        contentAmountStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        cellStyleMap.put("contentAmt", contentAmountStyle);
        CellStyle specialContentAmountStyle = this.buildDefaultContentCellStyle(workbook);
        specialContentAmountStyle.setAlignment(HorizontalAlignment.RIGHT);
        cellStyleMap.put("specialContentAmt", contentAmountStyle);
        XSSFCellStyle intervalColorString = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
        intervalColorString.setAlignment(HorizontalAlignment.LEFT);
        intervalColorString.setFillForegroundColor(new XSSFColor(new Color(239, 248, 254), (IndexedColorMap)new DefaultIndexedColorMap()));
        intervalColorString.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleMap.put("intervalColorText", intervalColorString);
        XSSFCellStyle intervalColorAmt = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
        intervalColorAmt.setAlignment(HorizontalAlignment.RIGHT);
        intervalColorAmt.setFillForegroundColor(new XSSFColor(new Color(239, 248, 254), (IndexedColorMap)new DefaultIndexedColorMap()));
        intervalColorAmt.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        intervalColorAmt.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        cellStyleMap.put("intervalColorAmt", intervalColorAmt);
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        if (excelSheet.getSheetNo() == 0) {
            this.minorityRecoveryWorkPaperExportTask.drawStyle(context, excelSheet, workbook, sheet, row, cell, cellValue);
        }
        super.callBackCell(context, excelSheet, workbook, sheet, row, cell, cellValue);
    }
}

