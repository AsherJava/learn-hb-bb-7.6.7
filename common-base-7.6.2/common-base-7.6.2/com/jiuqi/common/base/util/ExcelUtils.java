/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.DataFormat
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.IndexedColors
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 */
package com.jiuqi.common.base.util;

import java.beans.PropertyDescriptor;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.ReflectionUtils;

public class ExcelUtils {
    public static String KEY_AUTO_INCREMENT_NUM = "__AUTO_INCREMENT";
    public static String KEY_PARENT_BEGIN = "__";
    public static String KEY_NOVALUE_CLOUMN = "__NO_";

    private static Map<ExportColumnTypeEnum, CellStyle> getColumnStyle(Workbook workbook) {
        HashMap<ExportColumnTypeEnum, CellStyle> resultMap = new HashMap<ExportColumnTypeEnum, CellStyle>();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)10);
        font.setFontName("\u5b8b\u4f53");
        for (ExportColumnTypeEnum tempType : ExportColumnTypeEnum.values()) {
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            style.setFont(font);
            resultMap.put(tempType, style);
        }
        return resultMap;
    }

    private static <T> void setColumnValue(T item, String fieldName, Row row, int column, ExportColumnTypeEnum type, Map<ExportColumnTypeEnum, CellStyle> styleMap) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(fieldName, item.getClass());
        Method getMethod = pd.getReadMethod();
        Object obj = getMethod.invoke(item, new Object[0]);
        if (obj != null) {
            Cell cell = row.createCell(column);
            cell.setCellStyle(styleMap.get((Object)type));
            if (type != null) {
                DataFormat format;
                cell.setCellType(type.getCode());
                if (ExportColumnTypeEnum.BOOLEAN == type) {
                    cell.setCellValue(Boolean.parseBoolean(String.valueOf(obj)));
                }
                if (ExportColumnTypeEnum.NUMERIC == type) {
                    cell.setCellValue(Double.parseDouble(String.valueOf(obj)));
                }
                if (ExportColumnTypeEnum.FORMAT_NUMBER == type) {
                    format = row.getSheet().getWorkbook().createDataFormat();
                    cell.getCellStyle().setDataFormat(format.getFormat(type.getFormatStr()));
                    cell.setCellValue(Double.parseDouble(String.valueOf(obj)));
                }
                if (ExportColumnTypeEnum.STRING == type) {
                    cell.setCellValue(String.valueOf(obj));
                }
                if (ExportColumnTypeEnum.PERCENT == type) {
                    cell.getCellStyle().setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"0.00%"));
                    cell.setCellValue(Double.parseDouble(String.valueOf(obj)));
                }
                if (ExportColumnTypeEnum.MONEY == type) {
                    cell.getCellStyle().setDataFormat((short)4);
                    cell.setCellValue(Double.parseDouble(String.valueOf(obj)));
                }
                if (ExportColumnTypeEnum.DATE == type) {
                    format = row.getSheet().getWorkbook().createDataFormat();
                    cell.getCellStyle().setDataFormat(format.getFormat("yyyy-MM-dd"));
                    cell.setCellValue((Date)obj);
                }
                if (ExportColumnTypeEnum.DATETIME == type) {
                    format = row.getSheet().getWorkbook().createDataFormat();
                    cell.getCellStyle().setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));
                    cell.setCellValue((Date)obj);
                }
            } else {
                cell.setCellType(ExportColumnTypeEnum.STRING.getCode());
                cell.setCellValue(String.valueOf(obj));
            }
        }
    }

    private static CellStyle getTitleStyle(Workbook workbook) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font fontTitle = workbook.createFont();
        fontTitle.setFontHeightInPoints((short)10);
        fontTitle.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        fontTitle.setFontName("\u5b8b\u4f53");
        fontTitle.setBold(true);
        titleStyle.setFont(fontTitle);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setWrapText(true);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        return titleStyle;
    }

    public static <T> void createExcel(HttpServletResponse response, String fileName, Map<String, String> sheetMap, List<List<ExportColumnVO>> titleList, List<T> dataList) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Map<ExportColumnTypeEnum, CellStyle> styleMap = ExcelUtils.getColumnStyle((Workbook)workbook);
        int index = 0;
        for (String sheetName : sheetMap.keySet()) {
            ExcelUtils.createSheet((Workbook)workbook, sheetName, titleList.get(index), dataList, sheetMap.get(sheetName), styleMap);
            ++index;
        }
        response.setContentType("application/octet-stream");
        fileName = URLEncoder.encode(fileName + ".xls", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.flushBuffer();
        workbook.write((OutputStream)response.getOutputStream());
    }

    private static <T> void createSheet(Workbook workbook, String sheetName, List<ExportColumnVO> titleList, List<T> dataList, String propName, Map<ExportColumnTypeEnum, CellStyle> styleMap) throws Exception {
        Sheet sheet = workbook.createSheet(sheetName);
        AtomicInteger rownum = new AtomicInteger(0);
        Map<String, ExportColumnTypeEnum> columnFiledMap = ExcelUtils.createTitle(workbook, sheet, titleList, rownum);
        AtomicInteger index = new AtomicInteger(1);
        for (T item : dataList) {
            if (propName == null) {
                Row row = sheet.createRow(rownum.get());
                int column = 0;
                Set<String> keySet = columnFiledMap.keySet();
                for (String field : keySet) {
                    if (KEY_AUTO_INCREMENT_NUM.equals(field)) {
                        ExcelUtils.createCell(row, column, String.valueOf(rownum.get()));
                    } else {
                        ExcelUtils.setColumnValue(item, field, row, column, columnFiledMap.get(field), styleMap);
                    }
                    ++column;
                }
                rownum.set(rownum.addAndGet(1));
                continue;
            }
            Field tmpField = ReflectionUtils.findField(item.getClass(), propName);
            tmpField.setAccessible(true);
            if (tmpField.get(item) == null) continue;
            if (tmpField.get(item) instanceof List) {
                ExcelUtils.setChildRowValue(sheet, tmpField, item, rownum, columnFiledMap, index, styleMap);
                continue;
            }
            Object tempObj = tmpField.get(item);
            LinkedHashMap<String, List<Object>> dataListMap = new LinkedHashMap<String, List<Object>>();
            for (String field : columnFiledMap.keySet()) {
                String[] tempFields;
                if (!field.contains(".") || dataListMap.containsKey((tempFields = field.split("\\."))[0])) continue;
                Field tempListField = ReflectionUtils.findField(tempObj.getClass(), tempFields[0]);
                tempListField.setAccessible(true);
                dataListMap.put(tempFields[0], (List)tempListField.get(tempObj));
            }
            ExcelUtils.setChildRowValue(sheet, item, rownum, columnFiledMap, index, dataListMap, styleMap);
        }
    }

    private static void createCell(Row row, int column, String value) {
        ExcelUtils.createCell(row, column, value, null);
    }

    private static void createCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    private static <T> void setChildRowValue(Sheet sheet, T item, AtomicInteger rownum, Map<String, ExportColumnTypeEnum> columnFiledMap, AtomicInteger index, Map<String, List<Object>> dataListMap, Map<ExportColumnTypeEnum, CellStyle> styleMap) throws Exception {
        int rowAddNum = 0;
        boolean addRowFlag = false;
        Integer tempIndex = null;
        boolean firstFlag = true;
        for (String tempStr : dataListMap.keySet()) {
            List<Object> tempList = dataListMap.get(tempStr);
            int tempRowNum = rownum.get();
            Integer columnNum = null;
            for (Object obj : tempList) {
                Row row;
                addRowFlag = true;
                if (tempList.size() > rowAddNum) {
                    rowAddNum = tempList.size();
                }
                if ((row = sheet.getRow(tempRowNum)) == null) {
                    row = sheet.createRow(tempRowNum);
                }
                Set<String> tempSet = columnFiledMap.keySet();
                columnNum = 0;
                if (tempIndex != null) {
                    columnNum = tempIndex;
                }
                for (String tempProp : tempSet) {
                    String[] tempFields;
                    Integer region;
                    if (firstFlag && KEY_AUTO_INCREMENT_NUM.equals(tempProp)) {
                        if (!ExcelUtils.isMergedRegionColumn(sheet, rownum.get(), columnNum)) {
                            region = new CellRangeAddress(rownum.get(), rownum.get() + tempList.size() - 1, columnNum.intValue(), columnNum.intValue());
                            rowAddNum = tempList.size() - 1;
                            sheet.addMergedRegion((CellRangeAddress)region);
                            ExcelUtils.createCell(row, columnNum, String.valueOf(index.get()));
                            index.set(index.addAndGet(1));
                        }
                        region = columnNum;
                        Integer n = columnNum = Integer.valueOf(columnNum + 1);
                        continue;
                    }
                    if (firstFlag && tempProp.startsWith(KEY_PARENT_BEGIN)) {
                        Object tempType;
                        if (!ExcelUtils.isMergedRegionColumn(sheet, rownum.get(), columnNum)) {
                            region = new CellRangeAddress(rownum.get(), rownum.get() + tempList.size() - 1, columnNum.intValue(), columnNum.intValue());
                            sheet.addMergedRegion((CellRangeAddress)region);
                            tempType = columnFiledMap.get(tempProp);
                            tempProp = tempProp.replaceFirst(KEY_PARENT_BEGIN, "");
                            ExcelUtils.setColumnValue(item, tempProp, row, columnNum, (ExportColumnTypeEnum)((Object)tempType), styleMap);
                        }
                        region = columnNum;
                        tempType = columnNum = Integer.valueOf(columnNum + 1);
                        continue;
                    }
                    ExportColumnTypeEnum tempType = columnFiledMap.get(tempProp);
                    if (!tempProp.contains(".") || !tempStr.equals((tempFields = tempProp.split("\\."))[0])) continue;
                    tempProp = tempFields[1];
                    columnNum = ExcelUtils.getColumn(sheet, rownum.get(), columnNum);
                    ExcelUtils.setColumnValue(obj, tempProp, row, columnNum, tempType, styleMap);
                    Integer n = columnNum;
                    Integer n2 = columnNum = Integer.valueOf(columnNum + 1);
                }
                firstFlag = false;
                ++tempRowNum;
            }
            if (tempIndex != null) continue;
            tempIndex = columnNum;
        }
        if (addRowFlag) {
            rownum.set(rownum.addAndGet(rowAddNum));
        }
    }

    private static <T> void setChildRowValue(Sheet sheet, Field tmpField, T item, AtomicInteger rownum, Map<String, ExportColumnTypeEnum> columnFiledMap, AtomicInteger index, Map<ExportColumnTypeEnum, CellStyle> styleMap) throws Exception {
        List tempList = (List)tmpField.get(item);
        if (tempList != null && tempList.size() > 0) {
            for (int i = 0; i < tempList.size(); ++i) {
                Row row = sheet.createRow(rownum.get());
                Object obj = tempList.get(i);
                int column = 0;
                for (String field : columnFiledMap.keySet()) {
                    CellRangeAddress region;
                    if (KEY_AUTO_INCREMENT_NUM.equals(field)) {
                        if (!ExcelUtils.isMergedRegionColumn(sheet, rownum.get(), column)) {
                            region = new CellRangeAddress(rownum.get(), rownum.get() + tempList.size() - 1, column, column);
                            sheet.addMergedRegion(region);
                            ExcelUtils.createCell(row, column, String.valueOf(index.get()));
                            index.set(index.addAndGet(1));
                        }
                    } else if (field.startsWith(KEY_PARENT_BEGIN)) {
                        if (!ExcelUtils.isMergedRegionColumn(sheet, rownum.get(), column)) {
                            region = new CellRangeAddress(rownum.get(), rownum.get() + tempList.size() - 1, column, column);
                            sheet.addMergedRegion(region);
                            ExportColumnTypeEnum tempType = columnFiledMap.get(field);
                            field = field.replaceFirst(KEY_PARENT_BEGIN, "");
                            ExcelUtils.setColumnValue(item, field, row, column, tempType, styleMap);
                        }
                    } else {
                        if (field.contains(".")) {
                            String[] tempFields = field.split("\\.");
                            Field tempField = ReflectionUtils.findField(obj.getClass(), tempFields[0]);
                            tempField.setAccessible(true);
                            obj = tempField.get(obj);
                            field = tempFields[1];
                        }
                        ExcelUtils.setColumnValue(obj, field, row, column, columnFiledMap.get(field), styleMap);
                    }
                    ++column;
                }
                rownum.set(rownum.addAndGet(1));
            }
        }
    }

    private static boolean isMergedRegionColumn(Sheet sheet, int rowNum, int columnNum) {
        boolean resultFlag = false;
        int regionNum = sheet.getNumMergedRegions();
        for (int i = 0; i < regionNum; ++i) {
            CellRangeAddress tempAddress = sheet.getMergedRegion(i);
            if (!tempAddress.isInRange(rowNum, columnNum)) continue;
            return true;
        }
        return resultFlag;
    }

    private static int getColumn(Sheet sheet, int rowNum, int columnNum) {
        if (!ExcelUtils.isMergedRegionColumn(sheet, rowNum, columnNum)) {
            return columnNum;
        }
        columnNum = columnNum + ExcelUtils.getMergedRegionColumn(sheet, rowNum, columnNum) + 1;
        return ExcelUtils.getColumn(sheet, rowNum, columnNum);
    }

    private static int getMergedRegionColumn(Sheet sheet, int rowNum, int columnNum) {
        int regionNum = sheet.getNumMergedRegions();
        for (int i = 0; i < regionNum; ++i) {
            CellRangeAddress tempAddress = sheet.getMergedRegion(i);
            if (!tempAddress.isInRange(rowNum, columnNum)) continue;
            return tempAddress.getLastColumn() - columnNum;
        }
        return 0;
    }

    private static Map<String, ExportColumnTypeEnum> createTitle(Workbook workbook, Sheet sheet, List<ExportColumnVO> titleList, AtomicInteger rowNum) {
        LinkedHashMap<String, ExportColumnTypeEnum> resultTitleMap = new LinkedHashMap<String, ExportColumnTypeEnum>();
        Row row = null;
        CellStyle style = ExcelUtils.getTitleStyle(workbook);
        int column = 0;
        LinkedHashMap<String, ExportColumnVO> parentTitleMap = new LinkedHashMap<String, ExportColumnVO>();
        Row firstRow = sheet.createRow(0);
        for (ExportColumnVO tempColumnVO : titleList) {
            String[] tempFields;
            if (tempColumnVO.getProp().startsWith(KEY_NOVALUE_CLOUMN)) {
                parentTitleMap.put(tempColumnVO.getProp().replace(KEY_NOVALUE_CLOUMN, ""), tempColumnVO);
                if (row != null) continue;
                row = sheet.createRow(rowNum.get() + 1);
                rowNum.set(rowNum.addAndGet(1));
                row.setHeightInPoints(25.0f);
                continue;
            }
            if (tempColumnVO.getColspan() > 1 || tempColumnVO.getRowspan() > 1) {
                CellRangeAddress region = new CellRangeAddress(firstRow.getRowNum(), firstRow.getRowNum() + tempColumnVO.getRowspan() - 1, column, column + tempColumnVO.getColspan() - 1);
                sheet.addMergedRegion(region);
                sheet.setColumnWidth(column, tempColumnVO.getWidth() * 256);
                ExcelUtils.createCell(firstRow, column, tempColumnVO.getTitle(), style);
                resultTitleMap.put(tempColumnVO.getProp(), tempColumnVO.getType());
                ++column;
                continue;
            }
            if (tempColumnVO.getProp().contains(".") && parentTitleMap.get((tempFields = tempColumnVO.getProp().split("\\."))[0]) != null) {
                ExportColumnVO tempCol = (ExportColumnVO)parentTitleMap.get(tempFields[0]);
                if (tempCol.getStartColumn() == null) {
                    tempCol.setStartColumn(column);
                }
                tempCol.setColspan(tempCol.getColspan() + 1);
                if (tempCol.getWidth() != null) {
                    tempCol.setWidth(tempCol.getWidth() + tempColumnVO.getWidth());
                } else {
                    tempCol.setWidth(tempColumnVO.getWidth());
                }
            }
            if (row == null) {
                row = sheet.createRow(rowNum.get());
                rowNum.set(rowNum.addAndGet(1));
                row.setHeightInPoints(25.0f);
            }
            sheet.setColumnWidth(column, tempColumnVO.getWidth() * 256);
            ExcelUtils.createCell(row, column, tempColumnVO.getTitle(), style);
            resultTitleMap.put(tempColumnVO.getProp(), tempColumnVO.getType());
            ++column;
        }
        if (parentTitleMap.size() > 0) {
            rowNum.set(rowNum.addAndGet(1));
            for (String tempColumnName : parentTitleMap.keySet()) {
                ExportColumnVO tempColumn = (ExportColumnVO)parentTitleMap.get(tempColumnName);
                CellRangeAddress region = new CellRangeAddress(tempColumn.getStartRow().intValue(), tempColumn.getStartRow() + tempColumn.getRowspan() - 1, tempColumn.getStartColumn().intValue(), tempColumn.getStartColumn() + tempColumn.getColspan() - 2);
                sheet.addMergedRegion(region);
                ExcelUtils.createCell(firstRow, tempColumn.getStartColumn(), tempColumn.getTitle(), style);
            }
        }
        return resultTitleMap;
    }

    public static enum ExportColumnTypeEnum {
        BOOLEAN(CellType.BOOLEAN),
        NUMERIC(CellType.NUMERIC),
        FORMAT_NUMBER(CellType.NUMERIC, "0.00"),
        STRING(CellType.STRING),
        PERCENT(CellType.NUMERIC),
        MONEY(CellType.NUMERIC),
        DATE(CellType.STRING),
        DATETIME(CellType.STRING);

        private CellType code;
        private String formatStr = "0.00";

        private ExportColumnTypeEnum(CellType code) {
            this.code = code;
        }

        private ExportColumnTypeEnum(CellType code, String formatStr) {
            this.code = code;
            this.formatStr = formatStr;
        }

        public String getFormatStr() {
            return this.formatStr;
        }

        public void setFormatStr(String formatStr) {
            this.formatStr = formatStr;
        }

        public CellType getCode() {
            return this.code;
        }
    }

    public static class ExportColumnVO {
        private String prop;
        private String title;
        private Integer width = 15;
        private ExportColumnTypeEnum type = ExportColumnTypeEnum.STRING;
        private Integer colspan = 1;
        private Integer rowspan = 1;
        private Integer startColumn;
        private Integer startRow = 0;

        public ExportColumnVO(String prop, String title) {
            this.title = title;
            this.prop = prop;
        }

        public ExportColumnVO(String prop, String title, Integer width) {
            this(prop, title);
            this.width = width;
        }

        public ExportColumnVO(String prop, String title, Integer width, Integer rowspan, Integer colspan) {
            this(prop, title, width);
            this.rowspan = rowspan;
            this.colspan = colspan;
        }

        public ExportColumnVO(String prop, String title, Integer width, ExportColumnTypeEnum type) {
            this(prop, title, width);
            this.type = type;
        }

        public ExportColumnVO(String prop, String title, Integer width, ExportColumnTypeEnum type, Integer rowspan, Integer colspan) {
            this(prop, title, width, rowspan, colspan);
            this.type = type;
        }

        public Integer getColspan() {
            return this.colspan;
        }

        public void setColspan(Integer colspan) {
            this.colspan = colspan;
        }

        public Integer getRowspan() {
            return this.rowspan;
        }

        public void setRowspan(Integer rowspan) {
            this.rowspan = rowspan;
        }

        public ExportColumnTypeEnum getType() {
            return this.type;
        }

        public void setType(ExportColumnTypeEnum type) {
            this.type = type;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getProp() {
            return this.prop;
        }

        public void setProp(String prop) {
            this.prop = prop;
        }

        public Integer getWidth() {
            return this.width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getStartColumn() {
            return this.startColumn;
        }

        public void setStartColumn(Integer startColumn) {
            this.startColumn = startColumn;
        }

        public Integer getStartRow() {
            return this.startRow;
        }

        public void setStartRow(Integer startRow) {
            this.startRow = startRow;
        }
    }
}

