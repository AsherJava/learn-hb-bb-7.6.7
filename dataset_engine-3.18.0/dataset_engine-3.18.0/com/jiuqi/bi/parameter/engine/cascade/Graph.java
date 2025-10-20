/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.engine.cascade;

import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.cascade.GraphCycleException;
import com.jiuqi.bi.parameter.engine.cascade.IGraphNode;
import com.jiuqi.bi.parameter.engine.cascade.ParameterGraphNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    private List<IGraphNode> nodeList = new ArrayList<IGraphNode>();
    private boolean needTestSelf = false;

    public int nodeSize() {
        return this.nodeList.size();
    }

    public void addNode(IGraphNode node) {
        if (this.nodeList.contains(node)) {
            return;
        }
        this.nodeList.add(node);
    }

    public IGraphNode getNode(int index) {
        if (index < 0 || index > this.nodeList.size()) {
            return null;
        }
        return this.nodeList.get(index);
    }

    public int nodeIndex(IGraphNode node) {
        if (node == null) {
            return -1;
        }
        return this.nodeList.indexOf(node);
    }

    public void packNodes() {
        boolean loop = false;
        for (int i = 0; i < this.nodeSize(); ++i) {
            IGraphNode node = this.getNode(i);
            if (!node.canUnion()) {
                return;
            }
            HashSet<IGraphNode> route = new HashSet<IGraphNode>();
            try {
                this.testLoop(node, route);
                continue;
            }
            catch (GraphCycleException e) {
                loop = true;
                this.unionNodes(route);
                break;
            }
        }
        if (loop) {
            this.packNodes();
        }
    }

    public void testLoop() throws ParameterException {
        for (int i = 0; i < this.nodeSize(); ++i) {
            IGraphNode node = this.getNode(i);
            HashSet<IGraphNode> route = new HashSet<IGraphNode>();
            try {
                this.testLoop(node, route);
                continue;
            }
            catch (GraphCycleException e) {
                if (!(node instanceof ParameterGraphNode)) continue;
                ParameterGraphNode parameterGraphNode = (ParameterGraphNode)node;
                String parameterName = parameterGraphNode.getParameterModel().getName();
                throw new ParameterException("\u53c2\u6570[" + parameterName + "]\u548c\u5176\u5b83\u53c2\u6570\u4e4b\u95f4\u5b58\u5728\u5faa\u73af\u4f9d\u8d56\uff01");
            }
        }
    }

    public void setNeedTestSelf() {
        this.needTestSelf = true;
    }

    public List<IGraphNode> topologicalSort() {
        boolean allPoped;
        ArrayList<IGraphNode> ret = new ArrayList<IGraphNode>();
        int size = this.nodeSize();
        boolean[][] relationMap = new boolean[size][size];
        boolean[] poped = new boolean[size];
        for (int i = 0; i < size; ++i) {
            IGraphNode node = this.getNode(i);
            List<? extends IGraphNode> pointToNodeList = node.getPointToNodeList();
            for (int j = 0; j < pointToNodeList.size(); ++j) {
                IGraphNode pointToNode = pointToNodeList.get(j);
                int pointToNodeIndex = this.nodeIndex(pointToNode);
                relationMap[i][pointToNodeIndex] = true;
            }
        }
        boolean bl = allPoped = size == 0;
        while (!allPoped) {
            int i;
            for (i = 0; i < relationMap.length; ++i) {
                if (poped[i]) continue;
                boolean[] iRelationMap = relationMap[i];
                boolean allNoRelation = true;
                for (int j = 0; j < iRelationMap.length; ++j) {
                    if (!this.needTestSelf && i == j || poped[j] || !iRelationMap[j]) continue;
                    allNoRelation = false;
                    break;
                }
                if (!allNoRelation) continue;
                IGraphNode node = this.getNode(i);
                ret.add(node);
                poped[i] = true;
            }
            for (i = 0; i < poped.length; ++i) {
                allPoped = i == 0 ? poped[0] : allPoped && poped[i];
            }
        }
        return ret;
    }

    private void unionNodes(Set<IGraphNode> route) {
        if (route == null || route.isEmpty()) {
            return;
        }
        IGraphNode[] nodeArray = route.toArray(new IGraphNode[route.size()]);
        IGraphNode mainNode = nodeArray[0];
        if (nodeArray.length == 1) {
            nodeArray[0].unPointFrom(nodeArray[0]);
            nodeArray[0].unPointTo(nodeArray[0]);
            return;
        }
        if (nodeArray.length > 1) {
            for (int i = 1; i < nodeArray.length; ++i) {
                if (mainNode.equals(nodeArray[i])) continue;
                mainNode.union(nodeArray[i]);
                this.nodeList.remove(nodeArray[i]);
            }
        }
    }

    private void testLoop(IGraphNode node, Set<IGraphNode> route) throws GraphCycleException {
        if (node == null || route == null) {
            return;
        }
        if (route.contains(node)) {
            throw new GraphCycleException();
        }
        List<? extends IGraphNode> pointToNodeList = node.getPointToNodeList();
        if (pointToNodeList == null || pointToNodeList.size() == 0) {
            return;
        }
        route.add(node);
        for (int i = 0; i < pointToNodeList.size(); ++i) {
            IGraphNode pointToNode = pointToNodeList.get(i);
            if (!this.needTestSelf && pointToNode.equals(node)) continue;
            this.testLoop(pointToNode, route);
        }
        route.remove(node);
    }

    public List<IGraphNode> getNodeList() {
        return this.nodeList;
    }

    public void optimize() {
    }
}

