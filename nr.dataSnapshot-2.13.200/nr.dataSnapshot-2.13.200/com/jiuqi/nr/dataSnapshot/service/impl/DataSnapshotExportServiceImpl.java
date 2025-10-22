/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.dataentry.bean.ExportData
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.snapshot.consts.RegionType
 *  com.jiuqi.nr.snapshot.message.DifferenceData
 *  com.jiuqi.nr.snapshot.message.DifferenceDataItem
 */
package com.jiuqi.nr.dataSnapshot.service.impl;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotDifference;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotExportParam;
import com.jiuqi.nr.dataSnapshot.param.FixedDataRegionCompareDifference;
import com.jiuqi.nr.dataSnapshot.param.FloatCompareDifference;
import com.jiuqi.nr.dataSnapshot.param.FloatDataRegionCompareDifference;
import com.jiuqi.nr.dataSnapshot.param.IDataRegionCompareDifference;
import com.jiuqi.nr.dataSnapshot.service.IDataSnapshotExportService;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.snapshot.consts.RegionType;
import com.jiuqi.nr.snapshot.message.DifferenceData;
import com.jiuqi.nr.snapshot.message.DifferenceDataItem;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSnapshotExportServiceImpl
implements IDataSnapshotExportService {
    private static final Logger logger = LoggerFactory.getLogger(DataSnapshotExportServiceImpl.class);
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;

    @Override
    public ExportData compareResultExport(DataSnapshotExportParam info, List<DataSnapshotDifference> result, String formKey) {
        Boolean showAllFieldCompareResult = info.getShowAllFieldCompareResult();
        Boolean showDifferenceColor = info.getShowDifferenceColor();
        double deviation = info.getDeviation();
        XSSFWorkbook workbook = null;
        DataSnapshotDifference dataSnapshotDifference = null;
        for (DataSnapshotDifference diff : result) {
            if (!diff.getFormKey().equals(formKey)) continue;
            dataSnapshotDifference = diff;
        }
        if (dataSnapshotDifference == null) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u5bf9\u6bd4\u7ed3\u679c\u65e0\u5f53\u524d\u62a5\u8868");
            return null;
        }
        workbook = this.exportDataSnapshotDifference(dataSnapshotDifference, deviation, showAllFieldCompareResult, showDifferenceColor);
        if (null != workbook) {
            FormDefine formDefine = this.iRunTimeViewController.queryFormById(formKey);
            ByteArrayOutputStream os = new ByteArrayOutputStream(0xA00000);
            try {
                workbook.write(os);
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            byte[] byteArray = os.toByteArray();
            long now = Instant.now().toEpochMilli();
            Date date = new Date(now);
            SimpleDateFormat dateFormatForFolder = new SimpleDateFormat("yyyyMMddHH");
            String formatDateFolder = dateFormatForFolder.format(date);
            String fileName = formDefine.getTitle() + '_' + formDefine.getFormCode() + "_" + formatDateFolder + ".xlsx";
            ExportData exportData = new ExportData(fileName, byteArray);
            return exportData;
        }
        return null;
    }

    private XSSFWorkbook exportDataSnapshotDifference(DataSnapshotDifference dataSnapshotDifference, double deviation, Boolean showAllFieldCompareResult, Boolean showDifferenceColor) {
        XSSFWorkbook wb = new XSSFWorkbook();
        for (IDataRegionCompareDifference iDataRegionCompareDifference : dataSnapshotDifference.getDifferenceDatas().values()) {
            this.createSheet(wb, iDataRegionCompareDifference, dataSnapshotDifference.getDataSourceNames(), deviation, showAllFieldCompareResult, showDifferenceColor);
        }
        return wb;
    }

    private void createSheet(XSSFWorkbook wb, IDataRegionCompareDifference iDataRegionCompareDifference, List<String> sourceNames, double deviation, Boolean showAllFieldCompareResult, Boolean showDifferenceColor) {
        XSSFSheet sheet = wb.createSheet(iDataRegionCompareDifference.getRegionName() + iDataRegionCompareDifference.getRegionKey());
        XSSFCellStyle style = wb.createCellStyle();
        XSSFCellStyle greyStyle = wb.createCellStyle();
        XSSFCellStyle leftRedStyle = wb.createCellStyle();
        XSSFCellStyle rightRedStyle = wb.createCellStyle();
        XSSFCellStyle leftStyle = wb.createCellStyle();
        XSSFCellStyle rightStyle = wb.createCellStyle();
        style = this.createCellStyle(style, HSSFColor.HSSFColorPredefined.WHITE, HorizontalAlignment.CENTER);
        leftStyle = this.createCellStyle(leftStyle, HSSFColor.HSSFColorPredefined.WHITE, HorizontalAlignment.LEFT);
        rightStyle = this.createCellStyle(rightStyle, HSSFColor.HSSFColorPredefined.WHITE, HorizontalAlignment.RIGHT);
        leftRedStyle = this.createCellStyle(leftRedStyle, HSSFColor.HSSFColorPredefined.RED, HorizontalAlignment.LEFT);
        rightRedStyle = this.createCellStyle(rightRedStyle, HSSFColor.HSSFColorPredefined.RED, HorizontalAlignment.RIGHT);
        greyStyle = this.createCellStyle(greyStyle, HSSFColor.HSSFColorPredefined.GREY_25_PERCENT, HorizontalAlignment.CENTER);
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)13);
        font.setFontName("\u5b8b\u4f53");
        font.setBold(false);
        style.setFont(font);
        leftStyle.setFont(font);
        rightStyle.setFont(font);
        leftRedStyle.setFont(font);
        rightRedStyle.setFont(font);
        XSSFFont greyfont = wb.createFont();
        greyfont.setFontHeightInPoints((short)13);
        greyfont.setFontName("\u5b8b\u4f53");
        greyfont.setBold(true);
        greyStyle.setFont(greyfont);
        if (iDataRegionCompareDifference.getRegionType().equals((Object)RegionType.FIXED)) {
            FixedDataRegionCompareDifference fixedDataRegionCompareDifference = (FixedDataRegionCompareDifference)iDataRegionCompareDifference;
            sheet.setColumnWidth(0, 2048);
            sheet.setColumnWidth(1, 6400);
            for (int i = 0; i < sourceNames.size() + 1; ++i) {
                sheet.setColumnWidth(2 + i * 2, 3840);
                sheet.setColumnWidth(2 + i * 2 + 1, 3840);
            }
            XSSFRow row0 = sheet.createRow(0);
            XSSFCell cell0_0 = row0.createCell(0);
            cell0_0.setCellValue("\u5e8f\u53f7");
            cell0_0.setCellStyle(greyStyle);
            XSSFCell cell0_1 = row0.createCell(1);
            cell0_1.setCellValue("\u6307\u6807\u540d\u79f0");
            cell0_1.setCellStyle(greyStyle);
            XSSFCell cell0_2 = row0.createCell(2);
            cell0_2.setCellValue("\u5f53\u524d\u6570\u636e");
            cell0_2.setCellStyle(greyStyle);
            XSSFRow row1 = sheet.createRow(1);
            row1.createCell(0);
            row1.createCell(1);
            row1.createCell(2);
            CellRangeAddress cellAddresses0 = new CellRangeAddress(0, 1, 0, 0);
            sheet.addMergedRegion(cellAddresses0);
            CellRangeAddress cellAddresses1 = new CellRangeAddress(0, 1, 1, 1);
            sheet.addMergedRegion(cellAddresses1);
            CellRangeAddress cellAddresses2 = new CellRangeAddress(0, 1, 2, 2);
            sheet.addMergedRegion(cellAddresses2);
            XSSFCell cell0_X = null;
            XSSFCell cell0_X_1 = null;
            XSSFCell cell1_X = null;
            XSSFCell cell1_X_1 = null;
            for (int i = 0; i < sourceNames.size(); ++i) {
                cell0_X = row0.createCell(3 + i * 2);
                cell0_X_1 = row0.createCell(3 + i * 2 + 1);
                cell0_X.setCellValue(sourceNames.get(i));
                cell0_X.setCellStyle(greyStyle);
                cell0_X_1.setCellStyle(greyStyle);
                CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 3 + i * 2, 3 + i * 2 + 1);
                sheet.addMergedRegion(cellAddresses);
                cell1_X = row1.createCell(3 + i * 2);
                cell1_X_1 = row1.createCell(3 + i * 2 + 1);
                cell1_X.setCellValue("\u76ee\u6807\u503c");
                cell1_X.setCellStyle(greyStyle);
                cell1_X_1.setCellValue("\u589e\u51cf\u989d");
                cell1_X_1.setCellStyle(greyStyle);
            }
            int rowIndex = 0;
            for (int i = 0; i < fixedDataRegionCompareDifference.getDifferenceDataItems().size(); ++i) {
                FieldDefine fieldDefine;
                DifferenceDataItem differenceDataItem = fixedDataRegionCompareDifference.getDifferenceDataItems().get(i);
                if (!showAllFieldCompareResult.booleanValue()) {
                    Boolean noDiff = false;
                    for (DifferenceData differenceData : differenceDataItem.getDifferenceDatas()) {
                        if (differenceData.getScale() != -1.0 && differenceData.getDifference() == 0.0) continue;
                        noDiff = true;
                    }
                    if (!noDiff.booleanValue()) continue;
                }
                try {
                    fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(differenceDataItem.getFieldKey());
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                String horizontalStyle = "left";
                Boolean canShowDiff = false;
                if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DECIMAL) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_INTEGER) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FLOAT) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DATE) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
                    horizontalStyle = "right";
                }
                if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DECIMAL) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_INTEGER) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FLOAT)) {
                    canShowDiff = true;
                }
                XSSFRow row = sheet.createRow(rowIndex + 1 + 1);
                XSSFCell cell0 = row.createCell(0);
                cell0.setCellValue(rowIndex + 1);
                cell0.setCellStyle(style);
                ++rowIndex;
                XSSFCell cell1 = row.createCell(1);
                cell1.setCellValue(differenceDataItem.getFieldTitle());
                cell1.setCellStyle(style);
                XSSFCell cell2 = row.createCell(2);
                cell2.setCellValue(((DifferenceData)differenceDataItem.getDifferenceDatas().get(0)).getInitialValue());
                this.setCellHorizontal(cell2, horizontalStyle, leftStyle, rightStyle);
                XSSFCell celli_X = null;
                XSSFCell celli_X_1 = null;
                for (int j = 0; j < differenceDataItem.getDifferenceDatas().size(); ++j) {
                    DifferenceData differenceData = (DifferenceData)differenceDataItem.getDifferenceDatas().get(j);
                    celli_X = row.createCell(2 * j + 3);
                    celli_X_1 = row.createCell(2 * j + 3 + 1);
                    celli_X.setCellValue(differenceData.getCompareValue());
                    if (canShowDiff.booleanValue()) {
                        celli_X_1.setCellValue(differenceData.getDifference());
                    } else {
                        celli_X_1.setCellValue("");
                    }
                    celli_X_1.setCellStyle(style);
                    if (showDifferenceColor.booleanValue() && (differenceData.getScale() == -1.0 || differenceData.getScale() * 100.0 > deviation)) {
                        this.setCellHorizontal(celli_X, horizontalStyle, leftRedStyle, rightRedStyle);
                        continue;
                    }
                    this.setCellHorizontal(celli_X, horizontalStyle, leftStyle, rightStyle);
                }
            }
        } else {
            FloatDataRegionCompareDifference floatDataRegionCompareDifference = (FloatDataRegionCompareDifference)iDataRegionCompareDifference;
            if (floatDataRegionCompareDifference.getNaturalKeys() != null && floatDataRegionCompareDifference.getNaturalKeys().size() != 0) {
                int i;
                int dimLen = floatDataRegionCompareDifference.getNaturalKeys().size();
                sheet.setColumnWidth(0, 2048);
                XSSFRow row0 = sheet.createRow(0);
                XSSFCell cell0_0 = row0.createCell(0);
                cell0_0.setCellValue("\u5e8f\u53f7");
                cell0_0.setCellStyle(greyStyle);
                for (i = 0; i < dimLen; ++i) {
                    sheet.setColumnWidth(i + 1, 6400);
                    XSSFCell cell0_i = row0.createCell(i + 1);
                    cell0_i.setCellValue(floatDataRegionCompareDifference.getNaturalKeys().get(i).split(";")[1]);
                    cell0_i.setCellStyle(greyStyle);
                }
                for (i = 0; i < floatDataRegionCompareDifference.getFloatCompareDifferences().get(0).getDifferenceDataItems().size() * (sourceNames.size() + 1); ++i) {
                    sheet.setColumnWidth(2 * i + 1 + dimLen, 3840);
                    sheet.setColumnWidth(2 * i + 1 + dimLen + 1, 3840);
                }
                XSSFRow row1 = sheet.createRow(1);
                XSSFRow row2 = sheet.createRow(2);
                int indexHead = dimLen + 1;
                for (int i2 = 0; i2 < floatDataRegionCompareDifference.getFloatCompareDifferences().get(0).getDifferenceDataItems().size(); ++i2) {
                    FieldDefine fieldDefine;
                    int curIndexhead = indexHead;
                    try {
                        fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(floatDataRegionCompareDifference.getFloatCompareDifferences().get(0).getDifferenceDataItems().get(i2).getFieldKey());
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    Boolean canShowDiff = false;
                    if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DECIMAL) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_INTEGER) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FLOAT)) {
                        canShowDiff = true;
                    }
                    XSSFCell cell0_i = row0.createCell(indexHead);
                    cell0_i.setCellValue(floatDataRegionCompareDifference.getFloatCompareDifferences().get(0).getDifferenceDataItems().get(i2).getFieldTitle());
                    cell0_i.setCellStyle(greyStyle);
                    XSSFCell cell1_1 = row1.createCell(indexHead);
                    cell1_1.setCellValue("\u5f53\u524d\u6570\u636e");
                    cell1_1.setCellStyle(greyStyle);
                    row2.createCell(indexHead).setCellStyle(greyStyle);
                    sheet.addMergedRegion(new CellRangeAddress(1, 2, indexHead, indexHead));
                    ++indexHead;
                    for (int j = 0; j < sourceNames.size(); ++j) {
                        row0.createCell(indexHead).setCellStyle(greyStyle);
                        XSSFCell cell1_i = row1.createCell(indexHead);
                        cell1_i.setCellValue(sourceNames.get(j));
                        cell1_i.setCellStyle(greyStyle);
                        if (canShowDiff.booleanValue()) {
                            row1.createCell(indexHead + 1).setCellStyle(greyStyle);
                            sheet.addMergedRegion(new CellRangeAddress(1, 1, indexHead, indexHead + 1));
                            XSSFCell cell1_j = row2.createCell(indexHead);
                            cell1_j.setCellValue("\u76ee\u6807\u503c");
                            cell1_j.setCellStyle(greyStyle);
                            XSSFCell cell1_j_1 = row2.createCell(++indexHead);
                            cell1_j_1.setCellValue("\u589e\u51cf\u989d");
                            cell1_j_1.setCellStyle(greyStyle);
                            row0.createCell(indexHead).setCellStyle(greyStyle);
                            ++indexHead;
                            continue;
                        }
                        row2.createCell(indexHead).setCellStyle(greyStyle);
                        sheet.addMergedRegion(new CellRangeAddress(1, 2, indexHead, indexHead));
                        ++indexHead;
                    }
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, curIndexhead, indexHead - 1));
                }
                row1.createCell(0).setCellStyle(greyStyle);
                row2.createCell(0).setCellStyle(greyStyle);
                CellRangeAddress cellAddresses0_0 = new CellRangeAddress(0, 2, 0, 0);
                sheet.addMergedRegion(cellAddresses0_0);
                for (int i3 = 0; i3 < dimLen; ++i3) {
                    row1.createCell(i3 + 1).setCellStyle(greyStyle);
                    row2.createCell(i3 + 1).setCellStyle(greyStyle);
                    CellRangeAddress cellAddresses0_i = new CellRangeAddress(0, 2, i3 + 1, i3 + 1);
                    sheet.addMergedRegion(cellAddresses0_i);
                }
                int rowIndex = 0;
                for (int i4 = 0; i4 < floatDataRegionCompareDifference.getFloatCompareDifferences().size(); ++i4) {
                    XSSFCell cellj;
                    FloatCompareDifference floatCompareDifference = floatDataRegionCompareDifference.getFloatCompareDifferences().get(i4);
                    if (!showAllFieldCompareResult.booleanValue()) {
                        Boolean noDiff = false;
                        for (DifferenceDataItem differenceDataItem : floatCompareDifference.getDifferenceDataItems()) {
                            for (DifferenceData differenceData : differenceDataItem.getDifferenceDatas()) {
                                if (differenceData.getScale() != -1.0 && differenceData.getDifference() == 0.0) continue;
                                noDiff = true;
                            }
                        }
                        if (!noDiff.booleanValue()) continue;
                    }
                    XSSFRow row = sheet.createRow(2 + rowIndex + 1);
                    XSSFCell cell0 = row.createCell(0);
                    cell0.setCellValue(rowIndex + 1);
                    cell0.setCellStyle(style);
                    ++rowIndex;
                    int index = 1 + dimLen;
                    int floatDiffFlag = 0;
                    for (DifferenceDataItem differenceDataItem : floatCompareDifference.getDifferenceDataItems()) {
                        FieldDefine fieldDefine;
                        try {
                            fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(differenceDataItem.getFieldKey());
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        String horizontalStyle = "left";
                        if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DECIMAL) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_INTEGER) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FLOAT) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DATE) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
                            horizontalStyle = "right";
                        }
                        Boolean canShowDiff = false;
                        if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DECIMAL) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_INTEGER) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FLOAT)) {
                            canShowDiff = true;
                        }
                        XSSFCell cellIndex1 = row.createCell(index);
                        cellIndex1.setCellValue(((DifferenceData)differenceDataItem.getDifferenceDatas().get(0)).getInitialValue());
                        this.setCellHorizontal(cellIndex1, horizontalStyle, leftStyle, rightStyle);
                        ++index;
                        if (((DifferenceData)differenceDataItem.getDifferenceDatas().get(0)).getInitialValue() == null) {
                            ++floatDiffFlag;
                        }
                        for (DifferenceData differenceData : differenceDataItem.getDifferenceDatas()) {
                            XSSFCell cellIndex = row.createCell(index);
                            cellIndex.setCellValue(differenceData.getCompareValue());
                            if (canShowDiff.booleanValue()) {
                                XSSFCell cellIndexDiff = row.createCell(++index);
                                cellIndexDiff.setCellValue(differenceData.getDifference());
                                cellIndexDiff.setCellStyle(style);
                            }
                            if (showDifferenceColor.booleanValue() && (differenceData.getScale() == -1.0 || differenceData.getScale() * 100.0 > deviation)) {
                                this.setCellHorizontal(cellIndex, horizontalStyle, leftRedStyle, rightRedStyle);
                            } else {
                                this.setCellHorizontal(cellIndex, horizontalStyle, leftStyle, rightStyle);
                            }
                            ++index;
                        }
                    }
                    if (showDifferenceColor.booleanValue() && floatDiffFlag == floatCompareDifference.getDifferenceDataItems().size()) {
                        for (int j = 0; j < dimLen; ++j) {
                            cellj = row.createCell(j + 1);
                            cellj.setCellValue(floatCompareDifference.getNaturalDatas().get(j));
                            cellj.setCellStyle(leftRedStyle);
                        }
                        continue;
                    }
                    for (int j = 0; j < dimLen; ++j) {
                        cellj = row.createCell(j + 1);
                        cellj.setCellValue(floatCompareDifference.getNaturalDatas().get(j));
                        cellj.setCellStyle(leftStyle);
                    }
                }
            } else {
                sheet.setColumnWidth(0, 2048);
                for (int i = 0; i < floatDataRegionCompareDifference.getFloatCompareDifferences().get(0).getDifferenceDataItems().size() * (sourceNames.size() + 1); ++i) {
                    sheet.setColumnWidth(2 * i + 1, 3840);
                    sheet.setColumnWidth(2 * i + 1 + 1, 3840);
                }
                XSSFRow row0 = sheet.createRow(0);
                XSSFCell cell0_0 = row0.createCell(0);
                cell0_0.setCellValue("\u5e8f\u53f7");
                cell0_0.setCellStyle(greyStyle);
                XSSFRow row1 = sheet.createRow(1);
                row1.createCell(0).setCellStyle(greyStyle);
                XSSFRow row2 = sheet.createRow(2);
                row2.createCell(0).setCellStyle(greyStyle);
                int indexHead = 1;
                for (int i = 0; i < floatDataRegionCompareDifference.getFloatCompareDifferences().get(0).getDifferenceDataItems().size(); ++i) {
                    FieldDefine fieldDefine;
                    int curIndexhead = indexHead;
                    try {
                        fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(floatDataRegionCompareDifference.getFloatCompareDifferences().get(0).getDifferenceDataItems().get(i).getFieldKey());
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    Boolean canShowDiff = false;
                    if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DECIMAL) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_INTEGER) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FLOAT)) {
                        canShowDiff = true;
                    }
                    XSSFCell cell0_i = row0.createCell(indexHead);
                    cell0_i.setCellValue(floatDataRegionCompareDifference.getFloatCompareDifferences().get(0).getDifferenceDataItems().get(i).getFieldTitle());
                    cell0_i.setCellStyle(greyStyle);
                    XSSFCell cell1_1 = row1.createCell(indexHead);
                    cell1_1.setCellValue("\u5f53\u524d\u6570\u636e");
                    cell1_1.setCellStyle(greyStyle);
                    row2.createCell(indexHead).setCellStyle(greyStyle);
                    sheet.addMergedRegion(new CellRangeAddress(1, 2, indexHead, indexHead));
                    ++indexHead;
                    for (int j = 0; j < sourceNames.size(); ++j) {
                        row0.createCell(indexHead).setCellStyle(greyStyle);
                        XSSFCell cell1_i = row1.createCell(indexHead);
                        cell1_i.setCellValue(sourceNames.get(j));
                        cell1_i.setCellStyle(greyStyle);
                        if (canShowDiff.booleanValue()) {
                            row1.createCell(indexHead + 1).setCellStyle(greyStyle);
                            sheet.addMergedRegion(new CellRangeAddress(1, 1, indexHead, indexHead + 1));
                            XSSFCell cell1_j = row2.createCell(indexHead);
                            cell1_j.setCellValue("\u76ee\u6807\u503c");
                            cell1_j.setCellStyle(greyStyle);
                            XSSFCell cell1_j_1 = row2.createCell(++indexHead);
                            cell1_j_1.setCellValue("\u589e\u51cf\u989d");
                            cell1_j_1.setCellStyle(greyStyle);
                            row0.createCell(indexHead).setCellStyle(greyStyle);
                            ++indexHead;
                            continue;
                        }
                        row2.createCell(indexHead).setCellStyle(greyStyle);
                        sheet.addMergedRegion(new CellRangeAddress(1, 2, indexHead, indexHead));
                        ++indexHead;
                    }
                    row1.createCell(0).setCellStyle(greyStyle);
                    row2.createCell(0).setCellStyle(greyStyle);
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, curIndexhead, indexHead - 1));
                }
                sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
                int rowIndex = 0;
                for (int i = 0; i < floatDataRegionCompareDifference.getFloatCompareDifferences().size(); ++i) {
                    FloatCompareDifference floatCompareDifference = floatDataRegionCompareDifference.getFloatCompareDifferences().get(i);
                    if (!showAllFieldCompareResult.booleanValue()) {
                        Boolean noDiff = false;
                        for (DifferenceDataItem differenceDataItem : floatCompareDifference.getDifferenceDataItems()) {
                            for (DifferenceData differenceData : differenceDataItem.getDifferenceDatas()) {
                                if (differenceData.getScale() != -1.0 && differenceData.getDifference() == 0.0) continue;
                                noDiff = true;
                            }
                        }
                        if (!noDiff.booleanValue()) continue;
                    }
                    XSSFRow row = sheet.createRow(2 + rowIndex + 1);
                    XSSFCell cell0 = row.createCell(0);
                    cell0.setCellValue(rowIndex + 1);
                    cell0.setCellStyle(style);
                    ++rowIndex;
                    int index = 1;
                    for (DifferenceDataItem differenceDataItem : floatCompareDifference.getDifferenceDataItems()) {
                        FieldDefine fieldDefine;
                        try {
                            fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(differenceDataItem.getFieldKey());
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        String horizontalStyle = "left";
                        if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DECIMAL) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_INTEGER) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FLOAT) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DATE) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DATE_TIME)) {
                            horizontalStyle = "right";
                        }
                        Boolean canShowDiff = false;
                        if (fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_DECIMAL) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_INTEGER) || fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_FLOAT)) {
                            canShowDiff = true;
                        }
                        XSSFCell cellIndex1 = row.createCell(index);
                        cellIndex1.setCellValue(((DifferenceData)differenceDataItem.getDifferenceDatas().get(0)).getInitialValue());
                        this.setCellHorizontal(cellIndex1, horizontalStyle, leftStyle, rightStyle);
                        ++index;
                        for (DifferenceData differenceData : differenceDataItem.getDifferenceDatas()) {
                            XSSFCell cellIndex = row.createCell(index);
                            cellIndex.setCellValue(differenceData.getCompareValue());
                            if (canShowDiff.booleanValue()) {
                                XSSFCell cellIndexDiff = row.createCell(++index);
                                cellIndexDiff.setCellValue(differenceData.getDifference());
                                cellIndexDiff.setCellStyle(style);
                            }
                            if (showDifferenceColor.booleanValue() && (differenceData.getScale() == -1.0 || differenceData.getScale() * 100.0 > deviation)) {
                                this.setCellHorizontal(cellIndex, horizontalStyle, leftRedStyle, rightRedStyle);
                            } else {
                                this.setCellHorizontal(cellIndex, horizontalStyle, leftStyle, rightStyle);
                            }
                            ++index;
                        }
                    }
                }
            }
        }
    }

    private void setCellHorizontal(XSSFCell cell, String horizontalStyle, XSSFCellStyle left, XSSFCellStyle right) {
        if (horizontalStyle.equals("left")) {
            cell.setCellStyle(left);
        } else {
            cell.setCellStyle(right);
        }
    }

    private void createSheet(XSSFWorkbook wb, String sheetName, List<List<String>> list, String[] title) {
        XSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setColumnWidth(0, 2048);
        sheet.setColumnWidth(1, 2560);
        sheet.setColumnWidth(2, 6400);
        sheet.setColumnWidth(3, 6400);
        sheet.setColumnWidth(4, 6400);
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle style = wb.createCellStyle();
        style = this.createCellStyle(style, null, null);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("\u5b8b\u4f53");
        font.setBold(true);
        style.setFont(font);
        XSSFCell cell = null;
        for (int i = 0; i < title.length; ++i) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        XSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); ++i) {
                row = sheet.createRow(i + 1);
                List<String> clist = list.get(i);
                for (int n = 0; n < clist.size(); ++n) {
                    String value = clist.get(n);
                    XSSFCell cellData = row.createCell(n);
                    cellData.setCellValue(value);
                    if (n == 0) {
                        cellData.setCellStyle(style2);
                        continue;
                    }
                    cellData.setCellStyle(style);
                }
            }
        }
        String[] actions = new String[]{"\u5b57\u7b26\u4e32\u5305\u542b\u7c7b", "\u8868\u8fbe\u5f0f\u7c7b"};
        String[] actions2 = new String[]{"\u662f", "\u5426"};
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)dvHelper.createExplicitListConstraint(actions);
        XSSFDataValidationConstraint dvConstraint2 = (XSSFDataValidationConstraint)dvHelper.createExplicitListConstraint(actions2);
        CellRangeAddressList addressList = new CellRangeAddressList(1, 65535, 1, 1);
        CellRangeAddressList addressList2 = new CellRangeAddressList(1, 65535, 4, 4);
        DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
        validation.setSuppressDropDownArrow(true);
        validation.setShowErrorBox(true);
        DataValidation validation2 = dvHelper.createValidation(dvConstraint2, addressList2);
        validation2.setSuppressDropDownArrow(true);
        validation2.setShowErrorBox(true);
        sheet.addValidationData(validation);
        sheet.addValidationData(validation2);
    }

    private XSSFCellStyle createCellStyle(XSSFCellStyle style, HSSFColor.HSSFColorPredefined color, HorizontalAlignment alignment) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        if (alignment != null) {
            style.setAlignment(alignment);
        }
        if (color != null) {
            style.setFillBackgroundColor(color.getIndex());
            style.setFillForegroundColor(color.getIndex());
        }
        return style;
    }

    @Override
    public List<ExportData> compareResultBatchExport(DataSnapshotExportParam info, List<DataSnapshotDifference> result, List<String> forms) {
        ArrayList<ExportData> exportDatas = new ArrayList<ExportData>();
        for (String formKey : forms) {
            exportDatas.add(this.compareResultExport(info, result, formKey));
        }
        return exportDatas;
    }

    @Override
    public List<ExportData> compareResultBatchExport(DataSnapshotExportParam info, List<DataSnapshotDifference> result) {
        ArrayList<String> forms = new ArrayList<String>();
        for (DataSnapshotDifference dataSnapshotDifference : result) {
            forms.add(dataSnapshotDifference.getFormKey());
        }
        return this.compareResultBatchExport(info, result, forms);
    }
}

