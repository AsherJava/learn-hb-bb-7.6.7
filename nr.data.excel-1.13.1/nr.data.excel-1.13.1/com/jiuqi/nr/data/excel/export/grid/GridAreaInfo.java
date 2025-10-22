/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.export.grid;

import com.jiuqi.nr.data.excel.export.grid.GridArea;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridAreaInfo {
    private String formKey;
    private final List<GridArea> gridAreaList = new ArrayList<GridArea>(2);
    private final Map<String, List<Integer>> regionAreaIndexMap = new HashMap<String, List<Integer>>(2);
    private boolean splitSheet;
    private int moreRow;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public void addGridArea(GridArea gridArea) {
        this.gridAreaList.add(gridArea);
        if (this.regionAreaIndexMap.containsKey(gridArea.getRegionKey())) {
            List<Integer> indexList = this.regionAreaIndexMap.get(gridArea.getRegionKey());
            indexList.add(this.gridAreaList.size() - 1);
        } else {
            ArrayList<Integer> indexList = new ArrayList<Integer>();
            indexList.add(this.gridAreaList.size() - 1);
            this.regionAreaIndexMap.put(gridArea.getRegionKey(), indexList);
        }
    }

    public List<GridArea> getGridAreaList() {
        return this.gridAreaList;
    }

    public Map<String, List<Integer>> getRegionAreaIndexMap() {
        return this.regionAreaIndexMap;
    }

    public boolean isSplitSheet() {
        return this.splitSheet;
    }

    public void setSplitSheet(boolean splitSheet) {
        this.splitSheet = splitSheet;
    }

    public int getMoreRow() {
        return this.moreRow;
    }

    public void setMoreRow(int moreRow) {
        this.moreRow = moreRow;
    }
}

