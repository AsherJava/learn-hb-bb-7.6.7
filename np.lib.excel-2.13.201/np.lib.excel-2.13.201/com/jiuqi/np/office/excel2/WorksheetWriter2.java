/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridCellStyleData
 *  com.jiuqi.nvwa.grid2.graphics.ImageDescriptor
 */
package com.jiuqi.np.office.excel2;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.office.excel2.CacheSXSSFWorkbook;
import com.jiuqi.np.office.excel2.CellImgAnchor;
import com.jiuqi.np.office.excel2.ColorOperateUtil;
import com.jiuqi.np.office.excel2.NrGridCellStyleData;
import com.jiuqi.np.office.excel2.filter.FilterColCondition;
import com.jiuqi.np.office.excel2.filter.FilterRegionCondition;
import com.jiuqi.np.office.excel2.filter.XSSFAutoFilterExtend;
import com.jiuqi.np.office.excel2.group.GroupDirection;
import com.jiuqi.np.office.excel2.group.ISheetGroup;
import com.jiuqi.np.office.excel2.label.ExcelLabel;
import com.jiuqi.np.office.excel2.link.CellLink;
import com.jiuqi.np.office.excel2.print.IExcelPrintSetup;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridCellStyleData;
import com.jiuqi.nvwa.grid2.graphics.ImageDescriptor;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PageMargin;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorksheetWriter2 {
    private static final Logger logger = LoggerFactory.getLogger(WorksheetWriter2.class);
    public static final int DATA_TYPE_GENERAL_WITH_DATA = -1;
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
    private List<ISheetGroup> sheetGroups;
    private List<ISheetGroup> colGroups;
    private List<RowGroupInfo> rowGroups;
    private IExcelPrintSetup excelPrintSetup;

    public WorksheetWriter2(Grid2Data grid2Data, Sheet sheet, SXSSFWorkbook workbook) {
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
        this.initGroup();
        this.writeContent(startAndEndList);
        this.mergeCells();
        this.tryAddFormLeftTopBorder();
        this.addFilters();
        this.handlerCellImgAnchor();
        this.handlerCellLink();
        this.handleColGroup();
        this.handlePrintSetup();
    }

    private void initGroup() {
        if (this.sheetGroups != null && !this.sheetGroups.isEmpty()) {
            this.rowGroups = new ArrayList<RowGroupInfo>();
            this.colGroups = new ArrayList<ISheetGroup>();
            int randomAccessWindowSize = this.workbook.getRandomAccessWindowSize();
            for (ISheetGroup sheetGroup : this.sheetGroups) {
                GroupDirection groupDirection = sheetGroup.getGroupDirection();
                if (GroupDirection.ROW.equals((Object)groupDirection)) {
                    int labeledStartIdx = this.handlerRowByLabel(sheetGroup.getStartIndex());
                    int sNO = labeledStartIdx / randomAccessWindowSize;
                    int labeledEndIdx = this.handlerRowByLabel(sheetGroup.getEndIndex());
                    int eNO = labeledEndIdx / randomAccessWindowSize;
                    boolean collapsed = sheetGroup.isCollapsed();
                    if (sNO == eNO) {
                        this.rowGroups.add(new RowGroupInfo(labeledStartIdx, labeledEndIdx, collapsed));
                        continue;
                    }
                    if (sNO + 1 == eNO) {
                        this.rowGroups.add(new RowGroupInfo(labeledStartIdx, (sNO + 1) * randomAccessWindowSize - 1, collapsed));
                        this.rowGroups.add(new RowGroupInfo(eNO * randomAccessWindowSize, labeledEndIdx, false));
                        continue;
                    }
                    if (eNO <= sNO + 1) continue;
                    this.rowGroups.add(new RowGroupInfo(labeledStartIdx, (sNO + 1) * randomAccessWindowSize - 1, collapsed));
                    for (int i = sNO + 1; i < eNO; ++i) {
                        this.rowGroups.add(new RowGroupInfo(i * randomAccessWindowSize, (i + 1) * randomAccessWindowSize - 1, false));
                    }
                    this.rowGroups.add(new RowGroupInfo(eNO * randomAccessWindowSize, labeledEndIdx, false));
                    continue;
                }
                if (!GroupDirection.COL.equals((Object)groupDirection)) continue;
                this.colGroups.add(sheetGroup);
            }
        }
    }

    private void handleColGroup() {
        if (this.colGroups != null && !this.colGroups.isEmpty()) {
            for (ISheetGroup sheetGroup : this.colGroups) {
                int startIndex = sheetGroup.getStartIndex();
                int endIndex = sheetGroup.getEndIndex();
                boolean collapsed = sheetGroup.isCollapsed();
                this.sheet.groupColumn(startIndex, endIndex);
                if (!collapsed) continue;
                this.sheet.setColumnGroupCollapsed(startIndex, true);
            }
        }
        if (this.rowGroups != null && !this.rowGroups.isEmpty()) {
            for (RowGroupInfo rowGroup : this.rowGroups) {
                if (this.sheet.getRow(rowGroup.startIndex) == null || this.sheet.getRow(rowGroup.endIndex) == null) continue;
                this.sheet.groupRow(rowGroup.startIndex, rowGroup.endIndex);
                if (!rowGroup.collapsed) continue;
                this.sheet.setRowGroupCollapsed(rowGroup.startIndex, true);
            }
        }
    }

    private void handlePrintSetup() {
        int[] columnBreakIndex;
        int[] rowBreakIndex;
        boolean pageScale;
        if (this.excelPrintSetup == null) {
            return;
        }
        PrintSetup printSetup = this.sheet.getPrintSetup();
        if (this.excelPrintSetup.isLandscape()) {
            printSetup.setLandscape(true);
        }
        if (this.excelPrintSetup.isLeft2Right()) {
            printSetup.setLeftToRight(true);
        }
        if (this.excelPrintSetup.getPaperSize() >= 0) {
            printSetup.setPaperSize(this.excelPrintSetup.getPaperSize());
        }
        boolean bl = pageScale = this.excelPrintSetup.getFitWidth() >= 0 || this.excelPrintSetup.getFitHeight() >= 0;
        if (pageScale) {
            this.sheet.setAutobreaks(true);
            this.sheet.setFitToPage(true);
        }
        if (this.excelPrintSetup.getFitWidth() >= 0) {
            printSetup.setFitWidth(this.excelPrintSetup.getFitWidth());
        } else if (pageScale) {
            printSetup.setFitWidth((short)0);
        }
        if (this.excelPrintSetup.getFitHeight() >= 0) {
            printSetup.setFitHeight(this.excelPrintSetup.getFitHeight());
        } else if (pageScale) {
            printSetup.setFitHeight((short)0);
        }
        if (this.excelPrintSetup.getTopMargin() >= 0.0) {
            this.sheet.setMargin(PageMargin.TOP, this.excelPrintSetup.getTopMargin());
        }
        if (this.excelPrintSetup.getLeftMargin() >= 0.0) {
            this.sheet.setMargin(PageMargin.LEFT, this.excelPrintSetup.getLeftMargin());
        }
        if (this.excelPrintSetup.getRightMargin() >= 0.0) {
            this.sheet.setMargin(PageMargin.RIGHT, this.excelPrintSetup.getRightMargin());
        }
        if (this.excelPrintSetup.getBottomMargin() >= 0.0) {
            this.sheet.setMargin(PageMargin.BOTTOM, this.excelPrintSetup.getBottomMargin());
        }
        if (this.excelPrintSetup.isHorizontallyCenter()) {
            this.sheet.setHorizontallyCenter(true);
        }
        if (this.excelPrintSetup.isVerticallyCenter()) {
            this.sheet.setVerticallyCenter(true);
        }
        if ((rowBreakIndex = this.excelPrintSetup.getRowBreakIndex()) != null && rowBreakIndex.length > 0 && this.excelPrintSetup.getFitHeight() <= 0) {
            for (int breakIndex : rowBreakIndex) {
                this.sheet.setRowBreak(this.handlerRowByLabel(breakIndex));
            }
        }
        if ((columnBreakIndex = this.excelPrintSetup.getColumnBreakIndex()) != null && columnBreakIndex.length > 0 && this.excelPrintSetup.getFitWidth() <= 0) {
            for (int breakIndex : columnBreakIndex) {
                this.sheet.setColumnBreak(breakIndex);
            }
        }
    }

    private void tryAddFormLeftTopBorder() {
        try {
            this.addFormLeftTopBorder();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void addFormLeftTopBorder() {
        int form0RowIndex = this.handlerRowByLabel(0);
        int form0ColIndex = 0;
        List<CellRangeAddress> affectedMergedRegions = this.sheet.getMergedRegions().stream().filter(mergedRegion -> mergedRegion.getFirstColumn() == form0ColIndex || mergedRegion.getFirstRow() == form0RowIndex).collect(Collectors.toList());
        int lastRowNum = this.sheet.getLastRowNum();
        if (lastRowNum < form0RowIndex) {
            throw new IllegalArgumentException(String.format("form0RowIndex:%s;lastRowNum:%s;sheet:%s", form0RowIndex, lastRowNum, this.sheet.getSheetName()));
        }
        for (int i = form0RowIndex; i <= lastRowNum; ++i) {
            Grid2CellField grid2CellField;
            NrGridCellStyleData cellStyleData;
            Row row = this.sheet.getRow(i);
            if (row == null) continue;
            int lastCellNum = row.getLastCellNum();
            if (i == form0RowIndex) {
                for (int j = form0ColIndex; j < lastCellNum; ++j) {
                    Cell cell = row.getCell(j);
                    GridCellData gridCellData = this.grid2Data.getGridCellData(j + 1, i + 1 - this.rowDeviation);
                    if (cell == null || gridCellData == null || !(gridCellData.getCellStyleData() instanceof NrGridCellStyleData)) continue;
                    NrGridCellStyleData cellStyleData2 = (NrGridCellStyleData)gridCellData.getCellStyleData();
                    Grid2CellField grid2CellField2 = this.getGrid2CellField(this.grid2Data, gridCellData);
                    if (j == form0ColIndex && this.cellLeftBorderCopyRight(i, cellStyleData2, this.grid2Data, grid2CellField2) || this.cellTopBorderCopyBottom(j, cellStyleData2, this.grid2Data, grid2CellField2)) continue;
                    String styleKey = this.buildStyleKey(gridCellData);
                    XSSFCellStyle cacheXSSFCellStyle = this.getCacheXSSFCellStyle(styleKey);
                    if (cacheXSSFCellStyle == null) {
                        cacheXSSFCellStyle = (XSSFCellStyle)this.workbook.createCellStyle();
                        XSSFCellStyle curCellStyle = (XSSFCellStyle)cell.getCellStyle();
                        if (curCellStyle == null) continue;
                        cacheXSSFCellStyle.cloneStyleFrom(curCellStyle);
                        CellRangeAddress cellAddresses = this.getCellAddresses(affectedMergedRegions, cell);
                        if (j == form0ColIndex && this.poiCellLeftBorderCopyRight(row, cacheXSSFCellStyle, curCellStyle, cellAddresses) || this.poiCellTopBorderCopyBottom(j, cacheXSSFCellStyle, curCellStyle, cellAddresses)) continue;
                        this.addCacheXSSFCellStyle(styleKey, cacheXSSFCellStyle);
                    }
                    cell.setCellStyle(cacheXSSFCellStyle);
                }
                continue;
            }
            Cell cell = row.getCell(form0ColIndex);
            GridCellData gridCellData = this.grid2Data.getGridCellData(form0ColIndex + 1, i + 1 - this.rowDeviation);
            if (cell == null || gridCellData == null || !(gridCellData.getCellStyleData() instanceof NrGridCellStyleData) || this.cellLeftBorderCopyRight(i, cellStyleData = (NrGridCellStyleData)gridCellData.getCellStyleData(), this.grid2Data, grid2CellField = this.getGrid2CellField(this.grid2Data, gridCellData))) continue;
            String styleKey = this.buildStyleKey(gridCellData);
            XSSFCellStyle cacheXSSFCellStyle = this.getCacheXSSFCellStyle(styleKey);
            if (cacheXSSFCellStyle == null) {
                cacheXSSFCellStyle = (XSSFCellStyle)this.workbook.createCellStyle();
                XSSFCellStyle curCellStyle = (XSSFCellStyle)cell.getCellStyle();
                if (curCellStyle == null) continue;
                cacheXSSFCellStyle.cloneStyleFrom(curCellStyle);
                CellRangeAddress cellAddresses = this.getCellAddresses(affectedMergedRegions, cell);
                if (this.poiCellLeftBorderCopyRight(row, cacheXSSFCellStyle, curCellStyle, cellAddresses)) continue;
                this.addCacheXSSFCellStyle(styleKey, cacheXSSFCellStyle);
            }
            cell.setCellStyle(cacheXSSFCellStyle);
        }
    }

    private boolean poiCellTopBorderCopyBottom(int poiColIndex, XSSFCellStyle cacheXSSFCellStyle, XSSFCellStyle curCellStyle, CellRangeAddress cellAddresses) {
        XSSFCellStyle copyPOICellStyle;
        if (cellAddresses == null) {
            copyPOICellStyle = curCellStyle;
        } else {
            Row sheetRow = this.sheet.getRow(cellAddresses.getLastRow());
            if (sheetRow == null) {
                return true;
            }
            Cell copyPOICell = sheetRow.getCell(poiColIndex);
            if (copyPOICell == null) {
                return true;
            }
            copyPOICellStyle = (XSSFCellStyle)copyPOICell.getCellStyle();
        }
        if (copyPOICellStyle == null) {
            return true;
        }
        BorderStyle copyBorderBottom = copyPOICellStyle.getBorderBottom();
        if (cacheXSSFCellStyle.getBorderTop() != copyBorderBottom) {
            cacheXSSFCellStyle.setBorderTop(copyBorderBottom);
        }
        XSSFColor copyBottomBorderXSSFColor = copyPOICellStyle.getBottomBorderXSSFColor();
        if (cacheXSSFCellStyle.getTopBorderXSSFColor() != null || copyBottomBorderXSSFColor != null) {
            cacheXSSFCellStyle.setTopBorderColor(copyBottomBorderXSSFColor);
        }
        return false;
    }

    private boolean cellTopBorderCopyBottom(int poiColIndex, NrGridCellStyleData cellStyleData, Grid2Data grid2Data, Grid2CellField grid2CellField) {
        NrGridCellStyleData copyCellStyle;
        if (grid2CellField == null) {
            copyCellStyle = cellStyleData;
        } else {
            GridCellData copyCell = grid2Data.getGridCellData(poiColIndex + 1, grid2CellField.bottom);
            if (copyCell == null || !(copyCell.getCellStyleData() instanceof NrGridCellStyleData)) {
                return true;
            }
            copyCellStyle = (NrGridCellStyleData)copyCell.getCellStyleData();
        }
        cellStyleData.setTopBorderStyle(copyCellStyle.getBottomBorderStyle());
        cellStyleData.setTopBorderColor(copyCellStyle.getBottomBorderColor());
        return false;
    }

    private boolean cellLeftBorderCopyRight(int poiRowIndex, NrGridCellStyleData cellStyleData, Grid2Data grid2Data, Grid2CellField grid2CellField) {
        NrGridCellStyleData copyCellStyle;
        if (grid2CellField == null) {
            copyCellStyle = cellStyleData;
        } else {
            GridCellData copyCell = grid2Data.getGridCellData(grid2CellField.right, poiRowIndex + 1 - this.rowDeviation);
            if (copyCell == null || !(copyCell.getCellStyleData() instanceof NrGridCellStyleData)) {
                return true;
            }
            copyCellStyle = (NrGridCellStyleData)copyCell.getCellStyleData();
        }
        cellStyleData.setLeftBorderStyle(copyCellStyle.getRightBorderStyle());
        cellStyleData.setLeftBorderColor(copyCellStyle.getRightBorderColor());
        return false;
    }

    private boolean poiCellLeftBorderCopyRight(Row row, XSSFCellStyle cacheXSSFCellStyle, XSSFCellStyle curCellStyle, CellRangeAddress cellAddresses) {
        XSSFCellStyle copyPOICellStyle;
        if (cellAddresses == null) {
            copyPOICellStyle = curCellStyle;
        } else {
            Cell copyPOICell = row.getCell(cellAddresses.getLastColumn());
            if (copyPOICell == null) {
                return true;
            }
            copyPOICellStyle = (XSSFCellStyle)copyPOICell.getCellStyle();
        }
        if (copyPOICellStyle == null) {
            return true;
        }
        BorderStyle copyBorderRight = copyPOICellStyle.getBorderRight();
        if (cacheXSSFCellStyle.getBorderLeft() != copyBorderRight) {
            cacheXSSFCellStyle.setBorderLeft(copyBorderRight);
        }
        XSSFColor copyRightBorderXSSFColor = copyPOICellStyle.getRightBorderXSSFColor();
        if (cacheXSSFCellStyle.getLeftBorderXSSFColor() != null || copyRightBorderXSSFColor != null) {
            cacheXSSFCellStyle.setLeftBorderColor(copyRightBorderXSSFColor);
        }
        return false;
    }

    private CellRangeAddress getCellAddresses(List<CellRangeAddress> mergedRegions, Cell cell) {
        if (CollectionUtils.isEmpty(mergedRegions)) {
            return null;
        }
        for (CellRangeAddress mergedRegion : mergedRegions) {
            if (!mergedRegion.isInRange(cell)) continue;
            return mergedRegion;
        }
        return null;
    }

    private Grid2CellField getGrid2CellField(Grid2Data grid2Data, GridCellData cell) {
        Grid2FieldList merges = grid2Data.merges();
        if (merges.count() <= 0) {
            return null;
        }
        for (int i = 0; i < merges.count(); ++i) {
            Grid2CellField grid2CellField = merges.get(i);
            if (grid2CellField.left > cell.getColIndex() || grid2CellField.right < cell.getColIndex() || grid2CellField.top > cell.getRowIndex() || grid2CellField.bottom < cell.getRowIndex()) continue;
            return grid2CellField;
        }
        return null;
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
        for (int j = 1; j < colCount; ++j) {
            this.adjustColumnWidth(j);
            this.hideCol(j);
        }
        int randomAccessWindowSize = this.workbook.getRandomAccessWindowSize();
        for (int i = 1; i < rowCount; ++i) {
            for (int j = 1; j < colCount; ++j) {
                GridCellData cellData = this.grid2Data.getGridCellData(j, i);
                this.replaceNrCellStyle(cellData);
                this.writeCell(cellData, startAndEndList);
            }
            this.adjustRowHeight(i);
            this.hideRow(i);
            this.handleRowGroupIfNeed(this.handlerRowByLabel(i - 1), randomAccessWindowSize);
        }
    }

    private void handleRowGroupIfNeed(int poiRowIndex, int randomAccessWindowSize) {
        if (this.rowGroups == null || this.rowGroups.isEmpty()) {
            return;
        }
        if (poiRowIndex <= 0) {
            return;
        }
        if ((poiRowIndex + 1) % randomAccessWindowSize != 0) {
            return;
        }
        int curNO = poiRowIndex / randomAccessWindowSize;
        int startIndex = curNO * randomAccessWindowSize;
        int endIndex = (curNO + 1) * randomAccessWindowSize - 1;
        Iterator<RowGroupInfo> iterator = this.rowGroups.iterator();
        while (iterator.hasNext()) {
            RowGroupInfo rowGroup = iterator.next();
            if (rowGroup.startIndex < startIndex || rowGroup.endIndex > endIndex || this.sheet.getRow(rowGroup.startIndex) == null) continue;
            this.sheet.groupRow(rowGroup.startIndex, rowGroup.endIndex);
            if (rowGroup.collapsed) {
                this.sheet.setRowGroupCollapsed(rowGroup.startIndex, true);
            }
            iterator.remove();
        }
    }

    private void replaceNrCellStyle(GridCellData cellData) {
        GridCellData copyCell;
        int rowIndex = cellData.getRowIndex();
        int colIndex = cellData.getColIndex();
        GridCellStyleData cellStyleData = cellData.getCellStyleData();
        NrGridCellStyleData nrGridCellStyleData = new NrGridCellStyleData(cellStyleData);
        if (rowIndex > 1 && (copyCell = this.grid2Data.getGridCellData(colIndex, rowIndex - 1)) != null) {
            nrGridCellStyleData.setTopBorderStyle(copyCell.getBottomBorderStyle());
            nrGridCellStyleData.setTopBorderColor(copyCell.getBottomBorderColor());
        }
        if (colIndex > 1 && (copyCell = this.grid2Data.getGridCellData(colIndex - 1, rowIndex)) != null) {
            nrGridCellStyleData.setLeftBorderStyle(copyCell.getRightBorderStyle());
            nrGridCellStyleData.setLeftBorderColor(copyCell.getRightBorderColor());
        }
        cellData.setCellStyleData((GridCellStyleData)nrGridCellStyleData);
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
            String stringCellValue;
            cell.setCellStyle(temp);
            if (cellData.getDataType() == 5 && cellData.getFormatter().equals("yyyy-MM") && (stringCellValue = cell.getStringCellValue()) != null && !stringCellValue.equals("")) {
                SimpleDateFormat dfD = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date parse = dfD.parse(stringCellValue);
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(parse);
                }
                catch (ParseException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    private XSSFCellStyle getFormat(GridCellData cellData, SXSSFCell cell) {
        XSSFCellStyle style = null;
        switch (cellData.getDataType()) {
            case -1: {
                style = this.getGeneralFormat(cellData, cell);
                this.setFormual(cell);
                break;
            }
            case 1: {
                style = this.getTextFormat(cellData, cell);
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
                style = this.getGeneralFormat(cellData, cell);
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
            style = this.buildCellStyle(cellData);
            this.addCacheXSSFCellStyle(styleKey, style);
            return style;
        }
        return style;
    }

    private XSSFCellStyle getDecimalFormat(GridCellData cellData, SXSSFCell cell) {
        String styleKey;
        XSSFCellStyle style;
        String cellValue = cellData.getEditText();
        if (StringUtils.isEmpty((String)cellValue)) {
            cellValue = cellData.getShowText();
        }
        if (StringUtils.isNotEmpty((String)cellValue)) {
            try {
                BigDecimal bigDecimal = new BigDecimal(cellValue);
                cell.setCellValue(bigDecimal.doubleValue());
            }
            catch (NumberFormatException e) {
                logger.debug("\u6570\u503c\u5355\u5143\u683c\u7c7b\u578b\u8f6c\u5316\u5f02\u5e38:{}\n\u5f02\u5e38\u503c:{}\n\u5f02\u5e38\u5355\u5143\u683c\u5750\u6807:{}-{}", e.getMessage(), cellValue, cellData.getRowIndex(), cellData.getColIndex());
                cell.setCellValue(cellValue);
            }
        }
        if (null == (style = this.getCacheXSSFCellStyle(styleKey = this.buildStyleKey(cellData)))) {
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

    private XSSFCellStyle getDataTimeFormat(GridCellData cellData, SXSSFCell cell) {
        if (!StringUtils.isEmpty((String)cellData.getEditText())) {
            cell.setCellValue(cellData.getEditText());
        } else if (!StringUtils.isEmpty((String)cellData.getShowText())) {
            cell.setCellValue(cellData.getShowText());
        }
        String styleKey = this.buildStyleKey(cellData);
        XSSFCellStyle style = this.getCacheXSSFCellStyle(styleKey);
        if (null == style) {
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

    private XSSFCellStyle getGeneralFormat(GridCellData cellData, SXSSFCell cell) {
        if (!StringUtils.isEmpty((String)cellData.getEditText())) {
            cell.setCellValue(cellData.getEditText());
        } else if (!StringUtils.isEmpty((String)cellData.getShowText())) {
            cell.setCellValue(cellData.getShowText());
        }
        String styleKey = this.buildStyleKey(cellData);
        XSSFCellStyle style = this.getCacheXSSFCellStyle(styleKey);
        if (null == style) {
            style = this.buildCellStyle(cellData);
            style.setDataFormat((short)0);
            style.setIndention((short)cellData.getIndent());
            this.addCacheXSSFCellStyle(styleKey, style);
            return style;
        }
        return style;
    }

    private XSSFCellStyle getTextFormat(GridCellData cellData, SXSSFCell cell) {
        if (!StringUtils.isEmpty((String)cellData.getEditText())) {
            cell.setCellValue(cellData.getEditText());
        } else if (!StringUtils.isEmpty((String)cellData.getShowText())) {
            cell.setCellValue(cellData.getShowText());
        }
        String styleKey = this.buildStyleKey(cellData);
        XSSFCellStyle style = this.getCacheXSSFCellStyle(styleKey);
        if (null == style) {
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
        if (styleData instanceof NrGridCellStyleData) {
            NrGridCellStyleData nrCellStyle = (NrGridCellStyleData)styleData;
            short leftBorder = this.transBorderStyle(nrCellStyle.getLeftBorderStyle());
            short topBorder = this.transBorderStyle(nrCellStyle.getTopBorderStyle());
            style.setBorderLeft(BorderStyle.valueOf(leftBorder));
            style.setBorderTop(BorderStyle.valueOf(topBorder));
            style.setLeftBorderColor(colorUtil.getEdgeColor(nrCellStyle.getLeftBorderColor()));
            style.setTopBorderColor(colorUtil.getEdgeColor(nrCellStyle.getTopBorderColor()));
        }
        if (cellData.isVertText()) {
            style.setRotation((short)255);
        }
        if (styleData.isFitFontSize()) {
            style.setShrinkToFit(true);
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
        if ((fontStyle & 2) > 0) {
            font.setBold(true);
        }
        if ((fontStyle & 4) > 0) {
            font.setItalic(true);
        }
        if ((fontStyle & 8) > 0) {
            font.setUnderline((byte)1);
        }
        if ((fontStyle & 0x20) > 0) {
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
            case 9: {
                return BorderStyle.DOTTED.getCode();
            }
            case 10: {
                return BorderStyle.DASHED.getCode();
            }
            case 11: {
                return BorderStyle.DASH_DOT_DOT.getCode();
            }
            case 12: {
                return BorderStyle.MEDIUM_DASHED.getCode();
            }
            case 13: {
                return BorderStyle.MEDIUM_DASH_DOT.getCode();
            }
            case 14: {
                return BorderStyle.MEDIUM_DASH_DOT_DOT.getCode();
            }
            case 15: {
                return BorderStyle.HAIR.getCode();
            }
            case 16: {
                return BorderStyle.SLANTED_DASH_DOT.getCode();
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

    private void adjustRowHeight(int row) {
        SXSSFRow xssfRow = (SXSSFRow)this.sheet.getRow(this.handlerRowByLabel(row - 1));
        if (this.grid2Data.isRowAutoHeight(row)) {
            xssfRow.setHeightInPoints(-1.0f);
        } else {
            int rowHeight = this.grid2Data.getRowHeight(row);
            if (xssfRow != null) {
                xssfRow.setHeight((short)(rowHeight * 15));
            }
        }
    }

    private void adjustColumnWidth(int col) {
        int colWidth = this.grid2Data.getColumnWidth(col);
        boolean columnAutoWidth = this.grid2Data.isColumnAutoWidth(col);
        if (columnAutoWidth) {
            try {
                this.sheet.autoSizeColumn(col - 1);
            }
            catch (Exception e) {
                logger.debug(e.getMessage());
            }
        } else {
            this.sheet.setColumnWidth(col - 1, colWidth * 32);
        }
    }

    @Deprecated
    private void adjustHW() {
        int colCount = this.grid2Data.getColumnCount();
        int rowCount = this.grid2Data.getRowCount();
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

    @Deprecated
    private void adjustHW(int row, int col) {
        SXSSFRow xssfRow = (SXSSFRow)this.sheet.getRow(this.handlerRowByLabel(row - 1));
        if (this.grid2Data.isRowAutoHeight(row)) {
            xssfRow.setHeightInPoints(-1.0f);
        } else {
            int rowHeight = this.grid2Data.getRowHeight(row);
            if (xssfRow != null) {
                xssfRow.setHeight((short)(rowHeight * 15));
            }
        }
        int colWidth = this.grid2Data.getColumnWidth(col);
        boolean columnAutoWidth = this.grid2Data.isColumnAutoWidth(col);
        if (columnAutoWidth) {
            try {
                this.sheet.autoSizeColumn(col - 1);
            }
            catch (Exception e) {
                logger.debug(e.getMessage());
            }
        } else {
            this.sheet.setColumnWidth(col - 1, colWidth * 32);
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

    private boolean whetherHideRowCol(int row, int col) {
        int sheetRowIndex = this.handlerRowByLabel(row - 1);
        int sheetColIndex = col - 1;
        if (this.grid2Data.isRowHidden(row) || this.grid2Data.isColumnHidden(col)) {
            if (this.sheetGroups != null && !this.sheetGroups.isEmpty()) {
                for (ISheetGroup sheetGroup : this.sheetGroups) {
                    GroupDirection groupDirection = sheetGroup.getGroupDirection();
                    int startIndex = sheetGroup.getStartIndex();
                    int endIndex = sheetGroup.getEndIndex();
                    if (GroupDirection.ROW == groupDirection) {
                        int labeledStartIdx = this.handlerRowByLabel(startIndex);
                        int labeledEndIdx = this.handlerRowByLabel(endIndex);
                        if (labeledStartIdx > sheetRowIndex || labeledEndIdx < sheetRowIndex) continue;
                        return false;
                    }
                    if (GroupDirection.COL != groupDirection || startIndex > sheetColIndex || endIndex < sheetColIndex) continue;
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private void hideRow(int row) {
        if (this.grid2Data.isRowHidden(row)) {
            this.sheet.getRow(this.handlerRowByLabel(row - 1)).setZeroHeight(true);
        }
    }

    private void hideCol(int col) {
        if (this.grid2Data.isColumnHidden(col)) {
            this.sheet.setColumnHidden(col - 1, true);
        }
    }

    @Deprecated
    private void hideRowCol(int row, int col) {
        if (this.grid2Data.isRowHidden(row)) {
            this.sheet.getRow(this.handlerRowByLabel(row - 1)).setZeroHeight(true);
        }
        if (this.grid2Data.isColumnHidden(col)) {
            this.sheet.setColumnHidden(col - 1, true);
        }
    }

    private void setFormual(SXSSFCell cell) {
        if (null != this.formulaMap && this.formulaMap.size() > 0) {
            try {
                String englishName = this.index2ColName(cell.getColumnIndex() + 1);
                String mapKey = englishName + (cell.getRowIndex() + 1);
                String formaual = this.formulaMap.get(mapKey);
                if (!StringUtils.isEmpty((String)formaual)) {
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

    public void setSheetGroups(List<? extends ISheetGroup> sheetGroups) {
        if (sheetGroups != null && !sheetGroups.isEmpty()) {
            this.sheetGroups = new ArrayList<ISheetGroup>();
            this.sheetGroups.addAll(sheetGroups);
        } else {
            this.sheetGroups = null;
        }
    }

    public void setExcelPrintSetup(IExcelPrintSetup excelPrintSetup) {
        this.excelPrintSetup = excelPrintSetup;
    }

    static class RowGroupInfo {
        int startIndex;
        int endIndex;
        boolean collapsed;

        public RowGroupInfo(int startIndex, int endIndex, boolean collapsed) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.collapsed = collapsed;
        }
    }
}

