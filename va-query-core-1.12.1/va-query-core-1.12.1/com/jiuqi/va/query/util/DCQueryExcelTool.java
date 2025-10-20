/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.sql.enumerate.DisplayFormatEnum
 *  com.jiuqi.va.query.sql.vo.Column
 *  com.jiuqi.va.query.sql.vo.QueryGroupField
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  com.jiuqi.va.query.template.enumerate.AlignEnum
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.tree.vo.TableHeaderVO
 *  org.apache.poi.hssf.usermodel.HSSFRichTextString
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.DataFormat
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.IndexedColors
 *  org.apache.poi.ss.usermodel.RichTextString
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.ss.util.RegionUtil
 *  org.apache.poi.xssf.streaming.SXSSFCell
 *  org.apache.poi.xssf.streaming.SXSSFRow
 *  org.apache.poi.xssf.streaming.SXSSFSheet
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 */
package com.jiuqi.va.query.util;

import com.jiuqi.va.query.sql.dto.QueryExportCellDTO;
import com.jiuqi.va.query.sql.dto.QueryExportGroupStyleEnum;
import com.jiuqi.va.query.sql.enumerate.DisplayFormatEnum;
import com.jiuqi.va.query.sql.vo.Column;
import com.jiuqi.va.query.sql.vo.QueryGroupField;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.template.enumerate.AlignEnum;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.tree.vo.TableHeaderVO;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.DCQueryTreeTool;
import com.jiuqi.va.query.util.QueryUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class DCQueryExcelTool<T> {
    private static final Logger logger = LoggerFactory.getLogger(DCQueryExcelTool.class);
    public static final Integer MAX_ROWSUM = 1048570;
    private static final Integer ROW_ACCESS_WINDOW_SIZE = 500;
    private SXSSFWorkbook workbook;
    private String sheetName;
    private int colWidth = 20;
    private static Pattern NOT_NUMBER_PATTERN = Pattern.compile("[^0-9]");
    private Map<String, CellStyle> columnStyleMap = new HashMap<String, CellStyle>();
    private DataFormat dataFormat;
    private CellStyle headCellStyle;
    private QueryExportGroupStyleEnum groupFieldGroupStyle;
    private int tagPrintRowSize;

    public DCQueryExcelTool() {
        this("sheet");
    }

    public DCQueryExcelTool(String title) {
        this(title, 20);
    }

    public DCQueryExcelTool(String title, int colWidth) {
        this.colWidth = colWidth;
        this.sheetName = title;
        this.workbook = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE.intValue());
        this.workbook.setCompressTempFiles(true);
        this.dataFormat = this.workbook.createDataFormat();
        this.initHeaderStyle();
    }

    private void initHeaderStyle() {
        this.headCellStyle = this.workbook.createCellStyle();
        this.headCellStyle.setAlignment(HorizontalAlignment.CENTER);
        this.headCellStyle.setBorderBottom(BorderStyle.THIN);
        this.headCellStyle.setBorderLeft(BorderStyle.THIN);
        this.headCellStyle.setBorderRight(BorderStyle.THIN);
        this.headCellStyle.setBorderTop(BorderStyle.THIN);
        this.headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        this.headCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        this.headCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.headCellStyle.setWrapText(true);
    }

    public Workbook exportWorkbook(List<TableHeaderVO> tableHeader, List<T> datas, boolean flag) throws Exception {
        this.splitDataToSheets(datas, this.columnTransformer(tableHeader), flag, null);
        return this.workbook;
    }

    public Workbook exportWorkbook(List<TableHeaderVO> tableHeader, List<T> dataList, boolean flag, QueryParamVO queryParamVO) throws Exception {
        this.splitDataToSheets(dataList, this.columnTransformer(tableHeader), flag, queryParamVO);
        return this.workbook;
    }

    public void writeDataToSheet(List<T> dataList, List<Column> titleColumnList, int rowIndex, List<QueryGroupField> groupFields, SXSSFSheet sheet) throws Exception {
        ArrayList<Column> columnList = new ArrayList<Column>();
        this.getColumnList(titleColumnList, columnList);
        if (Objects.nonNull((Object)this.groupFieldGroupStyle) && QueryExportGroupStyleEnum.MERGE_CELL_BY_ROW == this.groupFieldGroupStyle) {
            this.writeDataToSheetMergeByRow(dataList, columnList, rowIndex, sheet, groupFields);
        } else if (Objects.nonNull((Object)this.groupFieldGroupStyle) && QueryExportGroupStyleEnum.EXCEL_GROUP == this.groupFieldGroupStyle) {
            this.writeDataToSheetExcelGroup(dataList, columnList, rowIndex, sheet);
        } else {
            this.writeDataToSheetNoMerge(dataList, columnList, rowIndex, sheet);
        }
    }

    private void writeDataToSheetExcelGroup(List<T> list, List<Column> columnList, int rowIndex, SXSSFSheet sheet) throws Exception {
        Field outlineField = SXSSFRow.class.getDeclaredField("_outlineLevel");
        outlineField.setAccessible(true);
        Method setOutlineMethod = SXSSFSheet.class.getDeclaredMethod("setWorksheetOutlineLevelRowIfNecessary", Short.TYPE);
        setOutlineMethod.setAccessible(true);
        int columnListSize = columnList.size();
        int maxLevelRow = 0;
        int size = list.size();
        for (int i = 0; i < size; ++i) {
            SXSSFRow row = sheet.createRow(rowIndex + i);
            T v = list.get(i);
            Map tempMap = QueryUtils.getMap(v);
            QueryExportCellDTO cellInfo = Optional.ofNullable(tempMap.get("__CELL_INFO__")).map(QueryExportCellDTO.class::cast).orElse(null);
            for (int j = 0; j < columnListSize; ++j) {
                Column column = columnList.get(j);
                this.createCell(row, column, v, false, cellInfo != null && cellInfo.isGroupFlag());
            }
            if (cellInfo == null) continue;
            int level = cellInfo.getLevel();
            outlineField.setInt(row, level);
            maxLevelRow = Math.max(level, maxLevelRow);
        }
        if (maxLevelRow > 0) {
            setOutlineMethod.invoke(sheet, (short)maxLevelRow);
        }
    }

    private void writeDataToSheetMergeByRow(List<T> list, List<Column> columnList, int rowIndex, SXSSFSheet sheet, List<QueryGroupField> groupFields) throws Exception {
        int columnListSize = columnList.size();
        int size = list.size();
        int i = 0;
        int index = rowIndex;
        while (i < size) {
            SXSSFRow row = sheet.createRow(index);
            T v = list.get(i);
            for (int j = 0; j < columnListSize; ++j) {
                Column column = columnList.get(j);
                boolean flag = false;
                if (this.needDealCellMerge(column, v, groupFields)) {
                    this.dealCellMerge(sheet, v, rowIndex, j);
                    flag = true;
                }
                this.createCell(row, column, v, flag, false);
            }
            ++i;
            ++index;
        }
    }

    private void writeDataToSheetNoMerge(List<T> list, List<Column> columnList, int rowIndex, SXSSFSheet sheet) throws Exception {
        int columnListSize = columnList.size();
        int size = list.size();
        int i = 0;
        int index = rowIndex;
        while (i < size) {
            SXSSFRow row = sheet.createRow(index);
            T v = list.get(i);
            for (int j = 0; j < columnListSize; ++j) {
                Column column = columnList.get(j);
                this.createCell(row, column, v, false, false);
            }
            ++i;
            ++index;
        }
    }

    public SXSSFSheet writeSheet(List<Column> titleColumnList, int sheetIndex) {
        SXSSFSheet sheet = this.workbook.createSheet(this.sheetName + sheetIndex);
        sheet.setDefaultColumnWidth(this.colWidth);
        Column column = titleColumnList.get(0);
        int totalRow = column.getTotalRow();
        int totalCol = column.getTotalCol();
        this.createHead(sheet, totalRow, totalCol);
        this.createHead(titleColumnList, sheet, 0);
        return sheet;
    }

    public SXSSFSheet writeSheet(List<Column> titleColumnList, int sheetIndex, int blankRowSize) {
        SXSSFSheet sheet = this.workbook.createSheet(this.sheetName + sheetIndex);
        sheet.setDefaultColumnWidth(this.colWidth);
        Column column = titleColumnList.get(0);
        int totalRow = column.getTotalRow() + blankRowSize;
        int totalCol = column.getTotalCol();
        this.createHead(sheet, totalRow, totalCol);
        this.createHead(titleColumnList, sheet, blankRowSize);
        return sheet;
    }

    private void writeSheet(SXSSFSheet sheet, List<T> datas, List<Column> titleColumnList, boolean onlyExportHeader, QueryParamVO queryParamVO) throws Exception {
        sheet.setDefaultColumnWidth(this.colWidth);
        Column column = titleColumnList.get(0);
        int totalRow = column.getTotalRow();
        int totalCol = column.getTotalCol();
        sheet = this.createHead(sheet, totalRow, totalCol);
        this.createHead(titleColumnList, sheet, 0);
        if (onlyExportHeader) {
            this.writeSheetContent(titleColumnList, datas, sheet, totalRow, queryParamVO);
        }
    }

    private void splitDataToSheets(List<T> datas, List<Column> titleColumnList, boolean onlyExportHeader, QueryParamVO queryParamVO) throws Exception {
        int dataCount = datas.size();
        int pieces = dataCount / MAX_ROWSUM;
        for (int i = 1; i <= pieces; ++i) {
            SXSSFSheet sheet = this.workbook.createSheet(this.sheetName + i);
            List<T> subList = datas.subList((i - 1) * MAX_ROWSUM, i * MAX_ROWSUM);
            this.writeSheet(sheet, subList, titleColumnList, onlyExportHeader, queryParamVO);
        }
        SXSSFSheet sheet = this.workbook.createSheet(this.sheetName + (pieces + 1));
        this.writeSheet(sheet, datas.subList(pieces * MAX_ROWSUM, dataCount), titleColumnList, onlyExportHeader, queryParamVO);
    }

    private void writeSheetContent(List<Column> titleColumnList, List<T> datas, SXSSFSheet sheet, int rowIndex, QueryParamVO queryParamVO) throws Exception {
        ArrayList<Column> columnList = new ArrayList<Column>();
        this.getColumnList(titleColumnList, columnList);
        List<QueryGroupField> groupFields = Optional.ofNullable(queryParamVO.getGroupFields()).orElse(Collections.emptyList());
        int columnListSize = columnList.size();
        int size = datas.size();
        int i = 0;
        int index = rowIndex;
        while (i < size) {
            SXSSFRow row = sheet.createRow(index);
            T v = datas.get(i);
            for (int j = 0; j < columnListSize; ++j) {
                Column column = (Column)columnList.get(j);
                boolean flag = false;
                if (this.needDealCellMerge(column, v, groupFields)) {
                    this.dealCellMerge(sheet, v, rowIndex, j);
                    flag = true;
                }
                this.createCell(row, column, v, flag, false);
            }
            ++i;
            ++index;
        }
    }

    private boolean needDealCellMerge(Column column, T v, List<QueryGroupField> groupFieldList) {
        if (CollectionUtils.isEmpty(groupFieldList)) {
            return false;
        }
        if (Objects.isNull(column) || Objects.isNull(v)) {
            return false;
        }
        String fieldName = column.getFieldName();
        Map map = QueryUtils.getMap(v);
        Map originDataMap = QueryUtils.getMap(map.get("__ORIGIN_DATA_MAP__"));
        QueryExportCellDTO queryExportCellDTO = (QueryExportCellDTO)map.get("__CELL_INFO__");
        if (originDataMap.containsKey(fieldName)) {
            if (Objects.nonNull(queryExportCellDTO)) {
                queryExportCellDTO.setCrossRow(queryExportCellDTO.getChildrenSize());
                map.put("__CELL_INFO__", queryExportCellDTO);
            }
            return true;
        }
        return false;
    }

    private void getColumnList(List<Column> headerList, List<Column> columnList) {
        for (Column column : headerList) {
            List chilrenList;
            if (column.getFieldName() != null) {
                columnList.add(column);
            }
            if ((chilrenList = column.getListTpamscolumn()).isEmpty()) continue;
            this.getColumnList(chilrenList, columnList);
        }
    }

    private SXSSFCell createCell(SXSSFRow row, Column titleColumn, T v, boolean flag, boolean groupCellFlag) throws Exception {
        SXSSFCell cell = row.createCell(titleColumn.getCol());
        Object[] value = new Object[]{null};
        if (v instanceof Map) {
            Map map = QueryUtils.getMap(v);
            map.forEach((k, val) -> {
                if (titleColumn.getFieldName().equals(k) && !titleColumn.isHasChilren()) {
                    cell.setCellStyle(this.getStyleByField(titleColumn.getFieldSetting()));
                    if (ParamTypeEnum.NUMBER == titleColumn.getColumnType()) {
                        cell.setCellType(CellType.NUMERIC);
                    }
                    value[0] = val;
                }
            });
        } else {
            Field[] fields;
            Class<?> cls = v.getClass();
            for (Field field : fields = cls.getDeclaredFields()) {
                field.setAccessible(true);
                if (titleColumn.getFieldName().equals(field.getName()) && !titleColumn.isHasChilren()) {
                    cell.setCellStyle(this.getStyleByField(titleColumn.getFieldSetting()));
                    if (ParamTypeEnum.NUMBER == titleColumn.getColumnType()) {
                        cell.setCellType(CellType.NUMERIC);
                    }
                    value[0] = field.get(v);
                }
                if (!(value[0] instanceof Date)) continue;
                value[0] = this.parseDate((Date)value[0]);
            }
        }
        if (ObjectUtils.isEmpty(value[0])) {
            CellStyle styleByField = this.getStyleByField(titleColumn.getFieldSetting());
            cell.setCellStyle(styleByField);
            cell.setCellValue("");
        } else {
            String strValue = value[0].toString();
            try {
                if (ParamTypeEnum.NUMBER == titleColumn.getColumnType()) {
                    cell.setCellValue(Double.parseDouble(strValue));
                } else if (DisplayFormatEnum.DEFAULT.getTypeName().equals(titleColumn.getFieldSetting().getDisplayFormat())) {
                    cell.setCellValue(Double.parseDouble(strValue));
                } else {
                    cell.setCellValue(strValue);
                }
            }
            catch (Exception e) {
                cell.setCellValue(strValue);
            }
        }
        if (flag) {
            CellStyle cellStyle = cell.getCellStyle();
            if (Objects.isNull(cellStyle)) {
                cellStyle = this.workbook.createCellStyle();
            }
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cell.setCellStyle(cellStyle);
        }
        if (groupCellFlag) {
            String cacheKey = String.format("%s_GROUP_STYLE", titleColumn.getFieldName());
            CellStyle groupStyle = this.columnStyleMap.computeIfAbsent(cacheKey, k -> {
                CellStyle baseStyle = Optional.ofNullable(cell.getCellStyle()).orElseGet(() -> this.getStyleByField(titleColumn.getFieldSetting()));
                CellStyle newStyle = this.workbook.createCellStyle();
                newStyle.cloneStyleFrom(baseStyle);
                newStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
                newStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                return newStyle;
            });
            cell.setCellStyle(groupStyle);
        }
        return cell;
    }

    private void dealCellMerge(SXSSFSheet sheet, T v, int rowIndex, int j) {
        QueryExportCellDTO queryExportCellDTO;
        Map map = QueryUtils.getMap(v);
        Object excelIndex = map.get("excel_index");
        int start = 0;
        if (Objects.nonNull(excelIndex) && excelIndex instanceof Integer) {
            start = (Integer)excelIndex + rowIndex - 1;
        }
        if (Objects.nonNull(queryExportCellDTO = (QueryExportCellDTO)map.get("__CELL_INFO__"))) {
            int crossRow = queryExportCellDTO.getCrossRow();
            CellRangeAddress cra = new CellRangeAddress(start, start + crossRow, j, j);
            try {
                sheet.addMergedRegion(cra);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private CellStyle getHeaderStyleByField(TemplateFieldSettingVO fieldSetting, Column column) {
        boolean lastCellInColNotMergeFlag;
        CellStyle cellStyle = this.workbook.createCellStyle();
        this.initAlignStyle(fieldSetting, cellStyle);
        short index = HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex();
        cellStyle.setFillForegroundColor(index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        if (Objects.isNull(column)) {
            return cellStyle;
        }
        int rLen = column.getrLen();
        if (rLen > 0) {
            --rLen;
        }
        int totalRow = column.getTotalRow();
        int r = column.getRow();
        boolean bl = lastCellInColNotMergeFlag = rLen == 0 && r + 1 == totalRow;
        if (rLen == 0 && !lastCellInColNotMergeFlag) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        } else {
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        return cellStyle;
    }

    private CellStyle getStyleByField(TemplateFieldSettingVO fieldSetting) {
        if (this.columnStyleMap.get(fieldSetting.getName()) == null) {
            CellStyle columnCellStyle = this.workbook.createCellStyle();
            this.initAlignStyle(fieldSetting, columnCellStyle);
            this.initNumberStyle(fieldSetting, columnCellStyle);
            this.columnStyleMap.put(fieldSetting.getName(), columnCellStyle);
        }
        return this.columnStyleMap.get(fieldSetting.getName());
    }

    private void initNumberStyle(TemplateFieldSettingVO fieldSetting, CellStyle columnCellStyle) {
        if (!ParamTypeEnum.NUMBER.getTypeName().equals(fieldSetting.getDataType())) {
            return;
        }
        if (DisplayFormatEnum.DEFAULT.getTypeName().equals(fieldSetting.getDisplayFormat())) {
            if (fieldSetting.getDecimalLength() != null) {
                StringBuffer patternBuffer = new StringBuffer("0");
                if (fieldSetting.getDecimalLength() > 0) {
                    patternBuffer.append(".");
                    for (int i = 0; i < fieldSetting.getDecimalLength(); ++i) {
                        patternBuffer.append('0');
                    }
                }
                columnCellStyle.setDataFormat(this.dataFormat.getFormat(patternBuffer.toString()));
            } else {
                columnCellStyle.setDataFormat(this.dataFormat.getFormat("0.00"));
            }
        } else if (fieldSetting.getDisplayFormat() == null || DisplayFormatEnum.CURRENCY.getTypeName().equals(fieldSetting.getDisplayFormat())) {
            if (fieldSetting.getDecimalLength() != null) {
                StringBuffer patternBuffer = new StringBuffer("#,##0");
                if (fieldSetting.getDecimalLength() > 0) {
                    patternBuffer.append(".");
                    for (int i = 0; i < fieldSetting.getDecimalLength(); ++i) {
                        patternBuffer.append("0");
                    }
                }
                columnCellStyle.setDataFormat(this.dataFormat.getFormat(patternBuffer.toString()));
            } else {
                columnCellStyle.setDataFormat(this.dataFormat.getFormat("#,##0.00"));
            }
        }
    }

    private void initAlignStyle(TemplateFieldSettingVO fieldSetting, CellStyle columnCellStyle) {
        if (!DCQueryStringHandle.isEmpty(fieldSetting.getAlign())) {
            switch (AlignEnum.val((String)fieldSetting.getAlign())) {
                case LEFT: {
                    columnCellStyle.setAlignment(HorizontalAlignment.LEFT);
                    break;
                }
                case CENTER: {
                    columnCellStyle.setAlignment(HorizontalAlignment.CENTER);
                    break;
                }
                case RIGHT: {
                    columnCellStyle.setAlignment(HorizontalAlignment.RIGHT);
                }
            }
        } else if (ParamTypeEnum.NUMBER.getTypeName().equals(fieldSetting.getDataType())) {
            columnCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        } else {
            columnCellStyle.setAlignment(HorizontalAlignment.LEFT);
        }
        columnCellStyle.setBorderBottom(BorderStyle.THIN);
        columnCellStyle.setBorderLeft(BorderStyle.THIN);
        columnCellStyle.setBorderRight(BorderStyle.THIN);
        columnCellStyle.setBorderTop(BorderStyle.THIN);
    }

    private String parseDate(Date date) {
        String dateStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateStr = sdf.format(date);
        }
        catch (Exception e) {
            logger.warn("\u67e5\u8be2\u5b9a\u4e49\u5bfc\u51fa\u683c\u5f0f\u5316\u65e5\u671f\u5f02\u5e38", e);
        }
        return dateStr;
    }

    private SXSSFSheet createHead(SXSSFSheet sheetCo, int row, int col) {
        for (int i = 0; i < row; ++i) {
            SXSSFRow sheetCoRow = sheetCo.createRow(i);
            for (int j = 0; j < col; ++j) {
                sheetCoRow.createCell(j);
            }
        }
        return sheetCo;
    }

    private void createHead(List<Column> columnList, SXSSFSheet sheetCo, int rowIndex) {
        if (CollectionUtils.isEmpty(columnList)) {
            return;
        }
        SXSSFRow sxssfRow = sheetCo.getRow(rowIndex);
        for (Column tpamscolumn : columnList) {
            int row = tpamscolumn.getRow();
            int col = tpamscolumn.getCol();
            int cLen = tpamscolumn.getcLen();
            int rLen = tpamscolumn.getrLen();
            if (rLen > 0) {
                --rLen;
            }
            int endRow = row + rLen;
            int endCol = col + cLen;
            if (endCol > col) {
                --endCol;
            }
            SXSSFCell cell = sxssfRow.getCell(col);
            String content = tpamscolumn.getContent();
            HSSFRichTextString hssfRichTextString = new HSSFRichTextString(content);
            if (cLen != 0) {
                cell.setCellStyle(this.headCellStyle);
            } else {
                String width = tpamscolumn.getWidth();
                if (!DCQueryStringHandle.isEmpty(width)) {
                    Matcher widthMatch = NOT_NUMBER_PATTERN.matcher(width);
                    int i = Integer.parseInt(widthMatch.replaceAll("").trim());
                    sheetCo.setColumnWidth(col, i * 50);
                }
                TemplateFieldSettingVO fieldSetting = tpamscolumn.getFieldSetting();
                CellStyle headerStyleByField = this.getHeaderStyleByField(fieldSetting, tpamscolumn);
                cell.setCellStyle(headerStyleByField);
            }
            cell.setCellValue((RichTextString)hssfRichTextString);
            CellRangeAddress cra = new CellRangeAddress(row, endRow, col, endCol);
            if (row != endRow || col != endCol) {
                sheetCo.addMergedRegion(cra);
            }
            RegionUtil.setBorderBottom((BorderStyle)BorderStyle.THIN, (CellRangeAddress)cra, (Sheet)sheetCo);
            RegionUtil.setBorderLeft((BorderStyle)BorderStyle.THIN, (CellRangeAddress)cra, (Sheet)sheetCo);
            RegionUtil.setBorderRight((BorderStyle)BorderStyle.THIN, (CellRangeAddress)cra, (Sheet)sheetCo);
            if (!tpamscolumn.isHasChilren()) continue;
            rowIndex = row + 1;
            List tempColumnList = tpamscolumn.getListTpamscolumn();
            this.createHead(tempColumnList, sheetCo, rowIndex);
        }
    }

    public List<Column> columnTransformer(List<TableHeaderVO> tableHeader) {
        ArrayList<Column> columnList = new ArrayList<Column>();
        this.tableHeaderToColumnList(tableHeader, columnList, null);
        this.setParam(columnList, "0");
        List<Column> treeList = DCQueryTreeTool.buildByRecursive(columnList, "0");
        this.setColumnNum(columnList, treeList);
        return treeList;
    }

    private void tableHeaderToColumnList(List<TableHeaderVO> tableHeader, List<Column> titleList, String pid) {
        for (int i = 0; i < tableHeader.size(); ++i) {
            TableHeaderVO tableHeaderVO = tableHeader.get(i);
            if (DCQueryStringHandle.isEmpty(pid)) {
                pid = "0";
            }
            String id = pid + "|" + i;
            titleList.add(this.tableHeaderToMap(tableHeaderVO, id, pid));
            if (tableHeaderVO.getChildren() == null) continue;
            this.tableHeaderToColumnList(tableHeaderVO.getChildren(), titleList, id);
        }
    }

    private Column tableHeaderToMap(TableHeaderVO tableHeaderVO, String id, String pid) {
        Column column = new Column();
        column.setId(id);
        column.setPid(pid);
        column.setContent(tableHeaderVO.getTitle());
        column.setFieldName(tableHeaderVO.getKey());
        column.setWidth(tableHeaderVO.getWidth());
        column.setFieldSetting(tableHeaderVO.getFieldSetting());
        String dataType = tableHeaderVO.getFieldSetting().getDataType();
        String displayFormat = tableHeaderVO.getFieldSetting().getDisplayFormat();
        if (this.columnTypeIsNumber(dataType, displayFormat)) {
            column.setColumnType(ParamTypeEnum.NUMBER);
        } else {
            column.setColumnType(ParamTypeEnum.STRING);
        }
        return column;
    }

    private boolean columnTypeIsNumber(String dataType, String displayFormat) {
        if (!ParamTypeEnum.NUMBER.getTypeName().equals(dataType)) {
            return false;
        }
        if (DCQueryStringHandle.isEmpty(displayFormat)) {
            return true;
        }
        return !DisplayFormatEnum.PERCENT.getTypeName().equals(displayFormat) && !DisplayFormatEnum.AUTO_PERCENT.getTypeName().equals(displayFormat);
    }

    private void setParam(List<Column> list, String rootId) {
        int rLen = 0;
        int totalRow = DCQueryTreeTool.getMaxStep(list);
        int totalCol = DCQueryTreeTool.getDownChilren(list, rootId);
        for (int i = 0; i < list.size(); ++i) {
            Column poit = list.get(i);
            int treeStep = DCQueryTreeTool.getTreeStep(list, poit.getPid(), 0);
            poit.setTreeStep(treeStep);
            poit.setRow(treeStep);
            boolean hasCh = DCQueryTreeTool.hasChild(list, poit);
            poit.setHasChilren(hasCh);
            if (hasCh) {
                poit.setrLen(0);
            } else {
                if (treeStep < totalRow) {
                    rLen = totalRow - treeStep;
                }
                poit.setrLen(rLen);
            }
            poit.setTotalRow(totalRow);
            poit.setTotalCol(totalCol);
        }
    }

    private void setColumnNum(List<Column> list, List<Column> treeList) {
        ArrayList<Column> newList = new ArrayList<Column>();
        for (Column point : treeList) {
            int col = DCQueryTreeTool.getParentCol(list, point.getPid()).getCol();
            int brotherCol = DCQueryTreeTool.getBrotherChilNum(list, point);
            point.setCol(col + brotherCol);
            int cLen = DCQueryTreeTool.getDownChilren(list, point.getId());
            if (cLen <= 1) {
                cLen = 0;
            }
            point.setcLen(cLen);
            if (point.getListTpamscolumn().isEmpty()) continue;
            newList.addAll(point.getListTpamscolumn());
        }
        if (!newList.isEmpty()) {
            this.setColumnNum(list, newList);
        }
    }

    public int getColWidth() {
        return this.colWidth;
    }

    public void setColWidth(int colWidth) {
        this.colWidth = colWidth;
    }

    public SXSSFWorkbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(SXSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public QueryExportGroupStyleEnum getGroupFieldGroupStyle() {
        return this.groupFieldGroupStyle;
    }

    public void setGroupFieldGroupStyle(QueryExportGroupStyleEnum groupFieldGroupStyle) {
        this.groupFieldGroupStyle = groupFieldGroupStyle;
    }

    public int getTagPrintRowSize() {
        return this.tagPrintRowSize;
    }

    public void setTagPrintRowSize(int tagPrintRowSize) {
        this.tagPrintRowSize = tagPrintRowSize;
    }
}

