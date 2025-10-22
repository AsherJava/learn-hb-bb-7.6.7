/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegionGradeDataLoader {
    private RegionDataSet regionDataSet;
    private IReadonlyTable readonlyTable;
    private int currIndex;
    private DimensionValueSet currDimensionValueSet;
    private List<Integer> dataIndexList;
    public static int groupData = 1;
    public static int detailData = 2;

    public void loadRegionGradeData(IReadonlyTable readonlyTable, RegionDataSet regionDataSet) {
        this.regionDataSet = regionDataSet;
        this.readonlyTable = readonlyTable;
        if (readonlyTable.supportTreeGroup()) {
            this.currIndex = 0;
            while (this.currIndex < readonlyTable.getCount()) {
                this.loadRootRowData();
            }
        } else {
            for (int rowIndex = 0; rowIndex < readonlyTable.getCount(); ++rowIndex) {
                this.loadRowData(rowIndex);
            }
        }
    }

    private void loadRowData(int rowIndex) {
        IDataRow dataRow = this.readonlyTable.getItem(rowIndex);
        ArrayList<Serializable> relRow = new ArrayList<Serializable>();
        this.regionDataSet.getRel().add(rowIndex, relRow);
        relRow.add(0, Integer.valueOf(rowIndex));
        ArrayList children = new ArrayList();
        relRow.add(1, children);
        int deep = dataRow.getGroupTreeDeep();
        relRow.add(2, Integer.valueOf(deep));
        if (deep == 1) {
            this.regionDataSet.getRoot().add(rowIndex);
        }
        relRow.add(3, Integer.valueOf(-1));
        relRow.add(4, Integer.valueOf(dataRow.getGroupingFlag() >= 0 ? groupData : detailData));
        relRow.add(5, Integer.valueOf(0));
    }

    private void loadRootRowData() {
        int rowIndex = this.currIndex;
        int deep = 1;
        IDataRow dataRow = this.readonlyTable.getItem(rowIndex);
        this.loadRowDimension(dataRow);
        ArrayList<Serializable> relRow = new ArrayList<Serializable>();
        this.regionDataSet.getRel().add(rowIndex, relRow);
        this.regionDataSet.getRoot().add(rowIndex);
        relRow.add(0, Integer.valueOf(rowIndex));
        List<Object> children = new ArrayList();
        if (dataRow.getGroupingFlag() >= 0) {
            int groupingLevel = ((DataRowImpl)dataRow).getGroupingLevel();
            children = this.loadChildRowData(rowIndex, groupingLevel, deep + 1);
        }
        relRow.add(1, (Serializable)((Object)children));
        relRow.add(2, Integer.valueOf(deep));
        relRow.add(3, Integer.valueOf(-1));
        relRow.add(4, Integer.valueOf(dataRow.getGroupingFlag() >= 0 ? groupData : detailData));
        relRow.add(5, Integer.valueOf(0));
    }

    private List<Integer> loadChildRowData(int parentIndex, int parentLevel, int deep) {
        ArrayList<Integer> brothers = new ArrayList<Integer>();
        int currLever = Integer.MAX_VALUE;
        int currNumber = 0;
        while (this.currIndex < this.readonlyTable.getCount()) {
            int rowIndex = this.currIndex;
            IDataRow dataRow = this.readonlyTable.getItem(rowIndex);
            int rowLever = ((DataRowImpl)dataRow).getGroupingLevel();
            int rowParentLever = ((DataRowImpl)dataRow).getParentLevel();
            if (rowLever >= parentLevel) {
                return brothers;
            }
            if (rowParentLever >= parentLevel) {
                return brothers;
            }
            ++currNumber;
            if (currLever == Integer.MAX_VALUE) {
                currLever = rowLever;
            }
            this.loadRowDimension(dataRow);
            brothers.add(rowIndex);
            ArrayList<Serializable> relRow = new ArrayList<Serializable>();
            this.regionDataSet.getRel().add(rowIndex, relRow);
            relRow.add(0, Integer.valueOf(rowIndex));
            List<Object> children = new ArrayList();
            if (dataRow.getGroupingFlag() >= 0) {
                currNumber = 0;
                children = this.loadChildRowData(rowIndex, rowLever, deep + 1);
            }
            relRow.add(1, (Serializable)((Object)children));
            relRow.add(2, Integer.valueOf(deep));
            relRow.add(3, Integer.valueOf(parentIndex));
            relRow.add(4, Integer.valueOf(dataRow.getGroupingFlag() >= 0 ? groupData : detailData));
            relRow.add(5, Integer.valueOf(dataRow.getGroupingFlag() >= 0 ? 0 : currNumber));
        }
        return brothers;
    }

    private void loadRowDimension(IDataRow dataRow) {
        ++this.currIndex;
    }
}

