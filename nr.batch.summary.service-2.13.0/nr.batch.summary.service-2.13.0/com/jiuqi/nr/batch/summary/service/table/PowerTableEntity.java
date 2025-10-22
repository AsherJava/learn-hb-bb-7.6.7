/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table;

import com.jiuqi.nr.batch.summary.service.dbutil.TableEntityData;
import com.jiuqi.nr.batch.summary.service.table.AggregatorHelper;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumn;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumnMap;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableEntity;
import com.jiuqi.nr.batch.summary.service.table.JoinTableHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PowerTableEntity
extends TableEntityData
implements IPowerTableEntity {
    private static final String connectors = "-";

    public PowerTableEntity(String[] columns) {
        super(columns);
    }

    @Override
    public IPowerTableEntity andConditionRows(Map<String, Object> columnFilters) {
        String tagValue = "inner-tag";
        return this.andConditionRows(columnFilters, tagValue);
    }

    @Override
    public IPowerTableEntity andJoinRows(IPowerTableEntity joinTableData, Set<IPowerTableColumnMap> joinColumns) {
        JoinTableHelper joinTableHelper = new JoinTableHelper(this, joinTableData, joinColumns);
        return this.andJoinRows(joinTableData, joinColumns, joinTableHelper);
    }

    @Override
    public IPowerTableEntity andJoinRows(IPowerTableEntity joinTableData, Set<IPowerTableColumnMap> joinColumns, boolean keepUnJoinRows) {
        JoinTableHelper joinTableHelper = new JoinTableHelper(this, joinTableData, joinColumns);
        PowerTableEntity tableData = this.andJoinRows(joinTableData, joinColumns, joinTableHelper);
        IPowerTableEntity notJoinLeftRows = this.filterTagRows("and-join", false);
        Iterator<Object[]> leftIterator = notJoinLeftRows.rowIterator();
        while (leftIterator.hasNext()) {
            tableData.setRowData(tableData.getRowCount(), joinTableHelper.getOwnerTableColumns(), leftIterator.next());
        }
        IPowerTableEntity notJoinRightRows = joinTableData.filterTagRows("and-join", false);
        Iterator<Object[]> rightIterator = notJoinRightRows.rowIterator();
        while (rightIterator.hasNext()) {
            tableData.setRowData(tableData.getRowCount(), joinTableHelper.getReferTableColumns(), rightIterator.next());
        }
        return tableData;
    }

    @Override
    public IPowerTableEntity filterTagRows(String tagValue, boolean hasTag) {
        PowerTableEntity tableData = new PowerTableEntity(this.columns);
        Iterator<Object[]> iterator = this.rowIterator();
        int rowIndex = 0;
        while (iterator.hasNext()) {
            Object[] rowData = iterator.next();
            if (hasTag && tagValue.equals(this.getRowTag(rowIndex))) {
                tableData.setRowData(tableData.getRowCount(), this.columns, rowData);
            } else if (!hasTag && !tagValue.equals(this.getRowTag(rowIndex))) {
                tableData.setRowData(tableData.getRowCount(), this.columns, rowData);
            }
            ++rowIndex;
        }
        return tableData;
    }

    @Override
    public IPowerTableEntity groupByRows(Set<String> groupColumns, Set<IPowerTableColumn> aggregateColumns) {
        AggregatorHelper aggregatorHelper = new AggregatorHelper(groupColumns, aggregateColumns);
        PowerTableEntity tableData = new PowerTableEntity(aggregatorHelper.getAggregateColumns());
        Iterator<IPowerTableEntity> tableIterator = this.groupRowsIterator(groupColumns);
        int rowIndex = 0;
        while (tableIterator.hasNext()) {
            IPowerTableEntity groupTableData = tableIterator.next();
            Iterator<Object[]> columnIterator = groupTableData.columnIterator();
            int colIndex = 0;
            while (columnIterator.hasNext()) {
                Object[] colData = columnIterator.next();
                String columnCode = groupTableData.getColumns()[colIndex];
                if (aggregatorHelper.containsColumn(columnCode)) {
                    tableData.setCellValue(rowIndex, columnCode, aggregatorHelper.aggregateColData(columnCode, colData));
                }
                ++colIndex;
            }
            ++rowIndex;
        }
        return tableData;
    }

    @Override
    public IPowerTableEntity andConditionRows(Map<String, Object> columnFilters, String tagValue) {
        ArrayList<Integer> filterColumnIndexes = new ArrayList<Integer>();
        ArrayList filterColumnValues = new ArrayList();
        columnFilters.forEach((k, v) -> {
            filterColumnValues.add(v);
            filterColumnIndexes.add((Integer)this.colIdxMap.get(k));
        });
        String filterKeySet = filterColumnValues.stream().map(Object::toString).collect(Collectors.joining(connectors));
        PowerTableEntity tableData = new PowerTableEntity(this.columns);
        Iterator<Object[]> iterator = this.rowIterator();
        int rowIndex = 0;
        while (iterator.hasNext()) {
            Object[] rowData = iterator.next();
            String groupKey = PowerTableEntity.getGroupKey(rowData, filterColumnIndexes);
            if (groupKey.equals(filterKeySet)) {
                this.tagRow(rowIndex, tagValue);
                tableData.setRowData(tableData.getRowCount(), this.columns, rowData);
            }
            ++rowIndex;
        }
        return tableData;
    }

    private PowerTableEntity andJoinRows(IPowerTableEntity joinTableData, Set<IPowerTableColumnMap> joinColumns, JoinTableHelper joinTableHelper) {
        if (!joinTableHelper.canJoinTableData()) {
            throw new IllegalArgumentException("\u7f3a\u5931\u7684\u8fde\u63a5\u6761\u4ef6\u5217 " + joinColumns);
        }
        this.clearRowTags();
        joinTableData.clearRowTags();
        PowerTableEntity tableData = new PowerTableEntity(joinTableHelper.getSelectColumns());
        int rowIndex = 0;
        Iterator<Object[]> iterator = this.rowIterator();
        while (iterator.hasNext()) {
            Object[] ownerRowData = iterator.next();
            IPowerTableEntity conditionRows = joinTableData.andConditionRows(joinTableHelper.getColumnFilters(this.colIdxMap, ownerRowData), "and-join");
            Iterator<Object[]> condiIterator = conditionRows.rowIterator();
            while (condiIterator.hasNext()) {
                this.tagRow(rowIndex, "and-join");
                Object[] referRowData = condiIterator.next();
                int rowCount = tableData.getRowCount();
                tableData.setRowData(rowCount, joinTableHelper.getOwnerTableColumns(), ownerRowData);
                tableData.setRowData(rowCount, joinTableHelper.getReferTableColumns(), referRowData);
            }
            ++rowIndex;
        }
        return tableData;
    }

    private Iterator<IPowerTableEntity> groupRowsIterator(Set<String> groupColumns) {
        ArrayList<PowerTableEntity> groupedTables = new ArrayList<PowerTableEntity>();
        HashMap<String, PowerTableEntity> groupedMap = new HashMap<String, PowerTableEntity>();
        List<Integer> columnIndex = groupColumns.stream().map(columnCode -> (Integer)this.colIdxMap.get(columnCode)).collect(Collectors.toList());
        Iterator<Object[]> iterator = this.rowIterator();
        while (iterator.hasNext()) {
            Object[] rowData = iterator.next();
            String groupKey = PowerTableEntity.getGroupKey(rowData, columnIndex);
            PowerTableEntity tableData = (PowerTableEntity)groupedMap.get(groupKey);
            if (tableData == null) {
                tableData = new PowerTableEntity(this.columns);
                groupedTables.add(tableData);
                groupedMap.put(groupKey, tableData);
            }
            tableData.setRowData(tableData.getRowCount(), this.columns, rowData);
        }
        return groupedTables.iterator();
    }

    @Override
    public void clearRowTags() {
        Arrays.fill(this.rowTags, null);
    }

    public void tagRow(int rowIndex, String tagValue) {
        this.rowTags[rowIndex] = tagValue;
    }

    public String getRowTag(int rowIndex) {
        return this.rowTags[rowIndex];
    }

    protected static String getGroupKey(Object[] row, List<Integer> columnIndex) {
        return columnIndex.stream().map(index -> row[index].toString()).collect(Collectors.joining(connectors));
    }
}

