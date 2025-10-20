/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.business.TargetObject
 */
package com.jiuqi.va.workflow.domain;

import com.jiuqi.va.domain.workflow.business.TargetObject;
import java.util.List;

public class BusinessWorkflowNodesInfoSaveProcess {
    private String rsKey = null;
    private int total = 0;
    private int currIndex = 0;
    private List<TargetObject> nodes = null;
    private String failedMessage;

    public String getRsKey() {
        return this.rsKey;
    }

    public void setRsKey(String rsKey) {
        this.rsKey = rsKey;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrIndex() {
        return this.currIndex;
    }

    public void setCurrIndex(int currIndex) {
        this.currIndex = currIndex;
    }

    public List<TargetObject> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<TargetObject> nodes) {
        this.nodes = nodes;
    }

    public String getFailedMessage() {
        return this.failedMessage;
    }

    public void setFailedMessage(String failedMessage) {
        this.failedMessage = failedMessage;
    }
}

