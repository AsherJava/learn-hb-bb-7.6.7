/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.define;

import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.define.EdgeDefine;
import com.jiuqi.nr.graph.define.GraphDefine;
import com.jiuqi.nr.graph.define.IndexDefine;
import com.jiuqi.nr.graph.define.NodeDefine;
import com.jiuqi.nr.graph.exception.GraphDefineException;
import com.jiuqi.nr.graph.function.AttrValueGetter;
import com.jiuqi.nr.graph.function.EdgePredicate;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.LabelCollector;
import com.jiuqi.nr.graph.label.NodeLabel;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.Assert;

public class GraphDefineBuilder {
    private LabelCollector labelCollector;
    private Map<NodeLabel, NodeDefine> nodeMap;
    private Map<IndexLabel, IndexDefine> indexMap;
    private GraphDefine graphDefine;

    protected GraphDefineBuilder(String graphName) {
        Assert.notNull((Object)graphName, "graphName must not be null.");
        this.labelCollector = new LabelCollector(graphName);
        this.nodeMap = new HashMap<NodeLabel, NodeDefine>();
        this.indexMap = new HashMap<IndexLabel, IndexDefine>();
    }

    private void modified() {
        this.graphDefine = null;
    }

    public NodeLabel registerNode(String nodeName) {
        return this.registerNode(nodeName, null);
    }

    public NodeLabel registerNode(String nodeName, AttrValueGetter<Object, String> nodeKeyGetter) {
        Assert.notNull((Object)nodeName, "nodeName must not be null.");
        NodeLabel nodeLabel = this.labelCollector.createNodeLabel(nodeName);
        NodeDefine nodeDefine = new NodeDefine(nodeLabel, nodeKeyGetter);
        this.nodeMap.put(nodeLabel, nodeDefine);
        this.modified();
        return nodeLabel;
    }

    public EdgeLabel registerEdge(String edgeName, NodeLabel outLabel, NodeLabel inLabel) {
        return this.registerEdge(edgeName, outLabel, inLabel, null);
    }

    public EdgeLabel registerEdge(String edgeName, NodeLabel outLabel, NodeLabel inLabel, EdgePredicate predicate) {
        Assert.notNull((Object)edgeName, "edgeName must not be null.");
        Assert.notNull((Object)outLabel, "outLabel must not be null.");
        Assert.notNull((Object)inLabel, "inLabel must not be null.");
        NodeDefine outNodeDefine = this.nodeMap.get(outLabel);
        NodeDefine inNodeDefine = this.nodeMap.get(inLabel);
        if (null == outNodeDefine) {
            throw new GraphDefineException("\u672a\u627e\u5230\u8d77\u59cb\u8282\u70b9\u7c7b\u578b");
        }
        if (null == inNodeDefine) {
            throw new GraphDefineException("\u672a\u627e\u5230\u6307\u5411\u8282\u70b9\u7c7b\u578b");
        }
        EdgeLabel edgeLabel = this.labelCollector.createEdgeLabel(edgeName, outLabel.getName(), inLabel.getName());
        EdgeDefine edgeDefine = new EdgeDefine(edgeLabel, outLabel, inLabel, predicate);
        outNodeDefine.addEdgeDefine(edgeDefine);
        inNodeDefine.addEdgeDefine(edgeDefine);
        this.modified();
        return edgeLabel;
    }

    public IndexLabel registerIndex(String indexName, NodeLabel nodeLabel, AttrValueGetter<INode, String> indexKeyGetter) {
        Assert.notNull((Object)indexName, "indexName must not be null.");
        Assert.notNull((Object)nodeLabel, "nodeLabel must not be null.");
        Assert.notNull(indexKeyGetter, "indexKeyGetter must not be null.");
        if (!this.nodeMap.containsKey(nodeLabel)) {
            throw new GraphDefineException("\u672a\u627e\u5230\u7d22\u5f15\u6240\u5c5e\u8282\u70b9\u7c7b\u578b");
        }
        IndexLabel indexLabel = this.labelCollector.createIndexLabel(indexName);
        IndexDefine indexDefine = new IndexDefine(indexLabel, nodeLabel, indexKeyGetter);
        this.indexMap.put(indexLabel, indexDefine);
        this.modified();
        return indexLabel;
    }

    private void check() {
        if (this.nodeMap.isEmpty()) {
            throw new GraphDefineException("\u672a\u6ce8\u518c\u4efb\u4f55\u8282\u70b9\u7c7b\u578b");
        }
    }

    public GraphDefine getGraphDefine() {
        if (null != this.graphDefine) {
            return this.graphDefine;
        }
        this.check();
        NodeDefine[] nodeDefines = new NodeDefine[this.nodeMap.size()];
        for (Map.Entry<NodeLabel, NodeDefine> entry : this.nodeMap.entrySet()) {
            nodeDefines[entry.getKey().getIndex()] = entry.getValue();
        }
        IndexDefine[] indexDefines = new IndexDefine[this.indexMap.size()];
        for (Map.Entry<IndexLabel, IndexDefine> entry : this.indexMap.entrySet()) {
            indexDefines[entry.getKey().getIndex()] = entry.getValue();
        }
        this.graphDefine = new GraphDefine(this.labelCollector, nodeDefines, indexDefines);
        return this.graphDefine;
    }
}

