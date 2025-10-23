/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.cache;

import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.cache.GlobalIndex;
import com.jiuqi.nr.graph.cache.GraphCacheDefine;
import com.jiuqi.nr.graph.cache.IGraphMap;
import com.jiuqi.nr.graph.define.IndexDefine;
import com.jiuqi.nr.graph.define.NodeDefine;
import com.jiuqi.nr.graph.label.ILabel;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class GraphMap
extends HashMap<String, IGraph>
implements IGraphMap {
    protected GraphCacheDefine define;
    private GlobalIndex[] nodeGlobalIndex;
    private GlobalIndex[] indexGlobalIndex;

    public GraphMap(GraphCacheDefine define) {
        this.define = define;
        this.initGlobalIndex();
    }

    private void initGlobalIndex() {
        if (this.define.isEnableGlobalIndex()) {
            this.nodeGlobalIndex = new GlobalIndex[this.define.getGraphDefine().nodeDefineSize()];
            this.indexGlobalIndex = new GlobalIndex[this.define.getGraphDefine().indexDefineSize()];
            Map<ILabel, Map<String, String>> globalIndex = this.define.getIndexSupplier().get();
            for (Map.Entry<ILabel, Map<String, String>> entry : globalIndex.entrySet()) {
                if (entry.getKey() instanceof IndexLabel) {
                    IndexLabel indexLabel = (IndexLabel)entry.getKey();
                    this.indexGlobalIndex[indexLabel.getIndex()] = GraphMap.createGlobalIndex(null == entry.getValue() ? new HashMap() : entry.getValue());
                    continue;
                }
                if (!(entry.getKey() instanceof NodeLabel)) continue;
                NodeLabel nodeLabel = (NodeLabel)entry.getKey();
                this.nodeGlobalIndex[nodeLabel.getIndex()] = GraphMap.createGlobalIndex(null == entry.getValue() ? new HashMap() : entry.getValue());
            }
        } else {
            this.nodeGlobalIndex = null;
            this.indexGlobalIndex = null;
        }
    }

    private static GlobalIndex createGlobalIndex(Map<String, String> map) {
        if (null == map || map == GlobalIndex.emptyGlobalIndex()) {
            return GlobalIndex.emptyGlobalIndex();
        }
        return new GlobalIndex(map);
    }

    private static GlobalIndex getGlobalIndex(GlobalIndex[] array, int index) {
        if (null == array[index]) {
            array[index] = GraphMap.createGlobalIndex(null);
        }
        return array[index];
    }

    private GlobalIndex[] copyNodeGlobalIndex() {
        GlobalIndex[] copy = new GlobalIndex[this.nodeGlobalIndex.length];
        for (int i = 0; i < copy.length; ++i) {
            copy[i] = GraphMap.createGlobalIndex(this.nodeGlobalIndex[i]);
        }
        return copy;
    }

    private GlobalIndex[] copyIndexGlobalIndex() {
        GlobalIndex[] copy = new GlobalIndex[this.indexGlobalIndex.length];
        for (int i = 0; i < copy.length; ++i) {
            copy[i] = GraphMap.createGlobalIndex(this.indexGlobalIndex[i]);
        }
        return copy;
    }

    @Override
    public GlobalIndex getGlobalIndex(NodeLabel label) {
        if (!this.define.isEnableGlobalIndex()) {
            return GlobalIndex.emptyGlobalIndex();
        }
        if (null == this.nodeGlobalIndex[label.getIndex()]) {
            this.nodeGlobalIndex[label.getIndex()] = GraphMap.createGlobalIndex(null);
        }
        return this.nodeGlobalIndex[label.getIndex()];
    }

    @Override
    public GlobalIndex getGlobalIndex(IndexLabel label) {
        if (!this.define.isEnableGlobalIndex()) {
            return GlobalIndex.emptyGlobalIndex();
        }
        if (null == this.indexGlobalIndex[label.getIndex()]) {
            this.indexGlobalIndex[label.getIndex()] = GraphMap.createGlobalIndex(null);
        }
        return this.indexGlobalIndex[label.getIndex()];
    }

    private void removeIndex(IGraph graph) {
        if (null == graph) {
            return;
        }
        Iterator<NodeDefine> nodeDefines = this.define.getGraphDefine().nodeDefineIterable();
        while (nodeDefines.hasNext()) {
            NodeDefine next = nodeDefines.next();
            NodeLabel nodeLabel = next.getLabel();
            GlobalIndex globalIndex = GraphMap.getGlobalIndex(this.nodeGlobalIndex, nodeLabel.getIndex());
            graph.forEachNode(nodeLabel, node -> globalIndex.remove(node.getKey()));
        }
        Iterator<IndexDefine> indexLabels = this.define.getGraphDefine().indexDefineIterator();
        while (indexLabels.hasNext()) {
            IndexDefine next = indexLabels.next();
            IndexLabel indexLabel = next.getLabel();
            GlobalIndex globalIndex = GraphMap.getGlobalIndex(this.indexGlobalIndex, indexLabel.getIndex());
            graph.forEachNode(indexLabel, (id, node) -> globalIndex.remove(id));
        }
    }

    private void addIndex(String key, IGraph graph) {
        if (null == graph) {
            return;
        }
        Iterator<NodeDefine> nodeDefines = this.define.getGraphDefine().nodeDefineIterable();
        while (nodeDefines.hasNext()) {
            NodeDefine next = nodeDefines.next();
            NodeLabel nodeLabel = next.getLabel();
            GlobalIndex globalIndex = GraphMap.getGlobalIndex(this.nodeGlobalIndex, nodeLabel.getIndex());
            if (globalIndex == GlobalIndex.emptyGlobalIndex()) continue;
            graph.forEachNode(nodeLabel, node -> globalIndex.put(node.getKey(), key));
        }
        Iterator<IndexDefine> indexLabels = this.define.getGraphDefine().indexDefineIterator();
        while (indexLabels.hasNext()) {
            IndexDefine next = indexLabels.next();
            IndexLabel indexLabel = next.getLabel();
            GlobalIndex globalIndex = GraphMap.getGlobalIndex(this.indexGlobalIndex, indexLabel.getIndex());
            if (globalIndex == GlobalIndex.emptyGlobalIndex()) continue;
            graph.forEachNode(indexLabel, (id, node) -> globalIndex.put(id, key));
        }
    }

    private void refreshIndex(String key, IGraph oldValue, IGraph newValue) {
        if (!this.define.isEnableGlobalIndex()) {
            return;
        }
        this.removeIndex(oldValue);
        this.addIndex(key, newValue);
    }

    private void refreshIndex(String key, IGraph oldValue) {
        if (null == this.define.getIndexRefresher()) {
            return;
        }
        this.removeIndex(oldValue);
        Map<ILabel, Map<String, String>> map = this.define.getIndexRefresher().apply(key);
        Iterator<NodeDefine> nodeDefines = this.define.getGraphDefine().nodeDefineIterable();
        while (nodeDefines.hasNext()) {
            Map<String, String> index;
            NodeDefine next = nodeDefines.next();
            NodeLabel nodeLabel = next.getLabel();
            GlobalIndex globalIndex = GraphMap.getGlobalIndex(this.nodeGlobalIndex, nodeLabel.getIndex());
            if (globalIndex == GlobalIndex.emptyGlobalIndex() || CollectionUtils.isEmpty(index = map.get(nodeLabel))) continue;
            globalIndex.putAll(index);
        }
        Iterator<IndexDefine> indexLabels = this.define.getGraphDefine().indexDefineIterator();
        while (indexLabels.hasNext()) {
            Map<String, String> index;
            IndexDefine next = indexLabels.next();
            IndexLabel indexLabel = next.getLabel();
            GlobalIndex globalIndex = GraphMap.getGlobalIndex(this.indexGlobalIndex, indexLabel.getIndex());
            if (globalIndex == GlobalIndex.emptyGlobalIndex() || CollectionUtils.isEmpty(index = map.get(indexLabel))) continue;
            globalIndex.putAll(index);
        }
    }

    @Override
    public boolean containsKey(String string) {
        return super.containsKey(string);
    }

    @Override
    public IGraph get(String key) {
        return (IGraph)super.get(key);
    }

    @Override
    public IGraphMap clone() {
        GraphMap clone = (GraphMap)super.clone();
        clone.define = this.define;
        clone.nodeGlobalIndex = this.copyNodeGlobalIndex();
        clone.indexGlobalIndex = this.copyIndexGlobalIndex();
        return clone;
    }

    @Override
    public IGraph put(String key, IGraph value) {
        IGraph oldGraph = super.put(key, value);
        this.refreshIndex(key, oldGraph, value);
        return oldGraph;
    }

    @Override
    public IGraph remove(String key) {
        IGraph oldGraph = (IGraph)super.remove(key);
        this.refreshIndex(key, oldGraph);
        return oldGraph;
    }

    @Override
    public void clear() {
        super.clear();
        this.initGlobalIndex();
    }
}

