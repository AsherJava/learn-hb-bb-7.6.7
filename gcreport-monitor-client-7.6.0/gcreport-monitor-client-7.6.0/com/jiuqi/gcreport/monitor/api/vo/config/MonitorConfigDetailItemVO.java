/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.vo.config;

import java.util.List;

public class MonitorConfigDetailItemVO {
    private String recid;
    private String monitorId;
    private String configId;
    private Integer flag;
    private String nodeStateCode;
    private List<String> gRelationNode;
    private String cNodeNewName;
    private String cNodeColor;

    public String getRecid() {
        return this.recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getMonitorId() {
        return this.monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getConfigId() {
        return this.configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public Integer getFlag() {
        return this.flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getNodeStateCode() {
        return this.nodeStateCode;
    }

    public void setNodeStateCode(String nodeStateCode) {
        this.nodeStateCode = nodeStateCode;
    }

    public List<String> getgRelationNode() {
        return this.gRelationNode;
    }

    public void setgRelationNode(List<String> gRelationNode) {
        this.gRelationNode = gRelationNode;
    }

    public String getcNodeNewName() {
        return this.cNodeNewName;
    }

    public void setcNodeNewName(String cNodeNewName) {
        this.cNodeNewName = cNodeNewName;
    }

    public String getcNodeColor() {
        return this.cNodeColor;
    }

    public void setcNodeColor(String cNodeColor) {
        this.cNodeColor = cNodeColor;
    }
}

