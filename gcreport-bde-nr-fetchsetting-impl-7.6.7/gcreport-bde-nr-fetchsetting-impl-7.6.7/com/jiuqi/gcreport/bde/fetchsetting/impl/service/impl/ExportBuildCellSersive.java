/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.RegionTypeEnum
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellUtil
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.bde.common.constant.RegionTypeEnum;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.ExcelRegionInfo;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.FetchSettingExportExecutor;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelCellStyleGroup;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;

public class ExportBuildCellSersive {
    private FetchSettingExportExecutor fetchSettingExportExecutor;

    public ExportBuildCellSersive(FetchSettingExportExecutor fetchSettingExportExecutor) {
        this.fetchSettingExportExecutor = fetchSettingExportExecutor;
    }

    public void buildSheetDataCells(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        if ("\u586b\u62a5\u8bf4\u660e".equals(sheet.getSheetName())) {
            this.buildExplainCell(context, excelSheet, workbook, sheet);
        } else {
            this.buildCell(context, excelSheet, workbook, sheet);
        }
    }

    private void buildExplainCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        List fetchSourceVOS = (List)context.getVarMap().get("FETCH_SOURCE");
        int offset = fetchSourceVOS.size();
        List<Integer> headList = Arrays.asList(1, 26, 31 + offset, 32 + offset, 46 + offset, 47 + offset, 48 + offset, 49 + offset, 57 + offset, 58 + offset, 59 + offset, 60 + offset, 68 + offset, 69 + offset, 70 + offset, 71 + offset);
        List<Integer> blueHeadList = Arrays.asList(0, 25, 29 + offset);
        ExcelCellStyleGroup excelCellStyleGroup = new ExcelCellStyleGroup(workbook);
        AtomicInteger rowIndex = new AtomicInteger(0);
        List rowDatas = excelSheet.getRowDatas();
        rowDatas.stream().forEach(rowData -> {
            Row row = sheet.createRow(rowIndex.get());
            int rowNum = row.getRowNum();
            for (int columnIndex = 0; columnIndex < ((Object[])rowData).length; ++columnIndex) {
                CellType cellType;
                CellStyle cellStyle;
                Cell cell = CellUtil.getCell((Row)row, (int)columnIndex);
                if (headList.contains(rowNum)) {
                    cellStyle = excelCellStyleGroup.getHeadStringStyle();
                    cellType = CellType.STRING;
                } else if (" ".equals(rowData[columnIndex])) {
                    cellStyle = excelCellStyleGroup.getContentLeftStringBackGreyStyle();
                    cellType = CellType.STRING;
                } else if (blueHeadList.contains(rowNum)) {
                    cellStyle = excelCellStyleGroup.getHeadStringBlueBackStyle();
                    cellType = CellType.STRING;
                } else {
                    cellStyle = (CellStyle)excelSheet.getContentCellStyleCache().get(columnIndex);
                    cellType = (CellType)excelSheet.getContentCellTypeCache().get(columnIndex);
                }
                cell.setCellStyle(cellStyle);
                cell.setCellType(cellType);
                Object cellValue = rowData[columnIndex];
                this.fetchSettingExportExecutor.writeCellValue(context, excelSheet, workbook, sheet, row, cell, cellValue);
                this.fetchSettingExportExecutor.callBackCell(context, excelSheet, workbook, sheet, row, cell, cellValue);
            }
            rowIndex.incrementAndGet();
            this.fetchSettingExportExecutor.callBackRow(context, excelSheet, workbook, sheet, row);
        });
    }

    private void buildCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        List headIndexList = (List)context.getVarMap().get(String.valueOf(excelSheet.getSheetNo()));
        AtomicInteger rowIndex = new AtomicInteger(0);
        ExcelCellStyleGroup excelCellStyleGroup = new ExcelCellStyleGroup(workbook);
        ArrayList<Integer> headCellIndex = new ArrayList<Integer>();
        ArrayList<Integer> floatSettingCellHeadIndex = new ArrayList<Integer>();
        List rowDatas = excelSheet.getRowDatas();
        for (ExcelRegionInfo excelRegionInfo : headIndexList) {
            int i;
            if (RegionTypeEnum.FIXED.equals((Object)excelRegionInfo.getRegionTypeEnum())) {
                for (i = 0; i < FetchSettingNrUtil.FIX_HEAD_SIZE; ++i) {
                    headCellIndex.add(excelRegionInfo.getStartIndex() + i);
                }
                continue;
            }
            for (i = 0; i < FetchSettingNrUtil.FLOAT_HEAD_SIZE; ++i) {
                if (i == 1) {
                    floatSettingCellHeadIndex.add(excelRegionInfo.getStartIndex() + i);
                    continue;
                }
                headCellIndex.add(excelRegionInfo.getStartIndex() + i);
            }
        }
        for (int rowDataIndex = 0; rowDataIndex < rowDatas.size(); ++rowDataIndex) {
            Object[] rowData = (Object[])rowDatas.get(rowDataIndex);
            Row row = sheet.createRow(rowIndex.get());
            for (int columnIndex = 0; columnIndex < rowData.length; ++columnIndex) {
                Cell cell = CellUtil.getCell((Row)row, (int)columnIndex);
                CellStyle cellStyle = headCellIndex.contains(rowDataIndex) ? excelCellStyleGroup.getHeadStringStyle() : (floatSettingCellHeadIndex.contains(rowDataIndex) ? (columnIndex % 2 == 1 && columnIndex <= 3 ? excelCellStyleGroup.getContentStringStyle() : excelCellStyleGroup.getHeadStringStyle()) : excelCellStyleGroup.getContentStringStyle());
                cell.setCellStyle(cellStyle);
                cell.setCellType(CellType.STRING);
                Object cellValue = rowData[columnIndex];
                this.fetchSettingExportExecutor.writeCellValue(context, excelSheet, workbook, sheet, row, cell, cellValue);
                this.fetchSettingExportExecutor.callBackCell(context, excelSheet, workbook, sheet, row, cell, cellValue);
            }
            rowIndex.incrementAndGet();
            this.fetchSettingExportExecutor.callBackRow(context, excelSheet, workbook, sheet, row);
        }
    }
}

