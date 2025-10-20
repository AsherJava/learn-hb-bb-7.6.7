/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.topo.Node
 */
package com.jiuqi.bi.parameter.engine.cascade;

import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.engine.cascade.Graph;
import com.jiuqi.bi.parameter.engine.cascade.IGraphNode;
import com.jiuqi.bi.parameter.engine.cascade.MetaBuffer;
import com.jiuqi.bi.parameter.engine.cascade.ParameterCascadeRelation;
import com.jiuqi.bi.parameter.engine.cascade.ParameterGraphNode;
import com.jiuqi.bi.parameter.manager.DataSourceFactoryManager;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactory;
import com.jiuqi.bi.parameter.manager.ICascadeAnalyzer;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.util.topo.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterCascadeEngine {
    private ParameterEngineEnv env;
    private Map<ParameterModel, ParameterGraphNode> paraModelAndGraphNodeMap = new HashMap<ParameterModel, ParameterGraphNode>();

    public ParameterCascadeEngine(ParameterEngineEnv parameterEngineEnv) {
        this.env = parameterEngineEnv;
    }

    public ParameterCascadeRelation buildParameterCascadeRelation() throws ParameterException {
        ParameterCascadeRelation relation = new ParameterCascadeRelation();
        Graph graph = this.createParameterGraph();
        relation.setGraph(graph);
        return relation;
    }

    public List<String> computeParameterGopology() throws ParameterException {
        boolean hasHeadNode;
        ParameterGraphNode pgn;
        Graph graph = this.createParameterGraph();
        List<IGraphNode> gnodelist = graph.getNodeList();
        ArrayList<Node> nodes = new ArrayList<Node>(gnodelist.size());
        HashMap<String, Node> map = new HashMap<String, Node>();
        for (IGraphNode iGraphNode : gnodelist) {
            pgn = (ParameterGraphNode)iGraphNode;
            String pname = pgn.getParameterModel().getName();
            Node node = (Node)map.get(pname);
            if (node != null) continue;
            Node node2 = new Node((Object)iGraphNode);
            nodes.add(node2);
            map.put(pname, node2);
        }
        for (Node node : nodes) {
            pgn = (ParameterGraphNode)node.getBindingData();
            List<? extends IGraphNode> pointFrom = pgn.getPointFromNodeList();
            for (IGraphNode iGraphNode : pointFrom) {
                ParameterGraphNode graphNode = (ParameterGraphNode)iGraphNode;
                Node node3 = (Node)map.get(graphNode.getParameterModel().getName());
                if (node3 == null || node.getPrevNodes().indexOf(node3) != -1) continue;
                node.getPrevNodes().add(node3);
            }
            List<? extends IGraphNode> list = pgn.getPointToNodeList();
            for (IGraphNode gn3 : list) {
                ParameterGraphNode graphNode = (ParameterGraphNode)gn3;
                Node node4 = (Node)map.get(graphNode.getParameterModel().getName());
                if (node4 == null || node.getNextNodes().indexOf(node4) != -1) continue;
                node.getNextNodes().add(node4);
            }
        }
        ArrayList ordered = new ArrayList();
        ArrayList<Node> arrayList = nodes;
        do {
            hasHeadNode = false;
            ArrayList<Node> temp = new ArrayList<Node>();
            for (Node node : arrayList) {
                if (node.getPrevNodes().size() != 0) continue;
                temp.add(node);
                hasHeadNode = true;
            }
            for (Node node : temp) {
                for (Node next : node.getNextNodes()) {
                    if (next == null) continue;
                    next.getPrevNodes().remove(node);
                }
                node.getNextNodes().clear();
            }
            arrayList.removeAll(temp);
            ordered.addAll(temp);
        } while (hasHeadNode);
        if (!arrayList.isEmpty()) {
            ordered.addAll(arrayList);
        }
        ArrayList<String> params = new ArrayList<String>();
        for (Node node : ordered) {
            ParameterGraphNode parameterGraphNode = (ParameterGraphNode)node.getBindingData();
            params.add(parameterGraphNode.getParameterModel().getName().toUpperCase());
        }
        return params;
    }

    private Graph createParameterGraph() throws ParameterException {
        Graph graph = new Graph();
        List<IGraphNode> nodeList = graph.getNodeList();
        List<ParameterModel> parameterModels = this.env.getParameterModels();
        if (parameterModels != null && parameterModels.size() != 0) {
            MetaBuffer buffer = new MetaBuffer();
            for (ParameterModel parameterModel : parameterModels) {
                DataSourceModelFactory modelFactory;
                ICascadeAnalyzer cascadeAnalyzer;
                if (!parameterModel.isNeedCascade()) continue;
                ParameterGraphNode graphNode = this.paraModelAndGraphNodeMap.get(parameterModel);
                if (graphNode == null) {
                    graphNode = new ParameterGraphNode();
                    graphNode.setParameterModel(parameterModel);
                    this.paraModelAndGraphNodeMap.put(parameterModel, graphNode);
                }
                List<String> cascadeParameterNames = parameterModel.getCascadeParameters();
                graphNode.pointFrom(this.getParameterModelsByName(cascadeParameterNames, parameterModels, graphNode));
                DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
                if (dataSourceModel != null && (cascadeAnalyzer = (modelFactory = DataSourceFactoryManager.getDataSourceModelFactory(dataSourceModel.getType())).createCascadeAnalyzer(this.env, buffer)) != null) {
                    try {
                        List<String> cascadeParameters = cascadeAnalyzer.analyze(dataSourceModel, this.env, true);
                        graphNode.pointFrom(this.getParameterModelsByName(cascadeParameters, parameterModels, null));
                        List<String> cascadedParameters = cascadeAnalyzer.analyze(dataSourceModel, this.env, false);
                        graphNode.pointTo(this.getParameterModelsByName(cascadedParameters, parameterModels, null));
                    }
                    catch (Exception e) {
                        throw new ParameterException("\u53c2\u6570\u3010" + parameterModel.getName() + "\u3011\u7ea7\u8054\u53c2\u6570\u6709\u8bef\uff0c" + e.getMessage(), e);
                    }
                }
                nodeList.add(graphNode);
            }
        }
        return graph;
    }

    private List<ParameterGraphNode> getParameterModelsByName(List<String> names, List<ParameterModel> parameterModels, ParameterGraphNode pointToGraphNode) {
        ArrayList<ParameterGraphNode> resultParameterModels = new ArrayList<ParameterGraphNode>();
        if (parameterModels != null && parameterModels.size() != 0 && names != null && names.size() != 0) {
            for (ParameterModel parameterModel : parameterModels) {
                if (!names.contains(parameterModel.getName())) continue;
                ParameterGraphNode graphNode = this.paraModelAndGraphNodeMap.get(parameterModel);
                if (graphNode == null) {
                    graphNode = new ParameterGraphNode();
                    this.paraModelAndGraphNodeMap.put(parameterModel, graphNode);
                    graphNode.setParameterModel(parameterModel);
                }
                if (pointToGraphNode != null) {
                    graphNode.pointTo(pointToGraphNode);
                }
                resultParameterModels.add(graphNode);
            }
        }
        return resultParameterModels;
    }
}

