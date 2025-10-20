/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.print.domain;

import com.jiuqi.va.query.print.GridCellProp;
import com.jiuqi.va.query.print.TableCellData;
import com.jiuqi.va.query.print.TableCellProp;
import java.util.List;
import java.util.Map;

public class TablePrintDrawContext {
    private Map<String, GridCellProp> cellPropMap;
    Map<Integer, Map<Integer, TableCellProp>> excludeEndMap;
    Map<Integer, Map<Integer, TableCellData>> onlyEndMap;
    private List<Integer> hiddenColumnIndexList;

    public Map<String, GridCellProp> getCellPropMap() {
        return this.cellPropMap;
    }

    public void setCellPropMap(Map<String, GridCellProp> cellPropMap) {
        this.cellPropMap = cellPropMap;
    }

    public List<Integer> getHiddenColumnIndexList() {
        return this.hiddenColumnIndexList;
    }

    public void setHiddenColumnIndexList(List<Integer> hiddenColumnIndexList) {
        this.hiddenColumnIndexList = hiddenColumnIndexList;
    }

    public Map<Integer, Map<Integer, TableCellProp>> getExcludeEndMap() {
        return this.excludeEndMap;
    }

    public void setExcludeEndMap(Map<Integer, Map<Integer, TableCellProp>> excludeEndMap) {
        this.excludeEndMap = excludeEndMap;
    }

    public Map<Integer, Map<Integer, TableCellData>> getOnlyEndMap() {
        return this.onlyEndMap;
    }

    public void setOnlyEndMap(Map<Integer, Map<Integer, TableCellData>> onlyEndMap) {
        this.onlyEndMap = onlyEndMap;
    }
}

