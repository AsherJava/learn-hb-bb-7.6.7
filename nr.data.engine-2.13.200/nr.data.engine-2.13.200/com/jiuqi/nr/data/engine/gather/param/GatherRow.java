/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.data.engine.gather.param;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.data.engine.gather.param.IGatherColumn;
import com.jiuqi.nr.data.engine.gather.param.SortInfo;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class GatherRow
implements Comparable<GatherRow> {
    private IGatherColumn[] columns;
    private List<SortInfo> sorts;
    protected List<AbstractData> sortValues;

    public GatherRow(IGatherColumn[] columns) {
        this.columns = columns;
    }

    public Object readValue(int index) {
        return this.columns[index].readValue();
    }

    public void writeValue(int index, Object value) {
        this.columns[index].writeValue(value);
    }

    public void setSorts(List<SortInfo> sorts) {
        this.sorts = sorts;
    }

    public List<AbstractData> getSortValues() {
        return this.sortValues;
    }

    public void generateSortValues() {
        if (this.sortValues == null) {
            this.sortValues = new ArrayList<AbstractData>();
        }
        for (SortInfo sortInfo : this.sorts) {
            IGatherColumn column = this.columns[sortInfo.getIndex()];
            this.sortValues.add(column.getAbstractData());
        }
    }

    @Override
    public int compareTo(@NotNull GatherRow o) {
        List<AbstractData> oSortValues = o.getSortValues();
        for (int i = 0; i < this.sortValues.size(); ++i) {
            AbstractData oSortData;
            AbstractData sortData = this.sortValues.get(i);
            if (sortData == (oSortData = oSortValues.get(i)) || sortData == null && oSortData.isNull || oSortData == null && sortData.isNull || sortData != null && sortData.isNull && oSortData.isNull) continue;
            SortInfo sortInfo = this.sorts.get(i);
            boolean desc = sortInfo.isDesc();
            if (sortData == null || sortData.isNull) {
                if (!desc) {
                    return -1;
                }
                return 1;
            }
            int compared = sortData.compareTo(oSortData);
            if (compared == 0) continue;
            if (!desc) {
                return compared;
            }
            return -compared;
        }
        return 0;
    }
}

