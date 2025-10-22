/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.excelUpload;

import com.jiuqi.nr.dataentry.excelUpload.RegionMatchInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportMatchResult {
    private final Map<String, List<String>> cellValueMap = new HashMap<String, List<String>>();
    private Map<String, Integer> floatRowCountMap;
    private Map<String, Integer> beginRows;
    private List<RegionMatchInfo> completeMatchList;
    private int sheetBeginRow;

    public List<String> getCellValue(String linkDataKey) {
        return this.cellValueMap.get(linkDataKey);
    }

    public void setCellValue(String linkDataKey, List<String> cellValue) {
        this.cellValueMap.put(linkDataKey, cellValue);
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
}

