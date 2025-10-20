/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 */
package com.jiuqi.common.expimp.dataexport.excel.common;

import com.jiuqi.common.expimp.converters.ExpImpConverter;
import com.jiuqi.common.expimp.converters.impl.DefaultExpImpConverter;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;

public class ExportExcelSheet {
    private Integer sheetNo;
    private String sheetName;
    private Integer sheetHeadSize;
    private final List<Object[]> rowDatas = new CopyOnWriteArrayList<Object[]>();
    private final Map<Integer, CellStyle> headCellStyleCache = new ConcurrentHashMap<Integer, CellStyle>();
    private final Map<Integer, CellStyle> contentCellStyleCache = new ConcurrentHashMap<Integer, CellStyle>();
    private final Map<Integer, CellType> contentCellTypeCache = new ConcurrentHashMap<Integer, CellType>();
    private final Map<Integer, Integer> columnWidthCache = new ConcurrentHashMap<Integer, Integer>();
    private ExpImpConverter converter;
    private final List<CellRangeAddress> cellRangeAddresses = new ArrayList<CellRangeAddress>();
    private boolean autoMergeHeadFlag = true;

    public ExportExcelSheet(Integer sheetNo, String sheetName) {
        this(sheetNo, sheetName, 1);
    }

    public ExportExcelSheet(Integer sheetNo, String sheetName, Integer sheetHeadSize) {
        this(sheetNo, sheetName, sheetHeadSize, new DefaultExpImpConverter());
    }

    public ExportExcelSheet(Integer sheetNo, String sheetName, Integer sheetHeadSize, ExpImpConverter converter) {
        this.sheetNo = sheetNo;
        this.sheetName = sheetName;
        this.sheetHeadSize = sheetHeadSize;
        this.converter = converter;
    }

    public Integer getSheetNo() {
        return this.sheetNo;
    }

    public void setSheetNo(Integer sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public Integer getSheetHeadSize() {
        return this.sheetHeadSize;
    }

    public List<Object[]> getRowDatas() {
        return this.rowDatas;
    }

    public Map<Integer, CellStyle> getHeadCellStyleCache() {
        return this.headCellStyleCache;
    }

    public Map<Integer, CellStyle> getContentCellStyleCache() {
        return this.contentCellStyleCache;
    }

    public Map<Integer, CellType> getContentCellTypeCache() {
        return this.contentCellTypeCache;
    }

    public List<CellRangeAddress> getCellRangeAddresses() {
        return this.cellRangeAddresses;
    }

    public boolean isAutoMergeHeadFlag() {
        return this.autoMergeHeadFlag;
    }

    public void setAutoMergeHeadFlag(boolean autoMergeHeadFlag) {
        this.autoMergeHeadFlag = autoMergeHeadFlag;
    }

    public ExpImpConverter getConverter() {
        return this.converter;
    }

    public void setConverter(ExpImpConverter converter) {
        this.converter = converter;
    }

    public Integer getColumnMaxIndex() {
        if (CollectionUtils.isEmpty(this.rowDatas)) {
            return -1;
        }
        return this.rowDatas.get(0).length - 1;
    }

    public void setColumnWidths(int[] columnWidths) {
        if (null == columnWidths) {
            return;
        }
        for (int i = 0; i < columnWidths.length; ++i) {
            this.columnWidthCache.put(i, columnWidths[i]);
        }
    }

    public Map<Integer, Integer> getColumnWidthCache() {
        return this.columnWidthCache;
    }

    public void callBackSheet(ExportContext context, Workbook workbook, Sheet sheet) {
        Map<Integer, Integer> columnWidthMap = this.getColumnWidthCache();
        for (Map.Entry<Integer, Integer> entry : columnWidthMap.entrySet()) {
            sheet.setColumnWidth(entry.getKey().intValue(), entry.getValue().intValue());
        }
    }

    public void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
    }
}

