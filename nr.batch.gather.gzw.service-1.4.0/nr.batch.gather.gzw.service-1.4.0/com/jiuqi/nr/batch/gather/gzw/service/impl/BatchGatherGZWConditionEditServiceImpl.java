/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.batch.summary.storage.CustomCalibreDao
 *  com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreRowDefine
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreValue
 *  com.jiuqi.nr.batch.summary.storage.enumeration.ConditionValueType
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 */
package com.jiuqi.nr.batch.gather.gzw.service.impl;

import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.batch.gather.gzw.service.BatchGatherGZWConditionEditService;
import com.jiuqi.nr.batch.gather.gzw.service.entity.BatchGatherExportParam;
import com.jiuqi.nr.batch.summary.storage.CustomCalibreDao;
import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreRowDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreValue;
import com.jiuqi.nr.batch.summary.storage.enumeration.ConditionValueType;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BatchGatherGZWConditionEditServiceImpl
implements BatchGatherGZWConditionEditService {
    private static final Logger logger = LoggerFactory.getLogger(BatchGatherGZWConditionEditServiceImpl.class);
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    @Autowired
    private CustomCalibreDao customCalibreDao;

    private String[] createTitle() {
        String calibreCode = this.i18nHelper.getMessage("Calibre-Code").equals("") ? "\u53e3\u5f84\u4ee3\u7801" : this.i18nHelper.getMessage("Calibre-Code");
        String calibreTitle = this.i18nHelper.getMessage("Calibre-Title").equals("") ? "\u53e3\u5f84\u540d\u79f0" : this.i18nHelper.getMessage("Calibre-Title");
        String expressionOrUnitList = this.i18nHelper.getMessage("expressionOrUnitList").equals("") ? "\u8868\u8fbe\u5f0f\u503c/\u5355\u4f4d\u5217\u8868" : this.i18nHelper.getMessage("expressionOrUnitList");
        String calibreType = this.i18nHelper.getMessage("Calibre-Type").equals("") ? "\u53e3\u5f84\u7c7b\u578b" : this.i18nHelper.getMessage("Calibre-Type");
        String parentCode = this.i18nHelper.getMessage("parentCode").equals("") ? "\u4e0a\u7ea7\u4ee3\u7801" : this.i18nHelper.getMessage("parentCode");
        String[] titles = new String[]{calibreCode, calibreTitle, expressionOrUnitList, calibreType, parentCode};
        return titles;
    }

    @Override
    public SXSSFWorkbook CustomCalibreExport(BatchGatherExportParam batchGatherExportParam) {
        SXSSFWorkbook workbook = null;
        List customCalibreRowList = this.customCalibreDao.findConditionRow(batchGatherExportParam.getSchemeKey());
        workbook = this.createWorkBookByFormulaCheckReturnInfo(customCalibreRowList, batchGatherExportParam);
        if (workbook != null) {
            return workbook;
        }
        return null;
    }

    private SXSSFWorkbook createWorkBookByFormulaCheckReturnInfo(List<CustomCalibreRow> customCalibreRowList, BatchGatherExportParam batchGatherExportParam) {
        Object createTitle = null;
        ArrayList<List<Object>> list = new ArrayList<List<Object>>();
        if (null != customCalibreRowList && customCalibreRowList.size() > 0) {
            boolean x = false;
            for (CustomCalibreRow customCalibreRow : customCalibreRowList) {
                ArrayList<Object> singleCustomCalibreRow = new ArrayList<Object>();
                singleCustomCalibreRow.add(customCalibreRow.getCode());
                singleCustomCalibreRow.add(customCalibreRow.getTitle());
                singleCustomCalibreRow.add(customCalibreRow.getValue().valueToClob());
                singleCustomCalibreRow.add(customCalibreRow.getValue().getValueType().value);
                singleCustomCalibreRow.add(customCalibreRow.getParentCode());
                list.add(singleCustomCalibreRow);
            }
            return this.exportWorkBook(list, this.createTitle(), batchGatherExportParam);
        }
        return this.exportWorkBook(null, this.createTitle(), batchGatherExportParam);
    }

    private SXSSFWorkbook exportWorkBook(List<List<Object>> list, String[] title, BatchGatherExportParam batchGatherExportParam) {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        String CustomCategoryGatherCondition = this.i18nHelper.getMessage("Custom-Category-Gather-Condition").equals("") ? "\u81ea\u5b9a\u4e49\u5206\u7c7b\u6c47\u603b\u6761\u4ef6" : this.i18nHelper.getMessage("Custom-Category-Gather-Condition");
        SXSSFSheet sheet = workbook.createSheet(CustomCategoryGatherCondition);
        block20: for (int i = 0; i < title.length; ++i) {
            switch (title[i]) {
                case "\u5e8f\u53f7": {
                    sheet.setColumnWidth(i, 2048);
                    continue block20;
                }
                case "\u53e3\u5f84\u4ee3\u7801": {
                    sheet.setColumnWidth(i, 2560);
                    continue block20;
                }
                case "\u53e3\u5f84\u540d\u79f0": {
                    sheet.setColumnWidth(i, 6400);
                    continue block20;
                }
                case "\u6279\u91cf\u6c47\u603b\u65b9\u6848": {
                    sheet.setColumnWidth(i, 6400);
                    continue block20;
                }
                case "\u8868\u8fbe\u5f0f\u503c/\u5355\u4f4d\u5217\u8868": {
                    sheet.setColumnWidth(i, 19200);
                    continue block20;
                }
                case "\u53e3\u5f84\u7c7b\u578b": {
                    sheet.setColumnWidth(i, 2560);
                    continue block20;
                }
                case "\u4e0a\u7ea7\u4ee3\u7801": {
                    sheet.setColumnWidth(i, 2560);
                    continue block20;
                }
                case "\u53e3\u5f84\u6392\u5e8f\u5b57\u6bb5": {
                    sheet.setColumnWidth(i, 6400);
                    continue block20;
                }
                default: {
                    sheet.setColumnWidth(i, 19200);
                }
            }
        }
        int titleIndex = 0;
        SXSSFRow row = sheet.createRow(titleIndex);
        CellStyle style = workbook.createCellStyle();
        style = this.createCellStyle(style);
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("\u5b8b\u4f53");
        font.setBold(true);
        style.setFont(font);
        for (int i = 0; i < title.length; ++i) {
            SXSSFCell cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        CellStyle style2 = workbook.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); ++i) {
                row = sheet.createRow(i + titleIndex + 1);
                List<Object> singleRow = list.get(i);
                for (int j = 0; j < singleRow.size(); ++j) {
                    String value = singleRow.get(j) != null ? singleRow.get(j).toString() : null;
                    SXSSFCell cell = row.createCell(j);
                    cell.setCellValue(value);
                    if (j == 0) {
                        cell.setCellStyle(style2);
                        continue;
                    }
                    cell.setCellStyle(style);
                }
            }
        }
        return workbook;
    }

    private CellStyle createCellStyle(CellStyle style) {
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_80_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        return style;
    }

    @Override
    public List<CustomCalibreRow> CustomCalibreImport(Sheet sheet, SummaryScheme scheme) {
        int i;
        int calibreTypeDataBase = 0;
        ArrayList<CustomCalibreRow> list = new ArrayList<CustomCalibreRow>();
        String[] titles = this.createTitle();
        Row headerRow = sheet.getRow(0);
        int headerRowlength = headerRow.getLastCellNum();
        for (i = 0; i < headerRowlength; ++i) {
            if (headerRow.getCell(i).getStringCellValue().equals(titles[i])) continue;
            return null;
        }
        for (i = 1; i <= sheet.getLastRowNum(); ++i) {
            CustomCalibreRowDefine customCalibreRowDefine = new CustomCalibreRowDefine();
            CustomCalibreValue customCalibreValue = new CustomCalibreValue();
            Row row = sheet.getRow(i);
            String calibreCode = this.getCellValue(row.getCell(0));
            String calibreTitle = this.getCellValue(row.getCell(1));
            String expressionOrUnitList = this.getCellValue(row.getCell(2));
            String calibreType = this.getCellValue(row.getCell(3));
            if (calibreType == "") {
                calibreTypeDataBase = 3;
            } else {
                BigDecimal bdCalibreType = new BigDecimal(calibreType);
                calibreTypeDataBase = bdCalibreType.intValue();
            }
            String parentCode = this.getCellValue(row.getCell(4));
            String CalibreOrdinal = System.currentTimeMillis() + "";
            String SerialNumber = UUID.randomUUID().toString();
            customCalibreRowDefine.setKey(SerialNumber);
            customCalibreRowDefine.setCode(calibreCode);
            customCalibreRowDefine.setTitle(calibreTitle);
            customCalibreRowDefine.setScheme(scheme.getKey());
            customCalibreRowDefine.setParentCode(parentCode);
            customCalibreRowDefine.setOrdinal(CalibreOrdinal);
            ConditionValueType conditionValueType = ConditionValueType.valueOf((Integer)calibreTypeDataBase);
            customCalibreValue.setValueType(conditionValueType);
            if (conditionValueType == ConditionValueType.EXPRESSION) {
                customCalibreValue.setExpression(expressionOrUnitList);
            } else if (conditionValueType == ConditionValueType.UNITS) {
                List checkList = BatchSummaryUtils.toJavaArray((String)expressionOrUnitList, String.class);
                customCalibreValue.setCheckList(checkList);
            } else {
                customCalibreValue.setExpression(expressionOrUnitList);
            }
            customCalibreRowDefine.setValue(customCalibreValue);
            list.add((CustomCalibreRow)customCalibreRowDefine);
        }
        return list;
    }

    public boolean hasLoop(List<CustomCalibreRow> CustomCalibreRows) {
        for (CustomCalibreRow customCalibreRow : CustomCalibreRows) {
            if (!this.isInLoop(customCalibreRow, CustomCalibreRows)) continue;
            return true;
        }
        return false;
    }

    public boolean isInLoop(CustomCalibreRow currentCustomCalibreRow, List<CustomCalibreRow> customCalibreRows) {
        String parentCode = currentCustomCalibreRow.getParentCode();
        if (parentCode == null || parentCode.isEmpty()) {
            return false;
        }
        for (CustomCalibreRow customCalibreRow : customCalibreRows) {
            if (!customCalibreRow.getCode().equals(parentCode)) continue;
            if (currentCustomCalibreRow.getCode().equals(customCalibreRow.getParentCode())) {
                return true;
            }
            return this.isInLoop(customCalibreRow, customCalibreRows);
        }
        return false;
    }

    public String judgeCellValueTypeAndGetvalue(Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            long longValue;
            double numericValue = cell.getNumericCellValue();
            if (numericValue == (double)(longValue = (long)numericValue)) {
                return String.valueOf(longValue);
            }
            String strValue = String.valueOf(numericValue);
            if (strValue.endsWith(".0")) {
                return strValue.substring(0, strValue.length() - 2);
            }
            return strValue;
        }
        return "";
    }

    public String getCellValue(Cell cell) {
        return cell == null ? "" : this.judgeCellValueTypeAndGetvalue(cell);
    }
}

