/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph;

import com.jiuqi.nr.graph.IDataRelation;
import com.jiuqi.nr.graph.IDataWrapper;
import com.jiuqi.nr.graph.IEdge;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.IPropertiesEditor;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface IGraphEditor
extends IGraph,
IPropertiesEditor {
    public INode addNode(NodeLabel var1, Object var2);

    default public List<INode> addNodes(NodeLabel nodeLabel, List<?> datas) {
        if (null == datas) {
            return Collections.emptyList();
        }
        ArrayList<INode> nodes = new ArrayList<INode>();
        for (Object data : datas) {
            nodes.add(this.addNode(nodeLabel, data));
        }
        return nodes;
    }

    public INode addNode(IDataWrapper var1);

    default public List<INode> addNodes(List<IDataWrapper> dataWrappers) {
        if (null == dataWrappers) {
            return Collections.emptyList();
        }
        ArrayList<INode> nodes = new ArrayList<INode>();
        for (IDataWrapper dataWrapper : dataWrappers) {
            nodes.add(this.addNode(dataWrapper));
        }
        return nodes;
    }

    public IEdge addEdge(EdgeLabel var1, INode var2, INode var3);

    default public List<IEdge> addEdges(EdgeLabel edgeLabel, Map<INode, INode> nodes) {
        if (null == nodes) {
            return Collections.emptyList();
        }
        ArrayList<IEdge> edges = new ArrayList<IEdge>();
        for (Map.Entry<INode, INode> entry : nodes.entrySet()) {
            edges.add(this.addEdge(edgeLabel, entry.getKey(), entry.getValue()));
        }
        return edges;
    }

    public IEdge addEdge(IDataRelation var1);

    default public List<IEdge> addEdges(List<IDataRelation> rels) {
        if (null == rels) {
            return Collections.emptyList();
        }
        ArrayList<IEdge> edges = new ArrayList<IEdge>();
        for (IDataRelation rel : rels) {
            edges.add(this.addEdge(rel));
        }
        return edges;
    }

    public void removeNode(NodeLabel var1, String var2);

    default public void removeNodes(NodeLabel nodeLabel, Collection<String> nodeKeys) {
        if (null == nodeKeys) {
            return;
        }
        for (String nodeKey : nodeKeys) {
            this.removeNode(nodeLabel, nodeKey);
        }
    }

    public void removeNode(INode var1);

    default public void removeNodes(Collection<INode> nodes) {
        if (null == nodes) {
            return;
        }
        for (INode node : nodes) {
            this.removeNode(node);
        }
    }

    public void removeEdge(EdgeLabel var1, INode var2, INode var3);

    default public void removeEdges(EdgeLabel edgeLabel, Map<INode, INode> nodes) {
        if (null == nodes) {
            return;
        }
        for (Map.Entry<INode, INode> entry : nodes.entrySet()) {
            this.removeEdge(edgeLabel, entry.getKey(), entry.getValue());
        }
    }

    default public void removeEdge(IEdge edge) {
        this.removeEdge((EdgeLabel)edge.getLabel(), edge.getOutNode(), edge.getInNode());
    }

    default public void removeEdges(Collection<IEdge> edges) {
        if (null == edges) {
            return;
        }
        for (IEdge edge : edges) {
            this.removeEdge(edge);
        }
    }

    public IGraph finish();
}

