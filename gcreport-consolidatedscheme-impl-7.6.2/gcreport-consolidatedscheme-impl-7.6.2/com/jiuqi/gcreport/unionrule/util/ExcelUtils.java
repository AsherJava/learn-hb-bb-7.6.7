/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.vo.ExportExcelVO
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
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
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package com.jiuqi.gcreport.unionrule.util;

import com.jiuqi.gcreport.unionrule.vo.ExportExcelVO;
import java.beans.PropertyDescriptor;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

public class ExcelUtils {
    private static Map<ExportColumnTypeEnum, CellStyle> getColumnStyle(Workbook workbook) {
        HashMap<ExportColumnTypeEnum, CellStyle> resultMap = new HashMap<ExportColumnTypeEnum, CellStyle>();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)10);
        font.setFontName("\u5b8b\u4f53");
        for (ExportColumnTypeEnum tempType : ExportColumnTypeEnum.values()) {
            CellStyle style = workbook.createCellStyle();
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setAlignment(HorizontalAlignment.LEFT);
            style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
            style.setWrapText(true);
            style.setFont(font);
            resultMap.put(tempType, style);
        }
        return resultMap;
    }

    private static <T> void setColumnValue(ExportExcelVO item, String fieldName, Row row, int column, ExportColumnTypeEnum type, Map<ExportColumnTypeEnum, CellStyle> styleMap, List<String> selectdimensions) throws Exception {
        if (selectdimensions.contains(fieldName)) {
            Cell cell = row.createCell(column);
            if (!CollectionUtils.isEmpty(item.getDimensions())) {
                item.getDimensions().get(fieldName);
                cell.setCellValue((String)item.getDimensions().get(fieldName));
            } else {
                cell.setCellValue("");
            }
        } else {
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
                        cell.setCellValue(Boolean.valueOf(String.valueOf(obj)).booleanValue());
                    }
                    if (ExportColumnTypeEnum.NUMERIC == type) {
                        cell.setCellValue(Double.valueOf(String.valueOf(obj)).doubleValue());
                    }
                    if (ExportColumnTypeEnum.FORMAT_NUMBER == type) {
                        format = row.getSheet().getWorkbook().createDataFormat();
                        cell.getCellStyle().setDataFormat(format.getFormat(type.getFormatStr()));
                        cell.setCellValue(Double.valueOf(String.valueOf(obj)).doubleValue());
                    }
                    if (ExportColumnTypeEnum.STRING == type) {
                        cell.setCellValue(String.valueOf(obj));
                    }
                    if (ExportColumnTypeEnum.PERCENT == type) {
                        cell.getCellStyle().setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"0.00%"));
                        cell.setCellValue(Double.valueOf(String.valueOf(obj)).doubleValue());
                    }
                    if (ExportColumnTypeEnum.MONEY == type) {
                        cell.getCellStyle().setDataFormat((short)4);
                        cell.setCellValue(Double.valueOf(String.valueOf(obj)).doubleValue());
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
    }

    private static CellStyle getTitleStyle(Workbook workbook) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font fontTitle = workbook.createFont();
        fontTitle.setFontHeightInPoints((short)10);
        fontTitle.setColor((short)0);
        fontTitle.setFontName("\u5b8b\u4f53");
        fontTitle.setBold(true);
        titleStyle.setFont(fontTitle);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
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

    private static void mergeContent(Sheet sheet, List<ExportColumnVO> titleList) {
        boolean flag = false;
        int index = 0;
        int rownum = -1;
        for (int i = 1; i <= sheet.getLastRowNum(); ++i) {
            String afterRow;
            Cell cell2 = sheet.getRow(i).getCell(1);
            Cell cell3 = i == sheet.getLastRowNum() ? null : sheet.getRow(i + 1).getCell(1);
            String frontRow = cell2.getStringCellValue();
            String string = afterRow = cell3 == null ? "" : cell3.getStringCellValue();
            if (frontRow.equals(afterRow)) {
                flag = true;
                ++index;
                if (rownum != -1) continue;
                rownum = i;
                continue;
            }
            if (!flag) continue;
            int col = 0;
            for (ExportColumnVO tempColumnVO : titleList) {
                if (tempColumnVO.prop == "index" || tempColumnVO.prop == "ruleTitle" || tempColumnVO.prop == "ruleType" || tempColumnVO.prop == "businessType" || tempColumnVO.prop == "ruleCondition" || tempColumnVO.prop == "startFlag" || tempColumnVO.prop == "options" || tempColumnVO.prop == "applyGcUnits") {
                    sheet.addMergedRegion(new CellRangeAddress(rownum, rownum + index, col, col));
                }
                ++col;
            }
            flag = false;
            index = 0;
            rownum = -1;
        }
    }

    public static <T> void createExcel(HttpServletResponse response, String fileName, Map<String, String> sheetMap, List<List<ExportColumnVO>> titleList, List<List<ExportExcelVO>> dataList, List<String> selectdimensions) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Map<ExportColumnTypeEnum, CellStyle> styleMap = ExcelUtils.getColumnStyle((Workbook)workbook);
        int index = 0;
        for (String sheetName : sheetMap.keySet()) {
            ExcelUtils.createSheet((Workbook)workbook, sheetName, titleList.get(0), dataList.get(index), sheetMap.get(sheetName), styleMap, selectdimensions);
            ++index;
        }
        response.setHeader("Charset", "UTF-8");
        response.setHeader("Content-Type", "application/force-download");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName + ".xlsx", "UTF-8") + "\"");
        response.flushBuffer();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write((OutputStream)outputStream);
        outputStream.flush();
        outputStream.close();
    }

    private static <T> void createSheet(Workbook workbook, String sheetName, List<ExportColumnVO> titleList, List<ExportExcelVO> dataList, String propName, Map<ExportColumnTypeEnum, CellStyle> styleMap, List<String> selectdimensions) throws Exception {
        Sheet sheet = workbook.createSheet(sheetName);
        AtomicInteger rownum = new AtomicInteger(0);
        Map<String, ExportColumnTypeEnum> columnFiledMap = ExcelUtils.createTitle(workbook, sheet, titleList, rownum);
        AtomicInteger index = new AtomicInteger(1);
        if (dataList != null) {
            for (ExportExcelVO item : dataList) {
                if (propName != null) continue;
                Row row = sheet.createRow(rownum.get());
                int column = 0;
                Set<String> keySet = columnFiledMap.keySet();
                for (String field : keySet) {
                    ExcelUtils.setColumnValue(item, field, row, column, columnFiledMap.get(field), styleMap, selectdimensions);
                    ++column;
                }
                rownum.set(rownum.addAndGet(1));
            }
        }
        ExcelUtils.mergeContent(sheet, titleList);
    }

    private static void createCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    private static Map<String, ExportColumnTypeEnum> createTitle(Workbook workbook, Sheet sheet, List<ExportColumnVO> titleList, AtomicInteger rowNum) {
        LinkedHashMap<String, ExportColumnTypeEnum> resultTitleMap = new LinkedHashMap<String, ExportColumnTypeEnum>();
        Row row = null;
        CellStyle style = ExcelUtils.getTitleStyle(workbook);
        int column = 0;
        LinkedHashMap parentTitleMap = new LinkedHashMap();
        Row firstRow = sheet.createRow(0);
        for (ExportColumnVO tempColumnVO : titleList) {
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

