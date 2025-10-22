/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldPageVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILVO
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.intermediatelibrary.service.impl;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateProgrammeService;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldPageVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class IntermediateLibraryExportImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Resource
    private IntermediateProgrammeService iPService;

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        JSONObject actionParams = new JSONObject(context.getParam());
        ArrayList<ExportExcelSheet> exportExcelSheetList = new ArrayList<ExportExcelSheet>();
        ExportExcelSheet mastSheet = new ExportExcelSheet(Integer.valueOf(0), "\u6570\u636e\u4ea4\u6362\u6307\u6807", Integer.valueOf(3));
        List<Object[]> rowDataList = this.getHeadData(actionParams, context.isTemplateExportFlag());
        if (!context.isTemplateExportFlag()) {
            this.getProgrammeForId(actionParams, rowDataList);
        }
        mastSheet.getRowDatas().addAll(rowDataList);
        exportExcelSheetList.add(mastSheet);
        return exportExcelSheetList;
    }

    private void getProgrammeForId(JSONObject actionParams, List<Object[]> rowDataList) {
        ILFieldCondition condition = new ILFieldCondition();
        condition.setProgrammeId(actionParams.getString("programmeId"));
        condition.setPageCurrent(-1);
        condition.setPageSize(-1);
        ILFieldPageVO fieldPageVo = this.iPService.getFieldOfProgrammeId(condition);
        List ilFieldVOList = fieldPageVo.getILFieldVOList();
        for (int count = 0; count < ilFieldVOList.size(); ++count) {
            ILFieldVO ilFieldVO = (ILFieldVO)ilFieldVOList.get(count);
            Object[] dataObject = new Object[]{count + 1, ilFieldVO.getFieldCode(), ilFieldVO.getFieldTitle(), ilFieldVO.getFieldType(), ilFieldVO.getFieldSize()};
            rowDataList.add(dataObject);
        }
    }

    private List<Object[]> getHeadData(JSONObject actionParams, boolean templateExportFlag) {
        ArrayList<Object[]> rowDataList = new ArrayList<Object[]>();
        ILCondition iLCondition = new ILCondition();
        iLCondition.setId(actionParams.getString("programmeId"));
        ILVO ilvo = this.iPService.getProgrammeForId(iLCondition);
        Object[] headTitles = new Object[]{"\u6570\u636e\u4ea4\u6362\u8bbe\u7f6e\u6307\u6807", "\u6570\u636e\u4ea4\u6362\u8bbe\u7f6e\u6307\u6807", "\u6570\u636e\u4ea4\u6362\u8bbe\u7f6e\u6307\u6807", "\u6570\u636e\u4ea4\u6362\u8bbe\u7f6e\u6307\u6807", "\u6570\u636e\u4ea4\u6362\u8bbe\u7f6e\u6307\u6807"};
        rowDataList.add(headTitles);
        Object[] programmeNameTitle = new Object[5];
        programmeNameTitle[0] = "\u6570\u636e\u4ea4\u6362\u65b9\u6848\u540d\u79f0\uff1a";
        programmeNameTitle[1] = "\u6570\u636e\u4ea4\u6362\u65b9\u6848\u540d\u79f0\uff1a";
        programmeNameTitle[2] = "\u6570\u636e\u4ea4\u6362\u65b9\u6848\u540d\u79f0\uff1a";
        if (templateExportFlag) {
            programmeNameTitle[3] = "";
            programmeNameTitle[4] = "";
        } else {
            programmeNameTitle[3] = ilvo.getProgrammeName();
            programmeNameTitle[4] = ilvo.getProgrammeName();
        }
        rowDataList.add(programmeNameTitle);
        Object[] titles = new Object[]{"\u5e8f\u53f7", "\u6307\u6807\u4ee3\u7801", "\u6307\u6807\u540d\u79f0", "\u6570\u636e\u7c7b\u578b", "\u6570\u636e\u7cbe\u5ea6"};
        rowDataList.add(titles);
        return rowDataList;
    }

    public String getName() {
        return "IntermediateExportExecutor";
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        CellStyle titleHeadCellStyle;
        int rowNum = row.getRowNum();
        int numberOfCells = row.getPhysicalNumberOfCells();
        if (rowNum == 0) {
            titleHeadCellStyle = this.buildDefaultHeadCellStyle(workbook);
            titleHeadCellStyle.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellStyle(titleHeadCellStyle);
        }
        if (rowNum == 1) {
            titleHeadCellStyle = this.buildDefaultHeadCellStyle(workbook);
            titleHeadCellStyle.setAlignment(HorizontalAlignment.LEFT);
            if (numberOfCells >= 3) {
                titleHeadCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
            }
            cell.setCellStyle(titleHeadCellStyle);
        }
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        sheet.setColumnWidth(0, 2560);
        sheet.setColumnWidth(1, 10240);
        sheet.setColumnWidth(2, 7680);
        sheet.setColumnWidth(3, 5120);
        sheet.setColumnWidth(4, 5120);
    }
}

