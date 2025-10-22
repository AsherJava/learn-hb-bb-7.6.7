/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GradeDataLoader {
    private RegionDataSet regionDataSet;
    private IRegionDataSet crudRegionDataSet;
    private int currIndex;
    public static int groupData = 1;
    public static int detailData = 2;

    public GradeDataLoader(IRegionDataSet crudRegionDataSet, RegionDataSet regionDataSet) {
        this.regionDataSet = regionDataSet;
        this.crudRegionDataSet = crudRegionDataSet;
    }

    public void loadRegionGradeData() {
        this.currIndex = 0;
        while (this.currIndex < this.crudRegionDataSet.getRowCount()) {
            this.loadRootRowData();
        }
    }

    private void loadRootRowData() {
        int rowIndex = this.currIndex;
        int deep = 1;
        IRowData iRowData = (IRowData)this.crudRegionDataSet.getRowData().get(rowIndex);
        this.loadRowDimension(iRowData);
        ArrayList<Serializable> relRow = new ArrayList<Serializable>();
        this.regionDataSet.getRel().add(rowIndex, relRow);
        this.regionDataSet.getRoot().add(rowIndex);
        relRow.add(0, Integer.valueOf(rowIndex));
        List<Object> children = new ArrayList();
        if (iRowData.getGroupTreeDeep() >= 0) {
            int groupingLevel = iRowData.getGroupTreeDeep();
            children = this.loadChildRowData(rowIndex, groupingLevel, deep + 1);
        }
        relRow.add(1, (Serializable)((Object)children));
        relRow.add(2, Integer.valueOf(deep));
        relRow.add(3, Integer.valueOf(-1));
        relRow.add(4, Integer.valueOf(iRowData.getGroupTreeDeep() >= 0 ? groupData : detailData));
        relRow.add(5, Integer.valueOf(iRowData.getDetailSeqNum()));
    }

    private List<Integer> loadChildRowData(int parentIndex, int parentLevel, int deep) {
        ArrayList<Integer> brothers = new ArrayList<Integer>();
        int currLever = Integer.MAX_VALUE;
        while (this.currIndex < this.crudRegionDataSet.getRowCount()) {
            int rowIndex = this.currIndex;
            IRowData iRowData = (IRowData)this.crudRegionDataSet.getRowData().get(rowIndex);
            int rowLever = iRowData.getGroupTreeDeep();
            int rowParentLever = -1;
            if (rowLever >= parentLevel) {
                return brothers;
            }
            if (rowParentLever >= parentLevel) {
                return brothers;
            }
            if (currLever == Integer.MAX_VALUE) {
                currLever = rowLever;
            }
            this.loadRowDimension(iRowData);
            brothers.add(rowIndex);
            ArrayList<Serializable> relRow = new ArrayList<Serializable>();
            this.regionDataSet.getRel().add(rowIndex, relRow);
            relRow.add(0, Integer.valueOf(rowIndex));
            List<Object> children = new ArrayList();
            if (iRowData.getGroupTreeDeep() >= 0) {
                children = this.loadChildRowData(rowIndex, rowLever, deep + 1);
            }
            relRow.add(1, (Serializable)((Object)children));
            relRow.add(2, Integer.valueOf(deep));
            relRow.add(3, Integer.valueOf(parentIndex));
            relRow.add(4, Integer.valueOf(iRowData.getGroupTreeDeep() >= 0 ? groupData : detailData));
            relRow.add(5, Integer.valueOf(iRowData.getDetailSeqNum()));
        }
        return brothers;
    }

    private void loadRowDimension(IRowData dataRow) {
        ++this.currIndex;
    }
}

