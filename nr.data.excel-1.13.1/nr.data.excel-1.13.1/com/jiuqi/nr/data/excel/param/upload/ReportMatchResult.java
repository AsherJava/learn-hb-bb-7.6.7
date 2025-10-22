/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.param.upload;

import com.jiuqi.nr.data.excel.param.upload.RegionMatchInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportMatchResult {
    private final Map<String, List<Object>> cellValueMap;
    private final Map<String, List<String>> cellFormatMap;
    private Map<String, Integer> floatRowCountMap;
    private Map<String, Integer> beginRows;
    private List<RegionMatchInfo> completeMatchList;
    private int sheetBeginRow;
    private final Map<String, Integer> regionTabNums = new HashMap<String, Integer>();
    private Map<Integer, String> tabNames;

    public ReportMatchResult() {
        this.cellValueMap = new HashMap<String, List<Object>>();
        this.cellFormatMap = new HashMap<String, List<String>>();
    }

    public List<Object> getCellValue(String linkDataKey) {
        return this.cellValueMap.get(linkDataKey);
    }

    public void setCellValue(String linkDataKey, List<Object> cellValue) {
        this.cellValueMap.put(linkDataKey, cellValue);
    }

    public List<String> getCellFormat(String linkDataKey) {
        return this.cellFormatMap.get(linkDataKey);
    }

    public void setCellFormat(String linkDataKey, List<String> cellFormat) {
        this.cellFormatMap.put(linkDataKey, cellFormat);
    }

    public void setFloatRowCount(String region, int rowCount) {
        if (this.floatRowCountMap == null) {
            this.floatRowCountMap = new HashMap<String, Integer>();
        }
        this.floatRowCountMap.put(region, rowCount);
    }

    public int getFloatRowCount(String region) {
        Integer rowCount;
        if (this.floatRowCountMap != null && (rowCount = this.floatRowCountMap.get(region)) != null) {
            return rowCount;
        }
        return 0;
    }

    public int getBeginRows(String region) {
        Integer rowCount;
        if (this.beginRows != null && (rowCount = this.beginRows.get(region)) != null) {
            return rowCount;
        }
        return 0;
    }

    public void setBeginRows(String region, int rowCount) {
        if (this.beginRows == null) {
            this.beginRows = new HashMap<String, Integer>();
        }
        this.beginRows.put(region, rowCount);
    }

    public List<RegionMatchInfo> getCompleteMatchList() {
        return this.completeMatchList;
    }

    public void setCompleteMatchList(List<RegionMatchInfo> completeMatchList) {
        this.completeMatchList = completeMatchList;
    }

    public int getSheetBeginRow() {
        return this.sheetBeginRow;
    }

    public void setSheetBeginRow(int sheetBeginRow) {
        this.sheetBeginRow = sheetBeginRow;
    }

    public Map<String, Integer> getRegionTabNums() {
        return this.regionTabNums;
    }

    public Map<Integer, String> getTabNames() {
        return this.tabNames;
    }

    public void setTabNames(Map<Integer, String> tabNames) {
        this.tabNames = tabNames;
    }
}

