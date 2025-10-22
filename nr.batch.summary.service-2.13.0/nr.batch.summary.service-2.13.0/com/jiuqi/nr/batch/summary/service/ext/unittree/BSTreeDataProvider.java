/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.itree.ITree$traverPloy
 *  com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BSTreeDataProvider
implements ITreeNodeProvider {
    private static final String NAME_OF_DATA = "data";
    private String onSelectNodeProviderKey;
    private IBaseNodeData locateNode;
    private IUnitTreeNodeBuilder currNodeBuilder;
    private Map<String, IUnitTreeNodeBuilder> nodeBuilderMap;
    private Map<String, ITreeNodeProvider> nodeProviderMap;
    private Map<String, IUnitTreeEntityRowProvider> rowProviderMap;

    public BSTreeDataProvider(Map<String, IUnitTreeNodeBuilder> nodeBuilderMap, Map<String, ITreeNodeProvider> nodeProviderMap, Map<String, IUnitTreeEntityRowProvider> rowProviderMap, IBaseNodeData locateNode, String onSelectNodeProviderKey) {
        this.locateNode = locateNode;
        this.rowProviderMap = rowProviderMap;
        this.nodeBuilderMap = nodeBuilderMap;
        this.nodeProviderMap = nodeProviderMap;
        this.onSelectNodeProviderKey = onSelectNodeProviderKey;
    }

    public List<ITree<IBaseNodeData>> getRoots() {
        ArrayList<ITree<IBaseNodeData>> roots = new ArrayList<ITree<IBaseNodeData>>();
        for (Map.Entry<String, ITreeNodeProvider> entry : this.nodeProviderMap.entrySet()) {
            String providerKey = entry.getKey();
            this.currNodeBuilder = this.nodeBuilderMap.get(providerKey);
            List oneRoots = entry.getValue().getRoots();
            this.setSelectOneNode(providerKey, oneRoots);
            roots.addAll(oneRoots);
        }
        return roots;
    }

    public List<ITree<IBaseNodeData>> getChildren(IBaseNodeData parentNode) {
        return this.getNodeDataProvider(parentNode).getChildren(parentNode);
    }

    public List<ITree<IBaseNodeData>> getTree() {
        List<ITree<IBaseNodeData>> tree = this.getRoots();
        if (this.locateNode == null) {
            return tree;
        }
        Set<String> path = this.collectNodePath(this.locateNode);
        if (null != path && !path.isEmpty()) {
            this.clearSelectState(tree);
            ITreeBreadthFirstIterator iterator = new ITreeBreadthFirstIterator(tree);
            while (iterator.hasNext()) {
                ITree target = iterator.peekFirst();
                if (path.contains(target.getKey()) && ((IBaseNodeData)target.getData()).get("batchGatherSchemeCode").equals(this.locateNode.get("batchGatherSchemeCode"))) {
                    target.setExpanded(true);
                    target.setSelected(target.getKey().equals(this.locateNode.getKey()));
                    target.setChildren(this.getChildren((IBaseNodeData)target.getData()));
                }
                iterator.next();
            }
        }
        return tree;
    }

    protected Set<String> collectNodePath(IBaseNodeData locateNode) {
        HashSet<String> paths = new HashSet<String>();
        String[] path = this.getRowDataProvider(locateNode).getNodePath(locateNode);
        if (path != null && path.length > 0) {
            paths.addAll(Arrays.asList(path));
            paths.add(locateNode.getKey());
        }
        return paths;
    }

    private ITreeNodeProvider getNodeDataProvider(IBaseNodeData nodeData) {
        Object schemeKey;
        Object data = nodeData.get(NAME_OF_DATA);
        if (data instanceof Map) {
            LinkedHashMap map = (LinkedHashMap)data;
            schemeKey = map.get("batchGatherSchemeCode");
        } else {
            schemeKey = nodeData.get("batchGatherSchemeCode");
        }
        String schemeCode = schemeKey.toString();
        this.currNodeBuilder = this.nodeBuilderMap.get(schemeCode);
        return this.nodeProviderMap.get(schemeCode);
    }

    private IUnitTreeEntityRowProvider getRowDataProvider(IBaseNodeData nodeData) {
        Object schemeKey;
        Object data = nodeData.get(NAME_OF_DATA);
        if (data instanceof Map) {
            LinkedHashMap map = (LinkedHashMap)data;
            schemeKey = map.get("batchGatherSchemeCode");
        } else {
            schemeKey = nodeData.get("batchGatherSchemeCode");
        }
        return this.rowProviderMap.get(schemeKey.toString());
    }

    private void setSelectOneNode(String providerKey, List<ITree<IBaseNodeData>> tree) {
        if (providerKey.equals(this.onSelectNodeProviderKey)) {
            Iterator iterator = tree.get(0).iterator(ITree.traverPloy.DEPTH_FIRST);
            while (iterator.hasNext()) {
                ITree next = (ITree)iterator.next();
                if (next.getKey().equals(providerKey)) continue;
                next.setSelected(true);
                break;
            }
        }
    }

    protected void clearSelectState(List<ITree<IBaseNodeData>> tree) {
        ITreeBreadthFirstIterator iterator = new ITreeBreadthFirstIterator(tree);
        while (iterator.hasNext()) {
            iterator.next().setSelected(false);
        }
    }
}

