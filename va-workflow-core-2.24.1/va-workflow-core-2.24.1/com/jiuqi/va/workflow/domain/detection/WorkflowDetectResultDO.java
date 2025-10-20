/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.domain.detection;

import java.io.Serializable;
import java.util.Set;

public class WorkflowDetectResultDO
implements Serializable {
    private String NodeName;
    private String nodeType;
    private Set<String> participants;
    private int status;
    private String msg;
    private String content;

    public String getNodeName() {
        return this.NodeName;
    }

    public void setNodeName(String nodeName) {
        this.NodeName = nodeName;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Set<String> getParticipants() {
        return this.participants;
    }

    public void setParticipants(Set<String> participants) {
        this.participants = participants;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

