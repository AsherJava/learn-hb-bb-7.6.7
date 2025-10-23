/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.define;

import com.jiuqi.nr.graph.define.EdgeDefine;
import com.jiuqi.nr.graph.define.IndexDefine;
import com.jiuqi.nr.graph.define.NodeDefine;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.GraphLabel;
import com.jiuqi.nr.graph.label.ILabelabled;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.LabelCollector;
import com.jiuqi.nr.graph.label.NodeLabel;
import com.jiuqi.nr.graph.util.IteratorUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GraphDefine
implements ILabelabled<GraphLabel> {
    private LabelCollector labelCollector;
    private NodeDefine[] nodeDefines;
    private IndexDefine[] indexDefines;
    private Map<EdgeLabel, EdgeDefine> edgeDefines;

    protected GraphDefine(LabelCollector labelCollector, NodeDefine[] nodeDefines, IndexDefine[] indexDefines) {
        this.labelCollector = labelCollector;
        this.nodeDefines = nodeDefines;
        this.indexDefines = indexDefines;
        if (null != nodeDefines) {
            this.edgeDefines = new HashMap<EdgeLabel, EdgeDefine>();
            for (NodeDefine nodeDefine : nodeDefines) {
                nodeDefine.outEdgeDefineIterator().forEachRemaining(d -> this.edgeDefines.put(d.getLabel(), (EdgeDefine)d));
            }
        }
    }

    @Override
    public GraphLabel getLabel() {
        return this.labelCollector.getGraphLabel();
    }

    public NodeLabel getNodeLabel(String nodeName) {
        return this.labelCollector.getNodeLabel(nodeName);
    }

    public IndexLabel getIndexLabel(String indexName) {
        return this.labelCollector.getIndexLabel(indexName);
    }

    public EdgeLabel getEdgeLabel(String edgeName) {
        return this.labelCollector.getEdgeLabel(edgeName);
    }

    public int nodeDefineSize() {
        return this.nodeDefines.length;
    }

    public NodeDefine getNodeDefine(NodeLabel nodeLabel) {
        if (nodeLabel.getIndex() >= this.nodeDefineSize()) {
            return null;
        }
        NodeDefine nodeDefine = this.nodeDefines[nodeLabel.getIndex()];
        if (nodeDefine.getLabel() != nodeLabel) {
            return null;
        }
        return nodeDefine;
    }

    public Iterator<NodeDefine> nodeDefineIterable() {
        return IteratorUtils.toIterator(this.nodeDefines);
    }

    public int indexDefineSize() {
        return this.indexDefines.length;
    }

    public IndexDefine getIndexDefine(IndexLabel indexLabel) {
        if (indexLabel.getIndex() >= this.indexDefineSize()) {
            return null;
        }
        IndexDefine indexDefine = this.indexDefines[indexLabel.getIndex()];
        if (indexDefine.getLabel() != indexLabel) {
            return null;
        }
        return indexDefine;
    }

    public Iterator<IndexDefine> indexDefineIterator() {
        return IteratorUtils.toIterator(this.indexDefines);
    }

    public Iterator<IndexDefine> indexDefineIterator(NodeLabel nodeLabel) {
        return IteratorUtils.filterIterator(this.indexDefineIterator(), i -> i.getNodeLabel().equals(nodeLabel));
    }

    public int edgeDefineSize() {
        return null == this.edgeDefines ? 0 : this.edgeDefines.size();
    }

    public EdgeDefine getEdgeDefine(EdgeLabel edgeLabel) {
        if (null == this.edgeDefines) {
            return null;
        }
        return this.edgeDefines.get(edgeLabel);
    }

    public Iterator<EdgeDefine> edgeDefineIterator() {
        return IteratorUtils.toIterator(this.edgeDefines);
    }
}

