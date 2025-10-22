/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.INodeImpl
 *  com.jiuqi.nr.common.itree.INodeMap
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.unit.uselector.filter.scheme;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.INodeImpl;
import com.jiuqi.nr.common.itree.INodeMap;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FilterSchemeHighLevelExecutor {
    private IUnitTreeContext context;
    private List<INodeMap<INode>> tree;
    private IUSelectorEntityRowProvider entityRowsProvider;

    public FilterSchemeHighLevelExecutor(IUnitTreeContext context, IUSelectorEntityRowProvider entityRowsProvider) {
        this.context = context;
        this.entityRowsProvider = entityRowsProvider;
    }

    public List<String> getHighestLevelSet(List<String> rangeList) {
        ArrayList<String> filterSet = new ArrayList<String>();
        List<INodeMap<INode>> tree = this.getTree(rangeList);
        tree.forEach(n -> filterSet.add(n.getKey()));
        return filterSet;
    }

    private List<INodeMap<INode>> getTree(List<String> rangeList) {
        if (null == this.tree) {
            this.tree = new ArrayList<INodeMap<INode>>();
            LinkedHashMap<String, INodeMap<INode>> nodeMap = new LinkedHashMap<String, INodeMap<INode>>();
            List<IEntityRow> checkRows = this.entityRowsProvider.getCheckRows(rangeList);
            for (IEntityRow row : checkRows) {
                if (null == row) continue;
                String rowKey = row.getEntityKeyData();
                INodeImpl impl = new INodeImpl();
                impl.setKey(row.getEntityKeyData());
                impl.setTitle(row.getTitle());
                impl.setCode(row.getCode());
                nodeMap.put(rowKey, (INodeMap<INode>)new INodeMap(rowKey, (Object)impl));
            }
            for (Map.Entry entry : nodeMap.entrySet()) {
                INodeMap node = (INodeMap)entry.getValue();
                INodeMap<INode> parentnode = this.findParenNode(this.entityRowsProvider, nodeMap, (INodeMap<INode>)node);
                if (null == parentnode) {
                    this.tree.add((INodeMap<INode>)node);
                    continue;
                }
                parentnode.appendChild(node);
            }
        }
        return this.tree;
    }

    private INodeMap<INode> findParenNode(IUSelectorEntityRowProvider entityRowsProvider, Map<String, INodeMap<INode>> nodeMap, INodeMap<INode> node) {
        String[] path = entityRowsProvider.getParentsEntityKeyDataPath(node.getKey());
        for (int i = path.length - 1; i > -1; --i) {
            String pKey = path[i];
            INodeMap<INode> pNode = nodeMap.get(pKey);
            if (null == pNode || pNode.getKey().equals(node.getKey())) continue;
            return pNode;
        }
        return null;
    }
}

