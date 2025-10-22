/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuiqi.nr.unit.treebase.entity.filter;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public interface IFilterEntityRow {
    public boolean matchRow(IEntityRow var1);

    public void setMatchRangeRows(List<IEntityRow> var1);

    public List<IEntityRow> getMatchResultSet(List<String> var1);
}

