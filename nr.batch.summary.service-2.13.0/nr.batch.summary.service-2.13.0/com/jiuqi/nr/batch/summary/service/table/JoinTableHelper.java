/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table;

import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumn;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumnMap;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableEntity;
import com.jiuqi.nr.batch.summary.service.table.PowerTableColumn;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JoinTableHelper {
    public static final String AND_JOIN_TAG = "and-join";
    private final Set<IPowerTableColumn> ownerTableColumns;
    private Set<IPowerTableColumn> joinOwnerColumns;
    private final Set<IPowerTableColumn> referTableColumns;
    private Set<IPowerTableColumn> joinReferColumns;
    private final Set<IPowerTableColumn> selectColumns = new LinkedHashSet<IPowerTableColumn>();
    private final Map<String, Object> columnFilters = new HashMap<String, Object>();
    private final String[] ownerSelectColumnCodes;
    private final String[] referSelectColumnCodes;

    protected JoinTableHelper(IPowerTableEntity ownerTableData, IPowerTableEntity referTableData) {
        this.ownerSelectColumnCodes = ownerTableData.getColumns();
        this.referSelectColumnCodes = referTableData.getColumns();
        this.ownerTableColumns = Arrays.stream(this.ownerSelectColumnCodes).map(PowerTableColumn::new).collect(Collectors.toCollection(LinkedHashSet::new));
        this.referTableColumns = Arrays.stream(this.referSelectColumnCodes).map(PowerTableColumn::new).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public JoinTableHelper(IPowerTableEntity ownerTableData, IPowerTableEntity referTableData, Set<IPowerTableColumnMap> joinColumns) {
        this(ownerTableData, referTableData);
        this.joinOwnerColumns = joinColumns.stream().map(IPowerTableColumnMap::getOwnerColumn).collect(Collectors.toCollection(LinkedHashSet::new));
        this.joinReferColumns = joinColumns.stream().map(IPowerTableColumnMap::getReferColumn).collect(Collectors.toCollection(LinkedHashSet::new));
        this.selectColumns.addAll(this.ownerTableColumns);
        this.selectColumns.addAll(this.referTableColumns);
    }

    public Map<String, Object> getColumnFilters(Map<String, Integer> ownerColIdxMap, Object[] ownerRowData) {
        this.joinOwnerColumns.forEach(column -> this.columnFilters.put(column.getColumnCode(), ownerRowData[(Integer)ownerColIdxMap.get(column.getColumnCode())]));
        return this.columnFilters;
    }

    public String[] getSelectColumns() {
        return (String[])this.selectColumns.stream().map(IPowerTableColumn::getColumnCode).toArray(String[]::new);
    }

    public String[] getOwnerTableColumns() {
        return this.ownerSelectColumnCodes;
    }

    public String[] getReferTableColumns() {
        return this.referSelectColumnCodes;
    }

    public boolean canJoinTableData() {
        return this.ownerTableColumns.containsAll(this.joinOwnerColumns) && this.referTableColumns.containsAll(this.joinReferColumns);
    }
}

