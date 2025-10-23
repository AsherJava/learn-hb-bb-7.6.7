/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.label;

import com.jiuqi.nr.graph.exception.GraphDefineException;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.GraphLabel;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.Assert;

public class LabelCollector {
    private GraphLabel graphLabel;
    private Map<String, NodeLabel> nodeLabelMap;
    private Map<String, IndexLabel> indexLabelMap;
    private Map<String, EdgeLabel> edgeLabelMap;
    private Map<String, Integer> outIndexMap;
    private Map<String, Integer> inIndexMap;

    public LabelCollector(String graphName) {
        Assert.notNull((Object)graphName, "graphName must not be null.");
        this.graphLabel = new GraphLabel(graphName);
        this.nodeLabelMap = new HashMap<String, NodeLabel>();
        this.indexLabelMap = new HashMap<String, IndexLabel>();
        this.edgeLabelMap = new HashMap<String, EdgeLabel>();
        this.outIndexMap = new HashMap<String, Integer>();
        this.inIndexMap = new HashMap<String, Integer>();
    }

    public GraphLabel getGraphLabel() {
        return this.graphLabel;
    }

    public NodeLabel getNodeLabel(String nodeName) {
        Assert.notNull((Object)nodeName, "nodeName must not be null.");
        return this.nodeLabelMap.get(nodeName);
    }

    public IndexLabel getIndexLabel(String indexName) {
        Assert.notNull((Object)indexName, "indexName must not be null.");
        return this.indexLabelMap.get(indexName);
    }

    public EdgeLabel getEdgeLabel(String edgeName) {
        Assert.notNull((Object)edgeName, "edgeName must not be null.");
        return this.edgeLabelMap.get(edgeName);
    }

    private GraphDefineException nameRegistedError(String name) {
        return new GraphDefineException("\u8282\u70b9\u540d\u79f0 [" + name + "] \u5df2\u7ecf\u88ab\u6ce8\u518c\u4e86");
    }

    public NodeLabel createNodeLabel(String nodeName) {
        Assert.notNull((Object)nodeName, "nodeName must not be null.");
        if (this.nodeLabelMap.containsKey(nodeName)) {
            throw this.nameRegistedError(nodeName);
        }
        NodeLabel nodeLabel = new NodeLabel(this.nodeLabelMap.size(), nodeName);
        this.nodeLabelMap.put(nodeName, nodeLabel);
        return nodeLabel;
    }

    public IndexLabel createIndexLabel(String indexName) {
        Assert.notNull((Object)indexName, "indexName must not be null.");
        if (this.indexLabelMap.containsKey(indexName)) {
            throw this.nameRegistedError(indexName);
        }
        IndexLabel indexLabel = new IndexLabel(this.indexLabelMap.size(), indexName);
        this.indexLabelMap.put(indexName, indexLabel);
        return indexLabel;
    }

    public EdgeLabel createEdgeLabel(String edgeName, String outNodeName, String inNodeName) {
        Assert.notNull((Object)edgeName, "edgeName must not be null.");
        Assert.notNull((Object)outNodeName, "outNodeName must not be null.");
        Assert.notNull((Object)inNodeName, "inNodeName must not be null.");
        if (this.edgeLabelMap.containsKey(edgeName)) {
            throw this.nameRegistedError(edgeName);
        }
        Integer outIndex = this.outIndexMap.compute(outNodeName, (k, v) -> {
            int n;
            if (null == v) {
                n = 0;
            } else {
                v = v + 1;
                n = v;
            }
            return n;
        });
        Integer inIndex = this.inIndexMap.compute(inNodeName, (k, v) -> {
            int n;
            if (null == v) {
                n = 0;
            } else {
                v = v + 1;
                n = v;
            }
            return n;
        });
        EdgeLabel edgeLabel = new EdgeLabel(outIndex, inIndex, edgeName);
        this.edgeLabelMap.put(edgeName, edgeLabel);
        return edgeLabel;
    }
}

