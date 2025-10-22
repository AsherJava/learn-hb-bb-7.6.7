/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.nr.period.common.utils.TimeDimField
 */
package com.jiuqi.bi.publicparam.datasource.timegranularity.filter;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.nr.period.common.utils.TimeDimField;
import java.util.ArrayList;
import java.util.List;

public class TimegranularityFilter {
    private Column<TimeDimField> column;
    private List<String> values = new ArrayList<String>();
    private boolean isRange = false;
    private int columnIndex;

    public TimegranularityFilter(Column<TimeDimField> column, List<String> values, boolean isRange) {
        this.columnIndex = column.getIndex();
        this.values = values;
        this.isRange = isRange;
    }

    public boolean judge(DataRow row) {
        if (this.values == null || this.values.size() == 0) {
            return true;
        }
        String value = row.getString(this.columnIndex);
        if (this.isRange && this.values.size() == 2) {
            return value.compareTo(this.values.get(0)) >= 0 && value.compareTo(this.values.get(1)) <= 0;
        }
        return this.values.contains(value);
    }

    public TimeDimField getField() {
        return (TimeDimField)this.column.getInfo();
    }

    public List<String> getValues() {
        return this.values;
    }

    public boolean isRange() {
        return this.isRange;
    }

    public void setRange(boolean isRange) {
        this.isRange = isRange;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
}

