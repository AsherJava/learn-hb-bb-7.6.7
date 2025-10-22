/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.graphics.Point
 *  com.jiuqi.np.office.excel2.steam.xlsx.impl.StreamingSheet
 *  com.jiuqi.np.office.excel2.steam.xlsx.impl.StreamingWorkbook
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.ImportResultExcelFileObject
 *  com.jiuqi.nr.common.importdata.ImportResultRegionObject
 *  com.jiuqi.nr.common.importdata.ImportResultReportObject
 *  com.jiuqi.nr.common.importdata.ImportResultSheetObject
 */
package com.jiuqi.nr.data.excel.utils;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.graphics.Point;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.StreamingSheet;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.StreamingWorkbook;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.ImportResultExcelFileObject;
import com.jiuqi.nr.common.importdata.ImportResultRegionObject;
import com.jiuqi.nr.common.importdata.ImportResultReportObject;
import com.jiuqi.nr.common.importdata.ImportResultSheetObject;
import com.jiuqi.nr.data.excel.param.bean.ExcelImportResultItem;
import com.jiuqi.nr.data.excel.param.bean.ImportResultItem;
import com.jiuqi.nr.data.excel.param.bean.ImportResultObject;
import com.jiuqi.nr.data.excel.param.bean.NodeCheckFieldMessage;
import com.jiuqi.nr.data.excel.param.bean.NodeCheckResultInfo;
import com.jiuqi.nr.data.excel.param.bean.NodeCheckResultItem;
import com.jiuqi.nr.data.excel.utils.ExcelImportUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelErrorUtil {
    private static final String KEY_SHEET = "sheet";
    private static final String KEY_ROW = "rowIndex";
    private static final String KEY_COUNT = "sheetCount";

    public static Workbook exportExcel(ImportResultObject res, ImportResultExcelFileObject tempRes, Workbook wb) {
        return ExcelErrorUtil.exportExcel(res, tempRes, wb, "(\u9519\u8bef\u4fe1\u606f\u6620\u5c04\u8868)");
    }

    public static Workbook exportExcel(ImportResultObject res, ImportResultExcelFileObject tempRes, Workbook wb, String sheetName) {
        Workbook workbookNew = ExcelErrorUtil.checkIs07(wb) ? new SXSSFWorkbook(500) : new HSSFWorkbook();
        CellStyle style = workbookNew.createCellStyle();
        style = ExcelErrorUtil.createCellStyle(style);
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbookNew.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("\u5b8b\u4f53");
        font.setBold(true);
        style.setFont(font);
        Integer sheetIs = 0;
        Integer index = 1;
        Map<String, Object> resMap = ExcelErrorUtil.getSheet(null, workbookNew, sheetIs, style, index, wb, sheetName);
        Sheet sheet = (Sheet)resMap.get(KEY_SHEET);
        sheetIs = (Integer)resMap.get(KEY_COUNT);
        index = (Integer)resMap.get(KEY_ROW);
        List<ImportResultItem> fails = res.getFails();
        res.setFails(fails);
        CellStyle styleNumber = workbookNew.createCellStyle();
        styleNumber.setAlignment(HorizontalAlignment.CENTER);
        if (null != tempRes.getFileError().getErrorCode()) {
            ExcelImportResultItem one = new ExcelImportResultItem(null, null, tempRes.getFileName(), tempRes.getFileError().getErrorInfo(), tempRes.getFileError().getErrorCode().getErrorCodeMsg());
            fails.add(one);
            resMap = ExcelErrorUtil.getSheet(sheet, workbookNew, sheetIs, style, index, wb, sheetName);
            sheet = (Sheet)resMap.get(KEY_SHEET);
            sheetIs = (Integer)resMap.get(KEY_COUNT);
            ExcelErrorUtil.createRow(null, workbookNew, styleNumber, index, one, sheet, null, null, null, null, wb);
        } else {
            CellStyle errorStyle = workbookNew.createCellStyle();
            errorStyle = ExcelErrorUtil.createErrorCellStyle(errorStyle);
            CreationHelper helper = workbookNew.getCreationHelper();
            CellStyle linkStyle = workbookNew.createCellStyle();
            Font cellFont = workbookNew.createFont();
            cellFont.setUnderline((byte)1);
            cellFont.setColor(IndexedColors.BLUE.index);
            linkStyle.setFont(cellFont);
            List sheetList = tempRes.getImportResultSheetObjectList();
            for (ImportResultSheetObject importResultSheetObject : sheetList) {
                Object object;
                if (null != importResultSheetObject.getSheetError().getErrorCode()) {
                    ExcelImportResultItem one = new ExcelImportResultItem(null, importResultSheetObject.getSheetName(), tempRes.getFileName(), importResultSheetObject.getSheetError().getErrorInfo(), importResultSheetObject.getSheetError().getErrorCode().getErrorCodeMsg());
                    fails.add(one);
                    resMap = ExcelErrorUtil.getSheet(sheet, workbookNew, sheetIs, style, index, wb, sheetName);
                    sheet = (Sheet)resMap.get(KEY_SHEET);
                    sheetIs = (Integer)resMap.get(KEY_COUNT);
                    index = (Integer)resMap.get(KEY_ROW);
                    ExcelErrorUtil.createRow(errorStyle, workbookNew, styleNumber, index, one, sheet, null, importResultSheetObject, null, null, wb);
                    Integer n = index;
                    index = index + 1;
                    object = index;
                    continue;
                }
                ImportResultReportObject importResultReportObject = importResultSheetObject.getImportResultReportObject();
                if (null == importResultReportObject) continue;
                if (null != importResultReportObject.getReportError().getErrorCode()) {
                    ExcelImportResultItem one = new ExcelImportResultItem(importResultReportObject.getReportName(), importResultSheetObject.getSheetName(), tempRes.getFileName(), importResultReportObject.getReportError().getErrorInfo(), importResultReportObject.getReportError().getErrorCode().getErrorCodeMsg());
                    fails.add(one);
                    resMap = ExcelErrorUtil.getSheet(sheet, workbookNew, sheetIs, style, index, wb, sheetName);
                    sheet = (Sheet)resMap.get(KEY_SHEET);
                    sheetIs = (Integer)resMap.get(KEY_COUNT);
                    index = (Integer)resMap.get(KEY_ROW);
                    ExcelErrorUtil.createRow(errorStyle, workbookNew, styleNumber, index, one, sheet, null, importResultSheetObject, null, null, wb);
                    object = index;
                    Integer n = index = Integer.valueOf(index + 1);
                    continue;
                }
                List regins = importResultReportObject.getImportResultRegionObjectList();
                for (ImportResultRegionObject importResultRegionObject : regins) {
                    if (null != importResultRegionObject.getRegionError().getErrorCode()) {
                        ExcelImportResultItem one = new ExcelImportResultItem(importResultReportObject.getReportName(), importResultSheetObject.getSheetName(), tempRes.getFileName(), importResultRegionObject.getRegionError().getErrorInfo(), importResultRegionObject.getRegionError().getErrorCode().getErrorCodeMsg());
                        fails.add(one);
                        resMap = ExcelErrorUtil.getSheet(sheet, workbookNew, sheetIs, style, index, wb, sheetName);
                        sheet = (Sheet)resMap.get(KEY_SHEET);
                        sheetIs = (Integer)resMap.get(KEY_COUNT);
                        index = (Integer)resMap.get(KEY_ROW);
                        ExcelErrorUtil.createRow(errorStyle, workbookNew, styleNumber, index, one, sheet, null, importResultSheetObject, null, null, wb);
                        Integer n = index;
                        Integer n2 = index = Integer.valueOf(index + 1);
                        continue;
                    }
                    List datas = importResultRegionObject.getImportErrorDataInfoList();
                    for (ImportErrorDataInfo importErrorDataInfo : datas) {
                        ExcelImportResultItem one = new ExcelImportResultItem(importResultReportObject.getReportName(), importResultSheetObject.getSheetName(), tempRes.getFileName(), importErrorDataInfo.getDataError().getErrorInfo(), importErrorDataInfo.getDataError().getErrorCode().getErrorCodeMsg());
                        fails.add(one);
                        resMap = ExcelErrorUtil.getSheet(sheet, workbookNew, sheetIs, style, index, wb, sheetName);
                        sheet = (Sheet)resMap.get(KEY_SHEET);
                        sheetIs = (Integer)resMap.get(KEY_COUNT);
                        index = (Integer)resMap.get(KEY_ROW);
                        ExcelErrorUtil.createRow(errorStyle, workbookNew, styleNumber, index, one, sheet, importErrorDataInfo, importResultSheetObject, helper, linkStyle, wb);
                        Integer n = index;
                        Integer n3 = index = Integer.valueOf(index + 1);
                    }
                }
            }
        }
        return workbookNew;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Map<String, Object> getSheet(Sheet sheet, Workbook wb, Integer sheetCount, CellStyle style, Integer index, Workbook oldWorkBook, String sheetName) {
        Integer n;
        Integer n2;
        int lastRow;
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        if (null == sheet) {
            sheet = wb.createSheet(sheetName);
            wb.setSheetOrder(sheetName, sheetCount);
            resMap.put(KEY_SHEET, sheet);
            resMap.put(KEY_COUNT, sheetCount);
            resMap.put(KEY_ROW, 1);
        } else if (wb instanceof HSSFWorkbook) {
            lastRow = sheet.getLastRowNum();
            if (lastRow <= 40000) {
                resMap.put(KEY_SHEET, sheet);
                resMap.put(KEY_COUNT, sheetCount);
                resMap.put(KEY_ROW, index);
                return resMap;
            }
            n2 = sheetCount;
            n = sheetCount = Integer.valueOf(sheetCount + 1);
            sheet = wb.createSheet(sheetName + sheetCount);
            wb.setSheetOrder(sheetName + sheetCount, sheetCount);
            resMap.put(KEY_ROW, 1);
        } else {
            lastRow = sheet.getLastRowNum();
            if (lastRow <= 100000) {
                resMap.put(KEY_SHEET, sheet);
                resMap.put(KEY_COUNT, sheetCount);
                resMap.put(KEY_ROW, index);
                return resMap;
            }
            n2 = sheetCount;
            n = sheetCount = Integer.valueOf(sheetCount + 1);
            sheet = wb.createSheet(sheetName + sheetCount);
            wb.setSheetOrder(sheetName + sheetCount, sheetCount);
            resMap.put(KEY_ROW, 1);
        }
        String[] title = new String[]{"\u5e8f\u53f7", "(\u9519\u8bef\u4fe1\u606f\u6620\u5c04\u8868)".equals(sheetName) ? "\u9519\u8bef\u4fe1\u606f" : "\u660e\u7ec6\u4fe1\u606f", "sheet\u9875", "\u62a5\u8868\u540d\u79f0"};
        sheet.setColumnWidth(0, 2048);
        sheet.setColumnWidth(1, 12800);
        sheet.setColumnWidth(2, 6400);
        sheet.setColumnWidth(3, 6400);
        Row row = sheet.createRow(0);
        Cell cell = null;
        for (int i = 0; i < title.length; ++i) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        resMap.put(KEY_SHEET, sheet);
        resMap.put(KEY_COUNT, sheetCount);
        if (oldWorkBook instanceof StreamingWorkbook) {
            Drawing<?> draw = sheet.createDrawingPatriarch();
            Comment comment = draw.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, 4, 1, 9, 5));
            XSSFRichTextString rtf = new XSSFRichTextString("\u6ce8\u91ca\uff1a\u56e0\u4e3a\u4e0a\u4f20\u6587\u4ef6\u8fc7\u5927\uff0c\u4e0b\u8f7d\u7684\u5931\u8d25excel\u6587\u4ef6\uff0c\u5c06\u9664\u53bb\u4e00\u4e9b\u4e0d\u5fc5\u8981\u7684\u6837\u5f0f\u4fe1\u606f\u3002");
            comment.setString(rtf);
            comment.setAuthor("\u7cfb\u7edf");
            comment.setVisible(true);
            row.getCell(0).setCellComment(comment);
        }
        return resMap;
    }

    private static CellStyle createCellStyle(CellStyle style) {
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

    private static CellStyle createErrorCellStyle(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        return style;
    }

    public static String index2ColName(int index) {
        int remainder;
        if (--index < 0) {
            return null;
        }
        int num = 65;
        String colName = "";
        do {
            if (colName.length() > 0) {
                --index;
            }
            remainder = index % 26;
            colName = (char)(remainder + num) + colName;
        } while ((index = (index - remainder) / 26) > 0);
        return colName;
    }

    private static void createRow(CellStyle errorStyle, Workbook newWorkBook, CellStyle style, int index, ExcelImportResultItem one, Sheet sheet, ImportErrorDataInfo dataInfo, ImportResultSheetObject sheetInfo, CreationHelper helper, CellStyle linkStyle, Workbook oldWorkBook) {
        Row row = sheet.createRow(index);
        Cell cell = row.createCell(0);
        cell.setCellValue(index);
        cell.setCellStyle(style);
        cell = row.createCell(1);
        if (null == dataInfo) {
            Sheet errorSheet;
            cell.setCellValue(one.getErrorCode() + " " + one.getErrorInfo());
            if (null != sheetInfo && !StringUtils.isEmpty((String)sheetInfo.getSheetName()) && null == (errorSheet = newWorkBook.getSheet(sheetInfo.getSheetName()))) {
                errorSheet = newWorkBook.createSheet(sheetInfo.getSheetName());
                ExcelErrorUtil.copySheets(errorSheet, oldWorkBook.getSheet(sheetInfo.getSheetName()));
            }
        } else {
            cell.setCellValue(dataInfo.getDataError().getErrorInfo());
            Point point = dataInfo.getExcelLocation();
            if (null != sheetInfo && !StringUtils.isEmpty((String)sheetInfo.getSheetName())) {
                Sheet errorSheet = newWorkBook.getSheet(sheetInfo.getSheetName());
                if (null == errorSheet) {
                    errorSheet = newWorkBook.createSheet(sheetInfo.getSheetName());
                    ExcelErrorUtil.copySheets(errorSheet, oldWorkBook.getSheet(sheetInfo.getSheetName()));
                }
                if (null != point) {
                    Row errorRow;
                    Hyperlink hyperlink = helper.createHyperlink(HyperlinkType.DOCUMENT);
                    int col = point.x;
                    String temp = "#'" + sheetInfo.getSheetName() + "'!" + ExcelErrorUtil.index2ColName(col) + point.y;
                    hyperlink.setAddress(temp);
                    cell.setHyperlink(hyperlink);
                    cell.setCellStyle(linkStyle);
                    if (null != errorSheet && null != (errorRow = errorSheet.getRow(point.y - 1))) {
                        Cell errorCell = errorRow.getCell(col - 1);
                        if (errorCell == null) {
                            errorCell = errorRow.createCell(col - 1);
                        }
                        errorCell.setCellStyle(errorStyle);
                    }
                }
            }
        }
        cell = row.createCell(2);
        if (null != sheetInfo) {
            cell.setCellValue(sheetInfo.getSheetName());
        } else {
            cell.setCellValue("");
        }
        cell = row.createCell(3);
        if (null != sheetInfo) {
            ImportResultReportObject report = sheetInfo.getImportResultReportObject();
            if (null != report) {
                cell.setCellValue(report.getReportName());
            } else {
                cell.setCellValue("");
            }
        } else {
            cell.setCellValue("");
        }
    }

    private static boolean checkIs07(Workbook workbook) {
        return !(workbook instanceof HSSFWorkbook);
    }

    private static void copySheets(Sheet newSheet, Sheet sheet) {
        short maxColumnNum = 0;
        HashMap<Integer, CellStyle> styleMap = new HashMap<Integer, CellStyle>();
        int i = 0;
        for (Object srcRow : sheet) {
            Row destRow = newSheet.createRow(i);
            if (srcRow != null) {
                ExcelErrorUtil.copyRow(sheet, newSheet, (Row)srcRow, destRow, styleMap);
            }
            ++i;
            if (sheet instanceof StreamingSheet || null == srcRow || srcRow.getLastCellNum() <= maxColumnNum) continue;
            maxColumnNum = srcRow.getLastCellNum();
        }
        List<CellRangeAddress> list = ExcelImportUtil.getRangeAddress(sheet);
        if (list != null && list.size() > 0) {
            for (CellRangeAddress mergedRegion : list) {
                CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow(), mergedRegion.getLastRow(), mergedRegion.getFirstColumn(), mergedRegion.getLastColumn());
                try {
                    newSheet.addMergedRegion(newMergedRegion);
                }
                catch (Exception exception) {}
            }
        }
        if (!(sheet instanceof StreamingSheet)) {
            for (short w = 0; w <= maxColumnNum; ++w) {
                newSheet.setColumnWidth(w, sheet.getColumnWidth(w));
            }
        }
    }

    private static void copyRow(Sheet srcSheet, Sheet destSheet, Row srcRow, Row destRow, Map<Integer, CellStyle> styleMap) {
        if (!(srcSheet instanceof StreamingSheet)) {
            destRow.setHeight(srcRow.getHeight());
        }
        for (int j = srcRow.getFirstCellNum(); j < srcRow.getLastCellNum(); ++j) {
            if (j < 0) continue;
            Cell oldCell = srcRow.getCell(j);
            Cell newCell = destRow.getCell(j);
            if (oldCell == null) continue;
            if (newCell == null) {
                newCell = destRow.createCell(j);
            }
            ExcelErrorUtil.copyCell(oldCell, newCell, styleMap);
        }
    }

    private static void copyCell(Cell oldCell, Cell newCell, Map<Integer, CellStyle> styleMap) {
        if (styleMap != null) {
            CellStyle oldCellStyle = oldCell.getCellStyle();
            if (oldCellStyle instanceof XSSFCellStyle) {
                XSSFCellStyle oldOne = (XSSFCellStyle)oldCellStyle;
                int stHashCode = oldOne.hashCode();
                XSSFCellStyle newCellStyle = (XSSFCellStyle)styleMap.get(stHashCode);
                if (newCellStyle == null) {
                    String dataFormatString;
                    DataFormat format = newCell.getSheet().getWorkbook().createDataFormat();
                    newCellStyle = (XSSFCellStyle)newCell.getSheet().getWorkbook().createCellStyle();
                    if (FillPatternType.NO_FILL != oldOne.getFillPattern()) {
                        newCellStyle.setFillPattern(oldOne.getFillPattern());
                        newCellStyle.setFillForegroundColor(oldOne.getFillForegroundColorColor());
                    }
                    if (null != (dataFormatString = oldOne.getDataFormatString()) && !"".equals(dataFormatString)) {
                        newCellStyle.setDataFormat(format.getFormat(dataFormatString));
                    }
                    newCellStyle.setBorderRight(oldOne.getBorderRight());
                    newCellStyle.setBorderBottom(oldOne.getBorderBottom());
                    styleMap.put(stHashCode, newCellStyle);
                }
                newCell.setCellStyle(newCellStyle);
            } else {
                HSSFCellStyle oldOne = (HSSFCellStyle)oldCellStyle;
                int stHashCode = oldOne.hashCode();
                HSSFCellStyle newCellStyle = (HSSFCellStyle)styleMap.get(stHashCode);
                if (newCellStyle == null) {
                    newCellStyle = (HSSFCellStyle)newCell.getSheet().getWorkbook().createCellStyle();
                    newCellStyle.cloneStyleFrom(oldOne);
                    if (FillPatternType.NO_FILL != oldOne.getFillPattern()) {
                        newCellStyle.setFillPattern(oldOne.getFillPattern());
                        newCellStyle.setFillForegroundColor(oldOne.getFillForegroundColorColor().getIndex());
                    }
                    newCellStyle.setBorderRight(oldOne.getBorderRight());
                    newCellStyle.setBorderBottom(oldOne.getBorderBottom());
                    newCellStyle.setBottomBorderColor(oldOne.getBottomBorderColor());
                    newCellStyle.setRightBorderColor(oldOne.getRightBorderColor());
                    styleMap.put(stHashCode, newCellStyle);
                }
                newCell.setCellStyle(newCellStyle);
            }
        }
        if (oldCell.getCellType() == CellType.STRING) {
            newCell.setCellValue(oldCell.getStringCellValue());
        } else if (oldCell.getCellType() == CellType.NUMERIC) {
            newCell.setCellValue(oldCell.getNumericCellValue());
        } else if (oldCell.getCellType() == CellType.BLANK) {
            newCell.setCellType(CellType.BLANK);
        } else if (oldCell.getCellType() == CellType.BOOLEAN) {
            newCell.setCellValue(oldCell.getBooleanCellValue());
        } else if (oldCell.getCellType() == CellType.ERROR) {
            newCell.setCellErrorValue(oldCell.getErrorCellValue());
        } else if (oldCell.getCellType() == CellType.FORMULA) {
            newCell.setCellFormula(oldCell.getCellFormula());
        }
    }

    public static void createMapper(SXSSFWorkbook workbook, Map<String, String> map) {
        SXSSFSheet mapperSheet = workbook.createSheet("(\u9875\u540d\u6620\u5c04\u8868)");
        SXSSFRow rowTitle = mapperSheet.getRow(0);
        if (rowTitle == null) {
            rowTitle = mapperSheet.createRow(0);
        }
        block10: for (int i = 0; i < 3; ++i) {
            Cell cell = rowTitle.getCell((short)i);
            if (cell == null) {
                cell = rowTitle.createCell(i);
            }
            switch (i) {
                case 0: {
                    cell.setCellValue("\u5e8f\u53f7");
                    continue block10;
                }
                case 1: {
                    cell.setCellValue("sheet\u9875\u5e8f\u5217");
                    continue block10;
                }
                case 2: {
                    cell.setCellValue("\u5bf9\u5e94\u5168\u540d");
                }
            }
        }
        mapperSheet.setColumnWidth(0, 2500);
        mapperSheet.setColumnWidth(1, 15000);
        mapperSheet.setColumnWidth(2, 17000);
        int rowCount = 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            SXSSFRow rowMapper = mapperSheet.getRow(rowCount);
            if (rowMapper == null) {
                rowMapper = mapperSheet.createRow(rowCount);
            }
            block12: for (int i = 0; i < 3; ++i) {
                Cell cell = rowMapper.getCell((short)i);
                if (cell == null) {
                    cell = rowMapper.createCell(i);
                }
                switch (i) {
                    case 0: {
                        cell.setCellValue(rowCount);
                        continue block12;
                    }
                    case 1: {
                        cell.setCellValue(entry.getKey());
                        continue block12;
                    }
                    case 2: {
                        cell.setCellValue(entry.getValue());
                    }
                }
            }
            ++rowCount;
        }
    }

    public static Workbook nodeCheckExport(NodeCheckResultInfo nodeCheckResultInfo) {
        ArrayList<List<String>> list = new ArrayList<List<String>>();
        Map<String, List<NodeCheckResultItem>> nodeCheckResult = nodeCheckResultInfo.getNodeCheckResult();
        if (null != nodeCheckResult && nodeCheckResult.size() > 0) {
            int index = 1;
            Set<Map.Entry<String, List<NodeCheckResultItem>>> entrySet = nodeCheckResult.entrySet();
            for (Map.Entry<String, List<NodeCheckResultItem>> entry : entrySet) {
                List<NodeCheckResultItem> values = entry.getValue();
                for (NodeCheckResultItem nodeCheckResultItem : values) {
                    ArrayList<String> oneCheck = new ArrayList<String>();
                    oneCheck.add(index + "");
                    oneCheck.add(entry.getKey());
                    oneCheck.add(nodeCheckResultItem.getParentValue() == null ? "" : nodeCheckResultItem.getParentValue().toPlainString());
                    oneCheck.add(nodeCheckResultItem.getChildValue() == null ? "" : nodeCheckResultItem.getChildValue().toPlainString());
                    oneCheck.add(nodeCheckResultItem.getMinusValue() == null ? "" : nodeCheckResultItem.getMinusValue().toPlainString());
                    NodeCheckFieldMessage nodeCheckFieldMessage = nodeCheckResultItem.getNodeCheckFieldMessage();
                    if (null != nodeCheckFieldMessage) {
                        oneCheck.add(nodeCheckFieldMessage.getFormTitle());
                    } else {
                        oneCheck.add("");
                    }
                    oneCheck.add(nodeCheckResultItem.getFieldTitle());
                    ++index;
                    list.add(oneCheck);
                }
            }
        }
        return ExcelErrorUtil.nodeCheckExport2007(list, ExcelErrorUtil.nodeCheckCreateTitle());
    }

    private static String[] nodeCheckCreateTitle() {
        String[] titles = new String[]{"\u5e8f\u53f7", "\u76ee\u6807\u5355\u4f4d", "\u76ee\u6807\u5355\u4f4d\u6570\u636e", "\u4e0b\u7ea7\u6c47\u603b\u6570\u636e", "\u5dee\u989d", "\u6240\u5728\u62a5\u8868", "\u6307\u6807\u6807\u9898"};
        return titles;
    }

    private static XSSFWorkbook nodeCheckExport2007(List<List<String>> list, String[] title) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("\u8282\u70b9\u68c0\u67e5\u7ed3\u679c");
        sheet.setColumnWidth(0, 2048);
        sheet.setColumnWidth(1, 6400);
        sheet.setColumnWidth(2, 6400);
        sheet.setColumnWidth(3, 12800);
        sheet.setColumnWidth(4, 6400);
        sheet.setColumnWidth(5, 6400);
        sheet.setColumnWidth(6, 6400);
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle style = wb.createCellStyle();
        style = ExcelErrorUtil.nodeCheckCreateCellStyle(style);
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
        return wb;
    }

    private static XSSFCellStyle nodeCheckCreateCellStyle(XSSFCellStyle style) {
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
}

