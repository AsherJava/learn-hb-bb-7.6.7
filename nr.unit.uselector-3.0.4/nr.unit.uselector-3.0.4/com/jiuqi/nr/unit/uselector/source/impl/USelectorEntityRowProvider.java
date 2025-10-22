/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.unit.uselector.source.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

public class USelectorEntityRowProvider
implements IUSelectorEntityRowProvider {
    protected IUnitTreeContext context;
    protected UnitTreeEntityDataQuery entityDataQuery;

    public USelectorEntityRowProvider(IUnitTreeContext context, UnitTreeEntityDataQuery entityDataQuery) {
        this.context = context;
        this.entityDataQuery = entityDataQuery;
    }

    @Override
    public int getTotalCount() {
        return this.entityDataQuery.makeIEntityTable(this.context).getTotalCount();
    }

    @Override
    public String[] getParentsEntityKeyDataPath(String rowKey) {
        return this.entityDataQuery.makeIEntityTable(this.context).getParentsEntityKeyDataPath(rowKey);
    }

    @Override
    public boolean hasParent(String rowKey) {
        IEntityRow entityRow = this.findEntityRow(rowKey);
        if (entityRow != null) {
            String[] path = entityRow.getParentsEntityKeyDataPath();
            return path != null && path.length != 0 && !path[0].equals(entityRow.getEntityKeyData());
        }
        return false;
    }

    @Override
    public boolean isLeaf(String rowKey) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, Collections.singletonList(rowKey));
        return dataTable.getDirectChildCount(rowKey) == 0;
    }

    @Override
    public IEntityRow findEntityRow(String rowKey) {
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context, Collections.singletonList(rowKey));
        return dataTable.findByEntityKey(rowKey);
    }

    @Override
    public List<IEntityRow> getAllRows() {
        return this.entityDataQuery.makeIEntityTable(this.context).getAllRows();
    }

    @Override
    public List<IEntityRow> getAllLeaveRows() {
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        List allRows = dataTable.getAllRows();
        return allRows.stream().filter(row -> dataTable.getDirectChildCount(row.getEntityKeyData()) == 0).collect(Collectors.toList());
    }

    @Override
    public List<IEntityRow> getAllNonLeaveRows() {
        IEntityTable dataTable = this.entityDataQuery.makeIEntityTable(this.context);
        List allRows = dataTable.getAllRows();
        return allRows.stream().filter(row -> dataTable.getDirectChildCount(row.getEntityKeyData()) > 0).collect(Collectors.toList());
    }

    @Override
    public List<IEntityRow> getChildRows(List<String> parents) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, parents);
        ArrayList<IEntityRow> childRows = new ArrayList<IEntityRow>();
        parents.forEach(parentKey -> childRows.addAll(dataTable.getChildRows(parentKey)));
        return childRows;
    }

    @Override
    public List<IEntityRow> getChildRowsAndSelf(List<String> parents) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, parents);
        ArrayList<IEntityRow> childRows = new ArrayList<IEntityRow>();
        parents.forEach(parentKey -> {
            childRows.add(dataTable.findByEntityKey(parentKey));
            childRows.addAll(dataTable.getChildRows(parentKey));
        });
        return childRows;
    }

    @Override
    public List<IEntityRow> getAllChildRows(List<String> parents) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, parents);
        ArrayList<IEntityRow> allChildRows = new ArrayList<IEntityRow>();
        parents.forEach(parentKey -> allChildRows.addAll(dataTable.getAllChildRows(parentKey)));
        return allChildRows;
    }

    @Override
    public List<IEntityRow> getAllChildRowsAndSelf(List<String> parents) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, parents);
        ArrayList<IEntityRow> allChildRows = new ArrayList<IEntityRow>();
        parents.forEach(parentKey -> {
            allChildRows.add(dataTable.findByEntityKey(parentKey));
            allChildRows.addAll(dataTable.getAllChildRows(parentKey));
        });
        return allChildRows;
    }

    @Override
    public List<IEntityRow> getAllChildLeaveRows(List<String> parents) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, parents);
        List allRows = dataTable.getAllRows();
        return allRows.stream().filter(row -> dataTable.getDirectChildCount(row.getEntityKeyData()) == 0).collect(Collectors.toList());
    }

    @Override
    public List<IEntityRow> getAllChildNonLeaveRows(List<String> parents) {
        IEntityTable dataTable = this.entityDataQuery.makeRangeFullTreeData(this.context, parents);
        List allRows = dataTable.getAllRows();
        return allRows.stream().filter(row -> dataTable.getDirectChildCount(row.getEntityKeyData()) > 0).collect(Collectors.toList());
    }

    @Override
    public List<IEntityRow> getAllParentRows(List<String> rowKeys) {
        List<IEntityRow> checkRows = this.getCheckRows(rowKeys);
        LinkedHashSet parentKeys = new LinkedHashSet();
        checkRows.forEach(row -> parentKeys.addAll(Arrays.asList(row.getParentsEntityKeyDataPath())));
        return this.getCheckRows(new ArrayList<String>(parentKeys));
    }

    @Override
    public List<IEntityRow> getCheckRows(List<String> rowKeys) {
        return this.entityDataQuery.makeIEntityTable(this.context, rowKeys).getAllRows();
    }

    @Override
    public List<IEntityRow> filterByFormulas(String ... expressions) {
        try {
            return this.entityDataQuery.makeIEntityTable(this.context, expressions).getAllRows();
        }
        catch (Exception e) {
            LoggerFactory.getLogger(USelectorEntityRowProvider.class).error(e.getMessage(), e);
            return new ArrayList<IEntityRow>();
        }
    }

    @Override
    public List<IEntityRow> filterByFormulas(IUnitTreeContext context, String ... expressions) {
        try {
            return this.entityDataQuery.makeIEntityTable(context, expressions).getAllRows();
        }
        catch (Exception e) {
            LoggerFactory.getLogger(USelectorEntityRowProvider.class).error(e.getMessage(), e);
            return new ArrayList<IEntityRow>();
        }
    }

    private IEntityRow[][] checkContinueRange(String startNode, String endNode) {
        if (StringUtils.isEmpty((String)startNode) || StringUtils.isEmpty((String)endNode)) {
            return new IEntityRow[0][0];
        }
        if (startNode.equals(endNode)) {
            return new IEntityRow[0][0];
        }
        IEntityTable entityTable = this.entityDataQuery.makeIEntityTable(this.context, Arrays.asList(startNode, endNode));
        IEntityRow startRow = entityTable.findByEntityKey(startNode);
        IEntityRow endRow = entityTable.findByEntityKey(endNode);
        if (startRow == null || endRow == null) {
            return new IEntityRow[0][0];
        }
        String startParentKey = startRow.getParentEntityKey();
        String endParentKey = endRow.getParentEntityKey();
        entityTable = this.entityDataQuery.makeIEntityTable(this.context, Arrays.asList(startParentKey, endParentKey));
        IEntityRow startParentRow = entityTable.findByEntityKey(startParentKey);
        IEntityRow endParentRow = entityTable.findByEntityKey(endParentKey);
        if (startParentRow == null && endParentRow == null) {
            return new IEntityRow[][]{{null, null}, {startRow, endRow}};
        }
        if (startParentRow != null && endParentRow != null && startParentRow.getEntityKeyData().equals(endParentRow.getEntityKeyData())) {
            return new IEntityRow[][]{{startParentRow, endParentRow}, {startRow, endRow}};
        }
        return new IEntityRow[0][0];
    }

    @Override
    public List<IEntityRow> getContinueNodeAndAllChildren(List<String> rangeNodeKeys) {
        if (rangeNodeKeys != null && !rangeNodeKeys.isEmpty()) {
            IEntityTable entityTable = this.entityDataQuery.makeRangeFullTreeData(this.context, rangeNodeKeys);
            return entityTable.getAllRows();
        }
        return new ArrayList<IEntityRow>();
    }

    @Override
    public List<IEntityRow> getContinueNode(List<String> rangeNodeKeys) {
        if (rangeNodeKeys != null && !rangeNodeKeys.isEmpty()) {
            IEntityTable entityTable = this.entityDataQuery.makeIEntityTable(this.context, rangeNodeKeys);
            return entityTable.getAllRows();
        }
        return new ArrayList<IEntityRow>();
    }
}

