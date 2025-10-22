/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet
 */
package com.jiuqi.nr.unit.treeimpl.filter;

import com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import java.util.List;

public class USelectorEntityRowFilter
implements IFilterEntityRow {
    private final List<String> filterSet;

    public USelectorEntityRowFilter(String selectorKey) {
        USelectorResultSet uSelectorResultSet = (USelectorResultSet)BeanUtil.getBean(USelectorResultSet.class);
        this.filterSet = uSelectorResultSet.getFilterSet(selectorKey);
    }

    public boolean matchRow(IEntityRow row) {
        return this.filterSet.contains(row.getEntityKeyData());
    }

    public void setMatchRangeRows(List<IEntityRow> rangeRows) {
    }

    public List<IEntityRow> getMatchResultSet(List<String> filter) {
        return null;
    }
}

