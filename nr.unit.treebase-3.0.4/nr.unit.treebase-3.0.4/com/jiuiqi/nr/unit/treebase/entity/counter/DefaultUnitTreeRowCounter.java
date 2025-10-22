/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 */
package com.jiuiqi.nr.unit.treebase.entity.counter;

import com.jiuiqi.nr.unit.treebase.entity.counter.IUnitTreeEntityRowCounter;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import java.util.HashMap;
import java.util.Map;

public class DefaultUnitTreeRowCounter
implements IUnitTreeEntityRowCounter {
    private final Map<String, Integer> allChildCountMap = new HashMap<String, Integer>();
    private final Map<String, Integer> directChildCountMap = new HashMap<String, Integer>();
    protected IEntityTable dataTable;
    protected UnitTreeSystemConfig unitTreeSystemConfig;
    private final boolean isCountOfAllChildrenNum;

    public DefaultUnitTreeRowCounter(IEntityTable dataTable) {
        this.dataTable = dataTable;
        this.unitTreeSystemConfig = (UnitTreeSystemConfig)BeanUtil.getBean(UnitTreeSystemConfig.class);
        this.isCountOfAllChildrenNum = this.unitTreeSystemConfig.isCountOfAllChildrenNum();
    }

    @Override
    public boolean isLeaf(IBaseNodeData rowData) {
        int childCount = this.dataTable.getDirectChildCount(rowData.getKey());
        return childCount == 0;
    }

    @Override
    public int getDirectChildCount(IBaseNodeData rowData) {
        String rowKey = rowData.getKey();
        if (!this.directChildCountMap.containsKey(rowKey)) {
            this.directChildCountMap.put(rowKey, this.innerDirectChildCount(rowData));
        }
        return this.directChildCountMap.get(rowKey);
    }

    @Override
    public int getAllChildCount(IBaseNodeData rowData) {
        String rowKey = rowData.getKey();
        if (!this.allChildCountMap.containsKey(rowKey)) {
            this.allChildCountMap.put(rowKey, this.innerAllChildCount(rowData));
        }
        return this.allChildCountMap.get(rowKey);
    }

    @Override
    public int getShowCountNumber(IBaseNodeData data) {
        if (this.isCountOfAllChildrenNum) {
            return this.getAllChildCount(data);
        }
        return this.getDirectChildCount(data);
    }

    protected int innerDirectChildCount(IBaseNodeData rowData) {
        return this.dataTable.getDirectChildCount(rowData.getKey());
    }

    protected int innerAllChildCount(IBaseNodeData rowData) {
        return this.dataTable.getAllChildCount(rowData.getKey());
    }
}

