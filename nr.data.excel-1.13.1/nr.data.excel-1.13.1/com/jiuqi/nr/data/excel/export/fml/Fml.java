/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.export.fml;

import com.jiuqi.nr.data.excel.export.fml.FmlNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class Fml {
    private FmlNode assignNode;
    private List<FmlNode> nodes;
    private String expressionKey;
    private Map<String, FmlNode> nodeMap;

    public FmlNode getFmlNodeByDataLink(String dataLinkKey) {
        if (this.nodeMap == null) {
            this.initNodeMap();
        }
        return this.nodeMap.get(dataLinkKey);
    }

    private void initNodeMap() {
        this.nodeMap = new HashMap<String, FmlNode>();
        if (this.assignNode != null) {
            this.nodeMap.put(this.assignNode.getDataLinkKey(), this.assignNode);
        }
        if (!CollectionUtils.isEmpty(this.nodes)) {
            this.nodes.forEach(n -> this.nodeMap.put(n.getDataLinkKey(), (FmlNode)n));
        }
    }

    public FmlNode getAssignNode() {
        return this.assignNode;
    }

    public void setAssignNode(FmlNode assignNode) {
        this.assignNode = assignNode;
    }

    public List<FmlNode> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<FmlNode> nodes) {
        this.nodes = nodes;
    }

    public String getExpressionKey() {
        return this.expressionKey;
    }

    public void setExpressionKey(String expressionKey) {
        this.expressionKey = expressionKey;
    }
}

