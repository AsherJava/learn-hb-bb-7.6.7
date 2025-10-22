/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 */
package com.jiuiqi.nr.unit.treebase.entity.counter2;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultUnitTreeNodeCounter
implements IUnitTreeNodeCounter {
    protected IUnitTreeContext context;
    protected IEntityTable cacheDataTable;
    protected IUnitTreeEntityDataQuery entityDataQuery;
    protected UnitTreeSystemConfig unitTreeSystemConfig;
    protected boolean isFullyBuiltTree = false;
    private final Map<String, Integer> allChildCountMap = new HashMap<String, Integer>();
    private final Map<String, Integer> directChildCountMap = new HashMap<String, Integer>();

    public DefaultUnitTreeNodeCounter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery) {
        this.context = context;
        this.entityDataQuery = entityDataQuery;
        this.unitTreeSystemConfig = (UnitTreeSystemConfig)SpringBeanUtils.getBean(UnitTreeSystemConfig.class);
    }

    @Override
    public Map<String, Integer> getRootNodeCountMap() {
        if (!this.unitTreeSystemConfig.isCountOfAllChildrenQuantities()) {
            return new HashMap<String, Integer>();
        }
        IEntityTable readDataTable = this.entityDataQuery.makeIEntityTable(this.context);
        List rootRows = readDataTable.getRootRows();
        Map<String, Integer> countMap = this.buildCountMapByRows(this.entityDataQuery, this.context, rootRows, rootRows);
        this.isFullyBuiltTree = true;
        return countMap;
    }

    @Override
    public Map<String, Integer> getChildNodeCountMap(IBaseNodeData parent) {
        if (!this.unitTreeSystemConfig.isCountOfAllChildrenQuantities()) {
            return new HashMap<String, Integer>();
        }
        IEntityTable readDataTable = this.entityDataQuery.makeIEntityTable(this.context);
        List childRows = readDataTable.getChildRows(parent.getKey());
        return this.buildCountMapByRows(this.entityDataQuery, this.context, childRows, childRows);
    }

    @Override
    public Map<String, Integer> getTreeNodeCountMap(IBaseNodeData locateNode) {
        if (!this.unitTreeSystemConfig.isCountOfAllChildrenQuantities()) {
            return new HashMap<String, Integer>();
        }
        IEntityTable readDataTable = this.entityDataQuery.makeFullTreeData(this.context);
        List rootRows = readDataTable.getRootRows();
        if (rootRows.isEmpty()) {
            return new HashMap<String, Integer>();
        }
        List<Object> scopeRows = locateNode == null || locateNode.getKey().isEmpty() ? Stream.of(rootRows, readDataTable.getChildRows(((IEntityRow)rootRows.get(0)).getEntityKeyData())).flatMap(Collection::stream).collect(Collectors.toList()) : this.getTargetScopeRows(readDataTable, rootRows, locateNode.getKey());
        Map<String, Integer> countMap = this.buildCountMapByRows(this.entityDataQuery, this.context, rootRows, scopeRows);
        this.isFullyBuiltTree = true;
        return countMap;
    }

    protected Map<String, Integer> buildCountMapByRows(IUnitTreeEntityDataQuery entityDataQuery, IUnitTreeContext context, List<IEntityRow> rootRows, List<IEntityRow> rangeRows) {
        this.cacheDataTable = this.getCacheDataTable(entityDataQuery, context, rootRows);
        HashMap<String, Integer> countMap = new HashMap<String, Integer>();
        if (this.cacheDataTable != null && rangeRows != null && !rangeRows.isEmpty()) {
            rangeRows.forEach(row -> countMap.put(row.getEntityKeyData(), this.getShowCountNumber((IEntityRow)row)));
        }
        return countMap;
    }

    private int getShowCountNumber(IEntityRow row) {
        if (this.unitTreeSystemConfig.isCountOfAllChildrenNum()) {
            return this.getAllChildCount(row);
        }
        return this.getDirectChildCount(row);
    }

    private int getDirectChildCount(IEntityRow row) {
        String rowKey = row.getEntityKeyData();
        if (!this.directChildCountMap.containsKey(rowKey)) {
            this.directChildCountMap.put(rowKey, this.innerDirectChildCount(row));
        }
        return this.directChildCountMap.get(rowKey);
    }

    private int getAllChildCount(IEntityRow row) {
        String rowKey = row.getEntityKeyData();
        if (!this.allChildCountMap.containsKey(rowKey)) {
            this.allChildCountMap.put(rowKey, this.innerAllChildCount(row));
        }
        return this.allChildCountMap.get(rowKey);
    }

    private IEntityTable getCacheDataTable(IUnitTreeEntityDataQuery entityDataQuery, IUnitTreeContext context, List<IEntityRow> rangeRows) {
        List<String> rangeKeys;
        if (!(this.isFullyBuiltTree || rangeRows == null || rangeRows.isEmpty() || (rangeKeys = rangeRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList())).isEmpty())) {
            return entityDataQuery.makeRangeFullTreeData(context, rangeKeys, false);
        }
        return this.cacheDataTable;
    }

    protected int innerDirectChildCount(IEntityRow row) {
        return this.cacheDataTable.getDirectChildCount(row.getEntityKeyData());
    }

    protected int innerAllChildCount(IEntityRow row) {
        return this.cacheDataTable.getAllChildCount(row.getEntityKeyData());
    }

    private List<IEntityRow> getTargetScopeRows(IEntityTable readDataTable, List<IEntityRow> rootRows, String locateNode) {
        IEntityRow locateRow = readDataTable.findByEntityKey(locateNode);
        String[] locatePath = locateRow.getParentsEntityKeyDataPath();
        ArrayList<IEntityRow> targetScopeRows = new ArrayList<IEntityRow>(rootRows);
        for (String nodeEntityKey : locatePath) {
            targetScopeRows.addAll(readDataTable.getChildRows(nodeEntityKey));
        }
        targetScopeRows.addAll(readDataTable.getChildRows(locateNode));
        return targetScopeRows;
    }
}

