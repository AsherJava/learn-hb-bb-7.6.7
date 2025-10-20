/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.domain;

import java.io.Serializable;
import java.util.Objects;

public class FlowTransferInfo
implements Serializable {
    private String nodeId;
    private String nodeName;
    private String transferTitle;
    private String nextNodeId;
    private String nextNodeName;
    private String nextNodeType;
    private String transferCondition;
    private String nodeType;
    private String participantStrategy;
    private String participantStrategyCfg;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FlowTransferInfo that = (FlowTransferInfo)o;
        return Objects.equals(this.nodeId, that.nodeId) && Objects.equals(this.nextNodeId, that.nextNodeId);
    }

    public int hashCode() {
        return Objects.hash(this.nodeId, this.nextNodeId);
    }

    public String getNextNodeType() {
        return this.nextNodeType;
    }

    public void setNextNodeType(String nextNodeType) {
        this.nextNodeType = nextNodeType;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNextNodeId() {
        return this.nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public String getParticipantStrategy() {
        return this.participantStrategy;
    }

    public void setParticipantStrategy(String participantStrategy) {
        this.participantStrategy = participantStrategy;
    }

    public String getParticipantStrategyCfg() {
        return this.participantStrategyCfg;
    }

    public void setParticipantStrategyCfg(String participantStrategyCfg) {
        this.participantStrategyCfg = participantStrategyCfg;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getTransferTitle() {
        return this.transferTitle;
    }

    public void setTransferTitle(String transferTitle) {
        this.transferTitle = transferTitle;
    }

    public String getNextNodeName() {
        return this.nextNodeName;
    }

    public void setNextNodeName(String nextNodeName) {
        this.nextNodeName = nextNodeName;
    }

    public String getTransferCondition() {
        return this.transferCondition;
    }

    public void setTransferCondition(String transferCondition) {
        this.transferCondition = transferCondition;
    }
}

