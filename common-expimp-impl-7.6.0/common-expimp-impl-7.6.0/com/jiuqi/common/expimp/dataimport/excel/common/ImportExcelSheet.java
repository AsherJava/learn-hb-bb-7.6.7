/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.dataimport.excel.common;

import com.jiuqi.common.expimp.converters.ExpImpConverter;
import com.jiuqi.common.expimp.converters.impl.DefaultExpImpConverter;
import java.util.List;
import java.util.stream.Collectors;

public class ImportExcelSheet {
    private Integer sheetNo;
    private String sheetName;
    private Integer sheetHeadSize;
    private List<ImportExcelSheetRowData> excelSheetRowDatas;
    private ExpImpConverter converter;

    public ImportExcelSheet(Integer sheetNo, String sheetName) {
        this(sheetNo, sheetName, 1);
    }

    public ImportExcelSheet(Integer sheetNo, String sheetName, Integer sheetHeadSize) {
        this(sheetNo, sheetName, sheetHeadSize, new DefaultExpImpConverter());
    }

    public ImportExcelSheet(Integer sheetNo, String sheetName, Integer sheetHeadSize, ExpImpConverter converter) {
        this.sheetNo = sheetNo;
        this.sheetName = sheetName;
        this.sheetHeadSize = sheetHeadSize;
        this.converter = converter;
    }

    public Integer getSheetNo() {
        return this.sheetNo;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public List<Object[]> getExcelSheetDatas() {
        List<ImportExcelSheetRowData> excelSheetDataRows = this.getExcelSheetRowDatas();
        List<Object[]> excelSheetDatas = excelSheetDataRows.stream().map(ImportExcelSheetRowData::getRowData).collect(Collectors.toList());
        return excelSheetDatas;
    }

    public List<ImportExcelSheetRowData> getExcelSheetRowDatas() {
        return this.excelSheetRowDatas;
    }

    public void setExcelSheetRowDatas(List<ImportExcelSheetRowData> excelSheetRowDatas) {
        this.excelSheetRowDatas = excelSheetRowDatas;
    }

    public Integer getSheetHeadSize() {
        return this.sheetHeadSize;
    }

    public void setSheetHeadSize(Integer sheetHeadSize) {
        this.sheetHeadSize = sheetHeadSize;
    }

    public ExpImpConverter getConverter() {
        return this.converter;
    }

    public void setConverter(ExpImpConverter converter) {
        this.converter = converter;
    }

    public static class ImportExcelSheetRowData {
        private Integer rowIndex;
        private Object[] rowData;

        public ImportExcelSheetRowData() {
        }

        public ImportExcelSheetRowData(Integer rowIndex, Object[] rowData) {
            this.rowIndex = rowIndex;
            this.rowData = rowData;
        }

        public Integer getRowIndex() {
            return this.rowIndex;
        }

        public void setRowIndex(Integer rowIndex) {
            this.rowIndex = rowIndex;
        }

        public Object[] getRowData() {
            return this.rowData;
        }

        public void setRowData(Object[] rowData) {
            this.rowData = rowData;
        }
    }
}

