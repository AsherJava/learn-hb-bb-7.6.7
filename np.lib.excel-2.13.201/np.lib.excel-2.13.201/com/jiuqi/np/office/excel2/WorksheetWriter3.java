/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.text.DecimalFormat
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridCellStyleData
 *  com.jiuqi.nvwa.grid2.graphics.ImageDescriptor
 */
package com.jiuqi.np.office.excel2;

import com.jiuqi.bi.text.DecimalFormat;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.office.excel2.CacheSXSSFWorkbook;
import com.jiuqi.np.office.excel2.CellImgAnchor;
import com.jiuqi.np.office.excel2.ColorOperateUtil;
import com.jiuqi.np.office.excel2.FontStyles2;
import com.jiuqi.np.office.excel2.filter.FilterColCondition;
import com.jiuqi.np.office.excel2.filter.FilterRegionCondition;
import com.jiuqi.np.office.excel2.filter.XSSFAutoFilterExtend;
import com.jiuqi.np.office.excel2.label.ExcelLabel;
import com.jiuqi.np.office.excel2.link.CellLink;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridCellStyleData;
import com.jiuqi.nvwa.grid2.graphics.ImageDescriptor;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class WorksheetWriter3 {
    private static final Logger logger = LoggerFactory.getLogger(WorksheetWriter3.class);
    private Grid2Data grid2Data;
    private Sheet sheet;
    private SXSSFWorkbook workbook;
    private Map<String, String> formulaMap;
    private List<CellLink> links;
    private List<FilterRegionCondition> filters;
    private boolean background = true;
    private List<CellImgAnchor> cellImgAnchors = new ArrayList<CellImgAnchor>();
    private Map<String, Font> cellFontMap;
    private Map<String, XSSFCellStyle> xSSFCellStyleMap;
    private CacheSXSSFWorkbook cacheSXSSFWorkbook;
    private List<ExcelLabel> labels;
    private int rowDeviation;
    private Pattern fpattern = Pattern.compile("[a-zA-Z]+[0-9]+");
    private Pattern npattern = Pattern.compile("[0-9]+");

    public WorksheetWriter3(Grid2Data grid2Data, Sheet sheet, SXSSFWorkbook workbook) {
        this.grid2Data = grid2Data;
        this.sheet = sheet;
        this.workbook = workbook;
        if (workbook instanceof CacheSXSSFWorkbook) {
            this.cacheSXSSFWorkbook = (CacheSXSSFWorkbook)workbook;
        } else {
            this.cellFontMap = new HashMap<String, Font>();
            this.xSSFCellStyleMap = new HashMap<String, XSSFCellStyle>();
        }
    }

    public void writeSheet() {
        this.writeSheet(null);
    }

    public void writeSheet(List<String> startAndEndList) {
        this.handlerUpperLabels();
        this.writeContent(startAndEndList);
        this.mergeCells();
        this.adjustHW();
        this.hideRowCol();
        this.addFilters();
        this.handlerCellImgAnchor();
        this.handlerCellLink();
    }

    private void handlerUpperLabels() {
        if (null != this.labels && !this.labels.isEmpty()) {
            for (ExcelLabel excelLabel : this.labels) {
                int rowIndex = excelLabel.getRowIndex();
                SXSSFRow sXSSFRow = this.sheet.getRow(rowIndex - 1) == null ? (SXSSFRow)this.sheet.createRow(rowIndex - 1) : (SXSSFRow)this.sheet.getRow(rowIndex - 1);
                SXSSFCell cell = sXSSFRow.createCell(excelLabel.getColIndex() - 1);
                cell.setCellValue(excelLabel.getText());
                String fontColor = excelLabel.getFontColor();
                String fontName = excelLabel.getFontName();
                String fontSize = excelLabel.getFontSize();
                boolean bold = excelLabel.isBold();
                boolean italic = excelLabel.isItalic();
                boolean strikeout = excelLabel.isStrikeout();
                boolean underline = excelLabel.isUnderline();
                String fontKey = fontColor + fontName + fontSize + bold + italic + strikeout + underline;
                XSSFFont font = (XSSFFont)this.getCacheFont(fontKey);
                if (font == null) {
                    font = (XSSFFont)this.workbook.createFont();
                    font.setFontHeightInPoints((short)Math.round(Double.valueOf(fontSize)));
                    font.setFontName(fontName);
                    if (bold) {
                        font.setBold(true);
                    }
                    if (italic) {
                        font.setItalic(true);
                    }
                    if (underline) {
                        font.setUnderline((byte)1);
                    }
                    if (strikeout) {
                        font.setStrikeout(true);
                    }
                    this.addCacheFont(fontKey, font);
                }
                XSSFCellStyle style = null;
                if (null != font && null == (style = this.getCacheXSSFCellStyle(fontKey))) {
                    style = (XSSFCellStyle)this.workbook.createCellStyle();
                    style.setFont(font);
                    if (excelLabel.getRowIndex() == 1 && excelLabel.getColIndex() == this.grid2Data.getColumnCount() / 2) {
                        style.setAlignment(HorizontalAlignment.CENTER);
                        style.setVerticalAlignment(VerticalAlignment.CENTER);
                    }
                    this.addCacheXSSFCellStyle(fontKey, style);
                }
                if (null != style) {
                    cell.setCellStyle(style);
                }
                int rowTo = excelLabel.getRowIndex() + excelLabel.getRowSpan();
                if (excelLabel.isUpper() && rowTo > this.rowDeviation) {
                    this.rowDeviation = rowTo;
                }
                if (excelLabel.getRowSpan() <= 0 && excelLabel.getColSpan() <= 0) continue;
                try {
                    this.sheet.addMergedRegion(new CellRangeAddress(excelLabel.getRowIndex(), rowTo, excelLabel.getColIndex(), excelLabel.getColIndex() + excelLabel.getColSpan()));
                }
                catch (Exception exception) {}
            }
        }
    }

    private int handlerRowByLabel(int row) {
        return row + this.rowDeviation;
    }

    private void handlerCellLink() {
        if (null != this.links && !this.links.isEmpty()) {
            CreationHelper helper = this.workbook.getCreationHelper();
            CellStyle linkStyle = this.workbook.createCellStyle();
            Font cellFont = this.workbook.createFont();
            cellFont.setUnderline((byte)1);
            cellFont.setColor(IndexedColors.BLUE.index);
            linkStyle.setFont(cellFont);
            for (CellLink cellLink : this.links) {
                Row row = this.sheet.getRow(this.handlerRowByLabel(cellLink.getRow() - 1));
                if (null != row) {
                    Cell cell = row.getCell(cellLink.getCol() - 1);
                    if (null != cell) {
                        CellStyle cellStyle = cell.getCellStyle();
                        if (null == cellStyle) {
                            cell.setCellStyle(linkStyle);
                        } else {
                            CellStyle createCellStyle = this.workbook.createCellStyle();
                            createCellStyle.cloneStyleFrom(cellStyle);
                            int fontIndexAsInt = createCellStyle.getFontIndexAsInt();
                            Font createFont = this.workbook.createFont();
                            Font font = this.workbook.getFontAt(fontIndexAsInt);
                            createFont.setBold(font.getBold());
                            createFont.setColor(IndexedColors.BLUE.index);
                            createFont.setUnderline((byte)1);
                            createFont.setFontHeightInPoints(font.getFontHeightInPoints());
                            createFont.setFontName(font.getFontName());
                            createFont.setItalic(font.getItalic());
                            createFont.setStrikeout(font.getStrikeout());
                            createCellStyle.setFont(createFont);
                            cell.setCellStyle(createCellStyle);
                        }
                        cell.setCellValue(cellLink.getShowText());
                        Hyperlink hyperlink = helper.createHyperlink(cellLink.getHyperlinkType());
                        if (HyperlinkType.URL == cellLink.getHyperlinkType()) {
                            hyperlink.setAddress(cellLink.getUrl());
                            cell.setHyperlink(hyperlink);
                            continue;
                        }
                        if (HyperlinkType.DOCUMENT == cellLink.getHyperlinkType()) {
                            String temp = "#'" + cellLink.getToSheetName() + "'!" + this.index2ColName(cellLink.getToCol()) + cellLink.getToRow();
                            hyperlink.setAddress(temp);
                            cell.setHyperlink(hyperlink);
                            continue;
                        }
                        logger.error("WorksheetWriter2 \u6682\u4e0d\u652f\u6301\u7684\u5355\u5143\u683c\u8df3\u8f6c\u94fe\u63a5\u7c7b\u578b " + (Object)((Object)cellLink.getHyperlinkType()));
                        continue;
                    }
                    logger.error("WorksheetWriter2 \u94fe\u63a5\u5355\u5143\u683c\u5750\u6807\u9519\u8bef \u5217\uff1a " + cellLink.getCol());
                    continue;
                }
                logger.error("WorksheetWriter2 \u94fe\u63a5\u5355\u5143\u683c\u5750\u6807\u9519\u8bef \u884c\uff1a " + cellLink.getRow());
            }
        }
    }

    private void handlerCellImgAnchor() {
        if (!this.cellImgAnchors.isEmpty()) {
            for (CellImgAnchor cellImgAnchor : this.cellImgAnchors) {
                int row1 = this.handlerRowByLabel(cellImgAnchor.getRow1());
                int col1 = cellImgAnchor.getCol1();
                int row2 = this.handlerRowByLabel(cellImgAnchor.getRow2());
                int col2 = cellImgAnchor.getCol2();
                int pictureIdx = this.workbook.addPicture(cellImgAnchor.getBytes(), cellImgAnchor.getPictureType());
                Drawing<?> drawing = this.sheet.createDrawingPatriarch();
                XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, col1, row1, col2, row2);
                drawing.createPicture(anchor, pictureIdx);
            }
        }
    }

    private void addFilters() {
        if (null != this.filters && this.filters.size() > 0) {
            for (FilterRegionCondition filterRegionCondition : this.filters) {
                String region = filterRegionCondition.getRegion();
                CellRangeAddress c = CellRangeAddress.valueOf(region);
                c.setFirstRow(this.handlerRowByLabel(c.getFirstRow()));
                c.setLastRow(this.handlerRowByLabel(c.getLastRow()));
                this.sheet.setAutoFilter(c);
                XSSFAutoFilterExtend autoFilter = new XSSFAutoFilterExtend(this.sheet);
                Map<String, List<FilterColCondition>> filterCols = filterRegionCondition.getFilterCols();
                Set<Map.Entry<String, List<FilterColCondition>>> entrySet = filterCols.entrySet();
                for (Map.Entry<String, List<FilterColCondition>> entry : entrySet) {
                    String colName = entry.getKey();
                    List<FilterColCondition> conditions = entry.getValue();
                    for (FilterColCondition condition : conditions) {
                        autoFilter.applyFilter(colName, condition.getFilterOperator(), condition.getValue(), condition.isAnd());
                    }
                }
                if (StringUtils.isEmpty((String)filterRegionCondition.getSortCol())) continue;
                autoFilter.applySort(filterRegionCondition.getSortCol(), filterRegionCondition.getIsAsc());
            }
        }
    }

    private void writeContent(List<String> startAndEndList) {
        int colCount = this.grid2Data.getColumnCount();
        int rowCount = this.grid2Data.getRowCount();
        for (int i = 1; i < rowCount; ++i) {
            for (int j = 1; j < colCount; ++j) {
                GridCellData cellData = this.grid2Data.getGridCellData(j, i);
                this.writeCell(cellData, startAndEndList);
            }
        }
    }

    private void writeCell(GridCellData cellData, List<String> startAndEndList) {
        XSSFCellStyle temp;
        int rowNum = cellData.getRowIndex() - 1;
        int colNum = cellData.getColIndex() - 1;
        SXSSFRow row = null;
        Row sheetRow = this.sheet.getRow(this.handlerRowByLabel(rowNum));
        if (sheetRow == null) {
            row = (SXSSFRow)this.sheet.createRow(this.handlerRowByLabel(rowNum));
            int rowCount = this.grid2Data.getRowCount();
            if (rowCount > 1000) {
                int rowHeight = this.grid2Data.getRowHeight(rowNum);
                if (row != null) {
                    row.setHeight((short)(rowHeight * 15));
                }
            }
        } else {
            row = (SXSSFRow)this.sheet.getRow(this.handlerRowByLabel(rowNum));
        }
        SXSSFCell cell = row.createCell(colNum);
        if (null != startAndEndList && !startAndEndList.isEmpty()) {
            for (String startAndEnd : startAndEndList) {
                String[] startAndEnds = startAndEnd.split(";");
                if (rowNum != Integer.parseInt(startAndEnds[1])) continue;
                this.sheet.groupRow(this.handlerRowByLabel(Integer.parseInt(startAndEnds[0])), this.handlerRowByLabel(Integer.parseInt(startAndEnds[1])));
            }
        }
        if (null != (temp = this.getFormat(cellData, cell))) {
            cell.setCellStyle(temp);
        }
    }

    private XSSFCellStyle getFormat(GridCellData cellData, SXSSFCell cell) {
        XSSFCellStyle style = null;
        switch (cellData.getDataType()) {
            case 1: {
                style = this.getStringFormat(cellData, cell);
                this.setFormual(cell);
                break;
            }
            case 2: {
                style = this.getDecimalFormat(cellData, cell);
                this.setFormual(cell);
                break;
            }
            case 4: {
                style = this.getBooleanFormat(cellData, cell);
                this.setFormual(cell);
                break;
            }
            case 5: {
                style = this.getDataTimeFormat(cellData, cell);
                this.setFormual(cell);
                break;
            }
            case 6: {
                style = this.getImgFormat(cellData, cell);
                break;
            }
            default: {
                style = this.getStringFormat(cellData, cell);
            }
        }
        return style;
    }

    private XSSFCellStyle getImgFormat(GridCellData cellData, SXSSFCell cell) {
        Object object = cellData.getCellData("IMG_DATA");
        if (null != object && object instanceof ImageDescriptor) {
            ImageDescriptor imageDescriptor = (ImageDescriptor)object;
            String extension = imageDescriptor.getImageId().toLowerCase();
            int pictureType = 5;
            if (extension.contains("emf")) {
                pictureType = 2;
            } else if (extension.contains("wmf")) {
                pictureType = 3;
            } else if (extension.contains("pict")) {
                pictureType = 4;
            } else if (extension.contains("png")) {
                pictureType = 6;
            } else if (extension.contains("dib")) {
                pictureType = 7;
            }
            int col2 = cell.getColumnIndex() + 1;
            int row2 = cell.getRowIndex() + 1;
            boolean merged = cellData.isMerged();
            if (merged) {
                Grid2FieldList fieldList = this.grid2Data.merges();
                for (int i = 0; i < fieldList.count(); ++i) {
                    Grid2CellField field = fieldList.get(i);
                    int rowFrom = field.top;
                    int colFrom = field.left;
                    if (cellData.getRowIndex() != rowFrom || cellData.getColIndex() != colFrom) continue;
                    col2 = field.right;
                    row2 = field.bottom;
                    break;
                }
            }
            CellImgAnchor cellImgAnchor = new CellImgAnchor();
            try {
                cellImgAnchor.setBytes(imageDescriptor.getImageData().getBytes());
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            cellImgAnchor.setPictureType(pictureType);
            cellImgAnchor.setCol1(cell.getColumnIndex());
            cellImgAnchor.setRow1(cell.getRowIndex());
            cellImgAnchor.setCol2(col2);
            cellImgAnchor.setRow2(row2);
            this.cellImgAnchors.add(cellImgAnchor);
        }
        return null;
    }

    private XSSFCellStyle getBooleanFormat(GridCellData cellData, SXSSFCell cell) {
        String styleKey;
        XSSFCellStyle style;
        String formatter;
        String[] trueAndFalse;
        if (!StringUtils.isEmpty((String)cellData.getEditText())) {
            cell.setCellValue(cellData.getEditText());
        } else if (!StringUtils.isEmpty((String)cellData.getShowText())) {
            cell.setCellValue(cellData.getShowText());
        }
        if (!StringUtils.isEmpty((String)cellData.getFormatter()) && (trueAndFalse = (formatter = cellData.getFormatter()).split("/")).length == 2) {
            String showTrue = trueAndFalse[0];
            String showfalse = trueAndFalse[1];
            if ("true".equalsIgnoreCase(cellData.getEditText())) {
                cell.setCellValue(showTrue);
            } else {
                cell.setCellValue(showfalse);
            }
        }
        if (null == (style = this.getCacheXSSFCellStyle(styleKey = this.buildStyleKey(cellData)))) {
            style = (XSSFCellStyle)this.workbook.createCellStyle();
            style = this.buildCellStyle(cellData);
            this.addCacheXSSFCellStyle(styleKey, style);
            return style;
        }
        return style;
    }

    private XSSFCellStyle getDecimalFormat(GridCellData cellData, SXSSFCell cell) {
        boolean isNumber;
        block18: {
            isNumber = true;
            try {
                if (!StringUtils.isEmpty((String)cellData.getEditText())) {
                    cell.setCellValue(Double.parseDouble(cellData.getEditText()));
                } else if (!StringUtils.isEmpty((String)cellData.getShowText())) {
                    BigDecimal bigDecimal;
                    NumberFormat numberInstance;
                    String tempValue = cellData.getShowText();
                    if (tempValue.contains(",")) {
                        numberInstance = DecimalFormat.getNumberInstance();
                        Object decimalCountObj = cellData.getCellData("decimalCount");
                        int decimal = 2;
                        if (decimalCountObj != null) {
                            decimal = Integer.parseInt(decimalCountObj.toString());
                        }
                        numberInstance.setMaximumFractionDigits(decimal);
                        Number number = numberInstance.parse(tempValue);
                        bigDecimal = new BigDecimal(number.toString());
                    } else if (tempValue.contains("%")) {
                        numberInstance = DecimalFormat.getPercentInstance();
                        Object decimalCountObj = cellData.getCellData("decimalCount");
                        int decimal = 2;
                        if (decimalCountObj != null) {
                            decimal = Integer.parseInt(decimalCountObj.toString());
                        }
                        numberInstance.setMaximumFractionDigits(decimal);
                        Number number = numberInstance.parse(tempValue);
                        bigDecimal = new BigDecimal(number.toString());
                    } else {
                        bigDecimal = new BigDecimal(tempValue);
                    }
                    cell.setCellValue(bigDecimal.doubleValue());
                }
            }
            catch (NumberFormatException e) {
                logger.error("excel\u5bfc\u51fa\uff0c\u6570\u503c\u7c7b\u578b\u5355\u5143\u683c\uff0c\u8bbe\u7f6e\u7684\u503c\u5374\u4e0d\u662f\u6570\u503c\uff01 editText:" + cellData.getEditText() + "  : showText:" + cellData.getShowText(), e);
                isNumber = false;
                if (!StringUtils.isEmpty((String)cellData.getEditText())) {
                    cell.setCellValue(cellData.getEditText());
                } else if (!StringUtils.isEmpty((String)cellData.getShowText())) {
                    cell.setCellValue(cellData.getShowText());
                }
            }
            catch (ParseException e) {
                logger.error("excel\u5bfc\u51fa\uff0c\u6570\u503c\u7c7b\u578b\u5355\u5143\u683c\uff0c\u8bbe\u7f6e\u7684\u503c\u5374\u4e0d\u662f\u6570\u503c\uff01 editText:" + cellData.getEditText() + "  : showText:" + cellData.getShowText(), e);
                isNumber = false;
                if (!StringUtils.isEmpty((String)cellData.getEditText())) {
                    cell.setCellValue(cellData.getEditText());
                }
                if (StringUtils.isEmpty((String)cellData.getShowText())) break block18;
                cell.setCellValue(cellData.getShowText());
            }
        }
        String styleKey = this.buildStyleKey(cellData);
        XSSFCellStyle style = this.getCacheXSSFCellStyle(styleKey);
        if (null == style) {
            style = (XSSFCellStyle)this.workbook.createCellStyle();
            style = this.buildCellStyle(cellData);
            if (StringUtils.isNotEmpty((String)cellData.getFormatter()) && isNumber) {
                DataFormat format = this.workbook.createDataFormat();
                style.setDataFormat(format.getFormat(cellData.getFormatter()));
            }
            this.addCacheXSSFCellStyle(styleKey, style);
            return style;
        }
        return style;
    }

    private XSSFCellStyle getDataTimeFormat(GridCellData cellData, SXSSFCell cell) {
        if (!StringUtils.isEmpty((String)cellData.getEditText())) {
            cell.setCellValue(cellData.getEditText());
        } else if (!StringUtils.isEmpty((String)cellData.getShowText())) {
            cell.setCellValue(cellData.getShowText());
        }
        String styleKey = this.buildStyleKey(cellData);
        XSSFCellStyle style = this.getCacheXSSFCellStyle(styleKey);
        if (null == style) {
            style = (XSSFCellStyle)this.workbook.createCellStyle();
            style = this.buildCellStyle(cellData);
            if (StringUtils.isNotEmpty((String)cellData.getFormatter())) {
                DataFormat format = this.workbook.createDataFormat();
                style.setDataFormat(format.getFormat(cellData.getFormatter()));
            }
            this.addCacheXSSFCellStyle(styleKey, style);
            return style;
        }
        return style;
    }

    private XSSFCellStyle getStringFormat(GridCellData cellData, SXSSFCell cell) {
        if (!StringUtils.isEmpty((String)cellData.getEditText())) {
            cell.setCellValue(cellData.getEditText());
        } else if (!StringUtils.isEmpty((String)cellData.getShowText())) {
            cell.setCellValue(cellData.getShowText());
        }
        String styleKey = this.buildStyleKey(cellData);
        XSSFCellStyle style = this.getCacheXSSFCellStyle(styleKey);
        if (null == style) {
            style = (XSSFCellStyle)this.workbook.createCellStyle();
            style = this.buildCellStyle(cellData);
            style.setDataFormat((short)0);
            style.setIndention((short)cellData.getIndent());
            this.addCacheXSSFCellStyle(styleKey, style);
            return style;
        }
        return style;
    }

    private String buildStyleKey(GridCellData cellData) {
        GridCellStyleData styleData = cellData.getCellStyleData();
        return styleData.hashCode() + "_" + cellData.getDataType() + "_" + cellData.getFormatter();
    }

    private XSSFCellStyle getCacheXSSFCellStyle(String styleKey) {
        if (null != this.cacheSXSSFWorkbook) {
            return this.cacheSXSSFWorkbook.getCacheXSSFCellStyleMap().get(styleKey);
        }
        return this.xSSFCellStyleMap.get(styleKey);
    }

    private void addCacheXSSFCellStyle(String styleKey, XSSFCellStyle xSSFCellStyle) {
        if (null != this.cacheSXSSFWorkbook) {
            this.cacheSXSSFWorkbook.addCacheXSSFCellStyleMap(styleKey, xSSFCellStyle);
        } else {
            this.xSSFCellStyleMap.put(styleKey, xSSFCellStyle);
        }
    }

    private XSSFCellStyle buildCellStyle(GridCellData cellData) {
        GridCellStyleData styleData = cellData.getCellStyleData();
        ColorOperateUtil colorUtil = new ColorOperateUtil(styleData);
        XSSFCellStyle style = (XSSFCellStyle)this.workbook.createCellStyle();
        style.setWrapText(styleData.isWrapLine());
        if (this.background) {
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(colorUtil.getForeGroundColor());
        }
        style.setFont(this.getCellFont(styleData));
        short horzAlign = this.transCellAlign(cellData.getHorzAlign(), true);
        short vertAlign = this.transCellAlign(cellData.getVertAlign(), false);
        style.setAlignment(HorizontalAlignment.forInt(horzAlign));
        style.setVerticalAlignment(VerticalAlignment.forInt(vertAlign));
        short rightBorder = this.transBorderStyle(styleData.getRightBorderStyle());
        short bottomBorder = this.transBorderStyle(styleData.getBottomBorderStyle());
        style.setBorderRight(BorderStyle.valueOf(rightBorder));
        style.setBorderBottom(BorderStyle.valueOf(bottomBorder));
        colorUtil.setEdgeColor(style);
        if (cellData.isVertText()) {
            style.setRotation((short)255);
        }
        return style;
    }

    private Font getCacheFont(String styleKey) {
        if (null != this.cacheSXSSFWorkbook) {
            return this.cacheSXSSFWorkbook.getCacheCellFontMap().get(styleKey);
        }
        return this.cellFontMap.get(styleKey);
    }

    private void addCacheFont(String styleKey, XSSFFont font) {
        if (null != this.cacheSXSSFWorkbook) {
            this.cacheSXSSFWorkbook.addCacheCellFontMap(styleKey, font);
        } else {
            this.cellFontMap.put(styleKey, font);
        }
    }

    private Font getCellFont(GridCellStyleData styleData) {
        XSSFColor fontColor = ColorOperateUtil.getFontColor(styleData);
        double sizeTemp = styleData.getFontSize();
        String fontName = styleData.getFontName();
        int fontStyle = styleData.getFontStyle();
        String styleKey = fontColor.getARGBHex() + fontName + fontStyle + sizeTemp;
        Font cacheFont = this.getCacheFont(styleKey);
        if (cacheFont != null) {
            return cacheFont;
        }
        XSSFFont font = (XSSFFont)this.workbook.createFont();
        font.setColor(fontColor);
        font.setFontHeightInPoints((short)Math.round(sizeTemp * 0.75));
        font.setFontName(fontName);
        if ((fontStyle & FontStyles2.FONT_BOLD.value) > 0) {
            font.setBold(true);
        }
        if ((fontStyle & FontStyles2.FONT_ITALIC.value) > 0) {
            font.setItalic(true);
        }
        if ((fontStyle & FontStyles2.FONT_U_SINGLE.value) > 0) {
            font.setUnderline((byte)1);
        }
        if ((fontStyle & FontStyles2.FONT_STRIKE_OUT.value) > 0) {
            font.setStrikeout(true);
        }
        this.addCacheFont(styleKey, font);
        return font;
    }

    private short transBorderStyle(int idx) {
        switch (idx) {
            case 0: {
                return BorderStyle.NONE.getCode();
            }
            case -1: {
                return BorderStyle.THIN.getCode();
            }
            case 1: {
                return BorderStyle.THIN.getCode();
            }
            case 2: {
                return BorderStyle.DASH_DOT.getCode();
            }
            case 4: {
                return BorderStyle.THICK.getCode();
            }
            case 8: {
                return BorderStyle.DOUBLE.getCode();
            }
        }
        return BorderStyle.THIN.getCode();
    }

    private short transCellAlign(int align, boolean isHorizontal) {
        if (isHorizontal) {
            switch (align) {
                case 0: {
                    return HorizontalAlignment.GENERAL.getCode();
                }
                case 1: {
                    return HorizontalAlignment.LEFT.getCode();
                }
                case 2: {
                    return HorizontalAlignment.RIGHT.getCode();
                }
                case 3: {
                    return HorizontalAlignment.CENTER.getCode();
                }
                case 4: 
                case 5: {
                    return HorizontalAlignment.JUSTIFY.getCode();
                }
                case 6: {
                    return HorizontalAlignment.RIGHT.getCode();
                }
            }
            return HorizontalAlignment.CENTER_SELECTION.getCode();
        }
        switch (align) {
            case 0: {
                return VerticalAlignment.CENTER.getCode();
            }
            case 1: {
                return VerticalAlignment.TOP.getCode();
            }
            case 2: {
                return VerticalAlignment.BOTTOM.getCode();
            }
            case 3: {
                return VerticalAlignment.CENTER.getCode();
            }
            case 4: 
            case 5: {
                return VerticalAlignment.JUSTIFY.getCode();
            }
            case 6: {
                return VerticalAlignment.BOTTOM.getCode();
            }
        }
        return VerticalAlignment.CENTER.getCode();
    }

    private void adjustHW() {
        int colCount = this.grid2Data.getColumnCount();
        int rowCount = this.grid2Data.getRowCount();
        if (this.sheet instanceof SXSSFSheet) {
            SXSSFSheet shit = (SXSSFSheet)this.sheet;
            shit.trackAllColumnsForAutoSizing();
        }
        for (int col = 1; col < colCount; ++col) {
            int colWidth = this.grid2Data.getColumnWidth(col);
            boolean columnAutoWidth = this.grid2Data.isColumnAutoWidth(col);
            if (columnAutoWidth) {
                try {
                    this.sheet.autoSizeColumn(col - 1);
                }
                catch (Exception e) {
                    logger.debug(e.getMessage());
                }
                continue;
            }
            this.sheet.setColumnWidth(col - 1, colWidth * 32);
        }
        if (rowCount < 1000) {
            for (int row = 1; row < rowCount; ++row) {
                int rowHeight = this.grid2Data.getRowHeight(row);
                Row xssfRow = this.sheet.getRow(this.handlerRowByLabel(row - 1));
                if (xssfRow == null) continue;
                xssfRow.setHeight((short)(rowHeight * 15));
            }
        }
    }

    private void mergeCells() {
        Grid2FieldList fieldList = this.grid2Data.merges();
        if (fieldList.count() < 1) {
            return;
        }
        for (int i = 0; i < fieldList.count(); ++i) {
            Grid2CellField field = fieldList.get(i);
            int rowFrom = field.top - 1;
            int rowTo = field.bottom - 1;
            int colFrom = field.left - 1;
            int colTo = field.right - 1;
            if (field.top > field.bottom || field.left > field.right || field.top == field.bottom && field.left == field.right) continue;
            try {
                this.sheet.addMergedRegion(new CellRangeAddress(this.handlerRowByLabel(rowFrom), this.handlerRowByLabel(rowTo), colFrom, colTo));
                continue;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private void hideRowCol() {
        for (int col = 1; col <= this.grid2Data.getColumnCount() - 1; ++col) {
            if (!this.grid2Data.isColumnHidden(col)) continue;
            this.sheet.setColumnHidden(col - 1, true);
        }
        for (int row = 1; row <= this.grid2Data.getRowCount() - 1; ++row) {
            if (!this.grid2Data.isRowHidden(row)) continue;
            this.sheet.getRow(this.handlerRowByLabel(row - 1)).setZeroHeight(true);
        }
    }

    private void setFormual(SXSSFCell cell) {
        if (null != this.formulaMap && this.formulaMap.size() > 0) {
            try {
                String englishName = this.index2ColName(cell.getColumnIndex() + 1);
                String mapKey = englishName + (cell.getRowIndex() + 1 - this.rowDeviation);
                String formaual = this.formulaMap.get(mapKey);
                if (!StringUtils.isEmpty((String)formaual)) {
                    if (this.rowDeviation > 0) {
                        String sheetName = this.sheet.getSheetName();
                        formaual = formaual.replace(sheetName, "\u4e34\u65f6\u66ff\u6362\u540d\u79f0");
                        Matcher fmatcher = this.fpattern.matcher(formaual);
                        String newFormaual = "";
                        int index = 0;
                        while (fmatcher.find()) {
                            String cellLocation = fmatcher.group();
                            Matcher nmatcher = this.npattern.matcher(cellLocation);
                            if (!nmatcher.find()) continue;
                            String rowIndex = nmatcher.group();
                            int row = Integer.valueOf(rowIndex) + this.rowDeviation;
                            String newLocation = cellLocation.replace(rowIndex + "", row + "");
                            if (formaual.indexOf(cellLocation) < 0) continue;
                            String temp = formaual.substring(index, formaual.indexOf(cellLocation) + cellLocation.length());
                            temp = temp.replace(cellLocation, newLocation);
                            newFormaual = newFormaual + temp;
                            index = formaual.indexOf(cellLocation) + cellLocation.length();
                        }
                        if (index < formaual.length()) {
                            newFormaual = newFormaual + formaual.substring(index);
                        }
                        formaual = newFormaual = newFormaual.replace("\u4e34\u65f6\u66ff\u6362\u540d\u79f0", sheetName);
                    }
                    cell.setCellFormula(formaual);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private String index2ColName(int index) {
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

    public void setBackground(boolean background) {
        this.background = background;
    }

    public void setFormulaMap(Map<String, String> formulaMap) {
        this.formulaMap = formulaMap;
    }

    public void setFilters(List<FilterRegionCondition> filters) {
        this.filters = filters;
    }

    public void setLinks(List<CellLink> links) {
        this.links = links;
    }

    public void setLabels(List<ExcelLabel> labels) {
        this.labels = labels;
    }
}

