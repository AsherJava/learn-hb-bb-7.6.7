/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
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
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimizeUnitTreeNodeCounter
implements IUnitTreeNodeCounter {
    private final IUnitTreeContext context;
    private final IUnitTreeEntityDataQuery entityDataQuery;
    private final UnitTreeSystemConfig unitTreeSystemConfig;

    public OptimizeUnitTreeNodeCounter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery) {
        this.context = context;
        this.entityDataQuery = entityDataQuery;
        this.unitTreeSystemConfig = (UnitTreeSystemConfig)SpringBeanUtils.getBean(UnitTreeSystemConfig.class);
    }

    @Override
    public Map<String, Integer> getRootNodeCountMap() {
        if (!this.unitTreeSystemConfig.isCountOfAllChildrenQuantities()) {
            return new HashMap<String, Integer>();
        }
        IEntityTable readDataTable = this.entityDataQuery.makeIEntityTable(this.context, this.unitTreeSystemConfig.isCountOfDiffUnit(), this.unitTreeSystemConfig.isCountOfLeaves());
        return this.unitTreeSystemConfig.isCountOfAllChildrenNum() ? readDataTable.getAllChildCountByParent(null) : readDataTable.getDirectChildCountByParent(null);
    }

    @Override
    public Map<String, Integer> getChildNodeCountMap(IBaseNodeData parent) {
        if (!this.unitTreeSystemConfig.isCountOfAllChildrenQuantities()) {
            return new HashMap<String, Integer>();
        }
        IEntityTable readDataTable = this.entityDataQuery.makeIEntityTable(this.context, this.unitTreeSystemConfig.isCountOfDiffUnit(), this.unitTreeSystemConfig.isCountOfLeaves());
        return this.unitTreeSystemConfig.isCountOfAllChildrenNum() ? readDataTable.getAllChildCountByParent(parent.getKey()) : readDataTable.getDirectChildCountByParent(parent.getKey());
    }

    @Override
    public Map<String, Integer> getTreeNodeCountMap(IBaseNodeData locateNode) {
        if (!this.unitTreeSystemConfig.isCountOfAllChildrenQuantities()) {
            return new HashMap<String, Integer>();
        }
        IEntityTable readDataTable = this.entityDataQuery.makeIEntityTable(this.context, this.unitTreeSystemConfig.isCountOfDiffUnit(), this.unitTreeSystemConfig.isCountOfLeaves());
        return this.unitTreeSystemConfig.isCountOfAllChildrenNum() ? this.getAllChildCount(locateNode, readDataTable) : this.getDirectChildCount(locateNode, readDataTable);
    }

    private Map<String, Integer> getAllChildCount(IBaseNodeData locateNode, IEntityTable readDataTable) {
        if (locateNode == null || locateNode.getKey().isEmpty()) {
            List rootRows = readDataTable.getRootRows();
            if (rootRows == null || rootRows.isEmpty()) {
                return Collections.emptyMap();
            }
            HashMap<String, Integer> resultCountMap = new HashMap<String, Integer>(readDataTable.getAllChildCountByParent(null));
            resultCountMap.putAll(readDataTable.getAllChildCountByParent(((IEntityRow)rootRows.get(0)).getEntityKeyData()));
            return resultCountMap;
        }
        IEntityRow locateRow = readDataTable.findByEntityKey(locateNode.getKey());
        String[] locatePath = locateRow.getParentsEntityKeyDataPath();
        HashMap<String, Integer> resultCountMap = new HashMap<String, Integer>(readDataTable.getAllChildCountByParent(null));
        for (String nodeEntityKey : locatePath) {
            resultCountMap.putAll(readDataTable.getAllChildCountByParent(nodeEntityKey));
        }
        resultCountMap.putAll(readDataTable.getAllChildCountByParent(locateNode.getKey()));
        return resultCountMap;
    }

    private Map<String, Integer> getDirectChildCount(IBaseNodeData locateNode, IEntityTable readDataTable) {
        if (locateNode == null || locateNode.getKey().isEmpty()) {
            List rootRows = readDataTable.getRootRows();
            if (rootRows == null || rootRows.isEmpty()) {
                return Collections.emptyMap();
            }
            HashMap<String, Integer> resultCountMap = new HashMap<String, Integer>(readDataTable.getDirectChildCountByParent(null));
            resultCountMap.putAll(readDataTable.getDirectChildCountByParent(((IEntityRow)rootRows.get(0)).getEntityKeyData()));
            return resultCountMap;
        }
        IEntityRow locateRow = readDataTable.findByEntityKey(locateNode.getKey());
        String[] locatePath = locateRow.getParentsEntityKeyDataPath();
        HashMap<String, Integer> resultCountMap = new HashMap<String, Integer>(readDataTable.getDirectChildCountByParent(null));
        for (String nodeEntityKey : locatePath) {
            resultCountMap.putAll(readDataTable.getDirectChildCountByParent(nodeEntityKey));
        }
        resultCountMap.putAll(readDataTable.getDirectChildCountByParent(locateNode.getKey()));
        return resultCountMap;
    }
}

