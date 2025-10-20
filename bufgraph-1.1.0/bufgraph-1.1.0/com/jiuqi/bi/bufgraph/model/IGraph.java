/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.model;

import com.jiuqi.bi.bufgraph.GraphException;
import com.jiuqi.bi.bufgraph.model.Direction;
import com.jiuqi.bi.bufgraph.model.Edge;
import com.jiuqi.bi.bufgraph.model.Node;
import java.util.List;

public interface IGraph {
    public Node getNodeByUID(String var1) throws GraphException;

    public Node getNodeById(int var1) throws GraphException;

    public List<Node> getRelatedNodes(String var1, Direction var2, String ... var3) throws GraphException;

    public List<Node> getDirectIncomingNodes(String var1) throws GraphException;

    public List<Node> getDirectOutgoingNodes(String var1) throws GraphException;

    public List<Edge[]> getPaths(String var1, String var2) throws GraphException;

    public List<Edge[]> getShortestPaths(Node var1, Node var2) throws GraphException;

    public List<Node> topo(boolean var1) throws GraphException;

    public int getDegree(String var1, Direction var2, String ... var3) throws GraphException;
}

