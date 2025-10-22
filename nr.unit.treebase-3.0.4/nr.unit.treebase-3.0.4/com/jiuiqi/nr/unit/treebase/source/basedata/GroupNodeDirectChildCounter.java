/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataRow;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataTable;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupNodeDirectChildCounter
implements IUnitTreeNodeCounter {
    protected IGroupDataTable groupDataTable;
    protected IUnitTreeEntityRowProvider dimRowProvider;
    protected IBaseNodeData locateNode;

    public GroupNodeDirectChildCounter(IGroupDataTable groupDataTable, IUnitTreeEntityRowProvider dimRowProvider, IBaseNodeData locateNode) {
        this.locateNode = locateNode;
        this.groupDataTable = groupDataTable;
        this.dimRowProvider = dimRowProvider;
    }

    @Override
    public Map<String, Integer> getRootNodeCountMap() {
        HashMap<String, Integer> nodeCountMap = new HashMap<String, Integer>();
        List<IGroupDataRow> rootGroupRows = this.groupDataTable.getRootGroupRows();
        for (IGroupDataRow row : rootGroupRows) {
            IBaseNodeData nodeData = this.getDataImpl(row);
            nodeCountMap.put(row.getEntityKeyData(), this.getShowTextNumber(nodeData));
        }
        return nodeCountMap;
    }

    @Override
    public Map<String, Integer> getChildNodeCountMap(IBaseNodeData parent) {
        HashMap<String, Integer> nodeCountMap = new HashMap<String, Integer>();
        IGroupDataRow groupRow = this.groupDataTable.findGroupRow(parent);
        if (groupRow != null && "node@Group".equals(groupRow.getRowType())) {
            List<IGroupDataRow> childGroupRows = this.groupDataTable.getChildGroupRows(parent);
            for (IGroupDataRow row : childGroupRows) {
                IBaseNodeData nodeData;
                if ("node@Dim".equals(row.getRowType())) {
                    nodeData = this.getDataImpl(row.getData());
                    nodeCountMap.put(row.getEntityKeyData(), this.getShowTextNumber(nodeData));
                    continue;
                }
                if (!"node@Group".equals(row.getRowType())) continue;
                nodeData = this.getDataImpl(row);
                nodeCountMap.put(row.getEntityKeyData(), this.getShowTextNumber(nodeData));
            }
        } else {
            List<IEntityRow> childEntityRows = this.dimRowProvider.getChildRows(parent);
            for (IEntityRow row : childEntityRows) {
                IBaseNodeData nodeData = this.getDataImpl(row);
                nodeCountMap.put(row.getEntityKeyData(), this.getShowTextNumber(nodeData));
            }
        }
        return nodeCountMap;
    }

    @Override
    public Map<String, Integer> getTreeNodeCountMap(IBaseNodeData locateNode) {
        if (locateNode == null) {
            locateNode = this.locateNode;
        }
        HashMap<String, Integer> nodeCountMap = new HashMap<String, Integer>();
        ArrayList<String> nodePath = new ArrayList<String>(Arrays.asList(this.groupDataTable.getNodePath(locateNode)));
        List<IGroupDataRow> tree = this.groupDataTable.getRootGroupRows();
        ArrayDeque<IBaseNodeData> nodeQue = new ArrayDeque<IBaseNodeData>(0);
        tree.forEach(e -> nodeQue.addLast(this.getDataImpl((IGroupDataRow)e)));
        while (!nodeQue.isEmpty()) {
            IBaseNodeData target = (IBaseNodeData)nodeQue.peekFirst();
            nodeCountMap.put(target.getKey(), this.getShowTextNumber(target));
            if (nodePath.contains(target.getKey())) {
                this.addChildrenToLast(nodeQue, target);
            }
            nodeQue.pollFirst();
        }
        return nodeCountMap;
    }

    private void addChildrenToLast(Deque<IBaseNodeData> nodeQue, IBaseNodeData parent) {
        IGroupDataRow groupRow = this.groupDataTable.findGroupRow(parent);
        if (groupRow != null && "node@Group".equals(groupRow.getRowType())) {
            List<IGroupDataRow> childGroupRows = this.groupDataTable.getChildGroupRows(parent);
            for (IGroupDataRow row : childGroupRows) {
                if ("node@Dim".equals(row.getRowType())) {
                    nodeQue.addLast(this.getDataImpl(row.getData()));
                    continue;
                }
                if (!"node@Group".equals(row.getRowType())) continue;
                nodeQue.addLast(this.getDataImpl(row));
            }
        } else {
            List<IEntityRow> childEntityRows = this.dimRowProvider.getChildRows(parent);
            for (IEntityRow row : childEntityRows) {
                nodeQue.addLast(this.getDataImpl(row));
            }
        }
    }

    private IBaseNodeData getDataImpl(IGroupDataRow dataRow) {
        BaseNodeDataImpl data = new BaseNodeDataImpl();
        data.setKey(dataRow.getEntityKeyData());
        data.setCode(dataRow.getCode());
        data.setTitle(dataRow.getTitle());
        data.put("nodeType", (Object)dataRow.getRowType());
        return data;
    }

    private IBaseNodeData getDataImpl(IEntityRow dataRow) {
        BaseNodeDataImpl data = new BaseNodeDataImpl();
        data.setKey(dataRow.getEntityKeyData());
        data.setCode(dataRow.getCode());
        data.setTitle(dataRow.getTitle());
        data.put("nodeType", (Object)"node@Dim");
        return data;
    }

    protected int getShowTextNumber(IBaseNodeData nodeData) {
        if ("node@Group".equals(nodeData.get("nodeType"))) {
            return this.groupDataTable.getDirectChildCount(nodeData);
        }
        return this.dimRowProvider.getDirectChildCount(nodeData);
    }
}

