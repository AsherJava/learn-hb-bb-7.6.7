/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuiqi.nr.unit.treebase.entity.filter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.List;
import java.util.stream.Collectors;

public class LevelsEntityFilter
implements IFilterEntityRow {
    private List<Integer> levels;
    private IUnitTreeContext context;
    private IUnitTreeEntityDataQuery entityDataQuery;

    public LevelsEntityFilter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery) {
        this.context = context;
        this.entityDataQuery = entityDataQuery;
    }

    public LevelsEntityFilter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, List<String> strLevels) {
        this(context, entityDataQuery);
        this.levels = strLevels.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    @Override
    public boolean matchRow(IEntityRow row) {
        return this.levels.contains(row.getParentsEntityKeyDataPath().length);
    }

    @Override
    public void setMatchRangeRows(List<IEntityRow> rangeRows) {
    }

    @Override
    public List<IEntityRow> getMatchResultSet(List<String> strLevels) {
        this.levels = strLevels.stream().map(Integer::parseInt).collect(Collectors.toList());
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        List allRows = dataTable.getAllRows();
        return allRows.stream().filter(this::matchRow).collect(Collectors.toList());
    }
}

