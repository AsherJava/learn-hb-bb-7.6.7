/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.digraph;

import com.jiuqi.bi.util.collection.IMatrix;
import com.jiuqi.bi.util.collection.Matrix;
import com.jiuqi.bi.util.digraph.DGLink;
import com.jiuqi.bi.util.digraph.DGNode;
import com.jiuqi.bi.util.digraph.EDigraph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Digraph<T> {
    private List<DGNode<T>> nodes = new ArrayList<DGNode<T>>();
    private List<DGLink<T>> links = new ArrayList<DGLink<T>>();
    private static final int MAX_PRINT_SIZE = 64;

    public List<DGNode<T>> getNodes() {
        return this.nodes;
    }

    public List<DGLink<T>> getLinks() {
        return this.links;
    }

    public DGNode<T> add(T item) {
        DGNode<T> node = new DGNode<T>(item);
        this.add(node);
        return node;
    }

    public void add(DGNode<T> node) {
        this.nodes.add(node);
    }

    public DGLink<T> link(DGNode<T> initial, DGNode<T> terminal) {
        DGLink<T> link = new DGLink<T>(initial, terminal);
        initial.getAffects().add(link);
        terminal.getDepends().add(link);
        this.links.add(link);
        return link;
    }

    public void orderNodes() throws EDigraph {
        this.toplogicOrder(null);
    }

    public void orderNodes(boolean resort) throws EDigraph {
        this.orderNodes();
        if (resort) {
            Collections.sort(this.nodes, new Comparator<DGNode<T>>(){

                @Override
                public int compare(DGNode<T> n1, DGNode<T> n2) {
                    return n1.getOrder() - n2.getOrder();
                }
            });
        }
    }

    private void toplogicOrder(Map<DGNode<T>, Set<DGNode<T>>> depends) throws EDigraph {
        List<DGNode<T>> readyNodes = this.initOrderNodes();
        OrderToken token = new OrderToken();
        while (!readyNodes.isEmpty()) {
            List<DGNode<T>> topNodes = this.fetchTopNodes(readyNodes, token);
            if (topNodes.isEmpty() && (topNodes = this.checkDependencies(readyNodes, token)).isEmpty()) {
                throw new EDigraph("\u7a0b\u5e8f\u6267\u884c\u9519\u8bef\uff0c\u65e0\u6cd5\u5b8c\u6210\u5bf9\u6709\u5411\u56fe\u7684\u62d3\u6251\u6392\u5e8f\uff1a" + this.stringOf((T)readyNodes));
            }
            this.markAffects(topNodes);
            if (depends == null) continue;
            this.markDepends(topNodes, depends);
        }
    }

    private void markDepends(List<DGNode<T>> topNodes, Map<DGNode<T>, Set<DGNode<T>>> depends) {
        for (DGNode<T> prev : topNodes) {
            Set<DGNode<T>> prevDepends = depends.get(prev);
            for (DGLink<T> link : prev.getAffects()) {
                DGNode<T> next = link.getTerminal();
                Set<DGNode<T>> nextDepends = depends.get(next);
                if (nextDepends == null) {
                    nextDepends = new HashSet<DGNode<T>>();
                    depends.put(next, nextDepends);
                }
                nextDepends.add(prev);
                if (prevDepends == null) continue;
                nextDepends.addAll(prevDepends);
            }
        }
    }

    public IMatrix<Boolean> buildAffects() throws EDigraph {
        HashMap<DGNode<T>, Set<DGNode<T>>> depends = new HashMap<DGNode<T>, Set<DGNode<T>>>();
        this.toplogicOrder(depends);
        return this.buildAffectMatrix(depends);
    }

    public List<T> getRawNodes() {
        ArrayList<T> rawNodes = new ArrayList<T>(this.nodes.size());
        for (DGNode<T> node : this.nodes) {
            rawNodes.add(node.get());
        }
        return rawNodes;
    }

    protected String stringOf(List<DGNode<T>> nodes) {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        for (int i = 0; i < Math.min(64, nodes.size()); ++i) {
            if (i > 0) {
                buffer.append(", ");
            }
            T item = nodes.get(i).get();
            buffer.append(this.stringOf(item));
        }
        if (nodes.size() > 64) {
            buffer.append(", (").append(nodes.size()).append(")...");
        }
        buffer.append(']');
        return buffer.toString();
    }

    protected String stringOf(T item) {
        return item.toString();
    }

    private List<DGNode<T>> initOrderNodes() {
        for (DGNode<T> node : this.nodes) {
            node._inDegrees = node.getDepends().size();
        }
        return new LinkedList<DGNode<T>>(this.nodes);
    }

    private List<DGNode<T>> fetchTopNodes(List<DGNode<T>> readyNodes, OrderToken token) {
        ArrayList<DGNode<T>> nodes = new ArrayList<DGNode<T>>();
        Iterator<DGNode<T>> i = readyNodes.iterator();
        while (i.hasNext()) {
            DGNode<T> node = i.next();
            if (node._inDegrees != 0) continue;
            node.setLevel(token.level);
            node.setOrder(token.order++);
            nodes.add(node);
            i.remove();
        }
        ++token.level;
        return nodes;
    }

    private void markAffects(List<DGNode<T>> nodes) {
        for (DGNode<T> node : nodes) {
            for (DGLink<T> link : node.getAffects()) {
                --link.getTerminal()._inDegrees;
            }
        }
    }

    private List<DGNode<T>> checkDependencies(List<DGNode<T>> readyNodes, OrderToken token) throws EDigraph {
        Stack<DGNode<T>> route = new Stack<DGNode<T>>();
        DGNode<T> breakNode = this.scanRoute(route, readyNodes.get(0));
        if (breakNode == null) {
            throw new EDigraph("\u7a0b\u5e8f\u9519\u8bef\uff0c\u65e0\u6cd5\u5206\u6790\u6709\u5411\u56fe\u7684\u5faa\u73af\u4f9d\u8d56\u5173\u7cfb\uff1a" + this.stringOf((T)readyNodes));
        }
        breakNode._inDegrees = 0;
        readyNodes.remove(breakNode);
        breakNode.setOrder(token.order++);
        breakNode.setLevel(token.level++);
        ArrayList<DGNode<T>> breakNodes = new ArrayList<DGNode<T>>(1);
        breakNodes.add(breakNode);
        return breakNodes;
    }

    private DGNode<T> scanRoute(Stack<DGNode<T>> route, DGNode<T> current) throws EDigraph {
        if (route.contains(current)) {
            route.push(current);
            return this.processDeadRoute(route);
        }
        route.push(current);
        for (DGLink<T> link : current.getAffects()) {
            DGNode<T> breakNode;
            DGNode<T> next = link.getTerminal();
            if (next._inDegrees <= 0 || (breakNode = this.scanRoute(route, next)) == null) continue;
            return breakNode;
        }
        route.pop();
        return null;
    }

    protected DGNode<T> processDeadRoute(List<DGNode<T>> route) throws EDigraph {
        throw new EDigraph("\u6709\u5411\u56fe\u4e2d\u5b58\u5728\u5faa\u73af\u4f9d\u8d56\u5173\u7cfb\uff0c\u4f9d\u8d56\u8def\u5f84\u4e3a\uff1a" + this.stringOf((T)route));
    }

    private IMatrix<Boolean> buildAffectMatrix(Map<DGNode<T>, Set<DGNode<T>>> depends) {
        int index = 0;
        for (DGNode<T> node : this.nodes) {
            node._inDegrees = index++;
        }
        Matrix<Boolean> affects = new Matrix<Boolean>(this.nodes.size(), this.nodes.size());
        for (Map.Entry<DGNode<T>, Set<DGNode<T>>> e : depends.entrySet()) {
            DGNode<T> next = e.getKey();
            for (DGNode<T> prev : e.getValue()) {
                affects.set(prev._inDegrees, next._inDegrees, true);
            }
        }
        return affects;
    }

    private static final class OrderToken {
        public int level;
        public int order;

        private OrderToken() {
        }

        public String toString() {
            return this.level + ":" + this.order;
        }
    }
}

