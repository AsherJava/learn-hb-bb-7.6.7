/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataRow;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataTable;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupNodeDataBuilder;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupNodeProvider
implements ITreeNodeProvider {
    private final IBaseNodeData actionNode;
    protected IGroupDataTable groupDataTable;
    protected IGroupNodeDataBuilder nodeBuilder;
    protected IUnitTreeEntityRowProvider dimRowProvider;

    public GroupNodeProvider(IGroupDataTable groupDataTable, IUnitTreeEntityRowProvider dimRowProvider, IGroupNodeDataBuilder nodeBuilder, IBaseNodeData actionNode) {
        this.actionNode = actionNode;
        this.nodeBuilder = nodeBuilder;
        this.dimRowProvider = dimRowProvider;
        this.groupDataTable = groupDataTable;
    }

    public List<ITree<IBaseNodeData>> getTree() {
        return this.getTree(this.actionNode);
    }

    public List<ITree<IBaseNodeData>> getRoots() {
        List<IGroupDataRow> rootGroupRows = this.groupDataTable.getRootGroupRows();
        return rootGroupRows.stream().map(groupRow -> this.nodeBuilder.buildTreeNode((IGroupDataRow)groupRow)).collect(Collectors.toList());
    }

    public List<ITree<IBaseNodeData>> getChildren(IBaseNodeData parentNode) {
        if ("node@Group".equals(parentNode.get("nodeType"))) {
            List<IEntityRow> childEntityRows;
            ArrayList<ITree<IBaseNodeData>> children = new ArrayList<ITree<IBaseNodeData>>();
            List<IGroupDataRow> childGroupRows = this.groupDataTable.getChildGroupRows(parentNode);
            Map<String, List<IGroupDataRow>> groupByNodeType = childGroupRows.stream().collect(Collectors.groupingBy(IGroupDataRow::getRowType));
            if (groupByNodeType.containsKey("node@Dim")) {
                childEntityRows = groupByNodeType.get("node@Dim").stream().map(IGroupDataRow::getData).collect(Collectors.toList());
                this.nodeBuilder.beforeCreateITreeNode(childEntityRows);
                children.addAll(childEntityRows.stream().map(entityRow -> this.nodeBuilder.buildTreeNode((IEntityRow)entityRow)).collect(Collectors.toList()));
            }
            if (groupByNodeType.containsKey("node@Group")) {
                childEntityRows = groupByNodeType.get("node@Group");
                children.addAll(childEntityRows.stream().map(groupRow -> this.nodeBuilder.buildTreeNode((IGroupDataRow)groupRow)).collect(Collectors.toList()));
            }
            return children;
        }
        List<IEntityRow> childEntityRows = this.dimRowProvider.getChildRows(parentNode);
        this.nodeBuilder.beforeCreateITreeNode(childEntityRows);
        return childEntityRows.stream().map(entityRow -> this.nodeBuilder.buildTreeNode((IEntityRow)entityRow)).collect(Collectors.toList());
    }

    public List<ITree<IBaseNodeData>> getTree(IBaseNodeData actionNode) {
        ArrayList<String> nodePath = new ArrayList<String>(Arrays.asList(this.groupDataTable.getNodePath(actionNode)));
        List<ITree<IBaseNodeData>> tree = this.getRoots();
        ArrayDeque nodeQue = new ArrayDeque(0);
        tree.forEach(nodeQue::addLast);
        while (!nodeQue.isEmpty()) {
            ITree target = (ITree)nodeQue.peekFirst();
            if (nodePath.contains(target.getKey())) {
                List<ITree<IBaseNodeData>> children = this.getChildren((IBaseNodeData)target.getData());
                target.setChildren(children);
                if (children != null && !children.isEmpty()) {
                    children.forEach(nodeQue::addLast);
                }
            }
            nodeQue.pollFirst();
        }
        return tree;
    }
}

