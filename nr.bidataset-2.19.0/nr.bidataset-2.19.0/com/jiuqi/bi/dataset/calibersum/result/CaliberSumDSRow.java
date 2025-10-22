/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.nr.calibre2.domain.CalibreDataRegion
 */
package com.jiuqi.bi.dataset.calibersum.result;

import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSField;
import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSModel;
import com.jiuqi.bi.dataset.calibersum.result.CaliberSumDSColumn;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.nr.calibre2.domain.CalibreDataRegion;
import java.util.ArrayList;
import java.util.List;

public class CaliberSumDSRow {
    private CalibreDataRegion calibreDataRegion;
    private int rowIndex;
    private List<CaliberSumDSColumn> columns = new ArrayList<CaliberSumDSColumn>();
    private List<CaliberSumDSRow> parentRows;

    public CaliberSumDSRow(CaliberSumDSModel dsModel, CalibreDataRegion calibreDataRegion, int rowIndex) {
        this.calibreDataRegion = calibreDataRegion;
        this.rowIndex = rowIndex;
        for (DSField dsField : dsModel.getFields()) {
            CaliberSumDSField caliberSumDSField = (CaliberSumDSField)dsField;
            CaliberSumDSColumn column = new CaliberSumDSColumn(caliberSumDSField);
            this.columns.add(column);
        }
    }

    public CalibreDataRegion getCalibreDataRegion() {
        return this.calibreDataRegion;
    }

    public void setCalibreDataRegion(CalibreDataRegion calibreDataRegion) {
        this.calibreDataRegion = calibreDataRegion;
    }

    public String getMainDimKey() {
        return this.calibreDataRegion.getCalibreData().getValue().getEntityKey();
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public List<CaliberSumDSColumn> getColumns() {
        return this.columns;
    }

    public void setValue(int index, Object value) {
        this.columns.get(index).setValue(value);
    }

    public void statisticValue(int index, Object value) {
        this.columns.get(index).statistic(value);
    }

    public void adjustValue(int index, Object value) {
        this.columns.get(index).adjust(value);
    }

    public Object getValue(int index) {
        return this.columns.get(index).getValue();
    }

    public int getColumnCount() {
        return this.columns.size();
    }

    public List<CaliberSumDSRow> getParentRows() {
        if (this.parentRows == null) {
            this.parentRows = new ArrayList<CaliberSumDSRow>();
        }
        return this.parentRows;
    }

    public boolean hasParents() {
        return this.parentRows != null;
    }
}

