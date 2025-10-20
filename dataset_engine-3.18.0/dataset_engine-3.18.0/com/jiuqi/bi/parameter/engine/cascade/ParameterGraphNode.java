/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.engine.cascade;

import com.jiuqi.bi.parameter.engine.cascade.IGraphNode;
import com.jiuqi.bi.parameter.model.ParameterModel;
import java.util.ArrayList;
import java.util.List;

public class ParameterGraphNode
implements IGraphNode {
    private ParameterModel parameterModel;
    private List<ParameterGraphNode> pointToNodeList = new ArrayList<ParameterGraphNode>();
    private List<ParameterGraphNode> pointFromNodeList = new ArrayList<ParameterGraphNode>();

    @Override
    public boolean canUnion() {
        return false;
    }

    @Override
    public List<? extends IGraphNode> getPointFromNodeList() {
        return this.pointFromNodeList;
    }

    @Override
    public List<? extends IGraphNode> getPointToNodeList() {
        return this.pointToNodeList;
    }

    @Override
    public void pointFrom(IGraphNode node) {
        if (node == null || !(node instanceof ParameterGraphNode) || this.pointFromNodeList.contains(node)) {
            return;
        }
        this.pointFromNodeList.add((ParameterGraphNode)node);
    }

    @Override
    public void pointFrom(List<? extends IGraphNode> nodeList) {
        if (nodeList == null || nodeList.size() == 0) {
            return;
        }
        this.pointFromNodeList.addAll(nodeList);
    }

    @Override
    public void pointTo(IGraphNode node) {
        if (node == null || !(node instanceof ParameterGraphNode) || this.pointToNodeList.contains(node)) {
            return;
        }
        this.pointToNodeList.add((ParameterGraphNode)node);
    }

    @Override
    public void pointTo(List<? extends IGraphNode> nodeList) {
        if (nodeList == null || nodeList.size() == 0) {
            return;
        }
        this.pointToNodeList.addAll(nodeList);
    }

    @Override
    public void unPointFrom(IGraphNode node) {
        if (this.pointFromNodeList == null || this.pointFromNodeList.size() == 0) {
            return;
        }
        this.pointFromNodeList.remove(node);
    }

    @Override
    public void unPointTo(IGraphNode node) {
        if (this.pointToNodeList == null || this.pointToNodeList.size() == 0) {
            return;
        }
        this.pointToNodeList.remove(node);
    }

    @Override
    public void union(IGraphNode node) {
        throw new RuntimeException("\u8be5\u7c7b\u578b\u7684\u56fe\u8282\u70b9\u4e0d\u652f\u6301\u5408\u5e76");
    }

    public ParameterModel getParameterModel() {
        return this.parameterModel;
    }

    public void setParameterModel(ParameterModel parameterModel) {
        this.parameterModel = parameterModel;
    }
}

