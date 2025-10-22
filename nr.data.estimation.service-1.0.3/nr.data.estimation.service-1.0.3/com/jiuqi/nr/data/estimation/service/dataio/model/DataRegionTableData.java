/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.service.dataio.CheckPolicy;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionTableData;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellValue;
import com.jiuqi.nr.data.estimation.service.dataio.ITableDataSet;
import com.jiuqi.nr.data.estimation.service.dataio.model.TableDataSet;
import java.util.Iterator;
import java.util.List;

public class DataRegionTableData
implements IDataRegionTableData {
    private static final long serialVersionUID = -5059058636626696632L;
    private TableDataSet currTableData;
    private final TableDataSet newTableData = new TableDataSet();
    private final TableDataSet updateTableData = new TableDataSet();
    private final TableDataSet deleteTableData = new TableDataSet();
    private final DimensionValueSet pubDimensionValueSet;

    public DataRegionTableData(DimensionValueSet pubDimensionValueSet) {
        this.currTableData = this.newTableData;
        this.pubDimensionValueSet = pubDimensionValueSet;
    }

    @Override
    public boolean isEmpty() {
        return this.newTableData.isEmpty() && this.updateTableData.isEmpty() && this.deleteTableData.isEmpty();
    }

    @Override
    public int getRowCount() {
        return this.currTableData.getRowCount();
    }

    @Override
    public String[] getColumns() {
        return this.currTableData.getColumns();
    }

    @Override
    public ITableCellValue getCellValue(int rowIdx, String column) {
        return this.currTableData.getCellValue(rowIdx, column);
    }

    @Override
    public Iterator<ITableCellValue[]> rowIterator() {
        return this.currTableData.rowIterator();
    }

    @Override
    public Iterator<ITableCellValue[]> columnIterator() {
        return this.currTableData.columnIterator();
    }

    @Override
    public DimensionValueSet getPubColumnValueSet() {
        return this.pubDimensionValueSet;
    }

    @Override
    public ITableDataSet getNewTableData() {
        return this.newTableData;
    }

    @Override
    public ITableDataSet getUpdateTableData() {
        return this.updateTableData;
    }

    @Override
    public ITableDataSet getDeleteTableData() {
        return this.deleteTableData;
    }

    @Override
    public boolean isEmpty(CheckPolicy policy) {
        this.setCheckPolicy(policy);
        return this.currTableData.isEmpty();
    }

    private void setCheckPolicy(CheckPolicy policy) {
        policy = policy != null ? policy : CheckPolicy.NewSet;
        switch (policy) {
            case NewSet: {
                this.currTableData = this.newTableData;
                break;
            }
            case UpdateSet: {
                this.currTableData = this.updateTableData;
                break;
            }
            case DeleteSet: {
                this.currTableData = this.deleteTableData;
            }
        }
    }

    public void appendTableData(CheckPolicy policy, List<String> listColumn) {
        this.setCheckPolicy(policy);
        this.extendColumns(listColumn);
    }

    public void extendRows(int rowCount) {
        this.currTableData.appendRows(rowCount);
    }

    public void extendColumns(List<String> listColumn) {
        this.currTableData.appendCols(listColumn.toArray(new String[0]));
    }

    public void setCellValue(int rowIdx, String column, ITableCellValue value) {
        this.currTableData.setCellValue(rowIdx, column, value);
    }

    public void setCellValue(int rowIdx, String column, Object newCellValue) {
        this.currTableData.setCellValue(rowIdx, column, newCellValue);
    }

    public void setCellValue(int rowIdx, String column, Object newCellValue, Object oldCellValue) {
        this.currTableData.setCellValue(rowIdx, column, newCellValue, oldCellValue);
    }

    public void toString(StringBuilder info) {
        info.append("=====================\u3010\u65b0\u589e\u3011\u5217\u8868========================").append('\n');
        this.newTableData.toString(info);
        info.append("=====================\u3010\u4fee\u6539\u3011\u5217\u8868========================").append('\n');
        this.updateTableData.toString(info);
        info.append("=====================\u3010\u5220\u9664\u3011\u5217\u8868========================").append('\n');
        this.deleteTableData.toString(info);
    }
}

