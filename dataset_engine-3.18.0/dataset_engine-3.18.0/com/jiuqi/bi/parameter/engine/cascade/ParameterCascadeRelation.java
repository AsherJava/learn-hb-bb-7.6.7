/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.engine.cascade;

import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.cascade.Graph;
import com.jiuqi.bi.parameter.engine.cascade.IGraphNode;
import com.jiuqi.bi.parameter.engine.cascade.ParameterGraphNode;
import com.jiuqi.bi.parameter.model.ParameterModel;
import java.util.ArrayList;
import java.util.List;

public class ParameterCascadeRelation {
    private Graph graph;

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public List<String> getCascadedParameters(String parameterName) {
        ArrayList<String> cascadedParameters = new ArrayList<String>();
        List<IGraphNode> nodeList = this.graph.getNodeList();
        for (IGraphNode iGraphNode : nodeList) {
            ParameterGraphNode parameterGraphNode = (ParameterGraphNode)iGraphNode;
            ParameterModel parameterModel = parameterGraphNode.getParameterModel();
            if (!parameterModel.getName().equalsIgnoreCase(parameterName)) continue;
            List<? extends IGraphNode> pointToNodeList = parameterGraphNode.getPointToNodeList();
            for (ParameterGraphNode parameterGraphNode2 : pointToNodeList) {
                String pointToParamterName = parameterGraphNode2.getParameterModel().getName();
                cascadedParameters.add(pointToParamterName);
            }
        }
        return cascadedParameters;
    }

    public List<String> getCascadeParameters(String parameterName) {
        ArrayList<String> cascadeParameters = new ArrayList<String>();
        List<IGraphNode> nodeList = this.graph.getNodeList();
        for (IGraphNode iGraphNode : nodeList) {
            ParameterGraphNode parameterGraphNode = (ParameterGraphNode)iGraphNode;
            ParameterModel parameterModel = parameterGraphNode.getParameterModel();
            if (!parameterModel.getName().equalsIgnoreCase(parameterName)) continue;
            List<? extends IGraphNode> pointFromNodeList = parameterGraphNode.getPointFromNodeList();
            for (ParameterGraphNode parameterGraphNode2 : pointFromNodeList) {
                if (!parameterGraphNode2.getParameterModel().isNeedCascade()) continue;
                String pointToParamterName = parameterGraphNode2.getParameterModel().getName();
                cascadeParameters.add(pointToParamterName);
            }
        }
        return cascadeParameters;
    }

    public void testLoop() throws ParameterException {
        this.graph.testLoop();
    }

    public void optimize() {
        this.graph.optimize();
    }
}

